package org.opendma.impl.core;

import org.opendma.api.OdmaType;
import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaChoiceValue;
import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaPropertyInfo;
import org.opendma.api.OdmaQName;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.impl.OdmaPropertyImpl;

public class OdmaStaticSystemPropertyInfo extends OdmaStaticSystemObject implements OdmaPropertyInfo {

    protected void patchReferenceClass(OdmaClass newReferenceClass) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        properties.put(OdmaCommonNames.PROPERTY_REFERENCECLASS,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_REFERENCECLASS,newReferenceClass,OdmaType.REFERENCE,false,true));
    }

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaPropertyInfoTemplate

    /**
     * Returns The name part of the qualified name of this property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return The name part of the qualified name of this property
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
     * Returns The namespace part of the qualified name of this property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMESPACE).getString()</code>.
     * 
     * <p>Property opendma:<b>Namespace</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return The namespace part of the qualified name of this property
     */
    public String getNamespace() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_NAMESPACE).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setNamespace(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_NAMESPACE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Text shown to end users to refer to this property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return Text shown to end users to refer to this property
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
     * Returns Numeric data type ID.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATATYPE).getInteger()</code>.
     * 
     * <p>Property opendma:<b>DataType</b>: Integer<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The data type of the property described by this object. See also the OdmaType enumeration for a mapping between the numeric type id and the type.</p>
     * 
     * @return Numeric data type ID
     */
    public Integer getDataType() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_DATATYPE).getInteger();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setDataType(Integer newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_DATATYPE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns The opendma:Class values of the property described by this object must be an instance of if and only if the data type is "Reference" (8), null otherwise.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCECLASS).getReference()</code>.
     * 
     * <p>Property opendma:<b>ReferenceClass</b>: Reference to Class (opendma)<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return The opendma:Class values of the property described by this object must be an instance of if and only if the data type is "Reference" (8), null otherwise
     */
    public OdmaClass getReferenceClass() {
        try {
            return (OdmaClass)getProperty(OdmaCommonNames.PROPERTY_REFERENCECLASS).getReference();
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
    public void setReferenceClass(OdmaClass newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_REFERENCECLASS).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Indicates if this property has single or multi cardinality.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MULTIVALUE).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>MultiValue</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if this property has single or multi cardinality
     */
    public Boolean isMultiValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_MULTIVALUE).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setMultiValue(Boolean newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_MULTIVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Indicates if at least one value is required.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REQUIRED).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Required</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if at least one value is required
     */
    public Boolean isRequired() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_REQUIRED).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setRequired(Boolean newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_REQUIRED).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Indicates if this property can be updated.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_READONLY).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>ReadOnly</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if this property can be updated
     */
    public Boolean isReadOnly() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_READONLY).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setReadOnly(Boolean newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_READONLY).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Indicates if this class should be hidden from end users and probably administrators.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Hidden</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if this class should be hidden from end users and probably administrators
     */
    public Boolean isHidden() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_HIDDEN).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setHidden(Boolean newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_HIDDEN).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Indicates if instances of this property are owned and managed by the system.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>System</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if instances of this property are owned and managed by the system
     */
    public Boolean isSystem() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_SYSTEM).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

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
    public void setSystem(Boolean newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_SYSTEM).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns List of opendma:ChoiceValue instances each describing one valid value for this property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHOICES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Choices</b>: Reference to ChoiceValue (opendma)<br/>
     * [MultiValue] [Writable] [Optional]<br/>
     * OpenDMA can restrict values of a property to a predefined set of valid values. In this case, this set is not empty and each opendma:ChoiceValue describes one valid option. If this set is empty, any value is allowed.</p>
     * 
     * @return List of opendma:ChoiceValue instances each describing one valid value for this property
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaChoiceValue> getChoices() {
        try {
            return (Iterable<OdmaChoiceValue>)getProperty(OdmaCommonNames.PROPERTY_CHOICES).getReferenceIterable();
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
     * the qualified name of this class<br>
     * <p>A convenience shortcut to getting the name and namespace separately</p>
     * 
     * @return the qualified name of this class
     */
    public OdmaQName getQName() {
        return new OdmaQName(getNamespace(),getName());
    }

}
