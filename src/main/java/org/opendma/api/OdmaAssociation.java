package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;
import java.util.Date;

/**
 * Full description follows.
 */
public interface OdmaAssociation extends OdmaObject {

    /**
     * Returns the <i>name</i> of this <code>Association</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>name</i> of this <code>Association</code>
     */
    String getName();

    /**
     * Sets the <i>name</i> of this <code>Association</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).setValue(value)</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the <i>name</i> of this <code>Association</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the <code>Container</code> of this <code>Association</code> in which the containable is said to be contained.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINER).getReference()</code>.
     * 
     * <p>Property <b>Container</b> (opendma): <b>Reference to Container (opendma)</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <code>Container</code> of this <code>Association</code> in which the containable is said to be contained
     */
    OdmaContainer getContainer();

    /**
     * Sets the <code>Container</code> of this <code>Association</code> in which the containable is said to be contained.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINER).setValue(value)</code>.
     * 
     * <p>Property <b>Container</b> (opendma): <b>Reference to Container (opendma)</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the <code>Container</code> of this <code>Association</code> in which the containable is said to be contained
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setContainer(OdmaContainer newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the <code>Containable</code> of this <code>Association</code> which is said to be contained in the container.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINABLE).getReference()</code>.
     * 
     * <p>Property <b>Containable</b> (opendma): <b>Reference to Containable (opendma)</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <code>Containable</code> of this <code>Association</code> which is said to be contained in the container
     */
    OdmaContainable getContainable();

    /**
     * Sets the <code>Containable</code> of this <code>Association</code> which is said to be contained in the container.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINABLE).setValue(value)</code>.
     * 
     * <p>Property <b>Containable</b> (opendma): <b>Reference to Containable (opendma)</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the <code>Containable</code> of this <code>Association</code> which is said to be contained in the container
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setContainable(OdmaContainable newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the date when this <code>Association</code> has been created.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * <p>Property <b>CreatedAt</b> (opendma): <b>DateTime</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Association</code> has been created
     */
    Date getCreatedAt();

    /**
     * Returns the user who has created this <code>Association</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * <p>Property <b>CreatedBy</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the user who has created this <code>Association</code>
     */
    String getCreatedBy();

    /**
     * Returns the date when this <code>Association</code> has been modified the last time.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * <p>Property <b>LastModifiedAt</b> (opendma): <b>DateTime</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Association</code> has been modified the last time
     */
    Date getLastModifiedAt();

    /**
     * Returns the user who has modified this <code>Association</code> the last time.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * <p>Property <b>LastModifiedBy</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the user who has modified this <code>Association</code> the last time
     */
    String getLastModifiedBy();

}
