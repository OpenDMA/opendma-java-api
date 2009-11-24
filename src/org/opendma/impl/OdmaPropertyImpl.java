package org.opendma.impl;

import java.util.Date;

import org.opendma.OdmaTypes;
import org.opendma.api.OdmaContent;
import org.opendma.api.OdmaObject;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaQName;
import org.opendma.api.collections.BlobList;
import org.opendma.api.collections.BooleanList;
import org.opendma.api.collections.DateTimeList;
import org.opendma.api.collections.DoubleList;
import org.opendma.api.collections.FloatList;
import org.opendma.api.collections.IntegerList;
import org.opendma.api.collections.LongList;
import org.opendma.api.collections.OdmaContentList;
import org.opendma.api.collections.OdmaObjectEnumeration;
import org.opendma.api.collections.ShortList;
import org.opendma.api.collections.StringList;
import org.opendma.exceptions.OdmaEngineRuntimeException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;

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

    public OdmaQName getName()
    {
        return name;
    }

    public int getType()
    {
        return dataType;
    }

    public Object getValue()
    {
        return value;
    }

    public boolean isDirty()
    {
        return dirty;
    }

    public boolean isMultiValue()
    {
        return multivalue;
    }

    public boolean isReadOnly()
    {
        return readonly;
    }

    public void setValue(Object newValue) throws OdmaInvalidDataTypeException
    {
        if(readonly)
        {
            
        }
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
        default:
            throw new OdmaEngineRuntimeException("OdmaProperty initialized with unknown data type "+Integer.toString(dataType));
        }
    }

    public byte[] getBlob() throws OdmaInvalidDataTypeException
    {
        if( (dataType == OdmaTypes.TYPE_BLOB) && (multivalue == false) )
        {
            return (byte[])value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_BLOB,false,dataType,multivalue);
        }
    }

    public BlobList getBlobList() throws OdmaInvalidDataTypeException
    {
        if(value instanceof BlobList)
        {
            return (BlobList)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_BLOB,true,dataType,multivalue);
        }
    }

    public Boolean getBoolean() throws OdmaInvalidDataTypeException
    {
        if(value instanceof Boolean)
        {
            return (Boolean)value;
        }
        else
        {
            throw new OdmaInvalidDataTypeException(OdmaTypes.TYPE_BOOLEAN,false,dataType,multivalue);
        }
    }

    public BooleanList getBooleanList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public OdmaContent getContent() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public OdmaContentList getContentList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Date getDateTime() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public DateTimeList getDateTimeList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Double getDouble() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public DoubleList getDoubleList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Float getFloat() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public FloatList getFloatList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Integer getInteger() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public IntegerList getIntegerList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Long getLong() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public LongList getLongList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public OdmaObject getObject() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public OdmaObjectEnumeration getObjectList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Short getShort() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public ShortList getShortList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public String getString() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public StringList getStringList() throws OdmaInvalidDataTypeException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
