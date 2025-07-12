package org.opendma.impl.core;

import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaType;
import org.opendma.api.OdmaPropertyInfo;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.impl.OdmaPropertyImpl;

public class OdmaStaticSystemClassObject extends OdmaStaticSystemClass
{

    public OdmaStaticSystemClassObject(OdmaStaticSystemClass superClass, Iterable<OdmaClass> subClasses, Iterable<OdmaClass> aspects, Iterable<OdmaPropertyInfo> declaredProperties, boolean retrievable, boolean searchable) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        super(subClasses);
        properties.put(OdmaCommonNames.PROPERTY_NAME,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_NAME,OdmaCommonNames.CLASS_OBJECT.getName(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_NAMESPACE,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_NAMESPACE,OdmaCommonNames.CLASS_OBJECT.getNamespace(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_DISPLAYNAME,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_DISPLAYNAME,OdmaCommonNames.CLASS_OBJECT.getName(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_SUPERCLASS,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_SUPERCLASS,superClass,OdmaType.REFERENCE,false,true));
        properties.put(OdmaCommonNames.PROPERTY_ASPECTS,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_ASPECTS,aspects,OdmaType.REFERENCE,true,true));
        properties.put(OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES,declaredProperties,OdmaType.REFERENCE,true,true));
        properties.put(OdmaCommonNames.PROPERTY_ASPECT,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_ASPECT,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_INSTANTIABLE,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_INSTANTIABLE,Boolean.TRUE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_HIDDEN,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_HIDDEN,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_SYSTEM,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_SYSTEM,Boolean.TRUE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_RETRIEVABLE,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_RETRIEVABLE,(retrievable?Boolean.TRUE:Boolean.FALSE),OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_SEARCHABLE,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_SEARCHABLE,(searchable?Boolean.TRUE:Boolean.FALSE),OdmaType.BOOLEAN,false,true));
        buildProperties();
    }

}
