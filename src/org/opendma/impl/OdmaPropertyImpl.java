package org.opendma.impl;

import org.opendma.OdmaTypes;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaProperty;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.collections.StringList;
import org.opendma.api.collections.IntegerList;
import org.opendma.api.collections.ShortList;
import org.opendma.api.collections.LongList;
import org.opendma.api.collections.FloatList;
import org.opendma.api.collections.DoubleList;
import org.opendma.api.collections.BooleanList;
import java.util.Date;
import org.opendma.api.collections.DateTimeList;
import org.opendma.api.collections.BlobList;
import org.opendma.api.OdmaObject;
import org.opendma.api.collections.OdmaObjectEnumeration;
import org.opendma.api.OdmaContent;
import org.opendma.api.collections.OdmaContentList;
import org.opendma.api.OdmaId;
import org.opendma.api.collections.OdmaIdList;
import org.opendma.api.OdmaGuid;
import org.opendma.api.collections.OdmaGuidList;

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
    protected int dataType;
    
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
    public OdmaPropertyImpl(OdmaQName name, Object value, int dataType, boolean multivalue, boolean readonly) throws OdmaInvalidDataTypeException
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
    public int getType()
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
            case OdmaTypes.TYPE_STRING:
                if(newValue instanceof StringList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_INTEGER:
                if(newValue instanceof IntegerList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_SHORT:
                if(newValue instanceof ShortList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_LONG:
                if(newValue instanceof LongList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_FLOAT:
                if(newValue instanceof FloatList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_DOUBLE:
                if(newValue instanceof DoubleList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_BOOLEAN:
                if(newValue instanceof BooleanList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_DATETIME:
                if(newValue instanceof DateTimeList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_BLOB:
                if(newValue instanceof BlobList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_REFERENCE:
                if(newValue instanceof OdmaObjectEnumeration)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_CONTENT:
                if(newValue instanceof OdmaContentList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_ID:
                if(newValue instanceof OdmaIdList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_GUID:
                if(newValue instanceof OdmaGuidList)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            default:
                throw new OdmaRuntimeException("OdmaProperty initialized with unknown data type "+Integer.toString(dataType));
            }
        }
        else
        {
            switch(dataType)
            {
            case OdmaTypes.TYPE_STRING:
                if(newValue instanceof String)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_INTEGER:
                if(newValue instanceof Integer)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_SHORT:
                if(newValue instanceof Short)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_LONG:
                if(newValue instanceof Long)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_FLOAT:
                if(newValue instanceof Float)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_DOUBLE:
                if(newValue instanceof Double)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_BOOLEAN:
                if(newValue instanceof Boolean)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_DATETIME:
                if(newValue instanceof Date)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_BLOB:
                if(newValue instanceof byte[])
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_REFERENCE:
                if(newValue instanceof OdmaObject)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_CONTENT:
                if(newValue instanceof OdmaContent)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_ID:
                if(newValue instanceof OdmaId)
                {
                    value = newValue;
                }
                else
                {
                    throw new OdmaInvalidDataTypeException(dataType,multivalue);
                }
                break;
            case OdmaTypes.TYPE_GUID:
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
                throw new OdmaRuntimeException("OdmaProperty initialized with unknown data type "+Integer.toString(dataType));
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_STRING) )
        {
            return (String)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_STRING,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_INTEGER) )
        {
            return (Integer)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_INTEGER,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_SHORT) )
        {
            return (Short)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_SHORT,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_LONG) )
        {
            return (Long)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_LONG,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_FLOAT) )
        {
            return (Float)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_FLOAT,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_DOUBLE) )
        {
            return (Double)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_DOUBLE,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_BOOLEAN) )
        {
            return (Boolean)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_BOOLEAN,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_DATETIME) )
        {
            return (Date)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_DATETIME,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_BLOB) )
        {
            return (byte[])value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_BLOB,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_REFERENCE) )
        {
            return (OdmaObject)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_REFERENCE,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_CONTENT) )
        {
            return (OdmaContent)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_CONTENT,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_ID) )
        {
            return (OdmaId)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_ID,false,dataType,multivalue);
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
        if( (multivalue == false) && (dataType == OdmaTypes.TYPE_GUID) )
        {
            return (OdmaGuid)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_GUID,false,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>StringList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>String</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>StringList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>String</i>
     *             property
     */
    public StringList getStringList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_STRING) )
        {
            return (StringList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_STRING,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>IntegerList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Integer</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>IntegerList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Integer</i>
     *             property
     */
    public IntegerList getIntegerList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_INTEGER) )
        {
            return (IntegerList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_INTEGER,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>ShortList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Short</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>ShortList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Short</i>
     *             property
     */
    public ShortList getShortList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_SHORT) )
        {
            return (ShortList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_SHORT,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>LongList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Long</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>LongList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Long</i>
     *             property
     */
    public LongList getLongList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_LONG) )
        {
            return (LongList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_LONG,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>FloatList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Float</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>FloatList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Float</i>
     *             property
     */
    public FloatList getFloatList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_FLOAT) )
        {
            return (FloatList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_FLOAT,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>DoubleList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Double</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>DoubleList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Double</i>
     *             property
     */
    public DoubleList getDoubleList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_DOUBLE) )
        {
            return (DoubleList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_DOUBLE,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>BooleanList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Boolean</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>BooleanList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Boolean</i>
     *             property
     */
    public BooleanList getBooleanList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_BOOLEAN) )
        {
            return (BooleanList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_BOOLEAN,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>DateTimeList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>DateTime</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>DateTimeList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>DateTime</i>
     *             property
     */
    public DateTimeList getDateTimeList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_DATETIME) )
        {
            return (DateTimeList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_DATETIME,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>BlobList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Blob</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>BlobList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Blob</i>
     *             property
     */
    public BlobList getBlobList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_BLOB) )
        {
            return (BlobList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_BLOB,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>OdmaObjectEnumeration</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaObjectEnumeration</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Reference</i>
     *             property
     */
    public OdmaObjectEnumeration getReferenceEnumeration() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_REFERENCE) )
        {
            return (OdmaObjectEnumeration)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_REFERENCE,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>OdmaContentList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Content</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaContentList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Content</i>
     *             property
     */
    public OdmaContentList getContentList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_CONTENT) )
        {
            return (OdmaContentList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_CONTENT,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>OdmaIdList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Id</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaIdList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Id</i>
     *             property
     */
    public OdmaIdList getIdList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_ID) )
        {
            return (OdmaIdList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_ID,true,dataType,multivalue);
        }
    }

    /**
     * Returns the <code>OdmaGuidList</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Guid</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaGuidList</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Guid</i>
     *             property
     */
    public OdmaGuidList getGuidList() throws OdmaInvalidDataTypeException
    {
        if( (multivalue == true) && (dataType == OdmaTypes.TYPE_GUID) )
        {
            return (OdmaGuidList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_GUID,true,dataType,multivalue);
        }
    }

}
