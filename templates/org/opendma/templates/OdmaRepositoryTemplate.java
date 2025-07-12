package org.opendma.templates;

import org.opendma.api.OdmaRepository;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaFolder;

/**
 * Template implementation of the interface <code>{@link OdmaRepository}</code>.<p>
 * 
 * A Repository represents a place where all Objects are stored. It often constitues a data isolation boundary where objects with different management requirements or access restrictions are separated into different repositories. Qualified names of classes and properties as well as unique object identifiers are only unique within a repository. They can be reused across different repositories. Object references are limited in scope within a single repository.
 */
public class OdmaRepositoryTemplate extends OdmaObjectTemplate implements OdmaRepository {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaRepositoryTemplate

    /**
     * Returns the internal technical name of this repository.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return the internal technical name of this repository
     */
    public String getName() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_NAME).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the internal technical name of this repository.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @param newValue
     *             The new value for the internal technical name of this repository
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setName(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_NAME).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the text shown to end users to refer to this repository.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return the text shown to end users to refer to this repository
     */
    public String getDisplayName() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_DISPLAYNAME).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the text shown to end users to refer to this repository.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @param newValue
     *             The new value for the text shown to end users to refer to this repository
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setDisplayName(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_DISPLAYNAME).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Valid class object describing the class hierarchy root.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ROOTCLASS).getReference()</code>.
     * 
     * <p>Property opendma:<b>RootClass</b>: Reference to Class (opendma)<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return Valid class object describing the class hierarchy root
     */
    public OdmaClass getRootClass() {
        try {
            return (OdmaClass)getProperty(OdmaCommonNames.PROPERTY_ROOTCLASS).getReference();
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
     * Returns set of valid aspect objects without a super class.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ROOTASPECTS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>RootAspects</b>: Reference to Class (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return set of valid aspect objects without a super class
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaClass> getRootAspects() {
        try {
            return (Iterable<OdmaClass>)getProperty(OdmaCommonNames.PROPERTY_ROOTASPECTS).getReferenceIterable();
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
     * Returns Object that has the opendma:Folder aspect representing the single root if this repository has a dedicated folder tree, null otherwise.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ROOTFOLDER).getReference()</code>.
     * 
     * <p>Property opendma:<b>RootFolder</b>: Reference to Folder (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return Object that has the opendma:Folder aspect representing the single root if this repository has a dedicated folder tree, null otherwise
     */
    public OdmaFolder getRootFolder() {
        try {
            return (OdmaFolder)getProperty(OdmaCommonNames.PROPERTY_ROOTFOLDER).getReference();
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
