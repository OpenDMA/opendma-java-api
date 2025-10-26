package org.opendma.api;

import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import java.util.List;
import java.util.Date;

/**
 * A <i>Property</i> of an <code>OdmaObject</code> in the OpenDMA
 * architecture. It is always bound in name, data type and cardinality and can
 * not change these. If this property is not read only (i.e.
 * <code>{@link #isReadOnly()}</code> returns <code>false</code>), it can
 * change its value by calling the <code>{@link #setValue(Object)}</code>
 * method. Changes are only persisted in the repository after a call to the
 * <code>{@link #save()}</code> method of the containing object.
 */
public interface OdmaProperty {

    /**
     * Returns the qualified name of this property.
     * 
     * @return the qualified name of this property.
     */
    public OdmaQName getName();

    /**
     * Returns the data type of this property.<br>
     * You can find a list of all data types in the <code>OdmaTypes</code>
     * class.
     * 
     * @return the data type of this property.
     * 
     * @see org.opendma.OdmaTypes
     */
    public OdmaType getType();

    /**
     * Returns the value of this property.<br>
     * The concrete <code>Object</code> returned by this method depends on the
     * data type of this property.
     * 
     * @return the value of this property.
     */
    public Object getValue();

    /**
     * Set the value of this property to the given new value. The
     * <code>Class</code> of the given <code>Object</code> has to match the
     * data type of this OdmaProperty.
     * 
     * @param newValue
     *            the new value to set this property to.
     * 
     * @throws OdmaInvalidDataTypeException
     *             if the Class of the newValue does not match
     *             the data type of this OdmaProperty
     * 
     * @throws OdmaAccessDeniedException
     *             if this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setValue(Object newValue) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException;

    /**
     * Checks if this property is a multi-value property.
     * 
     * @return <code>true</code> if and only if this property is a multi value property.
     */
    public boolean isMultiValue();

    /**
     * Checks if the property has unsaved changes
     * 
     * @return <code>true</code> if and only if this property has unsaved changes
     */
    public boolean isDirty();

    /**
     * Checks if this property is read-only.
     * 
     * @return <code>true</code> if and only if this property is read-only.
     */
    public boolean isReadOnly();

    /**
     * The availability state of a property value.
     */
    public enum PropertyResolutionState {
        
        /** Reading this value requires a round-trip to a back-end system */
        UNRESOLVED,

	    /** The OdmaId of the referenced object is immediately available, but reading the object value requires a round-trip to a back-end system */
        IDRESOLVED,
        
        /** The value is immediately available */
        RESOLVED
    }

    /**
     * Indicates if the value of this property is immediately available can be read without a round-trip to a back-end system.
     * 
     * @return the availability state of this property value.
     */
     public PropertyResolutionState getResolutionState();

    /**
     * Returns the <code>String</code> value of this property if and only if
     * the data type of this property is a single valued <i>String</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>String</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>String</i>
     *             property
     */
    public String getString() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Integer</code> value of this property if and only if
     * the data type of this property is a single valued <i>Integer</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Integer</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Integer</i>
     *             property
     */
    public Integer getInteger() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Short</code> value of this property if and only if
     * the data type of this property is a single valued <i>Short</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Short</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Short</i>
     *             property
     */
    public Short getShort() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Long</code> value of this property if and only if
     * the data type of this property is a single valued <i>Long</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Long</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Long</i>
     *             property
     */
    public Long getLong() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Float</code> value of this property if and only if
     * the data type of this property is a single valued <i>Float</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Float</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Float</i>
     *             property
     */
    public Float getFloat() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Double</code> value of this property if and only if
     * the data type of this property is a single valued <i>Double</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Double</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Double</i>
     *             property
     */
    public Double getDouble() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Boolean</code> value of this property if and only if
     * the data type of this property is a single valued <i>Boolean</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Boolean</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Boolean</i>
     *             property
     */
    public Boolean getBoolean() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>DateTime</code> value of this property if and only if
     * the data type of this property is a single valued <i>DateTime</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>DateTime</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>DateTime</i>
     *             property
     */
    public Date getDateTime() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Binary</code> value of this property if and only if
     * the data type of this property is a single valued <i>Binary</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Binary</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Binary</i>
     *             property
     */
    public byte[] getBinary() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Reference</code> value of this property if and only if
     * the data type of this property is a single valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Reference</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Reference</i>
     *             property
     */
    public OdmaObject getReference() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>OdmaId</code> of the <code>Reference</code> if and only if
     * the data type of this property is a single valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * Based on the PropertyResolutionState, it is possible that this OdmaId is immediately available
     * while the OdmaObject requires an additional round-trip to the server.
     * 
     * @return the <code>OdmaId</code> of the <code>Reference</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Reference</i>
     *             property
     */
    public OdmaId getReferenceId() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Content</code> value of this property if and only if
     * the data type of this property is a single valued <i>Content</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Content</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Content</i>
     *             property
     */
    public OdmaContent getContent() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Id</code> value of this property if and only if
     * the data type of this property is a single valued <i>Id</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Id</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Id</i>
     *             property
     */
    public OdmaId getId() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Guid</code> value of this property if and only if
     * the data type of this property is a single valued <i>Guid</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Guid</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Guid</i>
     *             property
     */
    public OdmaGuid getGuid() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>String</code> value of this property if and only if
     * the data type of this property is a multi valued <i>String</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>String</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>String</i>
     *             property
     */
    public List<String> getStringList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Integer</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Integer</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Integer</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Integer</i>
     *             property
     */
    public List<Integer> getIntegerList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Short</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Short</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Short</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Short</i>
     *             property
     */
    public List<Short> getShortList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Long</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Long</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Long</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Long</i>
     *             property
     */
    public List<Long> getLongList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Float</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Float</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Float</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Float</i>
     *             property
     */
    public List<Float> getFloatList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Double</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Double</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Double</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Double</i>
     *             property
     */
    public List<Double> getDoubleList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Boolean</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Boolean</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Boolean</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Boolean</i>
     *             property
     */
    public List<Boolean> getBooleanList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>DateTime</code> value of this property if and only if
     * the data type of this property is a multi valued <i>DateTime</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>DateTime</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>DateTime</i>
     *             property
     */
    public List<Date> getDateTimeList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Binary</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Binary</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Binary</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Binary</i>
     *             property
     */
    public List<byte[]> getBinaryList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Reference</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Reference</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Reference</i>
     *             property
     */
    public Iterable<? extends OdmaObject> getReferenceIterable() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Content</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Content</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Content</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Content</i>
     *             property
     */
    public List<OdmaContent> getContentList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Id</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Id</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Id</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Id</i>
     *             property
     */
    public List<OdmaId> getIdList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Guid</code> value of this property if and only if
     * the data type of this property is a multi valued <i>Guid</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Guid</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Guid</i>
     *             property
     */
    public List<OdmaGuid> getGuidList() throws OdmaInvalidDataTypeException;

}
