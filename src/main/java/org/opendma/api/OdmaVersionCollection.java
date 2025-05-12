package org.opendma.api;

import java.util.Date;

/**
 * Full description follows.
 */
public interface OdmaVersionCollection extends OdmaObject {

    /**
     * Returns collection of all versions of the <code>Document</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONS).getReferenceIterable()</code>.
     * 
     * <p>Property <b>Versions</b> (opendma): <b>Reference to Document (opendma)</b><br/>
     * [MultiValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return collection of all versions of the <code>Document</code>
     */
    Iterable<OdmaDocument> getVersions();

    /**
     * Returns the latest version of this <code>Document</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LATEST).getReference()</code>.
     * 
     * <p>Property <b>Latest</b> (opendma): <b>Reference to Document (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the latest version of this <code>Document</code>
     */
    OdmaDocument getLatest();

    /**
     * Returns the last released version of this <code>Document</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_RELEASED).getReference()</code>.
     * 
     * <p>Property <b>Released</b> (opendma): <b>Reference to Document (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the last released version of this <code>Document</code>
     */
    OdmaDocument getReleased();

    /**
     * Returns the version of this <code>Document</code> currently beeing worked on during a checkout. Only valid if and only if the corresponding <code>Document</code> is checked out..<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INPROGRESS).getReference()</code>.
     * 
     * <p>Property <b>InProgress</b> (opendma): <b>Reference to Document (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the version of this <code>Document</code> currently beeing worked on during a checkout. Only valid if and only if the corresponding <code>Document</code> is checked out.
     */
    OdmaDocument getInProgress();

    /**
     * Returns the date when this <code>Document</code> has been created.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * <p>Property <b>CreatedAt</b> (opendma): <b>DateTime</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Document</code> has been created
     */
    Date getCreatedAt();

    /**
     * Returns the user who has created this <code>Document</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * <p>Property <b>CreatedBy</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the user who has created this <code>Document</code>
     */
    String getCreatedBy();

}
