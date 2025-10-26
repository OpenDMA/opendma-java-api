package org.opendma.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaCoreObject;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaType;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaServiceException;

public class OdmaProxyHandler implements InvocationHandler {

    private final OdmaCoreObject coreObject;

    private static final Map<String, PropertyMapping> PROPERTY_MAP = new HashMap<>();

    static {
        PROPERTY_MAP.put("getOdmaClass", new PropertyMapping(OdmaCommonNames.PROPERTY_CLASS, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getId", new PropertyMapping(OdmaCommonNames.PROPERTY_ID, OdmaType.ID, false));
        PROPERTY_MAP.put("getGuid", new PropertyMapping(OdmaCommonNames.PROPERTY_GUID, OdmaType.GUID, false));
        PROPERTY_MAP.put("getRepository", new PropertyMapping(OdmaCommonNames.PROPERTY_REPOSITORY, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getName", new PropertyMapping(OdmaCommonNames.PROPERTY_NAME, OdmaType.STRING, false));
        PROPERTY_MAP.put("setName", new PropertyMapping(OdmaCommonNames.PROPERTY_NAME, OdmaType.STRING, false));
        PROPERTY_MAP.put("getNamespace", new PropertyMapping(OdmaCommonNames.PROPERTY_NAMESPACE, OdmaType.STRING, false));
        PROPERTY_MAP.put("setNamespace", new PropertyMapping(OdmaCommonNames.PROPERTY_NAMESPACE, OdmaType.STRING, false));
        PROPERTY_MAP.put("getDisplayName", new PropertyMapping(OdmaCommonNames.PROPERTY_DISPLAYNAME, OdmaType.STRING, false));
        PROPERTY_MAP.put("setDisplayName", new PropertyMapping(OdmaCommonNames.PROPERTY_DISPLAYNAME, OdmaType.STRING, false));
        PROPERTY_MAP.put("getSuperClass", new PropertyMapping(OdmaCommonNames.PROPERTY_SUPERCLASS, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getAspects", new PropertyMapping(OdmaCommonNames.PROPERTY_ASPECTS, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getDeclaredProperties", new PropertyMapping(OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getProperties", new PropertyMapping(OdmaCommonNames.PROPERTY_PROPERTIES, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("isAspect", new PropertyMapping(OdmaCommonNames.PROPERTY_ASPECT, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("isInstantiable", new PropertyMapping(OdmaCommonNames.PROPERTY_INSTANTIABLE, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("setInstantiable", new PropertyMapping(OdmaCommonNames.PROPERTY_INSTANTIABLE, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("isHidden", new PropertyMapping(OdmaCommonNames.PROPERTY_HIDDEN, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("setHidden", new PropertyMapping(OdmaCommonNames.PROPERTY_HIDDEN, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("isSystem", new PropertyMapping(OdmaCommonNames.PROPERTY_SYSTEM, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("setSystem", new PropertyMapping(OdmaCommonNames.PROPERTY_SYSTEM, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("isRetrievable", new PropertyMapping(OdmaCommonNames.PROPERTY_RETRIEVABLE, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("isSearchable", new PropertyMapping(OdmaCommonNames.PROPERTY_SEARCHABLE, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("getSubClasses", new PropertyMapping(OdmaCommonNames.PROPERTY_SUBCLASSES, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getDataType", new PropertyMapping(OdmaCommonNames.PROPERTY_DATATYPE, OdmaType.INTEGER, false));
        PROPERTY_MAP.put("setDataType", new PropertyMapping(OdmaCommonNames.PROPERTY_DATATYPE, OdmaType.INTEGER, false));
        PROPERTY_MAP.put("getReferenceClass", new PropertyMapping(OdmaCommonNames.PROPERTY_REFERENCECLASS, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("setReferenceClass", new PropertyMapping(OdmaCommonNames.PROPERTY_REFERENCECLASS, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("isMultiValue", new PropertyMapping(OdmaCommonNames.PROPERTY_MULTIVALUE, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("setMultiValue", new PropertyMapping(OdmaCommonNames.PROPERTY_MULTIVALUE, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("isRequired", new PropertyMapping(OdmaCommonNames.PROPERTY_REQUIRED, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("setRequired", new PropertyMapping(OdmaCommonNames.PROPERTY_REQUIRED, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("isReadOnly", new PropertyMapping(OdmaCommonNames.PROPERTY_READONLY, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("setReadOnly", new PropertyMapping(OdmaCommonNames.PROPERTY_READONLY, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("getChoices", new PropertyMapping(OdmaCommonNames.PROPERTY_CHOICES, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getStringValue", new PropertyMapping(OdmaCommonNames.PROPERTY_STRINGVALUE, OdmaType.STRING, false));
        PROPERTY_MAP.put("setStringValue", new PropertyMapping(OdmaCommonNames.PROPERTY_STRINGVALUE, OdmaType.STRING, false));
        PROPERTY_MAP.put("getIntegerValue", new PropertyMapping(OdmaCommonNames.PROPERTY_INTEGERVALUE, OdmaType.INTEGER, false));
        PROPERTY_MAP.put("setIntegerValue", new PropertyMapping(OdmaCommonNames.PROPERTY_INTEGERVALUE, OdmaType.INTEGER, false));
        PROPERTY_MAP.put("getShortValue", new PropertyMapping(OdmaCommonNames.PROPERTY_SHORTVALUE, OdmaType.SHORT, false));
        PROPERTY_MAP.put("setShortValue", new PropertyMapping(OdmaCommonNames.PROPERTY_SHORTVALUE, OdmaType.SHORT, false));
        PROPERTY_MAP.put("getLongValue", new PropertyMapping(OdmaCommonNames.PROPERTY_LONGVALUE, OdmaType.LONG, false));
        PROPERTY_MAP.put("setLongValue", new PropertyMapping(OdmaCommonNames.PROPERTY_LONGVALUE, OdmaType.LONG, false));
        PROPERTY_MAP.put("getFloatValue", new PropertyMapping(OdmaCommonNames.PROPERTY_FLOATVALUE, OdmaType.FLOAT, false));
        PROPERTY_MAP.put("setFloatValue", new PropertyMapping(OdmaCommonNames.PROPERTY_FLOATVALUE, OdmaType.FLOAT, false));
        PROPERTY_MAP.put("getDoubleValue", new PropertyMapping(OdmaCommonNames.PROPERTY_DOUBLEVALUE, OdmaType.DOUBLE, false));
        PROPERTY_MAP.put("setDoubleValue", new PropertyMapping(OdmaCommonNames.PROPERTY_DOUBLEVALUE, OdmaType.DOUBLE, false));
        PROPERTY_MAP.put("isBooleanValue", new PropertyMapping(OdmaCommonNames.PROPERTY_BOOLEANVALUE, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("setBooleanValue", new PropertyMapping(OdmaCommonNames.PROPERTY_BOOLEANVALUE, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("getDateTimeValue", new PropertyMapping(OdmaCommonNames.PROPERTY_DATETIMEVALUE, OdmaType.DATETIME, false));
        PROPERTY_MAP.put("setDateTimeValue", new PropertyMapping(OdmaCommonNames.PROPERTY_DATETIMEVALUE, OdmaType.DATETIME, false));
        PROPERTY_MAP.put("getBinaryValue", new PropertyMapping(OdmaCommonNames.PROPERTY_BINARYVALUE, OdmaType.BINARY, false));
        PROPERTY_MAP.put("setBinaryValue", new PropertyMapping(OdmaCommonNames.PROPERTY_BINARYVALUE, OdmaType.BINARY, false));
        PROPERTY_MAP.put("getReferenceValue", new PropertyMapping(OdmaCommonNames.PROPERTY_REFERENCEVALUE, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("setReferenceValue", new PropertyMapping(OdmaCommonNames.PROPERTY_REFERENCEVALUE, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getRootClass", new PropertyMapping(OdmaCommonNames.PROPERTY_ROOTCLASS, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getRootAspects", new PropertyMapping(OdmaCommonNames.PROPERTY_ROOTASPECTS, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getRootFolder", new PropertyMapping(OdmaCommonNames.PROPERTY_ROOTFOLDER, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getTitle", new PropertyMapping(OdmaCommonNames.PROPERTY_TITLE, OdmaType.STRING, false));
        PROPERTY_MAP.put("setTitle", new PropertyMapping(OdmaCommonNames.PROPERTY_TITLE, OdmaType.STRING, false));
        PROPERTY_MAP.put("getVersion", new PropertyMapping(OdmaCommonNames.PROPERTY_VERSION, OdmaType.STRING, false));
        PROPERTY_MAP.put("getVersionCollection", new PropertyMapping(OdmaCommonNames.PROPERTY_VERSIONCOLLECTION, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getVersionIndependentId", new PropertyMapping(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTID, OdmaType.ID, false));
        PROPERTY_MAP.put("getVersionIndependentGuid", new PropertyMapping(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTGUID, OdmaType.GUID, false));
        PROPERTY_MAP.put("getContentElements", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTENTELEMENTS, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getCombinedContentType", new PropertyMapping(OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE, OdmaType.STRING, false));
        PROPERTY_MAP.put("setCombinedContentType", new PropertyMapping(OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE, OdmaType.STRING, false));
        PROPERTY_MAP.put("getPrimaryContentElement", new PropertyMapping(OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("setPrimaryContentElement", new PropertyMapping(OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getCreatedAt", new PropertyMapping(OdmaCommonNames.PROPERTY_CREATEDAT, OdmaType.DATETIME, false));
        PROPERTY_MAP.put("getCreatedBy", new PropertyMapping(OdmaCommonNames.PROPERTY_CREATEDBY, OdmaType.STRING, false));
        PROPERTY_MAP.put("getLastModifiedAt", new PropertyMapping(OdmaCommonNames.PROPERTY_LASTMODIFIEDAT, OdmaType.DATETIME, false));
        PROPERTY_MAP.put("getLastModifiedBy", new PropertyMapping(OdmaCommonNames.PROPERTY_LASTMODIFIEDBY, OdmaType.STRING, false));
        PROPERTY_MAP.put("isCheckedOut", new PropertyMapping(OdmaCommonNames.PROPERTY_CHECKEDOUT, OdmaType.BOOLEAN, false));
        PROPERTY_MAP.put("getCheckedOutAt", new PropertyMapping(OdmaCommonNames.PROPERTY_CHECKEDOUTAT, OdmaType.DATETIME, false));
        PROPERTY_MAP.put("getCheckedOutBy", new PropertyMapping(OdmaCommonNames.PROPERTY_CHECKEDOUTBY, OdmaType.STRING, false));
        PROPERTY_MAP.put("getContentType", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTENTTYPE, OdmaType.STRING, false));
        PROPERTY_MAP.put("setContentType", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTENTTYPE, OdmaType.STRING, false));
        PROPERTY_MAP.put("getPosition", new PropertyMapping(OdmaCommonNames.PROPERTY_POSITION, OdmaType.INTEGER, false));
        PROPERTY_MAP.put("getContent", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTENT, OdmaType.CONTENT, false));
        PROPERTY_MAP.put("setContent", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTENT, OdmaType.CONTENT, false));
        PROPERTY_MAP.put("getSize", new PropertyMapping(OdmaCommonNames.PROPERTY_SIZE, OdmaType.LONG, false));
        PROPERTY_MAP.put("getFileName", new PropertyMapping(OdmaCommonNames.PROPERTY_FILENAME, OdmaType.STRING, false));
        PROPERTY_MAP.put("setFileName", new PropertyMapping(OdmaCommonNames.PROPERTY_FILENAME, OdmaType.STRING, false));
        PROPERTY_MAP.put("getLocation", new PropertyMapping(OdmaCommonNames.PROPERTY_LOCATION, OdmaType.STRING, false));
        PROPERTY_MAP.put("setLocation", new PropertyMapping(OdmaCommonNames.PROPERTY_LOCATION, OdmaType.STRING, false));
        PROPERTY_MAP.put("getVersions", new PropertyMapping(OdmaCommonNames.PROPERTY_VERSIONS, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getLatest", new PropertyMapping(OdmaCommonNames.PROPERTY_LATEST, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getReleased", new PropertyMapping(OdmaCommonNames.PROPERTY_RELEASED, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getInProgress", new PropertyMapping(OdmaCommonNames.PROPERTY_INPROGRESS, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getContainees", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTAINEES, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getAssociations", new PropertyMapping(OdmaCommonNames.PROPERTY_ASSOCIATIONS, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getParent", new PropertyMapping(OdmaCommonNames.PROPERTY_PARENT, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("setParent", new PropertyMapping(OdmaCommonNames.PROPERTY_PARENT, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getSubFolders", new PropertyMapping(OdmaCommonNames.PROPERTY_SUBFOLDERS, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getContainedIn", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTAINEDIN, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getContainedInAssociations", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTAINEDINASSOCIATIONS, OdmaType.REFERENCE, true));
        PROPERTY_MAP.put("getContainer", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTAINER, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("setContainer", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTAINER, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("getContainable", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTAINABLE, OdmaType.REFERENCE, false));
        PROPERTY_MAP.put("setContainable", new PropertyMapping(OdmaCommonNames.PROPERTY_CONTAINABLE, OdmaType.REFERENCE, false));
    }

    public static class PropertyMapping {
        private final OdmaQName qname;
        private final OdmaType type;
        private final boolean multiValue;
        public PropertyMapping(OdmaQName qname, OdmaType type, boolean multiValue) {
            this.qname = qname;
            this.type = type;
            this.multiValue = multiValue;
        }
    }

    public OdmaProxyHandler(OdmaCoreObject coreObject) {
        this.coreObject = coreObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getDeclaringClass().equals(OdmaCoreObject.class)) {
            try {
                return method.invoke(coreObject, args);
            } catch(InvocationTargetException ite) {
                throw ite.getCause();
            }
        }

        String methodName = method.getName();
        if (methodName.equals("getQName")) {
            String namespace = (String) handleGetter("getNamespace");
            String name = (String) handleGetter("getName");
            return new OdmaQName(namespace, name);
        } else if (methodName.startsWith("get") || methodName.startsWith("is")) {
            return handleGetter(method.getName());
        } else if (methodName.startsWith("set")) {
            return handleSetter(method.getName(), args[0]);
        }

        throw new OdmaRuntimeException("Unsupported method: " + methodName);
    }

    private Object handleGetter(String methodName) {
        PropertyMapping mapping = PROPERTY_MAP.get(methodName);
        if (mapping == null) {
            throw new OdmaRuntimeException("No property mapping found for method: " + methodName);
        }
        try {
            OdmaProperty property = coreObject.getProperty(mapping.qname);
            if (mapping.multiValue) {
                return handleMultiValueGetter(property, mapping.type);
            } else {
                return handleSingleValueGetter(property, mapping.type);
            }
        } catch (OdmaPropertyNotFoundException pnfe) {
            throw new OdmaServiceException("Predefined OpenDMA property missing: "+mapping.qname.toString());
        } catch (OdmaInvalidDataTypeException idte) {
            throw new OdmaServiceException("Predefined OpenDMA property has wrong type or cardinality: "+mapping.qname.toString());
        }
    }

    private Object handleMultiValueGetter(OdmaProperty property, OdmaType type) throws OdmaInvalidDataTypeException {
        switch (type) {
            case STRING:
                return property.getStringList();
            case INTEGER:
                return property.getIntegerList();
            case SHORT:
                return property.getShortList();
            case LONG:
                return property.getLongList();
            case FLOAT:
                return property.getFloatList();
            case DOUBLE:
                return property.getDoubleList();
            case BOOLEAN:
                return property.getBooleanList();
            case DATETIME:
                return property.getDateTimeList();
            case BINARY:
                return property.getBinaryList();
            case REFERENCE:
                return property.getReferenceIterable();
            case CONTENT:
                return property.getContentList();
            case ID:
                return property.getIdList();
            case GUID:
                return property.getGuidList();
            default:
                throw new OdmaRuntimeException("Unsupported multi-value type: " + type);
        }
    }

    private Object handleSingleValueGetter(OdmaProperty property, OdmaType type) throws OdmaInvalidDataTypeException {
        switch (type) {
            case STRING:
                return property.getString();
            case INTEGER:
                return property.getInteger();
            case SHORT:
                return property.getShort();
            case LONG:
                return property.getLong();
            case FLOAT:
                return property.getFloat();
            case DOUBLE:
                return property.getDouble();
            case BOOLEAN:
                return property.getBoolean();
            case DATETIME:
                return property.getDateTime();
            case BINARY:
                return property.getBinary();
            case REFERENCE:
                return property.getReference();
            case CONTENT:
                return property.getContent();
            case ID:
                return property.getId();
            case GUID:
                return property.getGuid();
            default:
                throw new OdmaRuntimeException("Unsupported single-value type: " + type);
        }
    }

    private Object handleSetter(String methodName, Object value) throws OdmaAccessDeniedException {
        PropertyMapping mapping = PROPERTY_MAP.get(methodName);
        if (mapping == null) {
            throw new OdmaRuntimeException("No property mapping found for method: " + methodName);
        }
        try {
            coreObject.setProperty(mapping.qname, value);
            return null;
        } catch (OdmaPropertyNotFoundException pnfe) {
            throw new OdmaServiceException("Predefined OpenDMA property missing: "+mapping.qname.toString());
        } catch (OdmaInvalidDataTypeException idte) {
            throw new OdmaServiceException("Predefined OpenDMA property has wrong type or cardinality: "+mapping.qname.toString());
        }
    }

} 
