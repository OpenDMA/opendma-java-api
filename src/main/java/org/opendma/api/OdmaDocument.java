package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;
import java.util.Date;

/**
 * A Document is the atomic element users work on in a content based environment. It can be compared to a file in a file system. Unlike files, it may consist of multiple octet streams. These content streams can for example contain images of the individual pages that make up the document. A Document is able to keep track of its changes (versioning) and manage the access to it (checkin and checkout).
 */
public interface OdmaDocument extends OdmaObject {

    /**
     * Returns the title of this document.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).getString()</code>.
     * 
     * <p>Property opendma:<b>Title</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Typically a human friendly readable description of this document. Does not need to be a file name, but can be the file name.</p>
     * 
     * @return the title of this document
     */
    String getTitle();

    /**
     * Sets the title of this document.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Title</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Typically a human friendly readable description of this document. Does not need to be a file name, but can be the file name.</p>
     * 
     * @param newValue
     *             The new value for the title of this document
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setTitle(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Identifier of this version consisting of a set of numbers separated by a point (e.g. 1.2.3).<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSION).getString()</code>.
     * 
     * <p>Property opendma:<b>Version</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * This value is heavily vendor specific. You should not have any expectations of the format.</p>
     * 
     * @return Identifier of this version consisting of a set of numbers separated by a point (e.g. 1.2.3)
     */
    String getVersion();

    /**
     * Returns reference to a opendma:VersionCollection object representing all versions of this document or null if versioning is not supported.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONCOLLECTION).getReference()</code>.
     * 
     * <p>Property opendma:<b>VersionCollection</b>: Reference to VersionCollection (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return reference to a opendma:VersionCollection object representing all versions of this document or null if versioning is not supported
     */
    OdmaVersionCollection getVersionCollection();

    /**
     * Returns the unique object identifier identifying this logical document independent from the specific version inside its opendma:Repository.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONINDEPENDENTID).getId()</code>.
     * 
     * <p>Property opendma:<b>VersionIndependentId</b>: String<br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Retrieving this Id from the Repository will result in the latest version</p>
     * 
     * @return the unique object identifier identifying this logical document independent from the specific version inside its opendma:Repository
     */
    OdmaId getVersionIndependentId();

    /**
     * Returns the global unique object identifier globally identifying this logical document independent from the specific version.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONINDEPENDENTGUID).getGuid()</code>.
     * 
     * <p>Property opendma:<b>VersionIndependentGuid</b>: String<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return the global unique object identifier globally identifying this logical document independent from the specific version
     */
    OdmaGuid getVersionIndependentGuid();

    /**
     * Returns set of opendma:ContentElement objects representing the individual binary elements this document is made up of.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTELEMENTS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>ContentElements</b>: Reference to ContentElement (opendma)<br/>
     * [MultiValue] [Writable] [Optional]<br/>
     * Typically has only one element. Can contain more then one element in rare cases, e.g. if individual pages of a document are scanned as separate images</p>
     * 
     * @return set of opendma:ContentElement objects representing the individual binary elements this document is made up of
     */
    Iterable<OdmaContentElement> getContentElements();

    /**
     * Returns the combined conent type of the whole Document, calculated from the content types of each ContentElement.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_COMBINEDCONTENTTYPE).getString()</code>.
     * 
     * <p>Property opendma:<b>CombinedContentType</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the combined conent type of the whole Document, calculated from the content types of each ContentElement
     */
    String getCombinedContentType();

    /**
     * Sets the combined conent type of the whole Document, calculated from the content types of each ContentElement.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_COMBINEDCONTENTTYPE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>CombinedContentType</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the combined conent type of the whole Document, calculated from the content types of each ContentElement
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setCombinedContentType(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the dedicated primary ContentElement. May only be null if ContentElements is empty..<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PRIMARYCONTENTELEMENT).getReference()</code>.
     * 
     * <p>Property opendma:<b>PrimaryContentElement</b>: Reference to ContentElement (opendma)<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the dedicated primary ContentElement. May only be null if ContentElements is empty.
     */
    OdmaContentElement getPrimaryContentElement();

    /**
     * Sets the dedicated primary ContentElement. May only be null if ContentElements is empty..<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PRIMARYCONTENTELEMENT).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>PrimaryContentElement</b>: Reference to ContentElement (opendma)<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the dedicated primary ContentElement. May only be null if ContentElements is empty.
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setPrimaryContentElement(OdmaContentElement newValue) throws OdmaAccessDeniedException;

    /**
     * Returns indicates if this document is checked out.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUT).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>CheckedOut</b>: Boolean<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return indicates if this document is checked out
     */
    Boolean isCheckedOut();

    /**
     * Returns the timestamp when this version of the document has been checked out, null if this document is not checked out.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUTAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>CheckedOutAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the timestamp when this version of the document has been checked out, null if this document is not checked out
     */
    Date getCheckedOutAt();

    /**
     * Returns the user who checked out this version of this document, null if this document is not checked out.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUTBY).getString()</code>.
     * 
     * <p>Property opendma:<b>CheckedOutBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the user who checked out this version of this document, null if this document is not checked out
     */
    String getCheckedOutBy();

}
