package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

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

}
