package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>ReferenceContentElement</i> specific version of the <code>{@link OdmaContentElement}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * Full description follows.
 */
public interface OdmaReferenceContentElement extends OdmaContentElement {

    /**
     * Returns the URI where the content is stored.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LOCATION).getString()</code>.
     * 
     * <p>Property <b>Location</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the URI where the content is stored
     */
    String getLocation();

    /**
     * Sets the URI where the content is stored.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LOCATION).setValue(value)</code>.
     * 
     * <p>Property <b>Location</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the URI where the content is stored
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setLocation(String newValue) throws OdmaAccessDeniedException;

}
