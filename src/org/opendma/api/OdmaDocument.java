package org.opendma.api;

import java.util.Date;

import org.opendma.api.collections.ContentElementList;
import org.opendma.api.collections.OdmaVersionEnumeration;

/**
 * The <i>Document</i> specific version of the <code>{@link OdmaObject}</code> interface that offers short cuts to
 * all defined OpenDMA properties.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaDocument extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the title of this <i>document</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).getString()</code>.
     * 
     * @return the title of this <i>document</i>
     */
    public String getTitle();

    /**
     * Returns the version string of this <i>document</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSION).getString()</code>.
     * 
     * @return the version string of this <i>document</i>
     */
    public String getVersion();

    /**
     * Returns the enumeration of all versions of this <i>document</i>.<br>
     * Shortcut for
     * <code>(OdmaVersionEnumeration)getProperty(OdmaTypes.PROPERTY_VERSIONCOLLECTION).getObjectList()</code>.
     * 
     * @return the version string of this <i>document</i>
     */
    public OdmaVersionEnumeration getVersionCollection();

    /**
     * Returns the list of all content elements of this <i>document</i>.<br>
     * Shortcut for
     * <code>((OdmaContentElementEnumeration)getProperty(OdmaTypes.PROPERTY_CONTENTELEMENTS).getObjectList()).asList()</code>.
     * 
     * @return the list of all content elements of this <i>document</i>
     */
    public ContentElementList getContentElements();

    /**
     * Returns the combined mime type of this <i>document</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_COMBINEDMIMETYPE).getString()</code>.
     * 
     * @return the version string of this <i>document</i>
     */
    public String getCombinedMimeType();

    /**
     * Returns the primary content of this <i>content element</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PRIMARYCONTENT).getContent()</code>.
     * 
     * @return the primary content of this <i>content element</i>
     */
    public OdmaContent getPrimaryContent();

    /**
     * Returns the primary size of this <i>content element</i> in octets.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PRIMARYSIZE).getLong()</code>.
     * 
     * @return the primary size of this <i>content element</i> in octets
     */
    public long getPrimarySize();

    /**
     * Returns the primary mime type of this <i>content element</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PRIMARYMIMETYPE).getString()</code>.
     * 
     * @return the primary mime type of this <i>content element</i>
     */
    public String getPrimaryMimeType();

    /**
     * Returns the primary file name of this <i>content element</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PRIMARYFILENAME).getString()</code>.
     * 
     * @return the primary file name of this <i>content element</i>
     */
    public String getPrimaryFileName();

    /**
     * Returns true if and only if this <i>document</i> has been checked out. The method
     * <code>{@link #getCheckedOutBy()}</code> will return the user who has checked out this document and the method
     * <code>{@link #getCheckedOutAt()}</code> will return the <code>Date</code> when this <i>document</i> has been
     * checked out.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ISCHECKEDOUT).getBoolean()</code>.
     * 
     * @return true if and only if this <i>document</i> has been checked out
     */
    public boolean isCheckedOut();

    /**
     * Returns the <code>Date</code> when this <i>document</i> has been created.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * @return the <code>Date</code> when this <i>document</i> has been created
     */
    public Date getCreatedAt();

    /**
     * Returns the <code>Date</code> when this <i>document</i> has been modified the last time. This date must be
     * idendical to the creation date of the current version.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * @return the <code>Date</code> when this <i>document</i> has been modified the last time
     */
    public Date getLastModifiedAt();

    /**
     * Returns the <code>Date</code> when this <i>document</i> has been checked out.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUTAT).getDateTime()</code>.
     * 
     * @return the <code>Date</code> when this <i>document</i> has been checked out
     */
    public Date getCheckedOutAt();

    /**
     * Returns the name of the user who has created this <i>document</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * @return the name of the user who has created this <i>document</i>
     */
    public String getCreatedBy();

    /**
     * Returns the name of the user who has modified this <i>document</i> the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * @return the name of the user who has modified this <i>document</i> the last time
     */
    public String getLastModifiedBy();

    /**
     * Returns the name of the user who has checked out this document.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHECKEDOUTBY).getString()</code>.
     * 
     * @return the name of the user who has checked out this document
     */
    public String getCheckedOutBy();

}