package org.opendma.api;

import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * Full description follows.
 */
public interface OdmaObject {

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
    OdmaProperty getProperty(OdmaQName propertyName) throws OdmaPropertyNotFoundException;

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
    void prepareProperties(OdmaQName[] propertyNames, boolean refresh);

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
    void setProperty(OdmaQName propertyName, Object newValue) throws OdmaPropertyNotFoundException, OdmaInvalidDataTypeException, OdmaAccessDeniedException;

    /**
     * Checks if there are pending changes to properties that have not been persisted to the server.
     * 
     * @return <code>true<code> if there are pending changes that have not yet been persisted, <code>false<code> otherwise
     */
    boolean isDirty();

    /**
     * Persists the current pending changes to properties at the server.
     */
    void save();

    /**
     * Determines whether this object's class or one of its ancestors matches or incorporates the specified class or aspect.
     * 
     * @param classOrAspectName
     *             The qualified name of the class or aspect to test.
     * 
     * @return <code>true</code> if the class matches or incorporates the specified aspect, <code>false</code> otherwise.
     */
    boolean instanceOf(OdmaQName classOrAspectName);

    // ----- Object specific property access -------------------------------------------------------

    /**
     * Returns the OpenDMA <code>Class</code> describing this <code>Object</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CLASS).getReference()</code>.
     * 
     * <p>Property <b>Class</b> (opendma): <b>Reference to Class (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the OpenDMA <code>Class</code> describing this <code>Object</code>
     */
    OdmaClass getOdmaClass();

    /**
     * Returns the <i>unique object identifier</i> identifying this <code>Object</code> inside its <code>Repository</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ID).getId()</code>.
     * 
     * <p>Property <b>Id</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>unique object identifier</i> identifying this <code>Object</code> inside its <code>Repository</code>
     */
    OdmaId getId();

    /**
     * Returns the <i>global unique object identifier</i> globally identifying this <code>Object</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_GUID).getGuid()</code>.
     * 
     * <p>Property <b>Guid</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>global unique object identifier</i> globally identifying this <code>Object</code>
     */
    OdmaGuid getGuid();

    /**
     * Returns the OpenDMA <code>Repository</code> where this <code>Object</code> resides.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REPOSITORY).getReference()</code>.
     * 
     * <p>Property <b>Repository</b> (opendma): <b>Reference to Repository (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the OpenDMA <code>Repository</code> where this <code>Object</code> resides
     */
    OdmaRepository getRepository();

}
