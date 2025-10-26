package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;
import java.util.Date;

/**
 * The <i>ChoiceValue</i> specific version of the <code>{@link OdmaObject}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * Describes a possible value of a property
 */
public interface OdmaChoiceValue extends OdmaObject {

    /**
     * Returns Text shown to end users to refer to this possible value option.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * This DisplayName indirections allows Administrators to define friendly descriptions for end users while storing internal numbers or abbreviation in the system</p>
     * 
     * @return Text shown to end users to refer to this possible value option
     */
    String getDisplayName();

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
    void setDisplayName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the String value of this choice or null, if the property info this choice is assigned to is not of data type String.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_STRINGVALUE).getString()</code>.
     * 
     * <p>Property opendma:<b>StringValue</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the String value of this choice or null, if the property info this choice is assigned to is not of data type String
     */
    String getStringValue();

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
    void setStringValue(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the Integer value of this choice or null, if the property info this choice is assigned to is not of data type Integer.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INTEGERVALUE).getInteger()</code>.
     * 
     * <p>Property opendma:<b>IntegerValue</b>: Integer<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Integer value of this choice or null, if the property info this choice is assigned to is not of data type Integer
     */
    Integer getIntegerValue();

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
    void setIntegerValue(Integer newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the Short value of this choice or null, if the property info this choice is assigned to is not of data type Short.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SHORTVALUE).getShort()</code>.
     * 
     * <p>Property opendma:<b>ShortValue</b>: Short<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Short value of this choice or null, if the property info this choice is assigned to is not of data type Short
     */
    Short getShortValue();

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
    void setShortValue(Short newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the Long value of this choice or null, if the property info this choice is assigned to is not of data type Long.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LONGVALUE).getLong()</code>.
     * 
     * <p>Property opendma:<b>LongValue</b>: Long<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Long value of this choice or null, if the property info this choice is assigned to is not of data type Long
     */
    Long getLongValue();

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
    void setLongValue(Long newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the Float value of this choice or null, if the property info this choice is assigned to is not of data type Float.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FLOATVALUE).getFloat()</code>.
     * 
     * <p>Property opendma:<b>FloatValue</b>: Float<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Float value of this choice or null, if the property info this choice is assigned to is not of data type Float
     */
    Float getFloatValue();

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
    void setFloatValue(Float newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the Double value of this choice or null, if the property info this choice is assigned to is not of data type Double.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DOUBLEVALUE).getDouble()</code>.
     * 
     * <p>Property opendma:<b>DoubleValue</b>: Double<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Double value of this choice or null, if the property info this choice is assigned to is not of data type Double
     */
    Double getDoubleValue();

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
    void setDoubleValue(Double newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the Boolean value of this choice or null, if the property info this choice is assigned to is not of data type Boolean.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BOOLEANVALUE).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>BooleanValue</b>: Boolean<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Boolean value of this choice or null, if the property info this choice is assigned to is not of data type Boolean
     */
    Boolean isBooleanValue();

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
    void setBooleanValue(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the DateTime value of this choice or null, if the property info this choice is assigned to is not of data type DateTime.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATETIMEVALUE).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>DateTimeValue</b>: DateTime<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the DateTime value of this choice or null, if the property info this choice is assigned to is not of data type DateTime
     */
    Date getDateTimeValue();

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
    void setDateTimeValue(Date newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the Binary value of this choice or null, if the property info this choice is assigned to is not of data type Binary.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BINARYVALUE).getBinary()</code>.
     * 
     * <p>Property opendma:<b>BinaryValue</b>: Binary<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Binary value of this choice or null, if the property info this choice is assigned to is not of data type Binary
     */
    byte[] getBinaryValue();

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
    void setBinaryValue(byte[] newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the Reference value of this choice or null, if the property info this choice is assigned to is not of data type Reference.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCEVALUE).getReference()</code>.
     * 
     * <p>Property opendma:<b>ReferenceValue</b>: Reference to Object (opendma)<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * Full description follows.</p>
     * 
     * @return the Reference value of this choice or null, if the property info this choice is assigned to is not of data type Reference
     */
    OdmaObject getReferenceValue();

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
    void setReferenceValue(OdmaObject newValue) throws OdmaAccessDeniedException;

}
