package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * Full description follows.
 */
public interface OdmaContentElement extends OdmaObject
{

    // ----- Object specific property access -------------------------------------------------------

    /**
     * Returns the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).getString()</code>.
     * 
     * <p>Property <b>ContentType</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted
     */
    String getContentType();

    /**
     * Sets the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).setValue(value)</code>.
     * 
     * <p>Property <b>ContentType</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setContentType(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the position of this element in the list of all content elements of the containing document.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_POSITION).getInteger()</code>.
     * 
     * <p>Property <b>Position</b> (opendma): <b>Integer</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the position of this element in the list of all content elements of the containing document
     */
    Integer getPosition();

}
