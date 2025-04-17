package org.opendma.templates;

import org.opendma.api.OdmaPropertyInfo;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaQName;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaChoiceValue;

/**
 * Template implementation of the interface <code>{@link OdmaPropertyInfo}</code>.<p>
 * 
 * Full description follows.
 */
public class OdmaPropertyInfoTemplate extends OdmaObjectTemplate implements OdmaPropertyInfo {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaPropertyInfoTemplate

    /**
     * Returns the internal (technical) <i>name</i> of this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the internal (technical) <i>name</i> of this <code>PropertyInfo</code>
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
     * Returns the <i>namespace</i> of this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMESPACE).getString()</code>.
     * 
     * <p>Property <b>Namespace</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>namespace</i> of this <code>PropertyInfo</code>
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
     * Returns the <i>display name</i> of this <code>PropertyInfo</code> to be displayed to end users.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property <b>DisplayName</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>display name</i> of this <code>PropertyInfo</code> to be displayed to end users
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
     * Returns the numeric ID of the data type described by this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATATYPE).getInteger()</code>.
     * 
     * <p>Property <b>DataType</b> (opendma): <b>Integer</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the numeric ID of the data type described by this <code>PropertyInfo</code>
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
     * Returns the required <code>Class</code> of the object referenced by this property if this <code>PropertyInfo</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCECLASS).getReference()</code>.
     * 
     * <p>Property <b>ReferenceClass</b> (opendma): <b>Reference to Class (opendma)</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the required <code>Class</code> of the object referenced by this property if this <code>PropertyInfo</code>
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
     * Returns whether <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_MULTIVALUE).getBoolean()</code>.
     * 
     * <p>Property <b>MultiValue</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether <code>Object</code>s of this <code>Class</code> can be created or not
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
     * Returns whether <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REQUIRED).getBoolean()</code>.
     * 
     * <p>Property <b>Required</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether <code>Object</code>s of this <code>Class</code> can be created or not
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
     * Returns whether this <code>Class</code> should be displayed to end users or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_READONLY).getBoolean()</code>.
     * 
     * <p>Property <b>ReadOnly</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> should be displayed to end users or not
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
     * Returns whether this <code>Class</code> should be displayed to end users or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).getBoolean()</code>.
     * 
     * <p>Property <b>Hidden</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> should be displayed to end users or not
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
     * Returns whether this <code>Class</code> is defined by the system (true) or by users (false).<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).getBoolean()</code>.
     * 
     * <p>Property <b>System</b> (opendma): <b>Boolean</b><br/>
     * [SingleValue] [Writable] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return whether this <code>Class</code> is defined by the system (true) or by users (false)
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
     * Returns set of possible choices the values of this property is limited to or null if there are no constraints for the value of this property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CHOICES).getReferenceIterable()</code>.
     * 
     * <p>Property <b>Choices</b> (opendma): <b>Reference to ChoiceValue (opendma)</b><br/>
     * [MultiValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return set of possible choices the values of this property is limited to or null if there are no constraints for the value of this property
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
     * the qualified name of this <code>Class</code><br>
     * <p>Full description follows.</p>
     * 
     * @return the qualified name of this <code>Class</code>
     */
    public OdmaQName getQName() {
        return new OdmaQName(getNamespace(),getName());
    }

}
