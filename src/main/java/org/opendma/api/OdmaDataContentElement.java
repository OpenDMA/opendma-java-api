package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>DataContentElement</i> specific version of the <code>{@link OdmaContentElement}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * A DataContentElement represents one atomic octet stream. The binary data is stored together with meta data like size and filename
 */
public interface OdmaDataContentElement extends OdmaContentElement {

    /**
     * Returns the binary data of this content element.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).getContent()</code>.
     * 
     * <p>Property opendma:<b>Content</b>: Content<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the binary data of this content element
     */
    OdmaContent getContent();

    /**
     * Sets the binary data of this content element.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Content</b>: Content<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the binary data of this content element
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setContent(OdmaContent newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the size of the data in number of octets.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SIZE).getLong()</code>.
     * 
     * <p>Property opendma:<b>Size</b>: Long<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the size of the data in number of octets
     */
    Long getSize();

    /**
     * Returns the optional file name of the data.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).getString()</code>.
     * 
     * <p>Property opendma:<b>FileName</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the optional file name of the data
     */
    String getFileName();

    /**
     * Sets the optional file name of the data.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>FileName</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the optional file name of the data
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setFileName(String newValue) throws OdmaAccessDeniedException;

}
