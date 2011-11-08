package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaContentElement extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).getString()</code>.
     * 
     * <p>Property <b>ContentType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted
     */
    public String getContentType();

    /**
     * Sets the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).setValue(value)</code>.
     * 
     * <p>Property <b>ContentType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setContentType(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the position of this element in the list of all content elements of the containing document.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_POSITION).getInteger()</code>.
     * 
     * <p>Property <b>Position</b> (opendma): <b>Integer</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the position of this element in the list of all content elements of the containing document
     */
    public Integer getPosition();

}
