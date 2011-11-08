package org.opendma.impl.core;

import org.opendma.OdmaTypes;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.impl.OdmaPropertyImpl;

public class OdmaStaticSystemPropertyInfoRootAspects extends OdmaStaticSystemPropertyInfo
{

    public OdmaStaticSystemPropertyInfoRootAspects() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        properties.put(OdmaTypes.PROPERTY_NAME,new OdmaPropertyImpl(OdmaTypes.PROPERTY_NAME,OdmaTypes.PROPERTY_ROOTASPECTS.getName(),OdmaTypes.TYPE_STRING,false,true));
        properties.put(OdmaTypes.PROPERTY_NAMEQUALIFIER,new OdmaPropertyImpl(OdmaTypes.PROPERTY_NAMEQUALIFIER,OdmaTypes.PROPERTY_ROOTASPECTS.getQualifier(),OdmaTypes.TYPE_STRING,false,true));
        properties.put(OdmaTypes.PROPERTY_DISPLAYNAME,new OdmaPropertyImpl(OdmaTypes.PROPERTY_DISPLAYNAME,OdmaTypes.PROPERTY_ROOTASPECTS.getName(),OdmaTypes.TYPE_STRING,false,true));
        properties.put(OdmaTypes.PROPERTY_DATATYPE,new OdmaPropertyImpl(OdmaTypes.PROPERTY_DATATYPE,new Integer(OdmaTypes.TYPE_REFERENCE),OdmaTypes.TYPE_INTEGER,false,true));
        properties.put(OdmaTypes.PROPERTY_REFERENCECLASS,new OdmaPropertyImpl(OdmaTypes.PROPERTY_REFERENCECLASS,null,OdmaTypes.TYPE_REFERENCE,false,true));
        properties.put(OdmaTypes.PROPERTY_MULTIVALUE,new OdmaPropertyImpl(OdmaTypes.PROPERTY_MULTIVALUE,Boolean.TRUE,OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_REQUIRED,new OdmaPropertyImpl(OdmaTypes.PROPERTY_REQUIRED,Boolean.FALSE,OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_READONLY,new OdmaPropertyImpl(OdmaTypes.PROPERTY_READONLY,Boolean.TRUE,OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_HIDDEN,new OdmaPropertyImpl(OdmaTypes.PROPERTY_HIDDEN,Boolean.FALSE,OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_SYSTEM,new OdmaPropertyImpl(OdmaTypes.PROPERTY_SYSTEM,Boolean.TRUE,OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_CHOICES,new OdmaPropertyImpl(OdmaTypes.PROPERTY_CHOICES,null,OdmaTypes.TYPE_REFERENCE,true,true));
    }

}
