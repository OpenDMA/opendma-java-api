package org.opendma.api;

import org.opendma.api.collections.OdmaDocumentEnumeration;

/**
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaVersionCollection extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns collection of all versions of the <code>Document</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONS).getReferenceEnumeration()</code>.
     * 
     * <p>Property <b>Versions</b> (opendma): <b>Reference to Document (opendma)</b><br>
     * [MultiValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return collection of all versions of the <code>Document</code>
     */
    public OdmaDocumentEnumeration getVersions();

    /**
     * Returns the latest version of this <code>Document</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LATEST).getReference()</code>.
     * 
     * <p>Property <b>Latest</b> (opendma): <b>Reference to Document (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the latest version of this <code>Document</code>
     */
    public OdmaDocument getLatest();

    /**
     * Returns the last released version of this <code>Document</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_RELEASED).getReference()</code>.
     * 
     * <p>Property <b>Released</b> (opendma): <b>Reference to Document (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the last released version of this <code>Document</code>
     */
    public OdmaDocument getReleased();

    /**
     * Returns the version of this <code>Document</code> currently beeing worked on during a checkout. Only valid if and only if the corresponding <code>Document</code> is checked out..<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INPROGRESS).getReference()</code>.
     * 
     * <p>Property <b>InProgress</b> (opendma): <b>Reference to Document (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the version of this <code>Document</code> currently beeing worked on during a checkout. Only valid if and only if the corresponding <code>Document</code> is checked out.
     */
    public OdmaDocument getInProgress();

}
