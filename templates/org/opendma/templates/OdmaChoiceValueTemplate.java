package org.opendma.templates;

import org.opendma.api.OdmaChoiceValue;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import java.util.Date;
import org.opendma.api.OdmaObject;

/**
 * Template implementation of the interface <code>{@link OdmaChoiceValue}</code>.<p>
 * 
 * Describes a possible value of a property
 */
public class OdmaChoiceValueTemplate extends OdmaObjectTemplate implements OdmaChoiceValue {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaChoiceValueTemplate

    /**
     * Returns Text shown to end users to refer to this possible value option.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * This DisplayName indirections allows Administrators to define friendly descriptions for end users while storing internal numbers or abbreviation in the system</p>
     * 
     * @return Text shown to end users to refer to this possible value option
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
     * Sets Text shown to end users to refer to this possible value option.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * This DisplayName indirections allows Administrators to define friendly descriptions for end users while storing internal numbers or abbreviation in the system</p>
     * 
     * @param newValue
     *             The new value for Text shown to end users to refer to this possible value option
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
     * Returns the String value of this choice or null, if the property info this choice is assigned to is not of data type String.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_STRINGVALUE).getString()</code>.
     * 
     * <p>Property opendma:<b>StringValue</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the String value of this choice or null, if the property info this choice is assigned to is not of data type String
     */
    public String getStringValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_STRINGVALUE).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the String value of this choice or null, if the property info this choice is assigned to is not of data type String.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_STRINGVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>StringValue</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the String value of this choice or null, if the property info this choice is assigned to is not of data type String
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setStringValue(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_STRINGVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the Integer value of this choice or null, if the property info this choice is assigned to is not of data type Integer.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INTEGERVALUE).getInteger()</code>.
     * 
     * <p>Property opendma:<b>IntegerValue</b>: Integer<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Integer value of this choice or null, if the property info this choice is assigned to is not of data type Integer
     */
    public Integer getIntegerValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_INTEGERVALUE).getInteger();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the Integer value of this choice or null, if the property info this choice is assigned to is not of data type Integer.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INTEGERVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>IntegerValue</b>: Integer<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the Integer value of this choice or null, if the property info this choice is assigned to is not of data type Integer
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setIntegerValue(Integer newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_INTEGERVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the Short value of this choice or null, if the property info this choice is assigned to is not of data type Short.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SHORTVALUE).getShort()</code>.
     * 
     * <p>Property opendma:<b>ShortValue</b>: Short<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Short value of this choice or null, if the property info this choice is assigned to is not of data type Short
     */
    public Short getShortValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_SHORTVALUE).getShort();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the Short value of this choice or null, if the property info this choice is assigned to is not of data type Short.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SHORTVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>ShortValue</b>: Short<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the Short value of this choice or null, if the property info this choice is assigned to is not of data type Short
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setShortValue(Short newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_SHORTVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the Long value of this choice or null, if the property info this choice is assigned to is not of data type Long.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LONGVALUE).getLong()</code>.
     * 
     * <p>Property opendma:<b>LongValue</b>: Long<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Long value of this choice or null, if the property info this choice is assigned to is not of data type Long
     */
    public Long getLongValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_LONGVALUE).getLong();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the Long value of this choice or null, if the property info this choice is assigned to is not of data type Long.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LONGVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>LongValue</b>: Long<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the Long value of this choice or null, if the property info this choice is assigned to is not of data type Long
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setLongValue(Long newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_LONGVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the Float value of this choice or null, if the property info this choice is assigned to is not of data type Float.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FLOATVALUE).getFloat()</code>.
     * 
     * <p>Property opendma:<b>FloatValue</b>: Float<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Float value of this choice or null, if the property info this choice is assigned to is not of data type Float
     */
    public Float getFloatValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_FLOATVALUE).getFloat();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the Float value of this choice or null, if the property info this choice is assigned to is not of data type Float.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FLOATVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>FloatValue</b>: Float<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the Float value of this choice or null, if the property info this choice is assigned to is not of data type Float
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setFloatValue(Float newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_FLOATVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the Double value of this choice or null, if the property info this choice is assigned to is not of data type Double.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DOUBLEVALUE).getDouble()</code>.
     * 
     * <p>Property opendma:<b>DoubleValue</b>: Double<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Double value of this choice or null, if the property info this choice is assigned to is not of data type Double
     */
    public Double getDoubleValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_DOUBLEVALUE).getDouble();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the Double value of this choice or null, if the property info this choice is assigned to is not of data type Double.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DOUBLEVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>DoubleValue</b>: Double<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the Double value of this choice or null, if the property info this choice is assigned to is not of data type Double
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setDoubleValue(Double newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_DOUBLEVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the Boolean value of this choice or null, if the property info this choice is assigned to is not of data type Boolean.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BOOLEANVALUE).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>BooleanValue</b>: Boolean<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Boolean value of this choice or null, if the property info this choice is assigned to is not of data type Boolean
     */
    public Boolean isBooleanValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_BOOLEANVALUE).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the Boolean value of this choice or null, if the property info this choice is assigned to is not of data type Boolean.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BOOLEANVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>BooleanValue</b>: Boolean<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the Boolean value of this choice or null, if the property info this choice is assigned to is not of data type Boolean
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setBooleanValue(Boolean newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_BOOLEANVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the DateTime value of this choice or null, if the property info this choice is assigned to is not of data type DateTime.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATETIMEVALUE).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>DateTimeValue</b>: DateTime<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the DateTime value of this choice or null, if the property info this choice is assigned to is not of data type DateTime
     */
    public Date getDateTimeValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_DATETIMEVALUE).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the DateTime value of this choice or null, if the property info this choice is assigned to is not of data type DateTime.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATETIMEVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>DateTimeValue</b>: DateTime<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the DateTime value of this choice or null, if the property info this choice is assigned to is not of data type DateTime
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setDateTimeValue(Date newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_DATETIMEVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the Binary value of this choice or null, if the property info this choice is assigned to is not of data type Binary.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BINARYVALUE).getBinary()</code>.
     * 
     * <p>Property opendma:<b>BinaryValue</b>: Binary<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Binary value of this choice or null, if the property info this choice is assigned to is not of data type Binary
     */
    public byte[] getBinaryValue() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_BINARYVALUE).getBinary();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the Binary value of this choice or null, if the property info this choice is assigned to is not of data type Binary.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BINARYVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>BinaryValue</b>: Binary<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the Binary value of this choice or null, if the property info this choice is assigned to is not of data type Binary
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setBinaryValue(byte[] newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_BINARYVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the Reference value of this choice or null, if the property info this choice is assigned to is not of data type Reference.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCEVALUE).getReference()</code>.
     * 
     * <p>Property opendma:<b>ReferenceValue</b>: Reference to Object (opendma)<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Reference value of this choice or null, if the property info this choice is assigned to is not of data type Reference
     */
    public OdmaObject getReferenceValue() {
        try {
            return (OdmaObject)getProperty(OdmaCommonNames.PROPERTY_REFERENCEVALUE).getReference();
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
     * Sets the Reference value of this choice or null, if the property info this choice is assigned to is not of data type Reference.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCEVALUE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>ReferenceValue</b>: Reference to Object (opendma)<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the Reference value of this choice or null, if the property info this choice is assigned to is not of data type Reference
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setReferenceValue(OdmaObject newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_REFERENCEVALUE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
