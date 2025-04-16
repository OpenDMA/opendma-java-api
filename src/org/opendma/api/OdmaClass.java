package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>Class</i> specific version of the <code>{@link OdmaObject}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * The <i>Class</i> specific version of the <code>{@link OdmaObject}</code> interface that offers short cuts to all defined OpenDMA properties.
 */
public interface OdmaClass extends OdmaObject {

    // ----- Object specific property access -------------------------------------------------------

    /**
     * Returns the internal (technical) <i>name</i> of this <code>Class</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the internal (technical) <i>name</i> of this <code>Class</code>
     */
    String getName();

    /**
     * Sets the internal (technical) <i>name</i> of this <code>Class</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).setValue(value)</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the internal (technical) <i>name</i> of this <code>Class</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the name <i>qualifier</i> of this <code>Class</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMEQUALIFIER).getString()</code>.
     * 
     * <p>Property <b>NameQualifier</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the name <i>qualifier</i> of this <code>Class</code>
     */
    String getNameQualifier();

    /**
     * Sets the name <i>qualifier</i> of this <code>Class</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMEQUALIFIER).setValue(value)</code>.
     * 
     * <p>Property <b>NameQualifier</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the name <i>qualifier</i> of this <code>Class</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setNameQualifier(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the <i>display name</i> of this <code>Class</code> to be displayed to end users.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property <b>DisplayName</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>display name</i> of this <code>Class</code> to be displayed to end users
     */
    String getDisplayName();

    /**
     * Sets the <i>display name</i> of this <code>Class</code> to be displayed to end users.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).setValue(value)</code>.
     * 
     * <p>Property <b>DisplayName</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the <i>display name</i> of this <code>Class</code> to be displayed to end users
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setDisplayName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the <i>super</i> <code>Class</code> that is extended by this <code>Class</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUPERCLASS).getReference()</code>.
     * 
     * <p>Property <b>SuperClass</b> (opendma): <b>Reference to Class (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>super</i> <code>Class</code> that is extended by this <code>Class</code>
     */
    OdmaClass getSuperClass();

    /**
     * Returns the list of <i>aspects</i> that are implemented by this <code>Class</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASPECTS).getReferenceIterable()</code>.
     * 
     * <p>Property <b>Aspects</b> (opendma): <b>Reference to Class (opendma)</b><br/>
     * [MultiValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the list of <i>aspects</i> that are implemented by this <code>Class</code>
     */
    Iterable<OdmaClass> getAspects();

    /**
     * Returns the list of <i>properties</i> that are desclared by this <code>Class</code> (does not contain inherited properties)..<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DECLAREDPROPERTIES).getReferenceIterable()</code>.
     * 
     * <p>Property <b>DeclaredProperties</b> (opendma): <b>Reference to PropertyInfo (opendma)</b><br/>
     * [MultiValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the list of <i>properties</i> that are desclared by this <code>Class</code> (does not contain inherited properties).
     */
    Iterable<OdmaPropertyInfo> getDeclaredProperties();

    /**
     * Returns the list of <i>properties</i> that are effective for objects of this <code>Class</code>. Contains inherited and declared properties..<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PROPERTIES).getReferenceIterable()</code>.
     * 
     * <p>Property <b>Properties</b> (opendma): <b>Reference to PropertyInfo (opendma)</b><br/>
     * [MultiValue] [ReadOnly] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the list of <i>properties</i> that are effective for objects of this <code>Class</code>. Contains inherited and declared properties.
     */
    Iterable<OdmaPropertyInfo> getProperties();

    /**
     * Returns whether this <code>Class</code> describes an Aspect or a valid class object.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASPECT).getBoolean()</code>.
     * 
     * <p>Property <b>Aspect</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> describes an Aspect or a valid class object
     */
    Boolean isAspect();

    /**
     * Returns whether <code>Object</code>s of this <code>Class</code> can be created or not.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INSTANTIABLE).getBoolean()</code>.
     * 
     * <p>Property <b>Instantiable</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether <code>Object</code>s of this <code>Class</code> can be created or not
     */
    Boolean isInstantiable();

    /**
     * Sets whether <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INSTANTIABLE).setValue(value)</code>.
     * 
     * <p>Property <b>Instantiable</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for whether <code>Object</code>s of this <code>Class</code> can be created or not
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setInstantiable(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns whether this <code>Class</code> should be displayed to end users or not.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).getBoolean()</code>.
     * 
     * <p>Property <b>Hidden</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> should be displayed to end users or not
     */
    Boolean isHidden();

    /**
     * Sets whether this <code>Class</code> should be displayed to end users or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).setValue(value)</code>.
     * 
     * <p>Property <b>Hidden</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for whether this <code>Class</code> should be displayed to end users or not
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setHidden(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns whether this <code>Class</code> is defined by the system (true) or by users (false).<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).getBoolean()</code>.
     * 
     * <p>Property <b>System</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> is defined by the system (true) or by users (false)
     */
    Boolean isSystem();

    /**
     * Sets whether this <code>Class</code> is defined by the system (true) or by users (false).<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).setValue(value)</code>.
     * 
     * <p>Property <b>System</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for whether this <code>Class</code> is defined by the system (true) or by users (false)
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setSystem(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns whether objects of this class can be retrieved from a session by their id or not.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_RETRIEVABLE).getBoolean()</code>.
     * 
     * <p>Property <b>Retrievable</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether objects of this class can be retrieved from a session by their id or not
     */
    Boolean isRetrievable();

    /**
     * Returns whether objects of this class can be found by a search query or not.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SEARCHABLE).getBoolean()</code>.
     * 
     * <p>Property <b>Searchable</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether objects of this class can be found by a search query or not
     */
    Boolean isSearchable();

    /**
     * Returns the list of <code>Class</code>es that extend this class (i.e. that contain a reference to this <code>Class</code> in their <code>SuperClass</code> property).<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUBCLASSES).getReferenceIterable()</code>.
     * 
     * <p>Property <b>SubClasses</b> (opendma): <b>Reference to Class (opendma)</b><br/>
     * [MultiValue] [ReadOnly] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the list of <code>Class</code>es that extend this class (i.e. that contain a reference to this <code>Class</code> in their <code>SuperClass</code> property)
     */
    Iterable<OdmaClass> getSubClasses();

    /**
     * Returns the qualified name of this <code>Class</code>.<br/>
     * Full description follows.
     * 
     * @return the qualified name of this <code>Class</code>
     */
    public OdmaQName getQName();

}
