package org.opendma.templates;

import org.opendma.api.OdmaFolder;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * Template implementation of the interface <code>{@link OdmaFolder}</code>.<p>
 * 
 * A Folder is an extension of the Container forming one single rooted loop-free tree.
 */
public class OdmaFolderTemplate extends OdmaContainerTemplate implements OdmaFolder {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaFolderTemplate

    /**
     * Returns the parent folder this folder is contained in.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PARENT).getReference()</code>.
     * 
     * <p>Property opendma:<b>Parent</b>: Reference to Folder (opendma)<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Following this property from folder object to folder object will ultimately lead to the single root folder of the Repository. This is the only folder having a null value for this property</p>
     * 
     * @return the parent folder this folder is contained in
     */
    public OdmaFolder getParent() {
        try {
            return (OdmaFolder)getProperty(OdmaCommonNames.PROPERTY_PARENT).getReference();
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
    public void setParent(OdmaFolder newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_PARENT).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the set of Folder objects that contain this folder in their opendma:Parent property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUBFOLDERS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>SubFolders</b>: Reference to Folder (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]<br/>
     * Using this property for a tree walk is safe. A folder is guaranteed to be loop free. It is neither defined if this set of objects is also part of the opendma:Containees property nor if there are corresponding association objects in opendma:Associations for each folder in this set.</p>
     * 
     * @return the set of Folder objects that contain this folder in their opendma:Parent property
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaFolder> getSubFolders() {
        try {
            return (Iterable<OdmaFolder>)getProperty(OdmaCommonNames.PROPERTY_SUBFOLDERS).getReferenceIterable();
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

}
