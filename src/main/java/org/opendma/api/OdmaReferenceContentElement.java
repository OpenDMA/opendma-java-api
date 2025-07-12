package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>ReferenceContentElement</i> specific version of the <code>{@link OdmaContentElement}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * A ReferenceContentElement represents a reference to external data. The reference is stored as URI to the content location.
 */
public interface OdmaReferenceContentElement extends OdmaContentElement {

    /**
     * Returns the URI where the content is stored.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LOCATION).getString()</code>.
     * 
     * <p>Property opendma:<b>Location</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the URI where the content is stored
     */
    String getLocation();

    /**
     * Sets the URI where the content is stored.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LOCATION).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Location</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the URI where the content is stored
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setLocation(String newValue) throws OdmaAccessDeniedException;

}
