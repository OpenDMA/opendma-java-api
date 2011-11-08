package org.opendma.impl.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.opendma.OdmaTypes;
import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaGuid;
import org.opendma.api.OdmaId;
import org.opendma.api.OdmaObject;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaRepository;
import org.opendma.api.collections.OdmaClassEnumeration;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaObjectNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;

public class OdmaStaticClassHierarchy
{

    protected Map propertyInfos = new HashMap();

    protected Map classInfos = new HashMap();

    protected Map allAvailableObjects = new HashMap();
    
    protected Map repositoryObjectProperties = new HashMap();
   
    protected OdmaRepository repositoryObject;

    public OdmaStaticSystemPropertyInfo getPropertyInfo(OdmaQName name)
    {
        return(OdmaStaticSystemPropertyInfo)propertyInfos.get(name);
    }

    public OdmaStaticSystemClass getClassInfo(OdmaQName name)
    {
        return(OdmaStaticSystemClass)classInfos.get(name);
    }

    public OdmaObject getObjectById(OdmaId id) throws OdmaObjectNotFoundException
    {
        OdmaObject o = (OdmaObject)allAvailableObjects.get(id.toString());
        if(o == null)
        {
            throw new OdmaObjectNotFoundException(id);
        }
        return o;
    }
    
    public Map getAllObjectsById()
    {
        return allAvailableObjects;
    }
    
    public Map getAllClassesByName()
    {
        return classInfos;
    }
    
    public Map getRepositoryObjectProperties()
    {
        return repositoryObjectProperties;
    }
    
    public OdmaStaticClassHierarchy(String repoName, String repoDisplayName, OdmaId repoId, OdmaGuid repoGuid) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        buildClassHierarchy();
        buildRepositoryObject(repoName,repoDisplayName,repoId,repoGuid);
        generateIds();
        buildAllAvaialbleObjectsMap();
    }
    
    public OdmaStaticClassHierarchy(OdmaRepository repo) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        buildClassHierarchy();
        repositoryObject = repo;
        setRepositoryObject();
        generateIds();
        buildAllAvaialbleObjectsMap();
    }

    protected void buildAllAvaialbleObjectsMap()
    {
        // all properties
        Iterator itPropertyInfos = propertyInfos.values().iterator();
        while(itPropertyInfos.hasNext())
        {
            OdmaStaticSystemPropertyInfo pi = (OdmaStaticSystemPropertyInfo)itPropertyInfos.next();
            allAvailableObjects.put(pi.getId().toString(),pi);
        }
        // all classes
        Iterator itClassInfos = classInfos.values().iterator();
        while(itClassInfos.hasNext())
        {
            OdmaStaticSystemClass ci = (OdmaStaticSystemClass)itClassInfos.next();
            allAvailableObjects.put(ci.getId().toString(),ci);
        }
        // the repository
        allAvailableObjects.put(repositoryObject.getId().toString(),repositoryObject);
    }

    protected void buildRepositoryObject(String name, String displayName, OdmaId id, OdmaGuid guid) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        OdmaStaticSystemRepository repo = new OdmaStaticSystemRepository(repositoryObjectProperties,name,displayName,id,guid,getClassInfo(OdmaTypes.CLASS_OBJECT),rootAspects);
        repo.patchClass(getClassInfo(OdmaTypes.CLASS_REPOSITORY));
        repositoryObject = repo;
        setRepositoryObject();
    }

    protected void setRepositoryObject() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        Iterator itPropertyInfos = propertyInfos.values().iterator();
        while(itPropertyInfos.hasNext())
        {
            OdmaStaticSystemPropertyInfo pi = (OdmaStaticSystemPropertyInfo)itPropertyInfos.next();
            pi.patchRepository(repositoryObject);
        }
        Iterator itClassInfos = classInfos.values().iterator();
        while(itClassInfos.hasNext())
        {
            OdmaStaticSystemClass ci = (OdmaStaticSystemClass)itClassInfos.next();
            ci.patchRepository(repositoryObject);
        }
    }
    
    protected HashMap subClassesEnumerations = new HashMap();
    
    public OdmaClassEnumeration getSubClassesEnumeration(OdmaQName className)
    {
        OdmaClassEnumeration result = (OdmaClassEnumeration)subClassesEnumerations.get(className);
        if(result != null)
        {
            return result;
        }
        synchronized(subClassesEnumerations)
        {
            result = (OdmaClassEnumeration)subClassesEnumerations.get(className);
            if(result != null)
            {
                return result;
            }
            result = new OdmaArrayListClassEnumeration();
            subClassesEnumerations.put(className,result);
            return result;
        }
    }
    
    public void registerSubClass(OdmaQName parentName, OdmaClass subClass)
    {
        if(!parentName.equals(subClass.getParent().getQName()))
        {
            throw new OdmaRuntimeException("Name of parent class does not equal regsitered parent");
        }
        OdmaArrayListClassEnumeration subClasses = (OdmaArrayListClassEnumeration)getSubClassesEnumeration(parentName);
        synchronized(subClasses)
        {
            if(!subClasses.contains(subClass))
            {
                subClasses.add(subClass);
            }
        }
    }
    
    public void registerAspectUsage(OdmaQName aspectName, OdmaClass usingClass)
    {
        OdmaArrayListClassEnumeration subClasses = (OdmaArrayListClassEnumeration)getSubClassesEnumeration(aspectName);
        synchronized(subClasses)
        {
            if(!subClasses.contains(usingClass))
            {
                subClasses.add(usingClass);
            }
        }
    }
    
    protected OdmaArrayListClassEnumeration rootAspects = new OdmaArrayListClassEnumeration();
    
    public void registerRootAspect(OdmaClass aspect)
    {
        synchronized(rootAspects)
        {
            if(!rootAspects.contains(aspect))
            {
                rootAspects.add(aspect);
            }
        }
    }
    
    public OdmaArrayListClassEnumeration getRootAspects()
    {
        return rootAspects;
    }
    
    public boolean isOrIsAncestorOf(OdmaClass c, OdmaQName testAgainst)
    {
        OdmaClass test = c;
        HashMap noLoopTest = new HashMap();
        while(test != null)
        {
            noLoopTest.put(test.getQName(),Boolean.TRUE);
            if(test.getQName().equals(testAgainst))
            {
                return true;
            }
            test = test.getParent();
            if((test != null)&&(noLoopTest.containsKey(test.getQName())))
            {
                throw new OdmaRuntimeException("Loop in class hierarchy of "+c.getQName());
            }
        }
        return false;
    }
    
    public boolean isOrIsExtending(OdmaClass c, OdmaQName testAgainst)
    {
        // test parent relationsship
        if(isOrIsAncestorOf(c,testAgainst))
        {
            return true;
        }
        // test aspects
        OdmaClassEnumeration aspects = c.getAspects();
        if(aspects != null)
        {
            Iterator itAspects = aspects.iterator();
            while(itAspects.hasNext())
            {
                OdmaClass aspect = (OdmaClass)itAspects.next();
                if(isOrIsAncestorOf(aspect,testAgainst))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateRepositoryObject(Map properties)
    {
        // remove id from map
        allAvailableObjects.remove(repositoryObject.getId().toString());
        // copy all system values that must not be changed
        properties.put(OdmaTypes.PROPERTY_ID, repositoryObjectProperties.get(OdmaTypes.PROPERTY_ID));
        properties.put(OdmaTypes.PROPERTY_GUID, repositoryObjectProperties.get(OdmaTypes.PROPERTY_GUID));
        properties.put(OdmaTypes.PROPERTY_REPOSITORY, repositoryObjectProperties.get(OdmaTypes.PROPERTY_REPOSITORY));
        properties.put(OdmaTypes.PROPERTY_ROOTCLASS, repositoryObjectProperties.get(OdmaTypes.PROPERTY_ROOTCLASS));
        properties.put(OdmaTypes.PROPERTY_ROOTASPECTS, repositoryObjectProperties.get(OdmaTypes.PROPERTY_ROOTASPECTS));
        // replace properties
        repositoryObjectProperties.clear();
        repositoryObjectProperties.putAll(properties);
        // put back in map
        allAvailableObjects.put(repositoryObject.getId().toString(),repositoryObject);
    }

    protected void generateIds() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        OdmaId repoId = repositoryObject.getId();
        Iterator itPropertyInfos = propertyInfos.values().iterator();
        while(itPropertyInfos.hasNext())
        {
            OdmaStaticSystemPropertyInfo pi = (OdmaStaticSystemPropertyInfo)itPropertyInfos.next();
            OdmaCoreId piId = new OdmaCoreId(pi.getNameQualifier()+"Property"+pi.getName());
            OdmaCoreGuid piGuid = new OdmaCoreGuid(repoId,piId);
            pi.patchIds(piId,piGuid);
        }
        Iterator itClassInfos = classInfos.values().iterator();
        while(itClassInfos.hasNext())
        {
            OdmaStaticSystemClass ci = (OdmaStaticSystemClass)itClassInfos.next();
            OdmaCoreId ciId = new OdmaCoreId(ci.getNameQualifier()+"Class"+ci.getName());
            OdmaCoreGuid ciGuid = new OdmaCoreGuid(repoId,ciId);
            ci.patchIds(ciId,ciGuid);
        }
    }
    
    public void buildClassHierarchy() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        OdmaArrayListClassEnumeration declaredAspects;
        OdmaArrayListPropertyInfoEnumeration declaredProperties;
        OdmaStaticSystemClass ssc;

        propertyInfos.put(OdmaTypes.PROPERTY_CLASS, new OdmaStaticSystemPropertyInfoClass());
        propertyInfos.put(OdmaTypes.PROPERTY_ID, new OdmaStaticSystemPropertyInfoId());
        propertyInfos.put(OdmaTypes.PROPERTY_GUID, new OdmaStaticSystemPropertyInfoGuid());
        propertyInfos.put(OdmaTypes.PROPERTY_REPOSITORY, new OdmaStaticSystemPropertyInfoRepository());
        propertyInfos.put(OdmaTypes.PROPERTY_NAME, new OdmaStaticSystemPropertyInfoName());
        propertyInfos.put(OdmaTypes.PROPERTY_NAMEQUALIFIER, new OdmaStaticSystemPropertyInfoNameQualifier());
        propertyInfos.put(OdmaTypes.PROPERTY_DISPLAYNAME, new OdmaStaticSystemPropertyInfoDisplayName());
        propertyInfos.put(OdmaTypes.PROPERTY_PARENT, new OdmaStaticSystemPropertyInfoParent());
        propertyInfos.put(OdmaTypes.PROPERTY_ASPECTS, new OdmaStaticSystemPropertyInfoAspects());
        propertyInfos.put(OdmaTypes.PROPERTY_DECLAREDPROPERTIES, new OdmaStaticSystemPropertyInfoDeclaredProperties());
        propertyInfos.put(OdmaTypes.PROPERTY_PROPERTIES, new OdmaStaticSystemPropertyInfoProperties());
        propertyInfos.put(OdmaTypes.PROPERTY_INSTANTIABLE, new OdmaStaticSystemPropertyInfoInstantiable());
        propertyInfos.put(OdmaTypes.PROPERTY_HIDDEN, new OdmaStaticSystemPropertyInfoHidden());
        propertyInfos.put(OdmaTypes.PROPERTY_SYSTEM, new OdmaStaticSystemPropertyInfoSystem());
        propertyInfos.put(OdmaTypes.PROPERTY_SUBCLASSES, new OdmaStaticSystemPropertyInfoSubClasses());
        propertyInfos.put(OdmaTypes.PROPERTY_DATATYPE, new OdmaStaticSystemPropertyInfoDataType());
        propertyInfos.put(OdmaTypes.PROPERTY_REFERENCECLASS, new OdmaStaticSystemPropertyInfoReferenceClass());
        propertyInfos.put(OdmaTypes.PROPERTY_MULTIVALUE, new OdmaStaticSystemPropertyInfoMultiValue());
        propertyInfos.put(OdmaTypes.PROPERTY_REQUIRED, new OdmaStaticSystemPropertyInfoRequired());
        propertyInfos.put(OdmaTypes.PROPERTY_READONLY, new OdmaStaticSystemPropertyInfoReadOnly());
        propertyInfos.put(OdmaTypes.PROPERTY_ROOTCLASS, new OdmaStaticSystemPropertyInfoRootClass());
        propertyInfos.put(OdmaTypes.PROPERTY_ROOTASPECTS, new OdmaStaticSystemPropertyInfoRootAspects());
        propertyInfos.put(OdmaTypes.PROPERTY_ROOTFOLDER, new OdmaStaticSystemPropertyInfoRootFolder());
        propertyInfos.put(OdmaTypes.PROPERTY_VERSIONSPECIFICID, new OdmaStaticSystemPropertyInfoVersionSpecificId());
        propertyInfos.put(OdmaTypes.PROPERTY_VERSIONSPECIFICGUID, new OdmaStaticSystemPropertyInfoVersionSpecificGuid());
        propertyInfos.put(OdmaTypes.PROPERTY_TITLE, new OdmaStaticSystemPropertyInfoTitle());
        propertyInfos.put(OdmaTypes.PROPERTY_VERSION, new OdmaStaticSystemPropertyInfoVersion());
        propertyInfos.put(OdmaTypes.PROPERTY_VERSIONCOLLECTION, new OdmaStaticSystemPropertyInfoVersionCollection());
        propertyInfos.put(OdmaTypes.PROPERTY_CONTENTELEMENTS, new OdmaStaticSystemPropertyInfoContentElements());
        propertyInfos.put(OdmaTypes.PROPERTY_COMBINEDMIMETYPE, new OdmaStaticSystemPropertyInfoCombinedMimeType());
        propertyInfos.put(OdmaTypes.PROPERTY_PRIMARYCONTENTELEMENT, new OdmaStaticSystemPropertyInfoPrimaryContentElement());
        propertyInfos.put(OdmaTypes.PROPERTY_CHECKEDOUT, new OdmaStaticSystemPropertyInfoCheckedOut());
        propertyInfos.put(OdmaTypes.PROPERTY_CREATEDAT, new OdmaStaticSystemPropertyInfoCreatedAt());
        propertyInfos.put(OdmaTypes.PROPERTY_VERSIONCREATEDAT, new OdmaStaticSystemPropertyInfoVersionCreatedAt());
        propertyInfos.put(OdmaTypes.PROPERTY_LASTMODIFIEDAT, new OdmaStaticSystemPropertyInfoLastModifiedAt());
        propertyInfos.put(OdmaTypes.PROPERTY_CHECKEDOUTAT, new OdmaStaticSystemPropertyInfoCheckedOutAt());
        propertyInfos.put(OdmaTypes.PROPERTY_CREATEDBY, new OdmaStaticSystemPropertyInfoCreatedBy());
        propertyInfos.put(OdmaTypes.PROPERTY_VERSIONCREATEDBY, new OdmaStaticSystemPropertyInfoVersionCreatedBy());
        propertyInfos.put(OdmaTypes.PROPERTY_LASTMODIFIEDBY, new OdmaStaticSystemPropertyInfoLastModifiedBy());
        propertyInfos.put(OdmaTypes.PROPERTY_CHECKEDOUTBY, new OdmaStaticSystemPropertyInfoCheckedOutBy());
        propertyInfos.put(OdmaTypes.PROPERTY_CONTENT, new OdmaStaticSystemPropertyInfoContent());
        propertyInfos.put(OdmaTypes.PROPERTY_SIZE, new OdmaStaticSystemPropertyInfoSize());
        propertyInfos.put(OdmaTypes.PROPERTY_MIMETYPE, new OdmaStaticSystemPropertyInfoMimeType());
        propertyInfos.put(OdmaTypes.PROPERTY_FILENAME, new OdmaStaticSystemPropertyInfoFileName());
        propertyInfos.put(OdmaTypes.PROPERTY_VERSIONS, new OdmaStaticSystemPropertyInfoVersions());
        propertyInfos.put(OdmaTypes.PROPERTY_LATEST, new OdmaStaticSystemPropertyInfoLatest());
        propertyInfos.put(OdmaTypes.PROPERTY_RELEASED, new OdmaStaticSystemPropertyInfoReleased());
        propertyInfos.put(OdmaTypes.PROPERTY_INPROGRESS, new OdmaStaticSystemPropertyInfoInProgress());
        propertyInfos.put(OdmaTypes.PROPERTY_CONTAINEES, new OdmaStaticSystemPropertyInfoContainees());
        propertyInfos.put(OdmaTypes.PROPERTY_ASSOCIATIONS, new OdmaStaticSystemPropertyInfoAssociations());
        propertyInfos.put(OdmaTypes.PROPERTY_SUBFOLDERS, new OdmaStaticSystemPropertyInfoSubFolders());
        propertyInfos.put(OdmaTypes.PROPERTY_CONTAINEDIN, new OdmaStaticSystemPropertyInfoContainedIn());
        propertyInfos.put(OdmaTypes.PROPERTY_CONTAINEDINASSOCIATIONS, new OdmaStaticSystemPropertyInfoContainedInAssociations());
        propertyInfos.put(OdmaTypes.PROPERTY_CONTAINER, new OdmaStaticSystemPropertyInfoContainer());
        propertyInfos.put(OdmaTypes.PROPERTY_CONTAINMENT, new OdmaStaticSystemPropertyInfoContainment());

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CLASS));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_ID));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_GUID));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_REPOSITORY));
        ssc = new OdmaStaticSystemClassObject(null,getSubClassesEnumeration(OdmaTypes.CLASS_OBJECT),declaredAspects,declaredProperties);
        classInfos.put(OdmaTypes.CLASS_OBJECT, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_NAMEQUALIFIER));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_PARENT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_ASPECTS));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_DECLAREDPROPERTIES));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_PROPERTIES));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_INSTANTIABLE));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_HIDDEN));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_SYSTEM));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_SUBCLASSES));
        ssc = new OdmaStaticSystemClassClass(getClassInfo(OdmaTypes.CLASS_OBJECT),getSubClassesEnumeration(OdmaTypes.CLASS_CLASS),declaredAspects,declaredProperties);
        registerSubClass(OdmaTypes.CLASS_OBJECT, ssc);
        classInfos.put(OdmaTypes.CLASS_CLASS, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_NAMEQUALIFIER));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_DATATYPE));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_REFERENCECLASS));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_MULTIVALUE));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_REQUIRED));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_READONLY));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_HIDDEN));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_SYSTEM));
        ssc = new OdmaStaticSystemClassPropertyInfo(getClassInfo(OdmaTypes.CLASS_OBJECT),getSubClassesEnumeration(OdmaTypes.CLASS_PROPERTYINFO),declaredAspects,declaredProperties);
        registerSubClass(OdmaTypes.CLASS_OBJECT, ssc);
        classInfos.put(OdmaTypes.CLASS_PROPERTYINFO, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_DISPLAYNAME));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_ROOTCLASS));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_ROOTASPECTS));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_ROOTFOLDER));
        ssc = new OdmaStaticSystemClassRepository(getClassInfo(OdmaTypes.CLASS_OBJECT),getSubClassesEnumeration(OdmaTypes.CLASS_REPOSITORY),declaredAspects,declaredProperties);
        registerSubClass(OdmaTypes.CLASS_OBJECT, ssc);
        classInfos.put(OdmaTypes.CLASS_REPOSITORY, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_VERSIONSPECIFICID));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_VERSIONSPECIFICGUID));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_TITLE));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_VERSION));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_VERSIONCOLLECTION));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CONTENTELEMENTS));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_COMBINEDMIMETYPE));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_PRIMARYCONTENTELEMENT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CHECKEDOUT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_VERSIONCREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_LASTMODIFIEDAT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CHECKEDOUTAT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_VERSIONCREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_LASTMODIFIEDBY));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CHECKEDOUTBY));
        ssc = new OdmaStaticSystemClassDocument(null,getSubClassesEnumeration(OdmaTypes.CLASS_DOCUMENT),declaredAspects,declaredProperties);
        registerRootAspect(ssc);
        classInfos.put(OdmaTypes.CLASS_DOCUMENT, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CONTENT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_SIZE));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_MIMETYPE));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_FILENAME));
        ssc = new OdmaStaticSystemClassContentElement(null,getSubClassesEnumeration(OdmaTypes.CLASS_CONTENTELEMENT),declaredAspects,declaredProperties);
        registerRootAspect(ssc);
        classInfos.put(OdmaTypes.CLASS_CONTENTELEMENT, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_VERSIONS));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_LATEST));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_RELEASED));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_INPROGRESS));
        ssc = new OdmaStaticSystemClassVersionCollection(null,getSubClassesEnumeration(OdmaTypes.CLASS_VERSIONCOLLECTION),declaredAspects,declaredProperties);
        registerRootAspect(ssc);
        classInfos.put(OdmaTypes.CLASS_VERSIONCOLLECTION, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_TITLE));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CONTAINEES));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_ASSOCIATIONS));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_LASTMODIFIEDAT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_LASTMODIFIEDBY));
        ssc = new OdmaStaticSystemClassContainer(null,getSubClassesEnumeration(OdmaTypes.CLASS_CONTAINER),declaredAspects,declaredProperties);
        registerRootAspect(ssc);
        classInfos.put(OdmaTypes.CLASS_CONTAINER, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_PARENT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_SUBFOLDERS));
        ssc = new OdmaStaticSystemClassFolder(getClassInfo(OdmaTypes.CLASS_CONTAINER),getSubClassesEnumeration(OdmaTypes.CLASS_FOLDER),declaredAspects,declaredProperties);
        registerSubClass(OdmaTypes.CLASS_CONTAINER, ssc);
        classInfos.put(OdmaTypes.CLASS_FOLDER, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CONTAINEDIN));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CONTAINEDINASSOCIATIONS));
        ssc = new OdmaStaticSystemClassContainable(null,getSubClassesEnumeration(OdmaTypes.CLASS_CONTAINABLE),declaredAspects,declaredProperties);
        registerRootAspect(ssc);
        classInfos.put(OdmaTypes.CLASS_CONTAINABLE, ssc);

        declaredAspects = null;
        declaredProperties = new OdmaArrayListPropertyInfoEnumeration();
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_NAME));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CONTAINER));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CONTAINMENT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CREATEDAT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_LASTMODIFIEDAT));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_CREATEDBY));
        declaredProperties.add(getPropertyInfo(OdmaTypes.PROPERTY_LASTMODIFIEDBY));
        ssc = new OdmaStaticSystemClassAssociation(null,getSubClassesEnumeration(OdmaTypes.CLASS_ASSOCIATION),declaredAspects,declaredProperties);
        registerRootAspect(ssc);
        classInfos.put(OdmaTypes.CLASS_ASSOCIATION, ssc);

        OdmaClass propertyInfoClass = getClassInfo(OdmaTypes.CLASS_PROPERTYINFO);
        Iterator itPropertyInfos = propertyInfos.values().iterator();
        while(itPropertyInfos.hasNext())
        {
            OdmaStaticSystemPropertyInfo pi = (OdmaStaticSystemPropertyInfo)itPropertyInfos.next();
            pi.patchClass(propertyInfoClass);
        }
        OdmaClass classClass = getClassInfo(OdmaTypes.CLASS_CLASS);
        Iterator itClassInfos = classInfos.values().iterator();
        while(itClassInfos.hasNext())
        {
            OdmaStaticSystemClass ci = (OdmaStaticSystemClass)itClassInfos.next();
            ci.patchClass(classClass);
        }

        getPropertyInfo(OdmaTypes.PROPERTY_CLASS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CLASS));
        getPropertyInfo(OdmaTypes.PROPERTY_REPOSITORY).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_REPOSITORY));
        getPropertyInfo(OdmaTypes.PROPERTY_PARENT).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CLASS));
        getPropertyInfo(OdmaTypes.PROPERTY_ASPECTS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CLASS));
        getPropertyInfo(OdmaTypes.PROPERTY_DECLAREDPROPERTIES).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_PROPERTYINFO));
        getPropertyInfo(OdmaTypes.PROPERTY_PROPERTIES).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_PROPERTYINFO));
        getPropertyInfo(OdmaTypes.PROPERTY_SUBCLASSES).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CLASS));
        getPropertyInfo(OdmaTypes.PROPERTY_REFERENCECLASS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CLASS));
        getPropertyInfo(OdmaTypes.PROPERTY_ROOTCLASS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CLASS));
        getPropertyInfo(OdmaTypes.PROPERTY_ROOTASPECTS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CLASS));
        getPropertyInfo(OdmaTypes.PROPERTY_ROOTFOLDER).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_FOLDER));
        getPropertyInfo(OdmaTypes.PROPERTY_VERSIONCOLLECTION).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_VERSIONCOLLECTION));
        getPropertyInfo(OdmaTypes.PROPERTY_CONTENTELEMENTS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CONTENTELEMENT));
        getPropertyInfo(OdmaTypes.PROPERTY_PRIMARYCONTENTELEMENT).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CONTENTELEMENT));
        getPropertyInfo(OdmaTypes.PROPERTY_VERSIONS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_DOCUMENT));
        getPropertyInfo(OdmaTypes.PROPERTY_LATEST).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_DOCUMENT));
        getPropertyInfo(OdmaTypes.PROPERTY_RELEASED).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_DOCUMENT));
        getPropertyInfo(OdmaTypes.PROPERTY_INPROGRESS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_DOCUMENT));
        getPropertyInfo(OdmaTypes.PROPERTY_CONTAINEES).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CONTAINABLE));
        getPropertyInfo(OdmaTypes.PROPERTY_ASSOCIATIONS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_ASSOCIATION));
        getPropertyInfo(OdmaTypes.PROPERTY_SUBFOLDERS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_FOLDER));
        getPropertyInfo(OdmaTypes.PROPERTY_CONTAINEDIN).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CONTAINER));
        getPropertyInfo(OdmaTypes.PROPERTY_CONTAINEDINASSOCIATIONS).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_ASSOCIATION));
        getPropertyInfo(OdmaTypes.PROPERTY_CONTAINER).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CONTAINER));
        getPropertyInfo(OdmaTypes.PROPERTY_CONTAINMENT).patchReferenceClass(getClassInfo(OdmaTypes.CLASS_CONTAINABLE));
    }

}
