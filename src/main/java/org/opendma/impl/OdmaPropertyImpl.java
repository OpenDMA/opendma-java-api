package org.opendma.impl;

import org.opendma.api.OdmaType;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaProperty;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaServiceException;
import java.util.List;
import java.util.Date;
import org.opendma.api.OdmaObject;
import org.opendma.api.OdmaContent;
import org.opendma.api.OdmaId;
import org.opendma.api.OdmaGuid;

/**
 * Standard implementation of the OdmaProperty interface.
 */
public class OdmaPropertyImpl implements OdmaProperty {
    
    /**
     * Interface for lazy property resolution.
     *
     */
    public interface OdmaLazyPropertyValueProvider {
        
        /**
         * Resolves the value of the property when accessed.
         *
         */
        Object resovlePropertyValue();
        
    }
    
    /** the name of this property */
    protected OdmaQName name;
    
    /** the data type of this property */
    protected OdmaType dataType;
    
    /** flag indicating if this property is multivalue or not */
    protected boolean multiValue;
    
    /** the value of this property */
    protected Object value;
    
    /** property value provider for lazy property resolution */
    protected OdmaLazyPropertyValueProvider valueProvider;
    
    /** flag indicating if the value of this property has changed */
    protected boolean dirty;
    
    /** flag indicating if this property is read only or not */
    protected boolean readOnly;
    
    /**
     * Create a new <code>OdmaPropertyImpl</code> with the given data.
     * 
     * @param name
     *     The name of this property.
     *     
     * @param value
     *     The value of this property.
     *     
     * @param valueProvider
     *     The provider of the propertie's value for lazy resolution.
     *     
     * @param dataType
     *     The data type of this property
     *     
     * @param multiValue
     *     Flag if this property is a multi value property
     *     
     * @param readOnly
     *     Flag if this property is read only.
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if the Class of the given Object does not match
     *             the data type of this property
     */
    protected OdmaPropertyImpl(OdmaQName name, Object value, OdmaLazyPropertyValueProvider valueProvider, OdmaType dataType, boolean multiValue, boolean readOnly) throws OdmaInvalidDataTypeException {
        this.name = name;
        this.dataType = dataType;
        this.multiValue = multiValue;
        this.readOnly = readOnly;
        if(valueProvider != null) {
            if(value != null) {
                throw new IllegalArgumentException("If a value provider is given, the value must be null.");
            }
            this.valueProvider = valueProvider;
        } else {
            setValueInternal(value);
            this.dirty = false;
        }
    }
    
    /**
     * Create a new <code>OdmaPropertyImpl</code> with the given value.
     * 
     * @param name
     *     The name of this property.
     *     
     * @param value
     *     The value of this property.
     *     
     * @param dataType
     *     The data type of this property
     *     
     * @param multiValue
     *     Flag if this property is a multi value property
     *     
     * @param readOnly
     *     Flag if this property is read only.
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if the Class of the given Object does not match
     *             the data type of this property
     */
    public static OdmaPropertyImpl fromValue(OdmaQName name, Object value, OdmaType dataType, boolean multiValue, boolean readOnly) throws OdmaInvalidDataTypeException {
        return new OdmaPropertyImpl(name, value, null, dataType, multiValue, readOnly);
    }
    
    /**
     * Create a new lazily resolved <code>OdmaPropertyImpl</code> from the given <code>OdmaLazyPropertyValueProvider</code>.
     * 
     * @param name
     *     The name of this property.
     *     
     * @param valueProvider
     *     The provider of the propertie's value for lazy resolution.
     *     
     * @param dataType
     *     The data type of this property
     *     
     * @param multiValue
     *     Flag if this property is a multi value property
     *     
     * @param readOnly
     *     Flag if this property is read only.
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if the Class of the given Object does not match
     *             the data type of this property
     */
    public static OdmaPropertyImpl fromValueProvider(OdmaQName name, OdmaLazyPropertyValueProvider valueProvider, OdmaType dataType, boolean multiValue, boolean readOnly) throws OdmaInvalidDataTypeException {
        return new OdmaPropertyImpl(name, null, valueProvider, dataType, multiValue, readOnly);
    }

    /**
     * Returns the qualified name of this property.
     * 
     * @return the qualified name of this property.
     */
    public OdmaQName getName() {
        return name;
    }

    /**
     * Returns the numeric identifier of the data type of this property.<br>
     * You can find a list of all data types in the <code>OdmaTypes</code>
     * class.
     * 
     * @return the numeric identifier of the data type of this property.
     * 
     * @see org.opendma.OdmaTypes
     */
    public OdmaType getType() {
        return dataType;
    }

    protected void enforceValue() {
        if(valueProvider != null) {
            try {
                setValueInternal(valueProvider.resovlePropertyValue());
                dirty = false;
                valueProvider = null;
            } catch (OdmaInvalidDataTypeException e) {
                throw new OdmaServiceException("Lazy property resolution failed. Provider delivered wrong type or cardinality.", e);
            }
        }
    }

    /**
     * Returns the value of this property.<br>
     * The concrete <code>Object</code> returned by this method depends on the
     * data type of this property.
     * 
     * @return the value of this property.
     */
    public Object getValue() {
        enforceValue();
        return value;
    }

    /**
     * Returns <code>true</code> if and only if this property has been changed and these
     * changes have not yet been saved.
     * 
     * @return <code>true</code> true if and only if this property has been changed and these
     *         changes have not yet been saved
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Returns <code>true</code> if and only if this property is a multi value property.
     * 
     * @return <code>true</code> if and only if this property is a multi value property.
     */
    public boolean isMultiValue() {
        return multiValue;
    }

    /**
     * Returns <code>true</code> if and only if this property must not be changed.
     * 
     * @return <code>true</code> if and only if this property must not be changed.
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Indicates if the value of this property is immediately available can be read without a round-trip to a back-end system.
     * 
     * @return <code>true</code> if the value is immediately available, <code>false</code> if reading this value requires a round-trip to a back-end system.
     */
    public boolean isResolved() {
        return valueProvider == null;
    }

    private boolean checkListAndValues(Object obj, Class<?> expectedElementsClass) {
        if(!(obj instanceof List<?>)) {
            return false;
        }
        for(Object element : (List<?>)obj) {
            if(!expectedElementsClass.isAssignableFrom(element.getClass())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Set the value of this property to the given new value. The
     * <code>Class</code> of the given <code>Object</code> has to match the
     * data type of this property.
     * 
     * @param newValue
     *            the new value to set this property to.
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if the Class of the given Object does not match
     *             the data type of this property
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setValue(Object newValue) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        if(readOnly)
        {
            throw new OdmaAccessDeniedException();
        }
        setValueInternal(newValue);
        valueProvider = null;
    }
    
    public void setValueInternal(Object newValue) throws OdmaInvalidDataTypeException {
        if(newValue == null)
        {
            if(multiValue)
            {
                throw new OdmaInvalidDataTypeException("Multi-valued properties must not be `null`. If a value is not required, the collection can be empty.");
            }
            value = null;
            dirty = true;
            return;
        }
        if(multiValue)
        {
            switch(dataType) {
            case STRING:
                if(checkListAndValues(newValue,String.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued String data type. It can only be set to values assignable to `List<String>`");
                }
                break;
            case INTEGER:
                if(checkListAndValues(newValue,Integer.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Integer data type. It can only be set to values assignable to `List<Integer>`");
                }
                break;
            case SHORT:
                if(checkListAndValues(newValue,Short.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Short data type. It can only be set to values assignable to `List<Short>`");
                }
                break;
            case LONG:
                if(checkListAndValues(newValue,Long.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Long data type. It can only be set to values assignable to `List<Long>`");
                }
                break;
            case FLOAT:
                if(checkListAndValues(newValue,Float.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Float data type. It can only be set to values assignable to `List<Float>`");
                }
                break;
            case DOUBLE:
                if(checkListAndValues(newValue,Double.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Double data type. It can only be set to values assignable to `List<Double>`");
                }
                break;
            case BOOLEAN:
                if(checkListAndValues(newValue,Boolean.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Boolean data type. It can only be set to values assignable to `List<Boolean>`");
                }
                break;
            case DATETIME:
                if(checkListAndValues(newValue,Date.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued DateTime data type. It can only be set to values assignable to `List<Date>`");
                }
                break;
            case BLOB:
                if(checkListAndValues(newValue,byte[].class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Blob data type. It can only be set to values assignable to `List<byte[]>`");
                }
                break;
            case REFERENCE:
                if(newValue instanceof Iterable<?>) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Reference data type. It can only be set to values assignable to `Iterable<OdmaObject>`");
                }
                break;
            case CONTENT:
                if(checkListAndValues(newValue,OdmaContent.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Content data type. It can only be set to values assignable to `List<OdmaContent>`");
                }
                break;
            case ID:
                if(checkListAndValues(newValue,OdmaId.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Id data type. It can only be set to values assignable to `List<OdmaId>`");
                }
                break;
            case GUID:
                if(checkListAndValues(newValue,OdmaGuid.class)) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a multi-valued Guid data type. It can only be set to values assignable to `List<OdmaGuid>`");
                }
                break;
            default:
                throw new OdmaRuntimeException("OdmaProperty initialized with unknown data type "+dataType);
            }
        }
        else
        {
            switch(dataType) {
            case STRING:
                if(newValue instanceof String) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued String data type. It can only be set to values assignable to `String`");
                }
                break;
            case INTEGER:
                if(newValue instanceof Integer) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Integer data type. It can only be set to values assignable to `Integer`");
                }
                break;
            case SHORT:
                if(newValue instanceof Short) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Short data type. It can only be set to values assignable to `Short`");
                }
                break;
            case LONG:
                if(newValue instanceof Long) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Long data type. It can only be set to values assignable to `Long`");
                }
                break;
            case FLOAT:
                if(newValue instanceof Float) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Float data type. It can only be set to values assignable to `Float`");
                }
                break;
            case DOUBLE:
                if(newValue instanceof Double) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Double data type. It can only be set to values assignable to `Double`");
                }
                break;
            case BOOLEAN:
                if(newValue instanceof Boolean) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Boolean data type. It can only be set to values assignable to `Boolean`");
                }
                break;
            case DATETIME:
                if(newValue instanceof Date) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued DateTime data type. It can only be set to values assignable to `Date`");
                }
                break;
            case BLOB:
                if(newValue instanceof byte[]) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Blob data type. It can only be set to values assignable to `byte[]`");
                }
                break;
            case REFERENCE:
                if(newValue instanceof OdmaObject) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Reference data type. It can only be set to values assignable to `OdmaObject`");
                }
                break;
            case CONTENT:
                if(newValue instanceof OdmaContent) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Content data type. It can only be set to values assignable to `OdmaContent`");
                }
                break;
            case ID:
                if(newValue instanceof OdmaId) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Id data type. It can only be set to values assignable to `OdmaId`");
                }
                break;
            case GUID:
                if(newValue instanceof OdmaGuid) {
                    value = newValue;
                } else {
                    throw new OdmaInvalidDataTypeException("This property has a single-valued Guid data type. It can only be set to values assignable to `OdmaGuid`");
                }
                break;
            default:
                throw new OdmaRuntimeException("OdmaProperty initialized with unknown data type "+dataType);
            }
        }
        dirty = true;
    }

    /**
     * Returns the <code>String</code> value of this property if and only if
     * the data type of this property is a single valued <i>String</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>String</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>String</i>
     *             property
     */
    public String getString() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.STRING) ) {
            enforceValue();
            return (String)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getString()`");
        }
    }

    /**
     * Returns the <code>Integer</code> value of this property if and only if
     * the data type of this property is a single valued <i>Integer</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Integer</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Integer</i>
     *             property
     */
    public Integer getInteger() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.INTEGER) ) {
            enforceValue();
            return (Integer)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getInteger()`");
        }
    }

    /**
     * Returns the <code>Short</code> value of this property if and only if
     * the data type of this property is a single valued <i>Short</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Short</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Short</i>
     *             property
     */
    public Short getShort() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.SHORT) ) {
            enforceValue();
            return (Short)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getShort()`");
        }
    }

    /**
     * Returns the <code>Long</code> value of this property if and only if
     * the data type of this property is a single valued <i>Long</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Long</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Long</i>
     *             property
     */
    public Long getLong() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.LONG) ) {
            enforceValue();
            return (Long)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getLong()`");
        }
    }

    /**
     * Returns the <code>Float</code> value of this property if and only if
     * the data type of this property is a single valued <i>Float</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Float</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Float</i>
     *             property
     */
    public Float getFloat() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.FLOAT) ) {
            enforceValue();
            return (Float)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getFloat()`");
        }
    }

    /**
     * Returns the <code>Double</code> value of this property if and only if
     * the data type of this property is a single valued <i>Double</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Double</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Double</i>
     *             property
     */
    public Double getDouble() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.DOUBLE) ) {
            enforceValue();
            return (Double)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getDouble()`");
        }
    }

    /**
     * Returns the <code>Boolean</code> value of this property if and only if
     * the data type of this property is a single valued <i>Boolean</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Boolean</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Boolean</i>
     *             property
     */
    public Boolean getBoolean() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.BOOLEAN) ) {
            enforceValue();
            return (Boolean)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getBoolean()`");
        }
    }

    /**
     * Returns the <code>DateTime</code> value of this property if and only if
     * the data type of this property is a single valued <i>DateTime</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>DateTime</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>DateTime</i>
     *             property
     */
    public Date getDateTime() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.DATETIME) ) {
            enforceValue();
            return (Date)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getDateTime()`");
        }
    }

    /**
     * Returns the <code>Blob</code> value of this property if and only if
     * the data type of this property is a single valued <i>Blob</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Blob</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Blob</i>
     *             property
     */
    public byte[] getBlob() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.BLOB) ) {
            enforceValue();
            return (byte[])value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getBlob()`");
        }
    }

    /**
     * Returns the <code>Reference</code> value of this property if and only if
     * the data type of this property is a single valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Reference</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Reference</i>
     *             property
     */
    public OdmaObject getReference() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.REFERENCE) ) {
            enforceValue();
            return (OdmaObject)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getReference()`");
        }
    }

    /**
     * Returns the <code>Content</code> value of this property if and only if
     * the data type of this property is a single valued <i>Content</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Content</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Content</i>
     *             property
     */
    public OdmaContent getContent() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.CONTENT) ) {
            enforceValue();
            return (OdmaContent)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getContent()`");
        }
    }

    /**
     * Returns the <code>Id</code> value of this property if and only if
     * the data type of this property is a single valued <i>Id</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Id</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Id</i>
     *             property
     */
    public OdmaId getId() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.ID) ) {
            enforceValue();
            return (OdmaId)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getId()`");
        }
    }

    /**
     * Returns the <code>Guid</code> value of this property if and only if
     * the data type of this property is a single valued <i>Guid</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Guid</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Guid</i>
     *             property
     */
    public OdmaGuid getGuid() throws OdmaInvalidDataTypeException {
        if( (multiValue == false) && (dataType == OdmaType.GUID) ) {
            enforceValue();
            return (OdmaGuid)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getGuid()`");
        }
    }

    /**
     * Returns the <code>String</code> value of this property if and only if
     * the data type of this property is a multi valued <i>String</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>String</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>String</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<String> getStringList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.STRING) ) {
            enforceValue();
            return (List<String>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getStringList()`");
        }
    }

    /**
     * Returns the <code>Integer</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Integer</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Integer</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Integer</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getIntegerList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.INTEGER) ) {
            enforceValue();
            return (List<Integer>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getIntegerList()`");
        }
    }

    /**
     * Returns the <code>Short</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Short</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Short</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Short</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Short> getShortList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.SHORT) ) {
            enforceValue();
            return (List<Short>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getShortList()`");
        }
    }

    /**
     * Returns the <code>Long</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Long</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Long</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Long</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Long> getLongList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.LONG) ) {
            enforceValue();
            return (List<Long>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getLongList()`");
        }
    }

    /**
     * Returns the <code>Float</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Float</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Float</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Float</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Float> getFloatList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.FLOAT) ) {
            enforceValue();
            return (List<Float>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getFloatList()`");
        }
    }

    /**
     * Returns the <code>Double</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Double</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Double</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Double</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Double> getDoubleList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.DOUBLE) ) {
            enforceValue();
            return (List<Double>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getDoubleList()`");
        }
    }

    /**
     * Returns the <code>Boolean</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Boolean</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Boolean</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Boolean</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Boolean> getBooleanList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.BOOLEAN) ) {
            enforceValue();
            return (List<Boolean>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getBooleanList()`");
        }
    }

    /**
     * Returns the <code>DateTime</code> value of this property if and only if
     * the data type of this property is a multi valued <i>DateTime</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>DateTime</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>DateTime</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Date> getDateTimeList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.DATETIME) ) {
            enforceValue();
            return (List<Date>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getDateTimeList()`");
        }
    }

    /**
     * Returns the <code>Blob</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Blob</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Blob</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Blob</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<byte[]> getBlobList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.BLOB) ) {
            enforceValue();
            return (List<byte[]>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getBlobList()`");
        }
    }

    /**
     * Returns the <code>Reference</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Reference</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Reference</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public Iterable<? extends OdmaObject> getReferenceIterable() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.REFERENCE) ) {
            enforceValue();
            return (Iterable<? extends OdmaObject>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getReferenceIterable()`");
        }
    }

    /**
     * Returns the <code>Content</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Content</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Content</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Content</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<OdmaContent> getContentList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.CONTENT) ) {
            enforceValue();
            return (List<OdmaContent>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getContentList()`");
        }
    }

    /**
     * Returns the <code>Id</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Id</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Id</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Id</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<OdmaId> getIdList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.ID) ) {
            enforceValue();
            return (List<OdmaId>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getIdList()`");
        }
    }

    /**
     * Returns the <code>Guid</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Guid</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Guid</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Guid</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<OdmaGuid> getGuidList() throws OdmaInvalidDataTypeException {
        if( (multiValue == true) && (dataType == OdmaType.GUID) ) {
            enforceValue();
            return (List<OdmaGuid>)value;
        } else {
            throw new OdmaInvalidDataTypeException("This property has a different data type and/or cardinality. It cannot return values with `getGuidList()`");
        }
    }

}
