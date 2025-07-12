package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;
import java.util.Date;

/**
 * A Container holds a set of containable objects that are said to be contained in this Container. This list of containees is build up with Association objects based on references to the containmer and the containee. This allows an object to be contained in multiple Containers or in no Container at all. A Container does not enforce a loop-free single rooted tree. Use a folder instead for this requirement.
 */
public interface OdmaContainer extends OdmaObject {

    /**
     * Returns the title of this container.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).getString()</code>.
     * 
     * <p>Property opendma:<b>Title</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the title of this container
     */
    String getTitle();

    /**
     * Sets the title of this container.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Title</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the title of this container
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setTitle(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the set of containable objects contained in this container.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Containees</b>: Reference to Containable (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return the set of containable objects contained in this container
     */
    Iterable<OdmaContainable> getContainees();

    /**
     * Returns the set of associations between this container and the contained objects.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASSOCIATIONS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Associations</b>: Reference to Association (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return the set of associations between this container and the contained objects
     */
    Iterable<OdmaAssociation> getAssociations();

    /**
     * Returns the timestamp when this container has been created.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>CreatedAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the timestamp when this container has been created
     */
    Date getCreatedAt();

    /**
     * Returns the user who created this container.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * <p>Property opendma:<b>CreatedBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the user who created this container
     */
    String getCreatedBy();

    /**
     * Returns the timestamp when this container has been modified the last time.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>LastModifiedAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * There is no definition what counts as a modification. Some systems update this timestamp when objects are added or removed, other systems only update this timestamp when properties of this object get changed.</p>
     * 
     * @return the timestamp when this container has been modified the last time
     */
    Date getLastModifiedAt();

    /**
     * Returns the user who modified this container the last time.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * <p>Property opendma:<b>LastModifiedBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * There is no definition what counts as a modification. Some systems update this timestamp when objects are added or removed, other systems only update this timestamp when properties of this object get changed.</p>
     * 
     * @return the user who modified this container the last time
     */
    String getLastModifiedBy();

}
