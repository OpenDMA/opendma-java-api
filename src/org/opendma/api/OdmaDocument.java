package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.collections.OdmaContentElementEnumeration;
import java.util.Date;

/**
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaDocument extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the <i>title</i> of this <code>Document</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).getString()</code>.
     * 
     * <p>Property <b>Title</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the <i>title</i> of this <code>Document</code>
     */
    public String getTitle();

    /**
     * Sets the <i>title</i> of this <code>Document</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).setValue(value)</code>.
     * 
     * <p>Property <b>Title</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setTitle(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the <i>version string</i> describing this <i>version</i> of this <code>Document</code> (e.g. 1.0, 1.1, 1.2, 2.0).<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSION).getString()</code>.
     * 
     * <p>Property <b>Version</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the <i>version string</i> describing this <i>version</i> of this <code>Document</code> (e.g. 1.0, 1.1, 1.2, 2.0)
     */
    public String getVersion();

    /**
     * Returns reference to a <i>VersionCollection</i> object containing the collection of all <i>versions</i> of this <code>Document</code> along with other information.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONCOLLECTION).getReference()</code>.
     * 
     * <p>Property <b>VersionCollection</b> (opendma): <b>Reference to VersionCollection (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return reference to a <i>VersionCollection</i> object containing the collection of all <i>versions</i> of this <code>Document</code> along with other information
     */
    public OdmaVersionCollection getVersionCollection();

    /**
     * Returns the <i>unique object identifier</i> identifying this logical document independent from the specific version inside its <code>Repository</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONINDEPENDENTID).getId()</code>.
     * 
     * <p>Property <b>VersionIndependentId</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>unique object identifier</i> identifying this logical document independent from the specific version inside its <code>Repository</code>
     */
    public OdmaId getVersionIndependentId();

    /**
     * Returns the <i>global unique object identifier</i> globally identifying this logical document independent from the specific version.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONINDEPENDENTGUID).getGuid()</code>.
     * 
     * <p>Property <b>VersionIndependentGuid</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>global unique object identifier</i> globally identifying this logical document independent from the specific version
     */
    public OdmaGuid getVersionIndependentGuid();

    /**
     * Returns the collection of all <code>ContentElement</code>s this <code>Document</code> consists of.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTELEMENTS).getReferenceEnumeration()</code>.
     * 
     * <p>Property <b>ContentElements</b> (opendma): <b>Reference to ContentElement (opendma)</b><br>
     * [MultiValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the collection of all <code>ContentElement</code>s this <code>Document</code> consists of
     */
    public OdmaContentElementEnumeration getContentElements();

    /**
     * Returns the combined mime type that has been build over all <code>ContentElement</code>s this <code>Document</code> consists of.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_COMBINEDCONTENTTYPE).getString()</code>.
     * 
     * <p>Property <b>CombinedContentType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the combined mime type that has been build over all <code>ContentElement</code>s this <code>Document</code> consists of
     */
    public String getCombinedContentType();

    /**
     * Sets the combined mime type that has been build over all <code>ContentElement</code>s this <code>Document</code> consists of.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_COMBINEDCONTENTTYPE).setValue(value)</code>.
     * 
     * <p>Property <b>CombinedContentType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setCombinedContentType(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the primary <code>ContentElement</code>s that represents this <code>Document</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PRIMARYCONTENTELEMENT).getReference()</code>.
     * 
     * <p>Property <b>PrimaryContentElement</b> (opendma): <b>Reference to ContentElement (opendma)</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the primary <code>ContentElement</code>s that represents this <code>Document</code>
     */
    public OdmaContentElement getPrimaryContentElement();

    /**
     * Sets the primary <code>ContentElement</code>s that represents this <code>Document</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PRIMARYCONTENTELEMENT).setValue(value)</code>.
     * 
     * <p>Property <b>PrimaryContentElement</b> (opendma): <b>Reference to ContentElement (opendma)</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setPrimaryContentElement(OdmaContentElement value) throws OdmaAccessDeniedException;

    /**
     * Returns the date when this version of this <code>Document</code> has been created.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * <p>Property <b>CreatedAt</b> (opendma): <b>DateTime</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the date when this version of this <code>Document</code> has been created
     */
    public Date getCreatedAt();

    /**
     * Returns the user who has created this version of this <code>Document</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * <p>Property <b>CreatedBy</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the user who has created this version of this <code>Document</code>
     */
    public String getCreatedBy();

    /**
     * Returns the date when this version of this <code>Document</code> has been modified the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * <p>Property <b>LastModifiedAt</b> (opendma): <b>DateTime</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the date when this version of this <code>Document</code> has been modified the last time
     */
    public Date getLastModifiedAt();

    /**
     * Returns the user who has modified this version of this <code>Document</code> the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * <p>Property <b>LastModifiedBy</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the user who has modified this version of this <code>Document</code> the last time
     */
    public String getLastModifiedBy();

    /**
     * Returns <code>true</code> if and only if this <code>Document</code> is checked out.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUT).getBoolean()</code>.
     * 
     * <p>Property <b>CheckedOut</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return <code>true</code> if and only if this <code>Document</code> is checked out
     */
    public Boolean getCheckedOut();

    /**
     * Returns the date when this <code>Document</code> has been checked out.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUTAT).getDateTime()</code>.
     * 
     * <p>Property <b>CheckedOutAt</b> (opendma): <b>DateTime</b><br>
     * [SingleValue] [ReadOnly] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Document</code> has been checked out
     */
    public Date getCheckedOutAt();

    /**
     * Returns the user who has checked out this <code>Document</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUTBY).getString()</code>.
     * 
     * <p>Property <b>CheckedOutBy</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the user who has checked out this <code>Document</code>
     */
    public String getCheckedOutBy();

}
