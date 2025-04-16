package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;
import java.util.Date;

/**
 * Full description follows.
 */
public interface OdmaContainer extends OdmaObject {

    // ----- Object specific property access -------------------------------------------------------

    /**
     * Returns the <i>title</i> of this <code>Container</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).getString()</code>.
     * 
     * <p>Property <b>Title</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>title</i> of this <code>Container</code>
     */
    String getTitle();

    /**
     * Sets the <i>title</i> of this <code>Container</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).setValue(value)</code>.
     * 
     * <p>Property <b>Title</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the <i>title</i> of this <code>Container</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setTitle(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the collection of all <code>Containable</code> objects that are contained in this <code>Container</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEES).getReferenceIterable()</code>.
     * 
     * <p>Property <b>Containees</b> (opendma): <b>Reference to Containable (opendma)</b><br/>
     * [MultiValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the collection of all <code>Containable</code> objects that are contained in this <code>Container</code>
     */
    Iterable<OdmaContainable> getContainees();

    /**
     * Returns the collection of all <code>Association</code>s between this <code>Container</code> and its containees.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASSOCIATIONS).getReferenceIterable()</code>.
     * 
     * <p>Property <b>Associations</b> (opendma): <b>Reference to Association (opendma)</b><br/>
     * [MultiValue] [ReadOnly] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the collection of all <code>Association</code>s between this <code>Container</code> and its containees
     */
    Iterable<OdmaAssociation> getAssociations();

    /**
     * Returns the date when this <code>Container</code> has been created.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * <p>Property <b>CreatedAt</b> (opendma): <b>DateTime</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Container</code> has been created
     */
    Date getCreatedAt();

    /**
     * Returns the user who has created this <code>Container</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * <p>Property <b>CreatedBy</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the user who has created this <code>Container</code>
     */
    String getCreatedBy();

    /**
     * Returns the date when this <code>Container</code> has been modified the last time.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * <p>Property <b>LastModifiedAt</b> (opendma): <b>DateTime</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Container</code> has been modified the last time
     */
    Date getLastModifiedAt();

    /**
     * Returns the user who has modified this <code>Container</code> the last time.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * <p>Property <b>LastModifiedBy</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the user who has modified this <code>Container</code> the last time
     */
    String getLastModifiedBy();

}
