package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>Repository</i> specific version of the <code>{@link OdmaObject}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * A Repository represents a place where all Objects are stored. It often constitues a data isolation boundary where objects with different management requirements or access restrictions are separated into different repositories. Qualified names of classes and properties as well as unique object identifiers are only unique within a repository. They can be reused across different repositories. Object references are limited in scope within a single repository.
 */
public interface OdmaRepository extends OdmaObject {

    /**
     * Returns the internal technical name of this repository.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return the internal technical name of this repository
     */
    String getName();

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
    void setName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the text shown to end users to refer to this repository.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return the text shown to end users to refer to this repository
     */
    String getDisplayName();

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
    void setDisplayName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Valid class object describing the class hierarchy root.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ROOTCLASS).getReference()</code>.
     * 
     * <p>Property opendma:<b>RootClass</b>: Reference to Class (opendma)<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return Valid class object describing the class hierarchy root
     */
    OdmaClass getRootClass();

    /**
     * Returns set of valid aspect objects without a super class.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ROOTASPECTS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>RootAspects</b>: Reference to Class (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return set of valid aspect objects without a super class
     */
    Iterable<OdmaClass> getRootAspects();

    /**
     * Returns Object that has the opendma:Folder aspect representing the single root if this repository has a dedicated folder tree, null otherwise.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ROOTFOLDER).getReference()</code>.
     * 
     * <p>Property opendma:<b>RootFolder</b>: Reference to Folder (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return Object that has the opendma:Folder aspect representing the single root if this repository has a dedicated folder tree, null otherwise
     */
    OdmaFolder getRootFolder();

}
