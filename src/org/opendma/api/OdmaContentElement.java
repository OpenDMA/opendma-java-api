package org.opendma.api;

/**
 * The <i>ContentElement</i> specific version of the <code>{@link OdmaObject}</code> interface that offers short cuts
 * to all defined OpenDMA properties.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaContentElement extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the content of this <i>content element</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).getContent()</code>.
     * 
     * @return the content of this <i>content element</i>
     */
    public OdmaContent getContent();

    /**
     * Returns the size of this <i>content element</i> in octets.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SIZE).getLong()</code>.
     * 
     * @return the size of this <i>content element</i> in octets
     */
    public long getSize();

    /**
     * Returns the mime type of this <i>content element</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MIMETYPE).getString()</code>.
     * 
     * @return the mime type of this <i>content element</i>
     */
    public String getMimeType();

    /**
     * Returns the file name of this <i>content element</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).getString()</code>.
     * 
     * @return the file name of this <i>content element</i>
     */
    public String getFileName();

}