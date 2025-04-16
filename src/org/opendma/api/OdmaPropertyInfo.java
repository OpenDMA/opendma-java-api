package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>PropertyInfo</i> specific version of the <code>{@link OdmaObject}</code> interface
 * that offers short cuts to all defined OpenDMA properties.<p>
 * 
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaPropertyInfo extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the internal (technical) <i>name</i> of this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the internal (technical) <i>name</i> of this <code>PropertyInfo</code>
     */
    public String getName();

    /**
     * Sets the internal (technical) <i>name</i> of this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).setValue(value)</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setName(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the name <i>qualifier</i> of this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMEQUALIFIER).getString()</code>.
     * 
     * <p>Property <b>NameQualifier</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the name <i>qualifier</i> of this <code>PropertyInfo</code>
     */
    public String getNameQualifier();

    /**
     * Sets the name <i>qualifier</i> of this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMEQUALIFIER).setValue(value)</code>.
     * 
     * <p>Property <b>NameQualifier</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setNameQualifier(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the <i>display name</i> of this <code>PropertyInfo</code> to be displayed to end users.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property <b>DisplayName</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>display name</i> of this <code>PropertyInfo</code> to be displayed to end users
     */
    public String getDisplayName();

    /**
     * Sets the <i>display name</i> of this <code>PropertyInfo</code> to be displayed to end users.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).setValue(value)</code>.
     * 
     * <p>Property <b>DisplayName</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setDisplayName(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the numeric ID of the data type described by this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATATYPE).getInteger()</code>.
     * 
     * <p>Property <b>DataType</b> (opendma): <b>Integer</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the numeric ID of the data type described by this <code>PropertyInfo</code>
     */
    public Integer getDataType();

    /**
     * Sets the numeric ID of the data type described by this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATATYPE).setValue(value)</code>.
     * 
     * <p>Property <b>DataType</b> (opendma): <b>Integer</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setDataType(Integer value) throws OdmaAccessDeniedException;

    /**
     * Returns the required <code>Class</code> of the object referenced by this property if this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCECLASS).getReference()</code>.
     * 
     * <p>Property <b>ReferenceClass</b> (opendma): <b>Reference to Class (opendma)</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the required <code>Class</code> of the object referenced by this property if this <code>PropertyInfo</code>
     */
    public OdmaClass getReferenceClass();

    /**
     * Sets the required <code>Class</code> of the object referenced by this property if this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCECLASS).setValue(value)</code>.
     * 
     * <p>Property <b>ReferenceClass</b> (opendma): <b>Reference to Class (opendma)</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setReferenceClass(OdmaClass value) throws OdmaAccessDeniedException;

    /**
     * Returns whether <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MULTIVALUE).getBoolean()</code>.
     * 
     * <p>Property <b>MultiValue</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return whether <code>Object</code>s of this <code>Class</code> can be created or not
     */
    public Boolean getMultiValue();

    /**
     * Sets whether <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MULTIVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>MultiValue</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setMultiValue(Boolean value) throws OdmaAccessDeniedException;

    /**
     * Returns whether <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REQUIRED).getBoolean()</code>.
     * 
     * <p>Property <b>Required</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return whether <code>Object</code>s of this <code>Class</code> can be created or not
     */
    public Boolean getRequired();

    /**
     * Sets whether <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REQUIRED).setValue(value)</code>.
     * 
     * <p>Property <b>Required</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setRequired(Boolean value) throws OdmaAccessDeniedException;

    /**
     * Returns whether this <code>Class</code> should be displayed to end users or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_READONLY).getBoolean()</code>.
     * 
     * <p>Property <b>ReadOnly</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> should be displayed to end users or not
     */
    public Boolean getReadOnly();

    /**
     * Sets whether this <code>Class</code> should be displayed to end users or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_READONLY).setValue(value)</code>.
     * 
     * <p>Property <b>ReadOnly</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setReadOnly(Boolean value) throws OdmaAccessDeniedException;

    /**
     * Returns whether this <code>Class</code> should be displayed to end users or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).getBoolean()</code>.
     * 
     * <p>Property <b>Hidden</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> should be displayed to end users or not
     */
    public Boolean getHidden();

    /**
     * Sets whether this <code>Class</code> should be displayed to end users or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).setValue(value)</code>.
     * 
     * <p>Property <b>Hidden</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setHidden(Boolean value) throws OdmaAccessDeniedException;

    /**
     * Returns whether this <code>Class</code> is defined by the system (true) or by users (false).<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).getBoolean()</code>.
     * 
     * <p>Property <b>System</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> is defined by the system (true) or by users (false)
     */
    public Boolean getSystem();

    /**
     * Sets whether this <code>Class</code> is defined by the system (true) or by users (false).<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).setValue(value)</code>.
     * 
     * <p>Property <b>System</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setSystem(Boolean value) throws OdmaAccessDeniedException;

    /**
     * Returns set of possible choices the values of this property is limited to or null if there are no constraints for the value of this property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHOICES).getReferenceIterable()</code>.
     * 
     * <p>Property <b>Choices</b> (opendma): <b>Reference to ChoiceValue (opendma)</b><br>
     * [MultiValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return set of possible choices the values of this property is limited to or null if there are no constraints for the value of this property
     */
    public Iterable<OdmaChoiceValue> getChoices();

    /**
     * the qualified name of this <code>Class</code><br>
     * <p>Full description follows.</p>
     * 
     * @return the qualified name of this <code>Class</code>
     */
    public OdmaQName getQName();

}
