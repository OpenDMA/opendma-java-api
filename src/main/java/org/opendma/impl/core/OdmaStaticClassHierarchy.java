package org.opendma.impl.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaGuid;
import org.opendma.api.OdmaId;
import org.opendma.api.OdmaObject;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaPropertyInfo;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaRepository;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaObjectNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;

public class OdmaStaticClassHierarchy {

    private Map<PropertyInfoKey, OdmaStaticSystemPropertyInfo> propertyInfos = new HashMap<PropertyInfoKey, OdmaStaticSystemPropertyInfo>();

    protected Map<OdmaQName, OdmaStaticSystemClass> classInfos = new HashMap<OdmaQName, OdmaStaticSystemClass>();

    protected Map<OdmaId, OdmaObject> allAvailableObjects = new HashMap<OdmaId, OdmaObject>();
    
    protected Map<OdmaQName, OdmaProperty> repositoryObjectProperties = new HashMap<OdmaQName, OdmaProperty>();
    
    protected OdmaId repositoryId;
   
    protected OdmaRepository repositoryObject;
    
    protected OdmaIdProvider idProvider;
    
    private static class PropertyInfoKey {
        private PropertyInfoKey(OdmaQName className, OdmaQName propertyName) {
            this.className = className;
            this.propertyName = propertyName;
        }
        OdmaQName className;
        OdmaQName propertyName;
        @Override
        public int hashCode() {
            return Objects.hash(className, propertyName);
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PropertyInfoKey other = (PropertyInfoKey) obj;
            return Objects.equals(className, other.className) && Objects.equals(propertyName, other.propertyName);
        }
    }

    public OdmaStaticSystemPropertyInfo getPropertyInfo(OdmaQName className, OdmaQName propertyName) {
        return propertyInfos.get(new PropertyInfoKey(className, propertyName));
    }

    protected void registerPropertyInfo(OdmaQName className, OdmaQName propertyName, OdmaStaticSystemPropertyInfo propertyInfo) {
        propertyInfos.put(new PropertyInfoKey(className, propertyName), propertyInfo);
    }

    public OdmaStaticSystemClass getClassInfo(OdmaQName name) {
        return classInfos.get(name);
    }

    public OdmaObject getObjectById(OdmaId id) throws OdmaObjectNotFoundException {
        OdmaObject o = allAvailableObjects.get(id);
        if(o == null) {
            throw new OdmaObjectNotFoundException(repositoryId, id);
        }
        return o;
    }
    
    public Map<OdmaId, OdmaObject> getAllObjectsById() {
        return allAvailableObjects;
    }
    
    public Map<OdmaQName,OdmaStaticSystemClass> getAllClassesByName() {
        return classInfos;
    }
    
    public Map<OdmaQName, OdmaProperty> getRepositoryObjectProperties() {
        return repositoryObjectProperties;
    }
    
    public interface OdmaIdProvider
    {
        OdmaId generatePropertyInfoId(OdmaPropertyInfo propInfo);
        OdmaId generateClassInfoId(OdmaClass cls);
    }
    
    public OdmaStaticClassHierarchy(String repoName, String repoDisplayName, OdmaId repositoryId, OdmaId repositoryObjectId, OdmaIdProvider idProvider) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        this.idProvider = idProvider;
        this.repositoryId = repositoryId;
        buildClassHierarchy();
        buildRepositoryObject(repoName, repoDisplayName, repositoryObjectId);
        generateIds();
        buildAllAvaialbleObjectsMap();
    }
    
    public OdmaStaticClassHierarchy(String repoName, String repoDisplayName, OdmaId repositoryId, OdmaId repositoryObjectId) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        this.idProvider =  new DefaultIdProvider();
        this.repositoryId = repositoryId;
        buildClassHierarchy();
        buildRepositoryObject(repoName, repoDisplayName, repositoryObjectId);
        generateIds();
        buildAllAvaialbleObjectsMap();
    }
    
    public OdmaStaticClassHierarchy(OdmaId repositoryId, OdmaRepository repo, OdmaIdProvider idProvider) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        this.idProvider = idProvider;
        this.repositoryId = repositoryId;
        buildClassHierarchy();
        repositoryObject = repo;
        setRepositoryObject();
        generateIds();
        buildAllAvaialbleObjectsMap();
    }
    
    public OdmaStaticClassHierarchy(OdmaId repositoryId, OdmaRepository repo) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        this.idProvider =  new DefaultIdProvider();
        this.repositoryId = repositoryId;
        buildClassHierarchy();
        repositoryObject = repo;
        setRepositoryObject();
        generateIds();
        buildAllAvaialbleObjectsMap();
    }

    protected void buildAllAvaialbleObjectsMap() {
        // all properties
        Iterator<OdmaStaticSystemPropertyInfo> itPropertyInfos = propertyInfos.values().iterator();
        while(itPropertyInfos.hasNext()) {
            OdmaStaticSystemPropertyInfo pi = itPropertyInfos.next();
            allAvailableObjects.put(pi.getId(),pi);
        }
        // all classes
        Iterator<OdmaStaticSystemClass> itClassInfos = classInfos.values().iterator();
        while(itClassInfos.hasNext()) {
            OdmaStaticSystemClass ci = (OdmaStaticSystemClass)itClassInfos.next();
            allAvailableObjects.put(ci.getId(),ci);
        }
        // the repository
        allAvailableObjects.put(repositoryObject.getId(),repositoryObject);
    }

    protected void buildRepositoryObject(String name, String displayName, OdmaId repositoryObjectId) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        OdmaStaticSystemRepository repo = new OdmaStaticSystemRepository(repositoryObjectProperties, name, displayName, repositoryObjectId, new OdmaGuid(repositoryObjectId, repositoryId), getClassInfo(OdmaCommonNames.CLASS_OBJECT), rootAspects);
        repo.patchClass(getClassInfo(OdmaCommonNames.CLASS_REPOSITORY));
        repositoryObject = repo;
        setRepositoryObject();
    }

    protected void setRepositoryObject() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        Iterator<OdmaStaticSystemPropertyInfo> itPropertyInfos = propertyInfos.values().iterator();
        while(itPropertyInfos.hasNext()) {
            OdmaStaticSystemPropertyInfo pi = itPropertyInfos.next();
            pi.patchRepository(repositoryObject);
        }
        Iterator<OdmaStaticSystemClass> itClassInfos = classInfos.values().iterator();
        while(itClassInfos.hasNext()) {
            OdmaStaticSystemClass ci = itClassInfos.next();
            ci.patchRepository(repositoryObject);
        }
    }
    
    protected HashMap<OdmaQName, ArrayList<OdmaClass>> subClassesArrayLists = new HashMap<OdmaQName, ArrayList<OdmaClass>>();
    
    private ArrayList<OdmaClass> getInternalSubClassesArrayList(OdmaQName className) {
        ArrayList<OdmaClass> result = subClassesArrayLists.get(className);
        if(result != null) {
            return result;
        }
        synchronized(subClassesArrayLists) {
            result = subClassesArrayLists.get(className);
            if(result != null) {
                return result;
            }
            result = new ArrayList<OdmaClass>();
            subClassesArrayLists.put(className,result);
            return result;
        }
    }
    
    public Iterable<OdmaClass> getSubClasses(final OdmaQName className) {
        return new Iterable<OdmaClass>() {
            public Iterator<OdmaClass> iterator() {
                final Iterator<OdmaClass> it = getInternalSubClassesArrayList(className).iterator();
                return new Iterator<OdmaClass>() {

                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    public OdmaClass next() {
                        return it.next();
                    }

                    public void remove() {
                        throw new OdmaRuntimeException("Class hierarchy cannot be modified via this method");
                    }
                };
            }
        };
    }
    
    public void registerSubClass(OdmaQName superClassName, OdmaClass subClass) {
        if(!superClassName.equals(subClass.getSuperClass().getQName())) {
            throw new OdmaRuntimeException("Name of super class does not equal registered super class");
        }
        ArrayList<OdmaClass> subClasses = getInternalSubClassesArrayList(superClassName);
        synchronized(subClasses) {
            for(OdmaClass c : subClasses) {
                if(c.getQName().equals(subClass.getQName())) {
                    throw new OdmaRuntimeException("registerSubClass failed. Subclass with same name already exists: "+subClass.getQName().toString());
                }
            }
            subClasses.add(subClass);
        }
    }
    
    public void registerAspectUsage(OdmaQName aspectName, OdmaClass usingClass) {
        ArrayList<OdmaClass> subClasses = getInternalSubClassesArrayList(aspectName);
        synchronized(subClasses) {
            for(OdmaClass c : subClasses) {
                if(c.getQName().equals(usingClass.getQName())) {
                    throw new OdmaRuntimeException("registerAspectUsage failed. Usage already registered for name: "+usingClass.getQName().toString());
                }
            }
            subClasses.add(usingClass);
        }
    }
    
    protected ArrayList<OdmaClass> rootAspects = new ArrayList<OdmaClass>();
    
    public void registerRootAspect(OdmaClass aspect) {
        synchronized(rootAspects) {
            for(OdmaClass c : rootAspects) {
                if(c.getQName().equals(aspect.getQName())) {
                    throw new OdmaRuntimeException("registerRootAspect failed. Root Aspect with same name already exists: "+aspect.getQName().toString());
                }
            }
            rootAspects.add(aspect);
        }
    }
    
    public Iterable<OdmaClass> getRootAspects() {
        return rootAspects;
    }
    
    public boolean isOrIsAncestorOf(OdmaClass c, OdmaQName testAgainst) {
        OdmaClass test = c;
        HashMap<OdmaQName, Boolean> noLoopTest = new HashMap<OdmaQName, Boolean>();
        while(test != null) {
            noLoopTest.put(test.getQName(),Boolean.TRUE);
            if(test.getQName().equals(testAgainst)) {
                return true;
            }
            test = test.getSuperClass();
            if((test != null)&&(noLoopTest.containsKey(test.getQName()))) {
                throw new OdmaRuntimeException("Loop in class hierarchy of "+c.getQName());
            }
        }
        return false;
    }
    
    public boolean isOrIsExtending(OdmaClass c, OdmaQName testAgainst)
    {
        // test super class relationship
        if(isOrIsAncestorOf(c,testAgainst)) {
            return true;
        }
        // test aspects
        Iterable<OdmaClass> aspects = c.getAspects();
        if(aspects != null) {
            Iterator<OdmaClass> itAspects = aspects.iterator();
            while(itAspects.hasNext()) {
                OdmaClass aspect = (OdmaClass)itAspects.next();
                if(isOrIsAncestorOf(aspect,testAgainst)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateRepositoryObject(Map<OdmaQName, OdmaProperty> properties) {
        // remove id from map
        allAvailableObjects.remove(repositoryObject.getId());
        // copy all system values that must not be changed
        properties.put(OdmaCommonNames.PROPERTY_ID, repositoryObjectProperties.get(OdmaCommonNames.PROPERTY_ID));
        properties.put(OdmaCommonNames.PROPERTY_GUID, repositoryObjectProperties.get(OdmaCommonNames.PROPERTY_GUID));
        properties.put(OdmaCommonNames.PROPERTY_REPOSITORY, repositoryObjectProperties.get(OdmaCommonNames.PROPERTY_REPOSITORY));
        properties.put(OdmaCommonNames.PROPERTY_ROOTCLASS, repositoryObjectProperties.get(OdmaCommonNames.PROPERTY_ROOTCLASS));
        properties.put(OdmaCommonNames.PROPERTY_ROOTASPECTS, repositoryObjectProperties.get(OdmaCommonNames.PROPERTY_ROOTASPECTS));
        // replace properties
        repositoryObjectProperties.clear();
        repositoryObjectProperties.putAll(properties);
        // put back in map
        allAvailableObjects.put(repositoryObject.getId(), repositoryObject);
    }
    
    private class DefaultIdProvider implements OdmaIdProvider {

        @Override
        public OdmaId generatePropertyInfoId(OdmaPropertyInfo propInfo) {
            return new OdmaId(propInfo.getNamespace()+"Property"+propInfo.getName());
        }

        @Override
        public OdmaId generateClassInfoId(OdmaClass cls) {
            return new OdmaId(cls.getNamespace()+"Class"+cls.getName());
        }
        
    }

    protected void generateIds() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        Iterator<OdmaStaticSystemPropertyInfo> itPropertyInfos = propertyInfos.values().iterator();
        while(itPropertyInfos.hasNext()) {
            OdmaStaticSystemPropertyInfo pi = itPropertyInfos.next();
            OdmaId piId = idProvider.generatePropertyInfoId(pi);
            OdmaGuid piGuid = new OdmaGuid(piId, repositoryId);
            pi.patchIds(piId,piGuid);
        }
        Iterator<OdmaStaticSystemClass> itClassInfos = classInfos.values().iterator();
        while(itClassInfos.hasNext()) {
            OdmaStaticSystemClass ci = itClassInfos.next();
            OdmaId ciId = idProvider.generateClassInfoId(ci);
            OdmaGuid ciGuid = new OdmaGuid(ciId, repositoryId);
            ci.patchIds(ciId,ciGuid);
        }
    }
    
    public boolean getRetrievable(OdmaQName className) {
        return false;
    }
    
    public boolean getSearchable(OdmaQName className) {
        return false;
    }
        public void buildClassHierarchy() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        ArrayList<OdmaClass> declaredAspects;
        ArrayList<OdmaPropertyInfo> declaredProperties;
        OdmaStaticSystemClass ssc;

        registerPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_CLASS, new OdmaStaticSystemPropertyInfoObjectClass());
        registerPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_ID, new OdmaStaticSystemPropertyInfoObjectId());
        registerPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_GUID, new OdmaStaticSystemPropertyInfoObjectGuid());
        registerPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_REPOSITORY, new OdmaStaticSystemPropertyInfoObjectRepository());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_NAME, new OdmaStaticSystemPropertyInfoClassName());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_NAMESPACE, new OdmaStaticSystemPropertyInfoClassNamespace());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_DISPLAYNAME, new OdmaStaticSystemPropertyInfoClassDisplayName());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SUPERCLASS, new OdmaStaticSystemPropertyInfoClassSuperClass());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_ASPECTS, new OdmaStaticSystemPropertyInfoClassAspects());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES, new OdmaStaticSystemPropertyInfoClassDeclaredProperties());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_PROPERTIES, new OdmaStaticSystemPropertyInfoClassProperties());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_ASPECT, new OdmaStaticSystemPropertyInfoClassAspect());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_HIDDEN, new OdmaStaticSystemPropertyInfoClassHidden());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SYSTEM, new OdmaStaticSystemPropertyInfoClassSystem());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_RETRIEVABLE, new OdmaStaticSystemPropertyInfoClassRetrievable());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SEARCHABLE, new OdmaStaticSystemPropertyInfoClassSearchable());
        registerPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SUBCLASSES, new OdmaStaticSystemPropertyInfoClassSubClasses());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_NAME, new OdmaStaticSystemPropertyInfoPropertyInfoName());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_NAMESPACE, new OdmaStaticSystemPropertyInfoPropertyInfoNamespace());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_DISPLAYNAME, new OdmaStaticSystemPropertyInfoPropertyInfoDisplayName());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_DATATYPE, new OdmaStaticSystemPropertyInfoPropertyInfoDataType());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_REFERENCECLASS, new OdmaStaticSystemPropertyInfoPropertyInfoReferenceClass());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_MULTIVALUE, new OdmaStaticSystemPropertyInfoPropertyInfoMultiValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_REQUIRED, new OdmaStaticSystemPropertyInfoPropertyInfoRequired());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_READONLY, new OdmaStaticSystemPropertyInfoPropertyInfoReadOnly());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_HIDDEN, new OdmaStaticSystemPropertyInfoPropertyInfoHidden());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_SYSTEM, new OdmaStaticSystemPropertyInfoPropertyInfoSystem());
        registerPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_CHOICES, new OdmaStaticSystemPropertyInfoPropertyInfoChoices());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_DISPLAYNAME, new OdmaStaticSystemPropertyInfoChoiceValueDisplayName());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_STRINGVALUE, new OdmaStaticSystemPropertyInfoChoiceValueStringValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_INTEGERVALUE, new OdmaStaticSystemPropertyInfoChoiceValueIntegerValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_SHORTVALUE, new OdmaStaticSystemPropertyInfoChoiceValueShortValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_LONGVALUE, new OdmaStaticSystemPropertyInfoChoiceValueLongValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_FLOATVALUE, new OdmaStaticSystemPropertyInfoChoiceValueFloatValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_DOUBLEVALUE, new OdmaStaticSystemPropertyInfoChoiceValueDoubleValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_BOOLEANVALUE, new OdmaStaticSystemPropertyInfoChoiceValueBooleanValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_DATETIMEVALUE, new OdmaStaticSystemPropertyInfoChoiceValueDateTimeValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_BINARYVALUE, new OdmaStaticSystemPropertyInfoChoiceValueBinaryValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_REFERENCEVALUE, new OdmaStaticSystemPropertyInfoChoiceValueReferenceValue());
        registerPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_NAME, new OdmaStaticSystemPropertyInfoRepositoryName());
        registerPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_DISPLAYNAME, new OdmaStaticSystemPropertyInfoRepositoryDisplayName());
        registerPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_ROOTCLASS, new OdmaStaticSystemPropertyInfoRepositoryRootClass());
        registerPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_ROOTASPECTS, new OdmaStaticSystemPropertyInfoRepositoryRootAspects());
        registerPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_ROOTFOLDER, new OdmaStaticSystemPropertyInfoRepositoryRootFolder());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_TITLE, new OdmaStaticSystemPropertyInfoDocumentTitle());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_VERSION, new OdmaStaticSystemPropertyInfoDocumentVersion());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_VERSIONCOLLECTION, new OdmaStaticSystemPropertyInfoDocumentVersionCollection());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTID, new OdmaStaticSystemPropertyInfoDocumentVersionIndependentId());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTGUID, new OdmaStaticSystemPropertyInfoDocumentVersionIndependentGuid());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CONTENTELEMENTS, new OdmaStaticSystemPropertyInfoDocumentContentElements());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE, new OdmaStaticSystemPropertyInfoDocumentCombinedContentType());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT, new OdmaStaticSystemPropertyInfoDocumentPrimaryContentElement());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CREATEDAT, new OdmaStaticSystemPropertyInfoDocumentCreatedAt());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CREATEDBY, new OdmaStaticSystemPropertyInfoDocumentCreatedBy());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_LASTMODIFIEDAT, new OdmaStaticSystemPropertyInfoDocumentLastModifiedAt());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_LASTMODIFIEDBY, new OdmaStaticSystemPropertyInfoDocumentLastModifiedBy());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CHECKEDOUT, new OdmaStaticSystemPropertyInfoDocumentCheckedOut());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CHECKEDOUTAT, new OdmaStaticSystemPropertyInfoDocumentCheckedOutAt());
        registerPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CHECKEDOUTBY, new OdmaStaticSystemPropertyInfoDocumentCheckedOutBy());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTENTELEMENT, OdmaCommonNames.PROPERTY_CONTENTTYPE, new OdmaStaticSystemPropertyInfoContentElementContentType());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTENTELEMENT, OdmaCommonNames.PROPERTY_POSITION, new OdmaStaticSystemPropertyInfoContentElementPosition());
        registerPropertyInfo(OdmaCommonNames.CLASS_DATACONTENTELEMENT, OdmaCommonNames.PROPERTY_CONTENT, new OdmaStaticSystemPropertyInfoDataContentElementContent());
        registerPropertyInfo(OdmaCommonNames.CLASS_DATACONTENTELEMENT, OdmaCommonNames.PROPERTY_SIZE, new OdmaStaticSystemPropertyInfoDataContentElementSize());
        registerPropertyInfo(OdmaCommonNames.CLASS_DATACONTENTELEMENT, OdmaCommonNames.PROPERTY_FILENAME, new OdmaStaticSystemPropertyInfoDataContentElementFileName());
        registerPropertyInfo(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT, OdmaCommonNames.PROPERTY_LOCATION, new OdmaStaticSystemPropertyInfoReferenceContentElementLocation());
        registerPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_VERSIONS, new OdmaStaticSystemPropertyInfoVersionCollectionVersions());
        registerPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_LATEST, new OdmaStaticSystemPropertyInfoVersionCollectionLatest());
        registerPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_RELEASED, new OdmaStaticSystemPropertyInfoVersionCollectionReleased());
        registerPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_INPROGRESS, new OdmaStaticSystemPropertyInfoVersionCollectionInProgress());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_TITLE, new OdmaStaticSystemPropertyInfoContainerTitle());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_CONTAINEES, new OdmaStaticSystemPropertyInfoContainerContainees());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_ASSOCIATIONS, new OdmaStaticSystemPropertyInfoContainerAssociations());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_CREATEDAT, new OdmaStaticSystemPropertyInfoContainerCreatedAt());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_CREATEDBY, new OdmaStaticSystemPropertyInfoContainerCreatedBy());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_LASTMODIFIEDAT, new OdmaStaticSystemPropertyInfoContainerLastModifiedAt());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_LASTMODIFIEDBY, new OdmaStaticSystemPropertyInfoContainerLastModifiedBy());
        registerPropertyInfo(OdmaCommonNames.CLASS_FOLDER, OdmaCommonNames.PROPERTY_PARENT, new OdmaStaticSystemPropertyInfoFolderParent());
        registerPropertyInfo(OdmaCommonNames.CLASS_FOLDER, OdmaCommonNames.PROPERTY_SUBFOLDERS, new OdmaStaticSystemPropertyInfoFolderSubFolders());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTAINABLE, OdmaCommonNames.PROPERTY_CONTAINEDIN, new OdmaStaticSystemPropertyInfoContainableContainedIn());
        registerPropertyInfo(OdmaCommonNames.CLASS_CONTAINABLE, OdmaCommonNames.PROPERTY_CONTAINEDINASSOCIATIONS, new OdmaStaticSystemPropertyInfoContainableContainedInAssociations());
        registerPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_NAME, new OdmaStaticSystemPropertyInfoAssociationName());
        registerPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CONTAINER, new OdmaStaticSystemPropertyInfoAssociationContainer());
        registerPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CONTAINABLE, new OdmaStaticSystemPropertyInfoAssociationContainable());
        registerPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CREATEDAT, new OdmaStaticSystemPropertyInfoAssociationCreatedAt());
        registerPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CREATEDBY, new OdmaStaticSystemPropertyInfoAssociationCreatedBy());
        registerPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_LASTMODIFIEDAT, new OdmaStaticSystemPropertyInfoAssociationLastModifiedAt());
        registerPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_LASTMODIFIEDBY, new OdmaStaticSystemPropertyInfoAssociationLastModifiedBy());

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_CLASS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_ID));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_GUID));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_REPOSITORY));
        ssc = new OdmaStaticSystemClassObject(null, getSubClasses(OdmaCommonNames.CLASS_OBJECT), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_OBJECT), getSearchable(OdmaCommonNames.CLASS_OBJECT));
        classInfos.put(OdmaCommonNames.CLASS_OBJECT, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_NAMESPACE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SUPERCLASS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_ASPECTS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_PROPERTIES));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_ASPECT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_HIDDEN));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SYSTEM));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_RETRIEVABLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SEARCHABLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SUBCLASSES));
        ssc = new OdmaStaticSystemClassClass(getClassInfo(OdmaCommonNames.CLASS_OBJECT), getSubClasses(OdmaCommonNames.CLASS_CLASS), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_CLASS), getSearchable(OdmaCommonNames.CLASS_CLASS));
        registerSubClass(OdmaCommonNames.CLASS_OBJECT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_CLASS, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_NAMESPACE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_DATATYPE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_REFERENCECLASS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_MULTIVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_REQUIRED));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_READONLY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_HIDDEN));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_SYSTEM));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_CHOICES));
        ssc = new OdmaStaticSystemClassPropertyInfo(getClassInfo(OdmaCommonNames.CLASS_OBJECT), getSubClasses(OdmaCommonNames.CLASS_PROPERTYINFO), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_PROPERTYINFO), getSearchable(OdmaCommonNames.CLASS_PROPERTYINFO));
        registerSubClass(OdmaCommonNames.CLASS_OBJECT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_PROPERTYINFO, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_STRINGVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_INTEGERVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_SHORTVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_LONGVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_FLOATVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_DOUBLEVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_BOOLEANVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_DATETIMEVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_BINARYVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_REFERENCEVALUE));
        ssc = new OdmaStaticSystemClassChoiceValue(getClassInfo(OdmaCommonNames.CLASS_OBJECT), getSubClasses(OdmaCommonNames.CLASS_CHOICEVALUE), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_CHOICEVALUE), getSearchable(OdmaCommonNames.CLASS_CHOICEVALUE));
        registerSubClass(OdmaCommonNames.CLASS_OBJECT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_CHOICEVALUE, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_ROOTCLASS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_ROOTASPECTS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_ROOTFOLDER));
        ssc = new OdmaStaticSystemClassRepository(getClassInfo(OdmaCommonNames.CLASS_OBJECT), getSubClasses(OdmaCommonNames.CLASS_REPOSITORY), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_REPOSITORY), getSearchable(OdmaCommonNames.CLASS_REPOSITORY));
        registerSubClass(OdmaCommonNames.CLASS_OBJECT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_REPOSITORY, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_TITLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_VERSION));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_VERSIONCOLLECTION));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTID));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTGUID));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CONTENTELEMENTS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_LASTMODIFIEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_LASTMODIFIEDBY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CHECKEDOUT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CHECKEDOUTAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CHECKEDOUTBY));
        ssc = new OdmaStaticSystemClassDocument(null, getSubClasses(OdmaCommonNames.CLASS_DOCUMENT), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_DOCUMENT), getSearchable(OdmaCommonNames.CLASS_DOCUMENT));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_DOCUMENT, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTENTELEMENT, OdmaCommonNames.PROPERTY_CONTENTTYPE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTENTELEMENT, OdmaCommonNames.PROPERTY_POSITION));
        ssc = new OdmaStaticSystemClassContentElement(null, getSubClasses(OdmaCommonNames.CLASS_CONTENTELEMENT), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_CONTENTELEMENT), getSearchable(OdmaCommonNames.CLASS_CONTENTELEMENT));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_CONTENTELEMENT, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DATACONTENTELEMENT, OdmaCommonNames.PROPERTY_CONTENT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DATACONTENTELEMENT, OdmaCommonNames.PROPERTY_SIZE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_DATACONTENTELEMENT, OdmaCommonNames.PROPERTY_FILENAME));
        ssc = new OdmaStaticSystemClassDataContentElement(getClassInfo(OdmaCommonNames.CLASS_CONTENTELEMENT), getSubClasses(OdmaCommonNames.CLASS_DATACONTENTELEMENT), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_DATACONTENTELEMENT), getSearchable(OdmaCommonNames.CLASS_DATACONTENTELEMENT));
        registerSubClass(OdmaCommonNames.CLASS_CONTENTELEMENT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_DATACONTENTELEMENT, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT, OdmaCommonNames.PROPERTY_LOCATION));
        ssc = new OdmaStaticSystemClassReferenceContentElement(getClassInfo(OdmaCommonNames.CLASS_CONTENTELEMENT), getSubClasses(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT), getSearchable(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT));
        registerSubClass(OdmaCommonNames.CLASS_CONTENTELEMENT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_VERSIONS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_LATEST));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_RELEASED));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_INPROGRESS));
        ssc = new OdmaStaticSystemClassVersionCollection(null, getSubClasses(OdmaCommonNames.CLASS_VERSIONCOLLECTION), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_VERSIONCOLLECTION), getSearchable(OdmaCommonNames.CLASS_VERSIONCOLLECTION));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_VERSIONCOLLECTION, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_TITLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_CONTAINEES));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_ASSOCIATIONS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_CREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_LASTMODIFIEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_LASTMODIFIEDBY));
        ssc = new OdmaStaticSystemClassContainer(null, getSubClasses(OdmaCommonNames.CLASS_CONTAINER), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_CONTAINER), getSearchable(OdmaCommonNames.CLASS_CONTAINER));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_CONTAINER, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_FOLDER, OdmaCommonNames.PROPERTY_PARENT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_FOLDER, OdmaCommonNames.PROPERTY_SUBFOLDERS));
        ssc = new OdmaStaticSystemClassFolder(getClassInfo(OdmaCommonNames.CLASS_CONTAINER), getSubClasses(OdmaCommonNames.CLASS_FOLDER), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_FOLDER), getSearchable(OdmaCommonNames.CLASS_FOLDER));
        registerSubClass(OdmaCommonNames.CLASS_CONTAINER, ssc);
        classInfos.put(OdmaCommonNames.CLASS_FOLDER, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTAINABLE, OdmaCommonNames.PROPERTY_CONTAINEDIN));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_CONTAINABLE, OdmaCommonNames.PROPERTY_CONTAINEDINASSOCIATIONS));
        ssc = new OdmaStaticSystemClassContainable(null, getSubClasses(OdmaCommonNames.CLASS_CONTAINABLE), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_CONTAINABLE), getSearchable(OdmaCommonNames.CLASS_CONTAINABLE));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_CONTAINABLE, ssc);

        declaredAspects = new ArrayList<OdmaClass>();
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CONTAINER));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CONTAINABLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_LASTMODIFIEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_LASTMODIFIEDBY));
        ssc = new OdmaStaticSystemClassAssociation(null, getSubClasses(OdmaCommonNames.CLASS_ASSOCIATION), Collections.unmodifiableList(declaredAspects), Collections.unmodifiableList(declaredProperties), getRetrievable(OdmaCommonNames.CLASS_ASSOCIATION), getSearchable(OdmaCommonNames.CLASS_ASSOCIATION));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_ASSOCIATION, ssc);

        OdmaClass propertyInfoClass = getClassInfo(OdmaCommonNames.CLASS_PROPERTYINFO);
        Iterator<OdmaStaticSystemPropertyInfo> itPropertyInfos = propertyInfos.values().iterator();
        while(itPropertyInfos.hasNext())
        {
            OdmaStaticSystemPropertyInfo pi = itPropertyInfos.next();
            pi.patchClass(propertyInfoClass);
        }
        OdmaClass classClass = getClassInfo(OdmaCommonNames.CLASS_CLASS);
        Iterator<OdmaStaticSystemClass> itClassInfos = classInfos.values().iterator();
        while(itClassInfos.hasNext())
        {
            OdmaStaticSystemClass ci = itClassInfos.next();
            ci.patchClass(classClass);
        }

        getPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_CLASS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.CLASS_OBJECT, OdmaCommonNames.PROPERTY_REPOSITORY).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_REPOSITORY));
        getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SUPERCLASS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_ASPECTS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_PROPERTYINFO));
        getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_PROPERTIES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_PROPERTYINFO));
        getPropertyInfo(OdmaCommonNames.CLASS_CLASS, OdmaCommonNames.PROPERTY_SUBCLASSES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_REFERENCECLASS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.CLASS_PROPERTYINFO, OdmaCommonNames.PROPERTY_CHOICES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CHOICEVALUE));
        getPropertyInfo(OdmaCommonNames.CLASS_CHOICEVALUE, OdmaCommonNames.PROPERTY_REFERENCEVALUE).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_OBJECT));
        getPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_ROOTCLASS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_ROOTASPECTS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.CLASS_REPOSITORY, OdmaCommonNames.PROPERTY_ROOTFOLDER).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_FOLDER));
        getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_VERSIONCOLLECTION).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION));
        getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_CONTENTELEMENTS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTENTELEMENT));
        getPropertyInfo(OdmaCommonNames.CLASS_DOCUMENT, OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTENTELEMENT));
        getPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_VERSIONS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_DOCUMENT));
        getPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_LATEST).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_DOCUMENT));
        getPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_RELEASED).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_DOCUMENT));
        getPropertyInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION, OdmaCommonNames.PROPERTY_INPROGRESS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_DOCUMENT));
        getPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_CONTAINEES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTAINABLE));
        getPropertyInfo(OdmaCommonNames.CLASS_CONTAINER, OdmaCommonNames.PROPERTY_ASSOCIATIONS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_ASSOCIATION));
        getPropertyInfo(OdmaCommonNames.CLASS_FOLDER, OdmaCommonNames.PROPERTY_PARENT).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_FOLDER));
        getPropertyInfo(OdmaCommonNames.CLASS_FOLDER, OdmaCommonNames.PROPERTY_SUBFOLDERS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_FOLDER));
        getPropertyInfo(OdmaCommonNames.CLASS_CONTAINABLE, OdmaCommonNames.PROPERTY_CONTAINEDIN).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTAINER));
        getPropertyInfo(OdmaCommonNames.CLASS_CONTAINABLE, OdmaCommonNames.PROPERTY_CONTAINEDINASSOCIATIONS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_ASSOCIATION));
        getPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CONTAINER).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTAINER));
        getPropertyInfo(OdmaCommonNames.CLASS_ASSOCIATION, OdmaCommonNames.PROPERTY_CONTAINABLE).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTAINABLE));
    }

}
