package org.opendma.impl;

import org.opendma.api.OdmaType;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaProperty;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaRuntimeException;
import java.util.List;
import java.util.Date;
import org.opendma.api.OdmaObject;
import org.opendma.api.OdmaContent;
import org.opendma.api.OdmaId;
import org.opendma.api.OdmaGuid;

/**
 * Description follows
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class OdmaPropertyImpl implements OdmaProperty
{
    
    /** the name of this property */
    protected OdmaQName name;
    
    /** the data type of this property */
    protected OdmaType dataType;
    
    /** flag indicating if this property is multivalue or not */
    protected boolean multivalue;
    
    /** the value of this property */
    protected Object value;
    
    /** flag indicating if the value of this property has changed */
    protected boolean dirty;
    
    /** flag indicating if this property is read only or not */
    protected boolean readonly;
    
    /**
     * Create a new <code>OdmaPropertyImpl</code> with the given data.
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
     * @param multivalue
     *     Flag if this property is a multi value property
     *     
     * @param readonly
     *     Flag if this property is read only.
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if the Class of the given Object does not match
     *             the data type of this property
     */
    public OdmaPropertyImpl(OdmaQName name, Object value, OdmaType dataType, boolean multivalue, boolean readonly) throws OdmaInvalidDataTypeException
    {
        this.name = name;
        this.dataType = dataType;
        this.multivalue = multivalue;
        try
        {
            setValue(value);
        }
        catch(OdmaAccessDeniedException e)
        {
            throw new OdmaRuntimeException("Implementation error.");
        }
        this.readonly = readonly;
        this.dirty = false;
    }

    /**
     * Returns the qualified name of this property.
     * 
     * @return the qualified name of this property.
     */
    public OdmaQName getName()
    {
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
    public OdmaType getType()
    {
        return dataType;
    }

    /**
     * Returns the value of this property.<br>
     * The concrete <code>Object</code> returned by this method depends on the
     * data type of this property.
     * 
     * @return the value of this property.
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * Returns <code>true</code> if and only if this property has been changed and these
     * changes have not yet been saved.
     * 
     * @return <code>true</code> true if and only if this property has been changed and these
     *         changes have not yet been saved
     */
    public boolean isDirty()
    {
        return dirty;
    }

    /**
     * Mark this property as dirty meaning that the value has been changed and not yet persisted.
     */
    public void setDirty()
    {
        dirty = true;
    }

    /**
     * Returns <code>true</code> if and only if this property is a multi value property.
     * 
     * @return <code>true</code> if and only if this property is a multi value property.
     */
    public boolean isMultiValue()
    {
        return multivalue;
    }

    /**
     * Returns <code>true</code> if and only if this property must not be changed.
     * 
     * @return <code>true</code> if and only if this property must not be changed.
     */
    public boolean isReadOnly()
    {
        return readonly;
    }

    private boolean checkListAndValues(Object obj, Class<?> expectedElementsClass)
    {
        if(!(obj instanceof List<?>))
        {
            return false;
        }
        for(Object element : (List<?>)obj)
        {
            if(!expectedElementsClass.isAssignableFrom(element.getClass()))
            {
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
    public void setValue(Object newValue) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        if(readonly)
        {
            throw new OdmaAccessDeniedException();
        }
        if(newValue == null)
        {
            value = null;
            return;
        }
        if(multivalue)
        {
            switch(dataType)
            {
            case STRING:
                if(checkListAndValues(newValue,String.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case INTEGER:
                if(checkListAndValues(newValue,Integer.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case SHORT:
                if(checkListAndValues(newValue,Short.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case LONG:
                if(checkListAndValues(newValue,Long.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case FLOAT:
                if(checkListAndValues(newValue,Float.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case DOUBLE:
                if(checkListAndValues(newValue,Double.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case BOOLEAN:
                if(checkListAndValues(newValue,Boolean.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case DATETIME:
                if(checkListAndValues(newValue,Date.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case BLOB:
                if(checkListAndValues(newValue,byte[].class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case REFERENCE:
                if(newValue instanceof Iterable<?>)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case CONTENT:
                if(checkListAndValues(newValue,OdmaContent.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case ID:
                if(checkListAndValues(newValue,OdmaId.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case GUID:
                if(checkListAndValues(newValue,OdmaGuid.class))
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            default:
                throw new OdmaRuntimeException("OdmaProperty initialized with unknown data type "+dataType);
            }
        }
        else
        {
            switch(dataType)
            {
            case STRING:
                if(newValue instanceof String)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case INTEGER:
                if(newValue instanceof Integer)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case SHORT:
                if(newValue instanceof Short)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case LONG:
                if(newValue instanceof Long)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case FLOAT:
                if(newValue instanceof Float)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case DOUBLE:
                if(newValue instanceof Double)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case BOOLEAN:
                if(newValue instanceof Boolean)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case DATETIME:
                if(newValue instanceof Date)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case BLOB:
                if(newValue instanceof byte[])
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case REFERENCE:
                if(newValue instanceof OdmaObject)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case CONTENT:
                if(newValue instanceof OdmaContent)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case ID:
                if(newValue instanceof OdmaId)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case GUID:
                if(newValue instanceof OdmaGuid)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
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
    public String getString() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.STRING) )
        {
            return (String)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.STRING,false,dataType,multivalue);
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
    public Integer getInteger() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.INTEGER) )
        {
            return (Integer)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.INTEGER,false,dataType,multivalue);
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
    public Short getShort() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.SHORT) )
        {
            return (Short)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.SHORT,false,dataType,multivalue);
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
    public Long getLong() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.LONG) )
        {
            return (Long)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.LONG,false,dataType,multivalue);
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
    public Float getFloat() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.FLOAT) )
        {
            return (Float)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.FLOAT,false,dataType,multivalue);
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
    public Double getDouble() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.DOUBLE) )
        {
            return (Double)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.DOUBLE,false,dataType,multivalue);
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
    public Boolean getBoolean() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.BOOLEAN) )
        {
            return (Boolean)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.BOOLEAN,false,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>Date</code> value of this property if and only if
     * the data type of this property is a single valued <i>DateTime</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Date</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>DateTime</i>
     *             property
     */
    public Date getDateTime() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.DATETIME) )
        {
            return (Date)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.DATETIME,false,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>byte[]</code> value of this property if and only if
     * the data type of this property is a single valued <i>Blob</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>byte[]</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Blob</i>
     *             property
     */
    public byte[] getBlob() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.BLOB) )
        {
            return (byte[])value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.BLOB,false,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>OdmaObject</code> value of this property if and only if
     * the data type of this property is a single valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaObject</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Reference</i>
     *             property
     */
    public OdmaObject getReference() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.REFERENCE) )
        {
            return (OdmaObject)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.REFERENCE,false,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>OdmaContent</code> value of this property if and only if
     * the data type of this property is a single valued <i>Content</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaContent</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Content</i>
     *             property
     */
    public OdmaContent getContent() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.CONTENT) )
        {
            return (OdmaContent)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.CONTENT,false,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>OdmaId</code> value of this property if and only if
     * the data type of this property is a single valued <i>Id</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaId</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Id</i>
     *             property
     */
    public OdmaId getId() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.ID) )
        {
            return (OdmaId)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.ID,false,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>OdmaGuid</code> value of this property if and only if
     * the data type of this property is a single valued <i>Guid</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaGuid</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Guid</i>
     *             property
     */
    public OdmaGuid getGuid() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == false) && (dataType == OdmaType.GUID) )
        {
            return (OdmaGuid)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.GUID,false,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<String></code> value of this property if and only if
     * the data type of this property is a multi valued <i>String</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<String></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>String</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<String> getStringList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.STRING) )
        {
            return (List<String>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.STRING,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<Integer></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Integer</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Integer></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Integer</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getIntegerList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.INTEGER) )
        {
            return (List<Integer>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.INTEGER,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<Short></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Short</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Short></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Short</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Short> getShortList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.SHORT) )
        {
            return (List<Short>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.SHORT,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<Long></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Long</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Long></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Long</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Long> getLongList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.LONG) )
        {
            return (List<Long>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.LONG,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<Float></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Float</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Float></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Float</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Float> getFloatList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.FLOAT) )
        {
            return (List<Float>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.FLOAT,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<Double></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Double</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Double></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Double</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Double> getDoubleList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.DOUBLE) )
        {
            return (List<Double>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.DOUBLE,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<Boolean></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Boolean</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Boolean></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Boolean</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Boolean> getBooleanList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.BOOLEAN) )
        {
            return (List<Boolean>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.BOOLEAN,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<Date></code> value of this property if and only if
     * the data type of this property is a multi valued <i>DateTime</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Date></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>DateTime</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<Date> getDateTimeList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.DATETIME) )
        {
            return (List<Date>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.DATETIME,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<byte[]></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Blob</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<byte[]></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Blob</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<byte[]> getBlobList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.BLOB) )
        {
            return (List<byte[]>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.BLOB,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>Iterable<? extends OdmaObject></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Iterable<? extends OdmaObject></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Reference</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public Iterable<? extends OdmaObject> getReferenceIterable() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.REFERENCE) )
        {
            return (Iterable<? extends OdmaObject>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.REFERENCE,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<OdmaContent></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Content</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<OdmaContent></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Content</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<OdmaContent> getContentList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.CONTENT) )
        {
            return (List<OdmaContent>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.CONTENT,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<OdmaId></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Id</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<OdmaId></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Id</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<OdmaId> getIdList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.ID) )
        {
            return (List<OdmaId>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.ID,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>List<OdmaGuid></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Guid</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<OdmaGuid></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Guid</i>
     *             property
     */
    @SuppressWarnings("unchecked")
    public List<OdmaGuid> getGuidList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaType.GUID) )
        {
            return (List<OdmaGuid>)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaType.GUID,true,dataType,multivalue);
        }
    }

}
