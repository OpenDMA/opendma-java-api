package org.opendma.impl;

import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaCoreObject;
import org.opendma.api.OdmaQName;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaObject;
import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaPropertyInfo;
import org.opendma.api.OdmaChoiceValue;
import org.opendma.api.OdmaRepository;
import org.opendma.api.OdmaDocument;
import org.opendma.api.OdmaContentElement;
import org.opendma.api.OdmaDataContentElement;
import org.opendma.api.OdmaReferenceContentElement;
import org.opendma.api.OdmaVersionCollection;
import org.opendma.api.OdmaContainer;
import org.opendma.api.OdmaFolder;
import org.opendma.api.OdmaContainable;
import org.opendma.api.OdmaAssociation;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OdmaProxyFactory {

    private static final Map<OdmaQName, Class<? extends OdmaObject>> INTERFACE_MAP = new HashMap<>();

    static {
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_OBJECT, OdmaObject.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_CLASS, OdmaClass.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaPropertyInfo.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaChoiceValue.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_REPOSITORY, OdmaRepository.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_DOCUMENT, OdmaDocument.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_CONTENTELEMENT, OdmaContentElement.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_DATACONTENTELEMENT, OdmaDataContentElement.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT, OdmaReferenceContentElement.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaVersionCollection.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_CONTAINER, OdmaContainer.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_FOLDER, OdmaFolder.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_CONTAINABLE, OdmaContainable.class);
        INTERFACE_MAP.put(OdmaCommonNames.CLASS_ASSOCIATION, OdmaAssociation.class);
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends OdmaObject> T createProxy(OdmaCoreObject coreObject, ClassLoader loader, List<OdmaQName> classNames) {

        List<Class<? extends OdmaObject>> interfaces = new ArrayList<Class<? extends OdmaObject>>();
        
        for (OdmaQName className : classNames) {
            Class<? extends OdmaObject> intf = INTERFACE_MAP.get(className);
            if (intf != null) {
                interfaces.add(intf);
            }
        }
        if (interfaces.isEmpty()) {
            throw new OdmaRuntimeException("No valid OpenDMA classes or aspects given.");
        }        

        return (T) Proxy.newProxyInstance( loader, interfaces.toArray(new Class<?>[0]), new OdmaProxyHandler(coreObject) );

    }

} 
