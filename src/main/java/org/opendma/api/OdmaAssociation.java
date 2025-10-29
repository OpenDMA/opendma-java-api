package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * An Association represents the directed link between an opendma:Container and an opendma:Containable object.
 */
public interface OdmaAssociation extends OdmaObject {

    /**
     * Returns the name of this association.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * This name is used to refer to this specific association in the context of it's container and tell tem apart. Many systems pose additional constraints on this name.</p>
     * 
     * @return the name of this association
     */
    String getName();

    /**
     * Sets the name of this association.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * This name is used to refer to this specific association in the context of it's container and tell tem apart. Many systems pose additional constraints on this name.</p>
     * 
     * @param newValue
     *             The new value for the name of this association
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the source of this directed link.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINER).getReference()</code>.
     * 
     * <p>Property opendma:<b>Container</b>: Reference to Container (opendma)<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return the source of this directed link
     */
    OdmaContainer getContainer();

    /**
     * Sets the source of this directed link.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINER).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Container</b>: Reference to Container (opendma)<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for the source of this directed link
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setContainer(OdmaContainer newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the destination of this directed link.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINABLE).getReference()</code>.
     * 
     * <p>Property opendma:<b>Containable</b>: Reference to Containable (opendma)<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return the destination of this directed link
     */
    OdmaContainable getContainable();

    /**
     * Sets the destination of this directed link.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINABLE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Containable</b>: Reference to Containable (opendma)<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for the destination of this directed link
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setContainable(OdmaContainable newValue) throws OdmaAccessDeniedException;

}
