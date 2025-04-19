package org.opendma.impl.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    protected Map<OdmaQName, OdmaStaticSystemPropertyInfo> propertyInfos = new HashMap<OdmaQName, OdmaStaticSystemPropertyInfo>();

    protected Map<OdmaQName, OdmaStaticSystemClass> classInfos = new HashMap<OdmaQName, OdmaStaticSystemClass>();

    protected Map<OdmaId, OdmaObject> allAvailableObjects = new HashMap<OdmaId, OdmaObject>();
    
    protected Map<OdmaQName, OdmaProperty> repositoryObjectProperties = new HashMap<OdmaQName, OdmaProperty>();
   
    protected OdmaRepository repositoryObject;

    public OdmaStaticSystemPropertyInfo getPropertyInfo(OdmaQName name) {
        return propertyInfos.get(name);
    }

    public OdmaStaticSystemClass getClassInfo(OdmaQName name) {
        return classInfos.get(name);
    }

    public OdmaObject getObjectById(OdmaId id) throws OdmaObjectNotFoundException {
        OdmaObject o = allAvailableObjects.get(id);
        if(o == null) {
            throw new OdmaObjectNotFoundException(new OdmaGuid(repositoryObject.getId(), id));
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
    
    public OdmaStaticClassHierarchy(String repoName, String repoDisplayName, OdmaId repoId, OdmaGuid repoGuid) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        buildClassHierarchy();
        buildRepositoryObject(repoName,repoDisplayName,repoId,repoGuid);
        generateIds();
        buildAllAvaialbleObjectsMap();
    }
    
    public OdmaStaticClassHierarchy(OdmaRepository repo) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
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

    protected void buildRepositoryObject(String name, String displayName, OdmaId id, OdmaGuid guid) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        OdmaStaticSystemRepository repo = new OdmaStaticSystemRepository(repositoryObjectProperties,name,displayName,id,guid,getClassInfo(OdmaCommonNames.CLASS_OBJECT),rootAspects);
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
            if(!subClasses.contains(subClass)) {
                subClasses.add(subClass);
            }
        }
    }
    
    public void registerAspectUsage(OdmaQName aspectName, OdmaClass usingClass) {
        ArrayList<OdmaClass> subClasses = getInternalSubClassesArrayList(aspectName);
        synchronized(subClasses) {
            if(!subClasses.contains(usingClass)) {
                subClasses.add(usingClass);
            }
        }
    }
    
    protected ArrayList<OdmaClass> rootAspects = new ArrayList<OdmaClass>();
    
    public void registerRootAspect(OdmaClass aspect) {
        synchronized(rootAspects) {
            if(!rootAspects.contains(aspect)) {
                rootAspects.add(aspect);
            }
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
        allAvailableObjects.put(repositoryObject.getId(),repositoryObject);
    }

    protected void generateIds() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        OdmaId repoId = repositoryObject.getId();
        Iterator<OdmaStaticSystemPropertyInfo> itPropertyInfos = propertyInfos.values().iterator();
        while(itPropertyInfos.hasNext()) {
            OdmaStaticSystemPropertyInfo pi = itPropertyInfos.next();
            OdmaId piId = new OdmaId(pi.getNamespace()+"Property"+pi.getName());
            OdmaGuid piGuid = new OdmaGuid(repoId,piId);
            pi.patchIds(piId,piGuid);
        }
        Iterator<OdmaStaticSystemClass> itClassInfos = classInfos.values().iterator();
        while(itClassInfos.hasNext()) {
            OdmaStaticSystemClass ci = itClassInfos.next();
            OdmaId ciId = new OdmaId(ci.getNamespace()+"Class"+ci.getName());
            OdmaGuid ciGuid = new OdmaGuid(repoId,ciId);
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

        propertyInfos.put(OdmaCommonNames.PROPERTY_CLASS, new OdmaStaticSystemPropertyInfoObjectClass());
        propertyInfos.put(OdmaCommonNames.PROPERTY_ID, new OdmaStaticSystemPropertyInfoObjectId());
        propertyInfos.put(OdmaCommonNames.PROPERTY_GUID, new OdmaStaticSystemPropertyInfoObjectGuid());
        propertyInfos.put(OdmaCommonNames.PROPERTY_REPOSITORY, new OdmaStaticSystemPropertyInfoObjectRepository());
        propertyInfos.put(OdmaCommonNames.PROPERTY_NAME, new OdmaStaticSystemPropertyInfoClassName());
        propertyInfos.put(OdmaCommonNames.PROPERTY_NAMESPACE, new OdmaStaticSystemPropertyInfoClassNamespace());
        propertyInfos.put(OdmaCommonNames.PROPERTY_DISPLAYNAME, new OdmaStaticSystemPropertyInfoClassDisplayName());
        propertyInfos.put(OdmaCommonNames.PROPERTY_SUPERCLASS, new OdmaStaticSystemPropertyInfoClassSuperClass());
        propertyInfos.put(OdmaCommonNames.PROPERTY_ASPECTS, new OdmaStaticSystemPropertyInfoClassAspects());
        propertyInfos.put(OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES, new OdmaStaticSystemPropertyInfoClassDeclaredProperties());
        propertyInfos.put(OdmaCommonNames.PROPERTY_PROPERTIES, new OdmaStaticSystemPropertyInfoClassProperties());
        propertyInfos.put(OdmaCommonNames.PROPERTY_ASPECT, new OdmaStaticSystemPropertyInfoClassAspect());
        propertyInfos.put(OdmaCommonNames.PROPERTY_INSTANTIABLE, new OdmaStaticSystemPropertyInfoClassInstantiable());
        propertyInfos.put(OdmaCommonNames.PROPERTY_HIDDEN, new OdmaStaticSystemPropertyInfoClassHidden());
        propertyInfos.put(OdmaCommonNames.PROPERTY_SYSTEM, new OdmaStaticSystemPropertyInfoClassSystem());
        propertyInfos.put(OdmaCommonNames.PROPERTY_RETRIEVABLE, new OdmaStaticSystemPropertyInfoClassRetrievable());
        propertyInfos.put(OdmaCommonNames.PROPERTY_SEARCHABLE, new OdmaStaticSystemPropertyInfoClassSearchable());
        propertyInfos.put(OdmaCommonNames.PROPERTY_SUBCLASSES, new OdmaStaticSystemPropertyInfoClassSubClasses());
        propertyInfos.put(OdmaCommonNames.PROPERTY_DATATYPE, new OdmaStaticSystemPropertyInfoPropertyInfoDataType());
        propertyInfos.put(OdmaCommonNames.PROPERTY_REFERENCECLASS, new OdmaStaticSystemPropertyInfoPropertyInfoReferenceClass());
        propertyInfos.put(OdmaCommonNames.PROPERTY_MULTIVALUE, new OdmaStaticSystemPropertyInfoPropertyInfoMultiValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_REQUIRED, new OdmaStaticSystemPropertyInfoPropertyInfoRequired());
        propertyInfos.put(OdmaCommonNames.PROPERTY_READONLY, new OdmaStaticSystemPropertyInfoPropertyInfoReadOnly());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CHOICES, new OdmaStaticSystemPropertyInfoPropertyInfoChoices());
        propertyInfos.put(OdmaCommonNames.PROPERTY_STRINGVALUE, new OdmaStaticSystemPropertyInfoChoiceValueStringValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_INTEGERVALUE, new OdmaStaticSystemPropertyInfoChoiceValueIntegerValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_SHORTVALUE, new OdmaStaticSystemPropertyInfoChoiceValueShortValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_LONGVALUE, new OdmaStaticSystemPropertyInfoChoiceValueLongValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_FLOATVALUE, new OdmaStaticSystemPropertyInfoChoiceValueFloatValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_DOUBLEVALUE, new OdmaStaticSystemPropertyInfoChoiceValueDoubleValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_BOOLEANVALUE, new OdmaStaticSystemPropertyInfoChoiceValueBooleanValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_DATETIMEVALUE, new OdmaStaticSystemPropertyInfoChoiceValueDateTimeValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_BLOBVALUE, new OdmaStaticSystemPropertyInfoChoiceValueBLOBValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_REFERENCEVALUE, new OdmaStaticSystemPropertyInfoChoiceValueReferenceValue());
        propertyInfos.put(OdmaCommonNames.PROPERTY_ROOTCLASS, new OdmaStaticSystemPropertyInfoRepositoryRootClass());
        propertyInfos.put(OdmaCommonNames.PROPERTY_ROOTASPECTS, new OdmaStaticSystemPropertyInfoRepositoryRootAspects());
        propertyInfos.put(OdmaCommonNames.PROPERTY_ROOTFOLDER, new OdmaStaticSystemPropertyInfoRepositoryRootFolder());
        propertyInfos.put(OdmaCommonNames.PROPERTY_TITLE, new OdmaStaticSystemPropertyInfoDocumentTitle());
        propertyInfos.put(OdmaCommonNames.PROPERTY_VERSION, new OdmaStaticSystemPropertyInfoDocumentVersion());
        propertyInfos.put(OdmaCommonNames.PROPERTY_VERSIONCOLLECTION, new OdmaStaticSystemPropertyInfoDocumentVersionCollection());
        propertyInfos.put(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTID, new OdmaStaticSystemPropertyInfoDocumentVersionIndependentId());
        propertyInfos.put(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTGUID, new OdmaStaticSystemPropertyInfoDocumentVersionIndependentGuid());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CONTENTELEMENTS, new OdmaStaticSystemPropertyInfoDocumentContentElements());
        propertyInfos.put(OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE, new OdmaStaticSystemPropertyInfoDocumentCombinedContentType());
        propertyInfos.put(OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT, new OdmaStaticSystemPropertyInfoDocumentPrimaryContentElement());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CREATEDAT, new OdmaStaticSystemPropertyInfoDocumentCreatedAt());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CREATEDBY, new OdmaStaticSystemPropertyInfoDocumentCreatedBy());
        propertyInfos.put(OdmaCommonNames.PROPERTY_LASTMODIFIEDAT, new OdmaStaticSystemPropertyInfoDocumentLastModifiedAt());
        propertyInfos.put(OdmaCommonNames.PROPERTY_LASTMODIFIEDBY, new OdmaStaticSystemPropertyInfoDocumentLastModifiedBy());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CHECKEDOUT, new OdmaStaticSystemPropertyInfoDocumentCheckedOut());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CHECKEDOUTAT, new OdmaStaticSystemPropertyInfoDocumentCheckedOutAt());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CHECKEDOUTBY, new OdmaStaticSystemPropertyInfoDocumentCheckedOutBy());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CONTENTTYPE, new OdmaStaticSystemPropertyInfoContentElementContentType());
        propertyInfos.put(OdmaCommonNames.PROPERTY_POSITION, new OdmaStaticSystemPropertyInfoContentElementPosition());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CONTENT, new OdmaStaticSystemPropertyInfoDataContentElementContent());
        propertyInfos.put(OdmaCommonNames.PROPERTY_SIZE, new OdmaStaticSystemPropertyInfoDataContentElementSize());
        propertyInfos.put(OdmaCommonNames.PROPERTY_FILENAME, new OdmaStaticSystemPropertyInfoDataContentElementFileName());
        propertyInfos.put(OdmaCommonNames.PROPERTY_LOCATION, new OdmaStaticSystemPropertyInfoReferenceContentElementLocation());
        propertyInfos.put(OdmaCommonNames.PROPERTY_VERSIONS, new OdmaStaticSystemPropertyInfoVersionCollectionVersions());
        propertyInfos.put(OdmaCommonNames.PROPERTY_LATEST, new OdmaStaticSystemPropertyInfoVersionCollectionLatest());
        propertyInfos.put(OdmaCommonNames.PROPERTY_RELEASED, new OdmaStaticSystemPropertyInfoVersionCollectionReleased());
        propertyInfos.put(OdmaCommonNames.PROPERTY_INPROGRESS, new OdmaStaticSystemPropertyInfoVersionCollectionInProgress());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CONTAINEES, new OdmaStaticSystemPropertyInfoContainerContainees());
        propertyInfos.put(OdmaCommonNames.PROPERTY_ASSOCIATIONS, new OdmaStaticSystemPropertyInfoContainerAssociations());
        propertyInfos.put(OdmaCommonNames.PROPERTY_PARENT, new OdmaStaticSystemPropertyInfoFolderParent());
        propertyInfos.put(OdmaCommonNames.PROPERTY_SUBFOLDERS, new OdmaStaticSystemPropertyInfoFolderSubFolders());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CONTAINEDIN, new OdmaStaticSystemPropertyInfoContainableContainedIn());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CONTAINEDINASSOCIATIONS, new OdmaStaticSystemPropertyInfoContainableContainedInAssociations());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CONTAINER, new OdmaStaticSystemPropertyInfoAssociationContainer());
        propertyInfos.put(OdmaCommonNames.PROPERTY_CONTAINABLE, new OdmaStaticSystemPropertyInfoAssociationContainable());

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CLASS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_ID));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_GUID));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_REPOSITORY));
        ssc = new OdmaStaticSystemClassObject(null,getSubClasses(OdmaCommonNames.CLASS_OBJECT),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_OBJECT),getSearchable(OdmaCommonNames.CLASS_OBJECT));
        classInfos.put(OdmaCommonNames.CLASS_OBJECT, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_NAMESPACE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_SUPERCLASS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_ASPECTS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_PROPERTIES));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_ASPECT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_INSTANTIABLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_HIDDEN));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_SYSTEM));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_RETRIEVABLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_SEARCHABLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_SUBCLASSES));
        ssc = new OdmaStaticSystemClassClass(getClassInfo(OdmaCommonNames.CLASS_OBJECT),getSubClasses(OdmaCommonNames.CLASS_CLASS),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_CLASS),getSearchable(OdmaCommonNames.CLASS_CLASS));
        registerSubClass(OdmaCommonNames.CLASS_OBJECT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_CLASS, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_NAMESPACE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_DATATYPE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_REFERENCECLASS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_MULTIVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_REQUIRED));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_READONLY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_HIDDEN));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_SYSTEM));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CHOICES));
        ssc = new OdmaStaticSystemClassPropertyInfo(getClassInfo(OdmaCommonNames.CLASS_OBJECT),getSubClasses(OdmaCommonNames.CLASS_PROPERTYINFO),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_PROPERTYINFO),getSearchable(OdmaCommonNames.CLASS_PROPERTYINFO));
        registerSubClass(OdmaCommonNames.CLASS_OBJECT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_PROPERTYINFO, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_STRINGVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_INTEGERVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_SHORTVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_LONGVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_FLOATVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_DOUBLEVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_BOOLEANVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_DATETIMEVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_BLOBVALUE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_REFERENCEVALUE));
        ssc = new OdmaStaticSystemClassChoiceValue(getClassInfo(OdmaCommonNames.CLASS_OBJECT),getSubClasses(OdmaCommonNames.CLASS_CHOICEVALUE),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_CHOICEVALUE),getSearchable(OdmaCommonNames.CLASS_CHOICEVALUE));
        registerSubClass(OdmaCommonNames.CLASS_OBJECT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_CHOICEVALUE, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_ROOTCLASS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_ROOTASPECTS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_ROOTFOLDER));
        ssc = new OdmaStaticSystemClassRepository(getClassInfo(OdmaCommonNames.CLASS_OBJECT),getSubClasses(OdmaCommonNames.CLASS_REPOSITORY),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_REPOSITORY),getSearchable(OdmaCommonNames.CLASS_REPOSITORY));
        registerSubClass(OdmaCommonNames.CLASS_OBJECT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_REPOSITORY, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_TITLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_VERSION));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_VERSIONCOLLECTION));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTID));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTGUID));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CONTENTELEMENTS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_LASTMODIFIEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_LASTMODIFIEDBY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CHECKEDOUT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CHECKEDOUTAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CHECKEDOUTBY));
        ssc = new OdmaStaticSystemClassDocument(null,getSubClasses(OdmaCommonNames.CLASS_DOCUMENT),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_DOCUMENT),getSearchable(OdmaCommonNames.CLASS_DOCUMENT));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_DOCUMENT, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CONTENTTYPE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_POSITION));
        ssc = new OdmaStaticSystemClassContentElement(null,getSubClasses(OdmaCommonNames.CLASS_CONTENTELEMENT),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_CONTENTELEMENT),getSearchable(OdmaCommonNames.CLASS_CONTENTELEMENT));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_CONTENTELEMENT, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CONTENT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_SIZE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_FILENAME));
        ssc = new OdmaStaticSystemClassDataContentElement(getClassInfo(OdmaCommonNames.CLASS_CONTENTELEMENT),getSubClasses(OdmaCommonNames.CLASS_DATACONTENTELEMENT),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_DATACONTENTELEMENT),getSearchable(OdmaCommonNames.CLASS_DATACONTENTELEMENT));
        registerSubClass(OdmaCommonNames.CLASS_CONTENTELEMENT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_DATACONTENTELEMENT, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_LOCATION));
        ssc = new OdmaStaticSystemClassReferenceContentElement(getClassInfo(OdmaCommonNames.CLASS_CONTENTELEMENT),getSubClasses(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT),getSearchable(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT));
        registerSubClass(OdmaCommonNames.CLASS_CONTENTELEMENT, ssc);
        classInfos.put(OdmaCommonNames.CLASS_REFERENCECONTENTELEMENT, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_VERSIONS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_LATEST));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_RELEASED));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_INPROGRESS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CREATEDBY));
        ssc = new OdmaStaticSystemClassVersionCollection(null,getSubClasses(OdmaCommonNames.CLASS_VERSIONCOLLECTION),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_VERSIONCOLLECTION),getSearchable(OdmaCommonNames.CLASS_VERSIONCOLLECTION));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_VERSIONCOLLECTION, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_TITLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINEES));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_ASSOCIATIONS));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_LASTMODIFIEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_LASTMODIFIEDBY));
        ssc = new OdmaStaticSystemClassContainer(null,getSubClasses(OdmaCommonNames.CLASS_CONTAINER),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_CONTAINER),getSearchable(OdmaCommonNames.CLASS_CONTAINER));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_CONTAINER, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_PARENT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_SUBFOLDERS));
        ssc = new OdmaStaticSystemClassFolder(getClassInfo(OdmaCommonNames.CLASS_CONTAINER),getSubClasses(OdmaCommonNames.CLASS_FOLDER),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_FOLDER),getSearchable(OdmaCommonNames.CLASS_FOLDER));
        registerSubClass(OdmaCommonNames.CLASS_CONTAINER, ssc);
        classInfos.put(OdmaCommonNames.CLASS_FOLDER, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINEDIN));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINEDINASSOCIATIONS));
        ssc = new OdmaStaticSystemClassContainable(null,getSubClasses(OdmaCommonNames.CLASS_CONTAINABLE),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_CONTAINABLE),getSearchable(OdmaCommonNames.CLASS_CONTAINABLE));
        registerRootAspect(ssc);
        classInfos.put(OdmaCommonNames.CLASS_CONTAINABLE, ssc);

        declaredAspects = null;
        declaredProperties = new ArrayList<OdmaPropertyInfo>();
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINER));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINABLE));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_CREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_LASTMODIFIEDAT));
        declaredProperties.add(getPropertyInfo(OdmaCommonNames.PROPERTY_LASTMODIFIEDBY));
        ssc = new OdmaStaticSystemClassAssociation(null,getSubClasses(OdmaCommonNames.CLASS_ASSOCIATION),declaredAspects,declaredProperties,getRetrievable(OdmaCommonNames.CLASS_ASSOCIATION),getSearchable(OdmaCommonNames.CLASS_ASSOCIATION));
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

        getPropertyInfo(OdmaCommonNames.PROPERTY_CLASS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.PROPERTY_REPOSITORY).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_REPOSITORY));
        getPropertyInfo(OdmaCommonNames.PROPERTY_SUPERCLASS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.PROPERTY_ASPECTS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_PROPERTYINFO));
        getPropertyInfo(OdmaCommonNames.PROPERTY_PROPERTIES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_PROPERTYINFO));
        getPropertyInfo(OdmaCommonNames.PROPERTY_SUBCLASSES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.PROPERTY_REFERENCECLASS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.PROPERTY_CHOICES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CHOICEVALUE));
        getPropertyInfo(OdmaCommonNames.PROPERTY_ROOTCLASS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.PROPERTY_ROOTASPECTS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CLASS));
        getPropertyInfo(OdmaCommonNames.PROPERTY_ROOTFOLDER).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_FOLDER));
        getPropertyInfo(OdmaCommonNames.PROPERTY_VERSIONCOLLECTION).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_VERSIONCOLLECTION));
        getPropertyInfo(OdmaCommonNames.PROPERTY_CONTENTELEMENTS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTENTELEMENT));
        getPropertyInfo(OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTENTELEMENT));
        getPropertyInfo(OdmaCommonNames.PROPERTY_VERSIONS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_DOCUMENT));
        getPropertyInfo(OdmaCommonNames.PROPERTY_LATEST).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_DOCUMENT));
        getPropertyInfo(OdmaCommonNames.PROPERTY_RELEASED).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_DOCUMENT));
        getPropertyInfo(OdmaCommonNames.PROPERTY_INPROGRESS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_DOCUMENT));
        getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINEES).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTAINABLE));
        getPropertyInfo(OdmaCommonNames.PROPERTY_ASSOCIATIONS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_ASSOCIATION));
        getPropertyInfo(OdmaCommonNames.PROPERTY_PARENT).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_FOLDER));
        getPropertyInfo(OdmaCommonNames.PROPERTY_SUBFOLDERS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_FOLDER));
        getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINEDIN).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTAINER));
        getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINEDINASSOCIATIONS).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_ASSOCIATION));
        getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINER).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTAINER));
        getPropertyInfo(OdmaCommonNames.PROPERTY_CONTAINABLE).patchReferenceClass(getClassInfo(OdmaCommonNames.CLASS_CONTAINABLE));
    }

}
