package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>ChoiceValue</i> specific version of the <code>{@link OdmaObject}</code> interface
 * that offers short cuts to all defined OpenDMA properties.<p>
 * 
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaChoiceValue extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the <i>display name</i> of this Choice to be displayed to end users.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property <b>DisplayName</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>display name</i> of this Choice to be displayed to end users
     */
    public String getDisplayName();

    /**
     * Sets the <i>display name</i> of this Choice to be displayed to end users.<br>
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
     * Returns the String value of this choice or null, if the property info this choice is assigned to is not of data type String.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_STRINGVALUE).getString()</code>.
     * 
     * <p>Property <b>StringValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the String value of this choice or null, if the property info this choice is assigned to is not of data type String
     */
    public String getStringValue();

    /**
     * Sets the String value of this choice or null, if the property info this choice is assigned to is not of data type String.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_STRINGVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>StringValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setStringValue(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the Integer value of this choice or null, if the property info this choice is assigned to is not of data type Integer.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INTEGERVALUE).getString()</code>.
     * 
     * <p>Property <b>IntegerValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the Integer value of this choice or null, if the property info this choice is assigned to is not of data type Integer
     */
    public String getIntegerValue();

    /**
     * Sets the Integer value of this choice or null, if the property info this choice is assigned to is not of data type Integer.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INTEGERVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>IntegerValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setIntegerValue(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the Short value of this choice or null, if the property info this choice is assigned to is not of data type Short.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SHORTVALUE).getString()</code>.
     * 
     * <p>Property <b>ShortValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the Short value of this choice or null, if the property info this choice is assigned to is not of data type Short
     */
    public String getShortValue();

    /**
     * Sets the Short value of this choice or null, if the property info this choice is assigned to is not of data type Short.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SHORTVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>ShortValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setShortValue(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the Long value of this choice or null, if the property info this choice is assigned to is not of data type Long.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LONGVALUE).getString()</code>.
     * 
     * <p>Property <b>LongValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the Long value of this choice or null, if the property info this choice is assigned to is not of data type Long
     */
    public String getLongValue();

    /**
     * Sets the Long value of this choice or null, if the property info this choice is assigned to is not of data type Long.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LONGVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>LongValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setLongValue(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the Float value of this choice or null, if the property info this choice is assigned to is not of data type Float.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FLOATVALUE).getString()</code>.
     * 
     * <p>Property <b>FloatValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the Float value of this choice or null, if the property info this choice is assigned to is not of data type Float
     */
    public String getFloatValue();

    /**
     * Sets the Float value of this choice or null, if the property info this choice is assigned to is not of data type Float.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FLOATVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>FloatValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setFloatValue(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the Double value of this choice or null, if the property info this choice is assigned to is not of data type Double.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DOUBLEVALUE).getString()</code>.
     * 
     * <p>Property <b>DoubleValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the Double value of this choice or null, if the property info this choice is assigned to is not of data type Double
     */
    public String getDoubleValue();

    /**
     * Sets the Double value of this choice or null, if the property info this choice is assigned to is not of data type Double.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DOUBLEVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>DoubleValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setDoubleValue(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the Boolean value of this choice or null, if the property info this choice is assigned to is not of data type Boolean.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BOOLEANVALUE).getString()</code>.
     * 
     * <p>Property <b>BooleanValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the Boolean value of this choice or null, if the property info this choice is assigned to is not of data type Boolean
     */
    public String getBooleanValue();

    /**
     * Sets the Boolean value of this choice or null, if the property info this choice is assigned to is not of data type Boolean.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BOOLEANVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>BooleanValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setBooleanValue(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the DateTime value of this choice or null, if the property info this choice is assigned to is not of data type DateTime.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATETIMEVALUE).getString()</code>.
     * 
     * <p>Property <b>DateTimeValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the DateTime value of this choice or null, if the property info this choice is assigned to is not of data type DateTime
     */
    public String getDateTimeValue();

    /**
     * Sets the DateTime value of this choice or null, if the property info this choice is assigned to is not of data type DateTime.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATETIMEVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>DateTimeValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setDateTimeValue(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the BLOB value of this choice or null, if the property info this choice is assigned to is not of data type BLOB.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BLOBVALUE).getString()</code>.
     * 
     * <p>Property <b>BLOBValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the BLOB value of this choice or null, if the property info this choice is assigned to is not of data type BLOB
     */
    public String getBLOBValue();

    /**
     * Sets the BLOB value of this choice or null, if the property info this choice is assigned to is not of data type BLOB.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_BLOBVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>BLOBValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setBLOBValue(String value) throws OdmaAccessDeniedException;

    /**
     * Returns the Reference value of this choice or null, if the property info this choice is assigned to is not of data type Reference.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCEVALUE).getString()</code>.
     * 
     * <p>Property <b>ReferenceValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the Reference value of this choice or null, if the property info this choice is assigned to is not of data type Reference
     */
    public String getReferenceValue();

    /**
     * Sets the Reference value of this choice or null, if the property info this choice is assigned to is not of data type Reference.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REFERENCEVALUE).setValue(value)</code>.
     * 
     * <p>Property <b>ReferenceValue</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setReferenceValue(String value) throws OdmaAccessDeniedException;

}
