package org.opendma.api;

import org.opendma.api.OdmaContent;
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
     * Returns the binary content of this <code>ContentElement</code> as octets.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).getContent()</code>.
     * 
     * <p>Property <b>Content</b> (opendma): <b>Content</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the binary content of this <code>ContentElement</code> as octets
     */
    public OdmaContent getContent();

    /**
     * Sets the binary content of this <code>ContentElement</code> as octets.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).setValue(value)</code>.
     * 
     * <p>Property <b>Content</b> (opendma): <b>Content</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setContent(OdmaContent value) throws OdmaAccessDeniedException;

    /**
     * Returns the number of octests the binary content of this <code>ContentElement</code> consists of.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SIZE).getLong()</code>.
     * 
     * <p>Property <b>Size</b> (opendma): <b>Long</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the number of octests the binary content of this <code>ContentElement</code> consists of
     */
    public Long getSize();

    /**
     * Returns the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MIMETYPE).getString()</code>.
     * 
     * <p>Property <b>MimeType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted
     */
    public String getMimeType();

    /**
     * Sets the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MIMETYPE).setValue(value)</code>.
     * 
     * <p>Property <b>MimeType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setMimeType(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the optional file name of this <code>ContentElement</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).getString()</code>.
     * 
     * <p>Property <b>FileName</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the optional file name of this <code>ContentElement</code>
     */
    public String getFileName();

    /**
     * Sets the optional file name of this <code>ContentElement</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).setValue(value)</code>.
     * 
     * <p>Property <b>FileName</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setFileName(String value) throws OdmaAccessDeniedException;

}
