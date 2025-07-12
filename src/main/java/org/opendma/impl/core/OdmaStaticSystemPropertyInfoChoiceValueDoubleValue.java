package org.opendma.impl.core;

import java.util.ArrayList;
import java.util.Collections;
import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaType;
import org.opendma.api.OdmaObject;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.impl.OdmaPropertyImpl;

public class OdmaStaticSystemPropertyInfoChoiceValueDoubleValue extends OdmaStaticSystemPropertyInfo {

    public OdmaStaticSystemPropertyInfoChoiceValueDoubleValue() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        properties.put(OdmaCommonNames.PROPERTY_NAME,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_NAME,OdmaCommonNames.PROPERTY_DOUBLEVALUE.getName(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_NAMESPACE,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_NAMESPACE,OdmaCommonNames.PROPERTY_DOUBLEVALUE.getNamespace(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_DISPLAYNAME,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_DISPLAYNAME,OdmaCommonNames.PROPERTY_DOUBLEVALUE.getName(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_DATATYPE,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_DATATYPE,Integer.valueOf(1),OdmaType.INTEGER,false,true));
        properties.put(OdmaCommonNames.PROPERTY_REFERENCECLASS,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_REFERENCECLASS,null,OdmaType.REFERENCE,false,true));
        properties.put(OdmaCommonNames.PROPERTY_MULTIVALUE,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_MULTIVALUE,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_REQUIRED,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_REQUIRED,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_READONLY,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_READONLY,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_HIDDEN,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_HIDDEN,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_SYSTEM,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_SYSTEM,Boolean.TRUE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_CHOICES,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_CHOICES,Collections.unmodifiableList(new ArrayList<OdmaObject>(0)),OdmaType.REFERENCE,true,true));
    }

}
