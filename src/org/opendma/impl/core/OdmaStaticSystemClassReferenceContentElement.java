package org.opendma.impl.core;

import org.opendma.OdmaTypes;
import org.opendma.api.collections.OdmaClassEnumeration;
import org.opendma.api.collections.OdmaPropertyInfoEnumeration;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.impl.OdmaPropertyImpl;

public class OdmaStaticSystemClassReferenceContentElement extends OdmaStaticSystemClass
{

    public OdmaStaticSystemClassReferenceContentElement(OdmaStaticSystemClass parent, OdmaClassEnumeration subClasses, OdmaClassEnumeration aspects, OdmaPropertyInfoEnumeration declaredProperties, boolean retrievable, boolean searchable) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        super(parent,subClasses);
        properties.put(OdmaTypes.PROPERTY_NAME,new OdmaPropertyImpl(OdmaTypes.PROPERTY_NAME,OdmaTypes.CLASS_REFERENCECONTENTELEMENT.getName(),OdmaTypes.TYPE_STRING,false,true));
        properties.put(OdmaTypes.PROPERTY_NAMEQUALIFIER,new OdmaPropertyImpl(OdmaTypes.PROPERTY_NAMEQUALIFIER,OdmaTypes.CLASS_REFERENCECONTENTELEMENT.getQualifier(),OdmaTypes.TYPE_STRING,false,true));
        properties.put(OdmaTypes.PROPERTY_DISPLAYNAME,new OdmaPropertyImpl(OdmaTypes.PROPERTY_DISPLAYNAME,OdmaTypes.CLASS_REFERENCECONTENTELEMENT.getName(),OdmaTypes.TYPE_STRING,false,true));
        properties.put(OdmaTypes.PROPERTY_PARENT,new OdmaPropertyImpl(OdmaTypes.PROPERTY_PARENT,parent,OdmaTypes.TYPE_REFERENCE,false,true));
        properties.put(OdmaTypes.PROPERTY_ASPECTS,new OdmaPropertyImpl(OdmaTypes.PROPERTY_ASPECTS,aspects,OdmaTypes.TYPE_REFERENCE,true,true));
        properties.put(OdmaTypes.PROPERTY_DECLAREDPROPERTIES,new OdmaPropertyImpl(OdmaTypes.PROPERTY_DECLAREDPROPERTIES,declaredProperties,OdmaTypes.TYPE_REFERENCE,true,true));
        properties.put(OdmaTypes.PROPERTY_ASPECT,new OdmaPropertyImpl(OdmaTypes.PROPERTY_ASPECT,Boolean.TRUE,OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_INSTANTIABLE,new OdmaPropertyImpl(OdmaTypes.PROPERTY_INSTANTIABLE,Boolean.FALSE,OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_HIDDEN,new OdmaPropertyImpl(OdmaTypes.PROPERTY_HIDDEN,Boolean.FALSE,OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_SYSTEM,new OdmaPropertyImpl(OdmaTypes.PROPERTY_SYSTEM,Boolean.TRUE,OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_RETRIEVABLE,new OdmaPropertyImpl(OdmaTypes.PROPERTY_RETRIEVABLE,(retrievable?Boolean.TRUE:Boolean.FALSE),OdmaTypes.TYPE_BOOLEAN,false,true));
        properties.put(OdmaTypes.PROPERTY_SEARCHABLE,new OdmaPropertyImpl(OdmaTypes.PROPERTY_SEARCHABLE,(searchable?Boolean.TRUE:Boolean.FALSE),OdmaTypes.TYPE_BOOLEAN,false,true));
        buildProperties();
    }

}
