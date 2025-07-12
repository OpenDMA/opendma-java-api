package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>Folder</i> specific version of the <code>{@link OdmaContainer}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * A Folder is an extension of the Container forming one single rooted loop-free tree.
 */
public interface OdmaFolder extends OdmaContainer {

    /**
     * Returns the parent folder this folder is contained in.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PARENT).getReference()</code>.
     * 
     * <p>Property opendma:<b>Parent</b>: Reference to Folder (opendma)<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Following this property from folder object to folder object will ultimately lead to the single root folder of the Repository. This is the only folder having a null value for this property</p>
     * 
     * @return the parent folder this folder is contained in
     */
    OdmaFolder getParent();

    /**
     * Sets the parent folder this folder is contained in.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PARENT).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Parent</b>: Reference to Folder (opendma)<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Following this property from folder object to folder object will ultimately lead to the single root folder of the Repository. This is the only folder having a null value for this property</p>
     * 
     * @param newValue
     *             The new value for the parent folder this folder is contained in
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setParent(OdmaFolder newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the set of Folder objects that contain this folder in their opendma:Parent property.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUBFOLDERS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>SubFolders</b>: Reference to Folder (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]<br/>
     * Using this property for a tree walk is safe. A folder is guaranteed to be loop free. It is neither defined if this set of objects is also part of the opendma:Containees property nor if there are corresponding association objects in opendma:Associations for each folder in this set.</p>
     * 
     * @return the set of Folder objects that contain this folder in their opendma:Parent property
     */
    Iterable<OdmaFolder> getSubFolders();

}
