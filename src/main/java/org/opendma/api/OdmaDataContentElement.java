package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>DataContentElement</i> specific version of the <code>{@link OdmaContentElement}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * Full description follows.
 */
public interface OdmaDataContentElement extends OdmaContentElement {

    /**
     * Returns the binary content of this <code>ContentElement</code> as octets.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).getContent()</code>.
     * 
     * <p>Property <b>Content</b> (opendma): <b>Content</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the binary content of this <code>ContentElement</code> as octets
     */
    OdmaContent getContent();

    /**
     * Sets the binary content of this <code>ContentElement</code> as octets.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).setValue(value)</code>.
     * 
     * <p>Property <b>Content</b> (opendma): <b>Content</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the binary content of this <code>ContentElement</code> as octets
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setContent(OdmaContent newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the number of octests the binary content of this <code>ContentElement</code> consists of.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SIZE).getLong()</code>.
     * 
     * <p>Property <b>Size</b> (opendma): <b>Long</b><br/>
     * [SingleValue] [ReadOnly] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the number of octests the binary content of this <code>ContentElement</code> consists of
     */
    Long getSize();

    /**
     * Returns the optional file name of this <code>ContentElement</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).getString()</code>.
     * 
     * <p>Property <b>FileName</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the optional file name of this <code>ContentElement</code>
     */
    String getFileName();

    /**
     * Sets the optional file name of this <code>ContentElement</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).setValue(value)</code>.
     * 
     * <p>Property <b>FileName</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the optional file name of this <code>ContentElement</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setFileName(String newValue) throws OdmaAccessDeniedException;

}
