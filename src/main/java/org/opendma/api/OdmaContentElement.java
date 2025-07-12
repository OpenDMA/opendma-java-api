package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * A ContentElement represents one atomic content element the Documents are made of. This abstract (non instantiable) base class defines the type of content and the position of this element in the sequence of all content elements.
 */
public interface OdmaContentElement extends OdmaObject {

    /**
     * Returns the content type (aka MIME type) of the content represented by this element.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).getString()</code>.
     * 
     * <p>Property opendma:<b>ContentType</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the content type (aka MIME type) of the content represented by this element
     */
    String getContentType();

    /**
     * Sets the content type (aka MIME type) of the content represented by this element.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>ContentType</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the content type (aka MIME type) of the content represented by this element
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setContentType(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the position of this element in the list of all content elements of the containing document.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_POSITION).getInteger()</code>.
     * 
     * <p>Property opendma:<b>Position</b>: Integer<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the position of this element in the list of all content elements of the containing document
     */
    Integer getPosition();

}
