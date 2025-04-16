package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>Folder</i> specific version of the <code>{@link OdmaContainer}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * Full description follows.
 */
public interface OdmaFolder extends OdmaContainer
{

    // ----- Object specific property access -------------------------------------------------------

    /**
     * Returns the <code>Folder</code> this <code>Folder</code> is a sub folder of.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PARENT).getReference()</code>.
     * 
     * <p>Property <b>Parent</b> (opendma): <b>Reference to Folder (opendma)</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the <code>Folder</code> this <code>Folder</code> is a sub folder of
     */
    OdmaFolder getParent();

    /**
     * Sets the <code>Folder</code> this <code>Folder</code> is a sub folder of.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PARENT).setValue(value)</code>.
     * 
     * <p>Property <b>Parent</b> (opendma): <b>Reference to Folder (opendma)</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the <code>Folder</code> this <code>Folder</code> is a sub folder of
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setParent(OdmaFolder newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the collection of <code>Folder</code>s which have this <code>Folder</code> in their parent ptoperty.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUBFOLDERS).getReferenceIterable()</code>.
     * 
     * <p>Property <b>SubFolders</b> (opendma): <b>Reference to Folder (opendma)</b><br/>
     * [MultiValue] [ReadOnly] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the collection of <code>Folder</code>s which have this <code>Folder</code> in their parent ptoperty
     */
    Iterable<OdmaFolder> getSubFolders();

}
