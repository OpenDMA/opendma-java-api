package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>PropertyInfo</i> specific version of the <code>{@link OdmaObject}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * Describes a property in OpenmDMA. Every object in OpenDMA has a reference to an opendma:Class which has the opendma:Properties set of PropertyInfo objects. Each describes one of the properties on the object.
 */
public interface OdmaPropertyInfo extends OdmaObject {

    /**
     * Returns The name part of the qualified name of this property.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return The name part of the qualified name of this property
     */
    String getName();

    /**
     * Sets The name part of the qualified name of this property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @param newValue
     *             The new value for The name part of the qualified name of this property
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns The namespace part of the qualified name of this property.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMESPACE).getString()</code>.
     * 
     * <p>Property opendma:<b>Namespace</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return The namespace part of the qualified name of this property
     */
    String getNamespace();

    /**
     * Sets The namespace part of the qualified name of this property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMESPACE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Namespace</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @param newValue
     *             The new value for The namespace part of the qualified name of this property
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setNamespace(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Text shown to end users to refer to this property.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return Text shown to end users to refer to this property
     */
    String getDisplayName();

    /**
     * Sets Text shown to end users to refer to this property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @param newValue
     *             The new value for Text shown to end users to refer to this property
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setDisplayName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Numeric data type ID.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATATYPE).getInteger()</code>.
     * 
     * <p>Property opendma:<b>DataType</b>: Integer<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The data type of the property described by this object. See also the OdmaType enumeration for a mapping between the numeric type id and the type.</p>
     * 
     * @return Numeric data type ID
     */
    Integer getDataType();

    /**
     * Sets Numeric data type ID.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATATYPE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>DataType</b>: Integer<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The data type of the property described by this object. See also the OdmaType enumeration for a mapping between the numeric type id and the type.</p>
     * 
     * @param newValue
     *             The new value for Numeric data type ID
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setDataType(Integer newValue) throws OdmaAccessDeniedException;

    /**
     * Returns The opendma:Class values of the property described by this object must be an instance of if and only if the data type is "Reference" (8), null otherwise.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCECLASS).getReference()</code>.
     * 
     * <p>Property opendma:<b>ReferenceClass</b>: Reference to Class (opendma)<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return The opendma:Class values of the property described by this object must be an instance of if and only if the data type is "Reference" (8), null otherwise
     */
    OdmaClass getReferenceClass();

    /**
     * Sets The opendma:Class values of the property described by this object must be an instance of if and only if the data type is "Reference" (8), null otherwise.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCECLASS).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>ReferenceClass</b>: Reference to Class (opendma)<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for The opendma:Class values of the property described by this object must be an instance of if and only if the data type is "Reference" (8), null otherwise
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setReferenceClass(OdmaClass newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Indicates if this property has single or multi cardinality.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MULTIVALUE).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>MultiValue</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if this property has single or multi cardinality
     */
    Boolean isMultiValue();

    /**
     * Sets Indicates if this property has single or multi cardinality.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MULTIVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>MultiValue</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for Indicates if this property has single or multi cardinality
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setMultiValue(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Indicates if at least one value is required.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REQUIRED).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Required</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if at least one value is required
     */
    Boolean isRequired();

    /**
     * Sets Indicates if at least one value is required.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REQUIRED).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Required</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for Indicates if at least one value is required
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setRequired(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Indicates if this property can be updated.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_READONLY).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>ReadOnly</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if this property can be updated
     */
    Boolean isReadOnly();

    /**
     * Sets Indicates if this property can be updated.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_READONLY).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>ReadOnly</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for Indicates if this property can be updated
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setReadOnly(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Indicates if this class should be hidden from end users and probably administrators.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Hidden</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if this class should be hidden from end users and probably administrators
     */
    Boolean isHidden();

    /**
     * Sets Indicates if this class should be hidden from end users and probably administrators.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Hidden</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for Indicates if this class should be hidden from end users and probably administrators
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setHidden(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Indicates if instances of this property are owned and managed by the system.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>System</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if instances of this property are owned and managed by the system
     */
    Boolean isSystem();

    /**
     * Sets Indicates if instances of this property are owned and managed by the system.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>System</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for Indicates if instances of this property are owned and managed by the system
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setSystem(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns List of opendma:ChoiceValue instances each describing one valid value for this property.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHOICES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Choices</b>: Reference to ChoiceValue (opendma)<br/>
     * [MultiValue] [Writable] [Optional]<br/>
     * OpenDMA can restrict values of a property to a predefined set of valid values. In this case, this set is not empty and each opendma:ChoiceValue describes one valid option. If this set is empty, any value is allowed.</p>
     * 
     * @return List of opendma:ChoiceValue instances each describing one valid value for this property
     */
    Iterable<OdmaChoiceValue> getChoices();

    /**
     * Returns the qualified name of this class.<br/>
     * A convenience shortcut to getting the name and namespace separately
     * 
     * @return the qualified name of this class
     */
    public OdmaQName getQName();

}
