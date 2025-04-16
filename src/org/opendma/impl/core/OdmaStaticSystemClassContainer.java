package org.opendma.impl.core;

import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaType;
import org.opendma.api.OdmaPropertyInfo;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.impl.OdmaPropertyImpl;

public class OdmaStaticSystemClassContainer extends OdmaStaticSystemClass
{

    public OdmaStaticSystemClassContainer(OdmaStaticSystemClass superClass, Iterable<OdmaClass> subClasses, Iterable<OdmaClass> aspects, Iterable<OdmaPropertyInfo> declaredProperties, boolean retrievable, boolean searchable) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        super(subClasses);
        properties.put(OdmaCommonNames.PROPERTY_NAME,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_NAME,OdmaCommonNames.CLASS_CONTAINER.getName(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_NAMEQUALIFIER,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_NAMEQUALIFIER,OdmaCommonNames.CLASS_CONTAINER.getQualifier(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_DISPLAYNAME,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_DISPLAYNAME,OdmaCommonNames.CLASS_CONTAINER.getName(),OdmaType.STRING,false,true));
        properties.put(OdmaCommonNames.PROPERTY_SUPERCLASS,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_SUPERCLASS,superClass,OdmaType.REFERENCE,false,true));
        properties.put(OdmaCommonNames.PROPERTY_ASPECTS,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_ASPECTS,aspects,OdmaType.REFERENCE,true,true));
        properties.put(OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES,declaredProperties,OdmaType.REFERENCE,true,true));
        properties.put(OdmaCommonNames.PROPERTY_ASPECT,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_ASPECT,Boolean.TRUE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_INSTANTIABLE,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_INSTANTIABLE,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_HIDDEN,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_HIDDEN,Boolean.FALSE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_SYSTEM,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_SYSTEM,Boolean.TRUE,OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_RETRIEVABLE,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_RETRIEVABLE,(retrievable?Boolean.TRUE:Boolean.FALSE),OdmaType.BOOLEAN,false,true));
        properties.put(OdmaCommonNames.PROPERTY_SEARCHABLE,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_SEARCHABLE,(searchable?Boolean.TRUE:Boolean.FALSE),OdmaType.BOOLEAN,false,true));
        buildProperties();
    }

}
