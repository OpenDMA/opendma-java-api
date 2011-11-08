package org.opendma.api;

import org.opendma.exceptions.OdmaObjectNotFoundException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.OdmaId;
import org.opendma.api.OdmaGuid;

/**
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaObject
{

    // =============================================================================================
    // Generic property access
    // =============================================================================================

    /**
     * Returns an <code>{@link OdmaProperty}</code> for the property identified by the given qualified name. The named
     * property is automatically retrieved from the server if it is not yet in the local cache. So it is wise to call
     * the method <code>{@link #prepareProperties(OdmaQName[], boolean)}</code> at first if you are interested in
     * multiple properties to reduce the number of round trips to the server.
     * 
     * @param propertyName
     *            the qualified name of the property to return
     * 
     * @return an <code>{@link OdmaProperty}</code> for the property identified by the given qualified name.
     * 
     * @throws OdmaObjectNotFoundException
     *             if and only if the given qualified name does not identify a property in the list of effective
     *             properties of the class of this object
     */
    public OdmaProperty getProperty(OdmaQName propertyName) throws OdmaObjectNotFoundException;

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
    public void prepareProperties(OdmaQName[] propertyNames, boolean refresh);

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
     * @throws OdmaObjectNotFoundException
     *             if and only if the given qualified name does not identify a property in the list of effective
     *             properties of the class of this object
     * @throws OdmaInvalidDataTypeException
     *             if and only if the Class of the given Object does not match the data type of the named property
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setProperty(OdmaQName propertyName, Object newValue) throws OdmaObjectNotFoundException, OdmaInvalidDataTypeException, OdmaAccessDeniedException;

    /**
     * Returns true if there are some pending changes to properties that have not yet been persisted to the server.
     * 
     * @return true if there are pending changes that have not yet been persisted
     */
    public boolean isDirty();

    /**
     * Persist the current pending changes to properties at the server.
     */
    public void save();

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the OpenDMA <code>Class</code> describing this <code>Object</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CLASS).getReference()</code>.
     * 
     * <p>Property <b>Class</b> (opendma): <b>Reference to Class (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the OpenDMA <code>Class</code> describing this <code>Object</code>
     */
    public OdmaClass getOdmaClass();

    /**
     * Returns the <i>unique object identifier</i> identifying this <code>Object</code> inside its <code>Repository</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ID).getId()</code>.
     * 
     * <p>Property <b>Id</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>unique object identifier</i> identifying this <code>Object</code> inside its <code>Repository</code>
     */
    public OdmaId getId();

    /**
     * Returns the <i>global unique object identifier</i> globally identifying this <code>Object</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_GUID).getGuid()</code>.
     * 
     * <p>Property <b>Guid</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>global unique object identifier</i> globally identifying this <code>Object</code>
     */
    public OdmaGuid getGuid();

    /**
     * Returns the OpenDMA <code>Repository</code> where this <code>Object</code> resides.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REPOSITORY).getReference()</code>.
     * 
     * <p>Property <b>Repository</b> (opendma): <b>Reference to Repository (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the OpenDMA <code>Repository</code> where this <code>Object</code> resides
     */
    public OdmaRepository getRepository();

}
