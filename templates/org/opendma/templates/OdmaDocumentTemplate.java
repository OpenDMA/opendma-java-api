package org.opendma.templates;

import org.opendma.api.OdmaDocument;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.OdmaVersionCollection;
import org.opendma.api.OdmaId;
import org.opendma.api.OdmaGuid;
import org.opendma.api.OdmaContentElement;
import java.util.Date;

/**
 * Template implementation of the interface <code>{@link OdmaDocument}</code>.<p>
 * 
 * A Document is the atomic element users work on in a content based environment. It can be compared to a file in a file system. Unlike files, it may consist of multiple octet streams. These content streams can for example contain images of the individual pages that make up the document. A Document is able to keep track of its changes (versioning) and manage the access to it (checkin and checkout).
 */
public class OdmaDocumentTemplate extends OdmaObjectTemplate implements OdmaDocument {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaDocumentTemplate

    /**
     * Returns the title of this document.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).getString()</code>.
     * 
     * <p>Property opendma:<b>Title</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Typically a human friendly readable description of this document. Does not need to be a file name, but can be the file name.</p>
     * 
     * @return the title of this document
     */
    public String getTitle() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_TITLE).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setTitle(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_TITLE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Identifier of this version consisting of a set of numbers separated by a point (e.g. 1.2.3).<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSION).getString()</code>.
     * 
     * <p>Property opendma:<b>Version</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * This value is heavily vendor specific. You should not have any expectations of the format.</p>
     * 
     * @return Identifier of this version consisting of a set of numbers separated by a point (e.g. 1.2.3)
     */
    public String getVersion() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_VERSION).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns reference to a opendma:VersionCollection object representing all versions of this document or null if versioning is not supported.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONCOLLECTION).getReference()</code>.
     * 
     * <p>Property opendma:<b>VersionCollection</b>: Reference to VersionCollection (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return reference to a opendma:VersionCollection object representing all versions of this document or null if versioning is not supported
     */
    public OdmaVersionCollection getVersionCollection() {
        try {
            return (OdmaVersionCollection)getProperty(OdmaCommonNames.PROPERTY_VERSIONCOLLECTION).getReference();
        }
        catch(ClassCastException cce) {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the unique object identifier identifying this logical document independent from the specific version inside its opendma:Repository.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONINDEPENDENTID).getId()</code>.
     * 
     * <p>Property opendma:<b>VersionIndependentId</b>: String<br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Retrieving this Id from the Repository will result in the latest version</p>
     * 
     * @return the unique object identifier identifying this logical document independent from the specific version inside its opendma:Repository
     */
    public OdmaId getVersionIndependentId() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTID).getId();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the global unique object identifier globally identifying this logical document independent from the specific version.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONINDEPENDENTGUID).getGuid()</code>.
     * 
     * <p>Property opendma:<b>VersionIndependentGuid</b>: String<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return the global unique object identifier globally identifying this logical document independent from the specific version
     */
    public OdmaGuid getVersionIndependentGuid() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTGUID).getGuid();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns set of opendma:ContentElement objects representing the individual binary elements this document is made up of.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTELEMENTS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>ContentElements</b>: Reference to ContentElement (opendma)<br/>
     * [MultiValue] [Writable] [Optional]<br/>
     * Typically has only one element. Can contain more then one element in rare cases, e.g. if individual pages of a document are scanned as separate images</p>
     * 
     * @return set of opendma:ContentElement objects representing the individual binary elements this document is made up of
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaContentElement> getContentElements() {
        try {
            return (Iterable<OdmaContentElement>)getProperty(OdmaCommonNames.PROPERTY_CONTENTELEMENTS).getReferenceIterable();
        }
        catch(ClassCastException cce) {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the combined conent type of the whole Document, calculated from the content types of each ContentElement.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_COMBINEDCONTENTTYPE).getString()</code>.
     * 
     * <p>Property opendma:<b>CombinedContentType</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the combined conent type of the whole Document, calculated from the content types of each ContentElement
     */
    public String getCombinedContentType() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setCombinedContentType(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the dedicated primary ContentElement. May only be null if ContentElements is empty..<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PRIMARYCONTENTELEMENT).getReference()</code>.
     * 
     * <p>Property opendma:<b>PrimaryContentElement</b>: Reference to ContentElement (opendma)<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the dedicated primary ContentElement. May only be null if ContentElements is empty.
     */
    public OdmaContentElement getPrimaryContentElement() {
        try {
            return (OdmaContentElement)getProperty(OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT).getReference();
        }
        catch(ClassCastException cce) {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setPrimaryContentElement(OdmaContentElement newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the timestamp when this version of this document has been created.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>CreatedAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the timestamp when this version of this document has been created
     */
    public Date getCreatedAt() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_CREATEDAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the User who created this version of this document.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * <p>Property opendma:<b>CreatedBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the User who created this version of this document
     */
    public String getCreatedBy() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_CREATEDBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the timestamp when this version of this document has been modified the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>LastModifiedAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the timestamp when this version of this document has been modified the last time
     */
    public Date getLastModifiedAt() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_LASTMODIFIEDAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the user who modified this version of this document the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * <p>Property opendma:<b>LastModifiedBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the user who modified this version of this document the last time
     */
    public String getLastModifiedBy() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_LASTMODIFIEDBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns indicates if this document is checked out.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUT).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>CheckedOut</b>: Boolean<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return indicates if this document is checked out
     */
    public Boolean isCheckedOut() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_CHECKEDOUT).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the timestamp when this version of the document has been checked out, null if this document is not checked out.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUTAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>CheckedOutAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the timestamp when this version of the document has been checked out, null if this document is not checked out
     */
    public Date getCheckedOutAt() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_CHECKEDOUTAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the user who checked out this version of this document, null if this document is not checked out.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUTBY).getString()</code>.
     * 
     * <p>Property opendma:<b>CheckedOutBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the user who checked out this version of this document, null if this document is not checked out
     */
    public String getCheckedOutBy() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_CHECKEDOUTBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
