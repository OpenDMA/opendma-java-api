package org.opendma.impl.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaGuid;
import org.opendma.api.OdmaId;
import org.opendma.api.OdmaObject;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaRepository;
import org.opendma.api.OdmaType;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.impl.OdmaPropertyImpl;

public class OdmaStaticSystemObject
{

    protected Map<OdmaQName, OdmaProperty> properties = new HashMap<OdmaQName, OdmaProperty>();

    protected OdmaStaticSystemObject()
    {
        // properties of opendma.org Object
        properties.put(OdmaCommonNames.PROPERTY_CLASS,null);
        properties.put(OdmaCommonNames.PROPERTY_ID,null);
        properties.put(OdmaCommonNames.PROPERTY_GUID,null);
        properties.put(OdmaCommonNames.PROPERTY_REPOSITORY,null);
    }

    protected void patchClass(OdmaClass newClass) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        properties.put(OdmaCommonNames.PROPERTY_CLASS,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_CLASS,newClass,OdmaType.REFERENCE,false,true));
    }

    protected void patchIds(OdmaId newId, OdmaGuid newGuid) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        properties.put(OdmaCommonNames.PROPERTY_ID,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_ID,newId,OdmaType.ID,false,true));
        properties.put(OdmaCommonNames.PROPERTY_GUID,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_GUID,newGuid,OdmaType.GUID,false,true));
    }

    protected void patchRepository(OdmaRepository newRepository) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException
    {
        properties.put(OdmaCommonNames.PROPERTY_REPOSITORY,new OdmaPropertyImpl(OdmaCommonNames.PROPERTY_REPOSITORY,newRepository,OdmaType.REFERENCE,false,true));
    }

    // ----- Generic property access ---------------------------------------------------------------

    /**
     * Returns an <code>{@link OdmaProperty}</code> for the property identified by the given qualified name.
     * The named property is automatically retrieved from the server if it is not yet in the local cache.
     * To optimize performance, consider calling <code>{@link #prepareProperties(OdmaQName[], boolean)}</code>
     * first when accessing multiple properties.
     * 
     * @param propertyName
     *            the qualified name of the property to return
     * 
     * @return an <code>{@link OdmaProperty}</code> for the property identified by the given qualified name.
     * 
     * @throws OdmaPropertyNotFoundException
     *             Thrown if the given qualified name does not identify a property in the effective properties of the class of this object.
     */
    public OdmaProperty getProperty(OdmaQName propertyName) throws OdmaPropertyNotFoundException {
        OdmaProperty p = (OdmaProperty)properties.get(propertyName);
        if(p == null)
            throw new OdmaPropertyNotFoundException(propertyName);
        return p;
    }

    /**
     * Checks if the specified properties are already in the local cache and retrieves them from the server if not.
     * If <code>refresh</code> is set to <code>true</code>, all specified properties are always retrieved from the server.
     * Such a refresh will reset unsaved changes of properties to the latest state on the server.
     * If a given qualified name does not identify a property, it is silently ignored.
     * 
     * @param propertyNames
     *            An array of qualified property names to retrieve or <code>null</code> to retrieve all properties.
     * 
     * @param refresh
     *            Indicates whether properties should be refreshed even if they are in the local cache.
     */
    public void prepareProperties(OdmaQName[] propertyNames, boolean refresh) {
        // do nothing here. We are static and all static properties are already in the Map
    }

    /**
     * Sets the specified property to a new value.<br>
     * This is a shortcut for <code>getProperty(propertyName).setValue(newValue)</code>. It avoids the retrieval of the property
     * in the <code>getProperty(propertyName)</code> method if the property is not yet in the local cache.
     * 
     * @param propertyName
     *            The qualified name of the property to be changed
     * @param newValue
     *            The new value for the property
     * 
     * @throws OdmaPropertyNotFoundException
     *             Thrown if the given qualified name does not identify a property in the effective properties of this object's class.
     * @throws OdmaInvalidDataTypeException
     *             Thrown if the type of <code>newValue</code> does not match the property's data type.
     * @throws OdmaAccessDeniedException
     *             Thrown if the current user does not have permission to modify the property.
     */
    public void setProperty(OdmaQName propertyName, Object newValue) throws OdmaPropertyNotFoundException, OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        // we are static. properties can not be changed.
        throw new OdmaAccessDeniedException();
    }

    /**
     * Checks if there are pending changes to properties that have not been persisted to the server.
     * 
     * @return <code>true<code> if there are pending changes that have not yet been persisted, <code>false<code> otherwise
     */
    public boolean isDirty() {
        // we are static. properties can not be changed.
        return false;
    }

    /**
     * Persists the current pending changes to properties at the server.
     */
    public void save() {
        // we are not dirty, and will never be. Nothing to do here.
    }

    /**
     * Determines whether this object's class or one of its ancestors matches or incorporates the specified class or aspect.
     * 
     * @param classOrAspectName
     *             The qualified name of the class or aspect to test.
     * 
     * @return <code>true</code> if the class matches or incorporates the specified aspect, <code>false</code> otherwise.
     */
    public boolean instanceOf(OdmaQName classOrAspectName) {
        OdmaClass test = getOdmaClass();
        while(test != null)
        {
            if(test.getQName().equals(classOrAspectName))
            {
                return true;
            }
            Iterable<OdmaClass> aspects = test.getAspects();
            if(aspects != null)
            {
                Iterator<OdmaClass> itAspects = aspects.iterator();
                while(itAspects.hasNext())
                {
                    OdmaClass declaredAspect = (OdmaClass)itAspects.next();
                    if(declaredAspect.getQName().equals(classOrAspectName))
                    {
                        return true;
                    }
                }
            }
            test = test.getSuperClass();
        }
        return false;
    }

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaObjectTemplate

    /**
     * Returns the OpenDMA <code>Class</code> describing this <code>Object</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CLASS).getReference()</code>.
     * 
     * <p>Property <b>Class</b> (opendma): <b>Reference to Class (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the OpenDMA <code>Class</code> describing this <code>Object</code>
     */
    public OdmaClass getOdmaClass()
    {
        try
        {
            return (OdmaClass)getProperty(OdmaCommonNames.PROPERTY_CLASS).getReference();
        }
        catch(ClassCastException cce)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the <i>unique object identifier</i> identifying this <code>Object</code> inside its <code>Repository</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ID).getId()</code>.
     * 
     * <p>Property <b>Id</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>unique object identifier</i> identifying this <code>Object</code> inside its <code>Repository</code>
     */
    public OdmaId getId()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_ID).getId();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the <i>global unique object identifier</i> globally identifying this <code>Object</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_GUID).getGuid()</code>.
     * 
     * <p>Property <b>Guid</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>global unique object identifier</i> globally identifying this <code>Object</code>
     */
    public OdmaGuid getGuid()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_GUID).getGuid();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the OpenDMA <code>Repository</code> where this <code>Object</code> resides.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REPOSITORY).getReference()</code>.
     * 
     * <p>Property <b>Repository</b> (opendma): <b>Reference to Repository (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the OpenDMA <code>Repository</code> where this <code>Object</code> resides
     */
    public OdmaRepository getRepository()
    {
        try
        {
            return (OdmaRepository)getProperty(OdmaCommonNames.PROPERTY_REPOSITORY).getReference();
        }
        catch(ClassCastException cce)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
