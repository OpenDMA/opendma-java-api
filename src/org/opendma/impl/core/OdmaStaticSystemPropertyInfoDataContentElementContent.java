package org.opendma.impl.core;

import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaType;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.impl.OdmaPropertyImpl;

public class OdmaStaticSystemPropertyInfoDataContentElementContent extends OdmaStaticSystemPropertyInfo {

    public OdmaStaticSystemPropertyInfoDataContentElementContent() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        properties.put(OdmaCommonNames.PROPERTY_NAME,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_NAME,OdmaCommonNames.PROPERTY_CONTENT.getName(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_NAMEQUALIFIER,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_NAMEQUALIFIER,OdmaCommonNames.PROPERTY_CONTENT.getQualifier(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_DISPLAYNAME,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_DISPLAYNAME,OdmaCommonNames.PROPERTY_CONTENT.getName(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_DATATYPE,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_DATATYPE,Integer.valueOf(11),OdmaType.INTEGER,false,true));
        properties.put(OdmaCommonNames.PROPERTY_REFERENCECLASS,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_REFERENCECLASS,null,OdmaType.REFERENCE,false,true));
        properties.put(OdmaCommonNames.PROPERTY_MULTIVALUE,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_MULTIVALUE,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_REQUIRED,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_REQUIRED,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_READONLY,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_READONLY,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_HIDDEN,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_HIDDEN,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_SYSTEM,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_SYSTEM,Boolean.TRUE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_CHOICES,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_CHOICES,null,OdmaType.REFERENCE,true,true));
    }

}
