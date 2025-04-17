package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>PropertyInfo</i> specific version of the <code>{@link OdmaObject}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * Full description follows.
 */
public interface OdmaPropertyInfo extends OdmaObject {

    // ----- Object specific property access -------------------------------------------------------

    /**
     * Returns the internal (technical) <i>name</i> of this <code>PropertyInfo</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the internal (technical) <i>name</i> of this <code>PropertyInfo</code>
     */
    String getName();

    /**
     * Sets the internal (technical) <i>name</i> of this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).setValue(value)</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the internal (technical) <i>name</i> of this <code>PropertyInfo</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the <i>namespace</i> of this <code>PropertyInfo</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMESPACE).getString()</code>.
     * 
     * <p>Property <b>Namespace</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>namespace</i> of this <code>PropertyInfo</code>
     */
    String getNamespace();

    /**
     * Sets the <i>namespace</i> of this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMESPACE).setValue(value)</code>.
     * 
     * <p>Property <b>Namespace</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the <i>namespace</i> of this <code>PropertyInfo</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setNamespace(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the <i>display name</i> of this <code>PropertyInfo</code> to be displayed to end users.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property <b>DisplayName</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>display name</i> of this <code>PropertyInfo</code> to be displayed to end users
     */
    String getDisplayName();

    /**
     * Sets the <i>display name</i> of this <code>PropertyInfo</code> to be displayed to end users.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).setValue(value)</code>.
     * 
     * <p>Property <b>DisplayName</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the <i>display name</i> of this <code>PropertyInfo</code> to be displayed to end users
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setDisplayName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the numeric ID of the data type described by this <code>PropertyInfo</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATATYPE).getInteger()</code>.
     * 
     * <p>Property <b>DataType</b> (opendma): <b>Integer</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the numeric ID of the data type described by this <code>PropertyInfo</code>
     */
    Integer getDataType();

    /**
     * Sets the numeric ID of the data type described by this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATATYPE).setValue(value)</code>.
     * 
     * <p>Property <b>DataType</b> (opendma): <b>Integer</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the numeric ID of the data type described by this <code>PropertyInfo</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setDataType(Integer newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the required <code>Class</code> of the object referenced by this property if this <code>PropertyInfo</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCECLASS).getReference()</code>.
     * 
     * <p>Property <b>ReferenceClass</b> (opendma): <b>Reference to Class (opendma)</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the required <code>Class</code> of the object referenced by this property if this <code>PropertyInfo</code>
     */
    OdmaClass getReferenceClass();

    /**
     * Sets the required <code>Class</code> of the object referenced by this property if this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCECLASS).setValue(value)</code>.
     * 
     * <p>Property <b>ReferenceClass</b> (opendma): <b>Reference to Class (opendma)</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the required <code>Class</code> of the object referenced by this property if this <code>PropertyInfo</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setReferenceClass(OdmaClass newValue) throws OdmaAccessDeniedException;

    /**
     * Returns whether <code>Object</code>s of this <code>Class</code> can be created or not.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MULTIVALUE).getBoolean()</code>.
     * 
     * <p>Property <b>MultiValue</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether <code>Object</code>s of this <code>Class</code> can be created or not
     */
    Boolean isMultiValue();

    /**
     * Sets whether <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MULTIVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>MultiValue</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for whether <code>Object</code>s of this <code>Class</code> can be created or not
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setMultiValue(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns whether <code>Object</code>s of this <code>Class</code> can be created or not.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REQUIRED).getBoolean()</code>.
     * 
     * <p>Property <b>Required</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether <code>Object</code>s of this <code>Class</code> can be created or not
     */
    Boolean isRequired();

    /**
     * Sets whether <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REQUIRED).setValue(value)</code>.
     * 
     * <p>Property <b>Required</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for whether <code>Object</code>s of this <code>Class</code> can be created or not
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setRequired(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns whether this <code>Class</code> should be displayed to end users or not.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_READONLY).getBoolean()</code>.
     * 
     * <p>Property <b>ReadOnly</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> should be displayed to end users or not
     */
    Boolean isReadOnly();

    /**
     * Sets whether this <code>Class</code> should be displayed to end users or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_READONLY).setValue(value)</code>.
     * 
     * <p>Property <b>ReadOnly</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for whether this <code>Class</code> should be displayed to end users or not
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setReadOnly(Boolean newValue) throws OdmaAccessDeniedException;

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
     * Returns set of possible choices the values of this property is limited to or null if there are no constraints for the value of this property.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHOICES).getReferenceIterable()</code>.
     * 
     * <p>Property <b>Choices</b> (opendma): <b>Reference to ChoiceValue (opendma)</b><br/>
     * [MultiValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return set of possible choices the values of this property is limited to or null if there are no constraints for the value of this property
     */
    Iterable<OdmaChoiceValue> getChoices();

    /**
     * Returns the qualified name of this <code>Class</code>.<br/>
     * Full description follows.
     * 
     * @return the qualified name of this <code>Class</code>
     */
    public OdmaQName getQName();

}
