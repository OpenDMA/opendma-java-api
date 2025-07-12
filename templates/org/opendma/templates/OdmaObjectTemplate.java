package org.opendma.templates;

import org.opendma.api.OdmaObject;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import java.util.Iterator;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaId;
import org.opendma.api.OdmaGuid;
import org.opendma.api.OdmaRepository;

/**
 * Template implementation of the interface <code>{@link OdmaObject}</code>.<p>
 * 
 * Root of the class hierarchy. Every class in OpenDMA extends this class. All objects in OpenDMA have the properties defined for this class.
 */
public class OdmaObjectTemplate implements OdmaObject {

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
        // TODO: implement me
        return null;
    }

    /**
     * Checks if the named properties are already in the local cache and performs one single round trip to the server to
     * retrieve all properties that are not yet in the cache. If the flag <code>refresh</code> is set to
     * <code>true</code>, the proerties are always retrieved from the server. Such a refresh will reset unsaved
     * changes of properties to the current value.<br>
     * If a given qualified name does not identify a property, it is silently ignored.
     * 
     * @param propertyNames
     *            array of qualified property names to retrieve from the server or <code>null</code> to retrieve all
     * 
     * @param refresh
     *            flag to indicate if the properties should also be retrieved if they are already in the local cache.
     */
    public void prepareProperties(OdmaQName[] propertyNames, boolean refresh) {
        // TODO: implement me
    }

    /**
     * Set the property identified by the given qualified name to the new value.<br>
     * This method is a shortcut for <code>getProperty(propertyName).setValue(newValue)</code> that can avoid the
     * retrieval of the property in the <code>getProperty(propertyName)</code> method if the property is not yet in
     * the local cache.
     * 
     * @param propertyName
     *            the qualified name of the property to be changed
     * @param newValue
     *            the new value to set the named property to
     * 
     * @throws OdmaPropertyNotFoundException
     *             if and only if the given qualified name does not identify a property in the list of effective
     *             properties of the class of this object
     * @throws OdmaInvalidDataTypeException
     *             if and only if the Class of the given Object does not match the data type of the named property
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setProperty(OdmaQName propertyName, Object newValue) throws OdmaPropertyNotFoundException, OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        // TODO: implement me
    }

    /**
     * Returns true if there are some pending changes to properties that have not yet been persisted to the server.
     * 
     * @return true if there are pending changes that have not yet been persisted
     */
    public boolean isDirty() {
        // TODO: implement me
        return false;
    }

    /**
     * Persist the current pending changes to properties at the server.
     */
    public void save() {
        // TODO: implement me
    }

    /**
     * Returns <code>true</code> if and only if the class of this object or one of its ancestors equals
     * the given name or the class of this object or one of its ancestors incorporates the aspect with
     * the given name.
     * 
     * @param classOrAspectName
     *             the qualified name of the class or aspect to test for
     * 
     * @return if the class of this object is or extends the given class or incorportes the given aspect
     */
    public boolean instanceOf(OdmaQName classOrAspectName) {
        // TODO: this code needs to be improved to take advantage of the actual repository
        OdmaClass test = getOdmaClass();
        while(test != null) {
            if(test.getQName().equals(classOrAspectName)) {
                return true;
            }
            Iterable<OdmaClass> aspects = test.getAspects();
            if(aspects != null) {
                Iterator<OdmaClass> itAspects = aspects.iterator();
                while(itAspects.hasNext()) {
                    OdmaClass declaredAspect = itAspects.next();
                    if(declaredAspect.getQName().equals(classOrAspectName)) {
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
     * Returns Reference to a valid class object describing this object.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CLASS).getReference()</code>.
     * 
     * <p>Property opendma:<b>Class</b>: Reference to Class (opendma)<br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * The opendma:Class describes the layout and features of this object. Here you can find a set of odma:PropertyInfo objects that describe all the properties with their qualified name, data type and cardinality. It also provides the text to be displayed to users to refer to these objects as well as flags indicating if these objects are system owner or should be hidden from end users.</p>
     * 
     * @return Reference to a valid class object describing this object
     */
    public OdmaClass getOdmaClass() {
        try {
            return (OdmaClass)getProperty(OdmaCommonNames.PROPERTY_CLASS).getReference();
        }
        catch(ClassCastException cce) {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the unique object identifier.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ID).getId()</code>.
     * 
     * <p>Property opendma:<b>Id</b>: String<br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * This identifier is unique within it's Repository and  must be immutable during the lifetime of this object. You can use it to refer to this object and retrieve it again at a later time.</p>
     * 
     * @return the unique object identifier
     */
    public OdmaId getId() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_ID).getId();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the global unique object identifier.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_GUID).getGuid()</code>.
     * 
     * <p>Property opendma:<b>Guid</b>: String<br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * A combination of the unique object identifier and the unique repository identifier. Use it to refer to this object across multiple repositories.</p>
     * 
     * @return the global unique object identifier
     */
    public OdmaGuid getGuid() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_GUID).getGuid();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the Repository this object belongs to.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REPOSITORY).getReference()</code>.
     * 
     * <p>Property opendma:<b>Repository</b>: Reference to Repository (opendma)<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return the Repository this object belongs to
     */
    public OdmaRepository getRepository() {
        try {
            return (OdmaRepository)getProperty(OdmaCommonNames.PROPERTY_REPOSITORY).getReference();
        }
        catch(ClassCastException cce) {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
