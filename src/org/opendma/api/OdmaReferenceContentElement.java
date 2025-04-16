package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>ReferenceContentElement</i> specific version of the <code>{@link OdmaContentElement}</code> interface
 * that offers short cuts to all defined OpenDMA properties.<p>
 * 
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaReferenceContentElement extends OdmaContentElement
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the URI where the content is stored.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LOCATION).getString()</code>.
     * 
     * <p>Property <b>Location</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @return the URI where the content is stored
     */
    public String getLocation();

    /**
     * Sets the URI where the content is stored.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LOCATION).setValue(value)</code>.
     * 
     * <p>Property <b>Location</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setLocation(String value) throws OdmaAccessDeniedException;

}
