package org.opendma.api;

import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaObjectNotFoundException;

/**
 * The basic <i>Object</i> in the OpenDMA architecture.<br>
 * It is made up of a list of <code>{@link OdmaProperty}</code>s that can be
 * retrieved by the getProperty() method.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaObject
{

    // =============================================================================================
    // Generic property access
    // =============================================================================================

    /**
     * Returns an <code>{@link OdmaProperty}</code> for the property
     * identified by the given qualified name. The named property is
     * automatically retrieved from the server if it is not yet in the local
     * cache. So it is wise to call the method
     * <code>{@link #prepareProperties(OdmaQName[], boolean)}</code> at first
     * if you are interested in multiple properties to reduce the number of
     * round trips to the server.
     * 
     * @param propertyName
     *            the qualified name of the property to return
     * 
     * @return an <code>{@link OdmaProperty}</code> for the property
     *         identified by the given qualified name.
     * 
     * @throws OdmaObjectNotFoundException
     *             if and only if the given qualified name does not identify a
     *             property in the list of effective properties of the class of
     *             this object
     */
    public OdmaProperty getProperty(OdmaQName propertyName) throws OdmaObjectNotFoundException;

    /**
     * Checks if the named properties are already in the local cache and
     * performs one single round trip to the server to retrieve all properties
     * that are not yet in the cache. If the flag <code>refresh</code> is set
     * to <code>true</code>, the proerties are always retrieved from the
     * server. Such a refresh will reset unsaved changes of properties to the
     * current value.<br>
     * If a given qualified name does not identify a property, it is silently
     * ignored.
     * 
     * @param propertyNames
     *            array of qualified property names to retrieve from the server
     *            or <code>null</code> to retrieve all
     * 
     * @param refresh
     *            flag to indicate if the properties should also be retrieved if
     *            they are already in the local cache.
     */
    public void prepareProperties(OdmaQName[] propertyNames, boolean refresh);

    /**
     * Set the property identified by the given qualified name to the new value.<br>
     * This method is a shortcut for
     * <code>getProperty(propertyName).setValue(newValue)</code> that can
     * avoid the retrieval of the property in the
     * <code>getProperty(propertyName)</code> method if the property is not
     * yet in the local cache.
     * 
     * @param propertyName
     *            the qualified name of the property to be changed
     * @param newValue
     *            the new value to set the named property to
     * 
     * @throws OdmaObjectNotFoundException
     *             if and only if the given qualified name does not identify a
     *             property in the list of effective properties of the class of
     *             this object
     * @throws OdmaInvalidDataTypeException
     *             if and only if the Class of the given Object does not match
     *             the data type of the named property
     */
    public void setProperty(OdmaQName propertyName, Object newValue) throws OdmaObjectNotFoundException, OdmaInvalidDataTypeException;

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the <code>{@link OdmaClass}</code> describing this <i>object</i>.<br>
     * Shortcut for
     * <code>(OdmaClass)getProperty(OdmaTypes.PROPERTY_CLASS)</code>.
     * 
     * @return the <code>{@link OdmaClass}</code> describing this <i>object</i>
     */
    public OdmaClass getOdmaClass();

    /**
     * Returns the <i>unique object identifier</i> as
     * <code>{@link OdmaId}</code> for this <i>object</i>.<br>
     * Shortcut for <code>(OdmaId)getProperty(OdmaTypes.PROPERTY_ID)</code>.
     * 
     * @return the <i>unique object identifier</i> as
     *         <code>{@link OdmaId}</code> for this <i>object</i>
     */
    public OdmaId getID();

    /**
     * Returns the <code>{@link OdmaRepository}</code> this <i>object</i> is
     * stored in.<br>
     * Shortcut for
     * <code>(OdmaRepository)getProperty(OdmaTypes.PROPERTY_REPOSITORY)</code>.
     * 
     * @return the <code>{@link OdmaRepository}</code> this <i>object</i> is
     *         stored in.
     */
    public OdmaRepository getRepository();

}