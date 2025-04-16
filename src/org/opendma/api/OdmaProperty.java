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
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaProperty
{

    /**
     * Returns the qualified name of this property.
     * 
     * @return the qualified name of this property.
     */
    public OdmaQName getName();

    /**
     * Returns the value of this property.<br>
     * The concrete <code>Object</code> returned by this method depends on the
     * data type of this property.
     * 
     * @return the value of this property.
     */
    public Object getValue();

    /**
     * Returns the numeric identifier of the data type of this property.<br>
     * You can find a list of all data types in the <code>OdmaTypes</code>
     * class.
     * 
     * @return the numeric identifier of the data type of this property.
     * 
     * @see org.opendma.OdmaTypes
     */
    public OdmaType getType();

    /**
     * Set the value of this property to the given new value. The
     * <code>Class</code> of the given <code>Object</code> has to match the
     * data type of this property.
     * 
     * @param newValue
     *            the new value to set this property to.
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if the Class of the given Object does not match
     *             the data type of this property
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setValue(Object newValue) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException;

    /**
     * Returns <code>true</code> if and only if this property is a multi value property.
     * 
     * @return <code>true</code> if and only if this property is a multi value property.
     */
    public boolean isMultiValue();

    /**
     * Returns <code>true</code> if and only if this property has been changed and these
     * changes have not yet been saved.
     * 
     * @return <code>true</code> true if and only if this property has been changed and these
     *         changes have not yet been saved
     */
    public boolean isDirty();

    /**
     * Returns <code>true</code> if and only if this property must not be changed.
     * 
     * @return <code>true</code> if and only if this property must not be changed.
     */
    public boolean isReadOnly();

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
     * Returns the <code>Date</code> value of this property if and only if
     * the data type of this property is a single valued <i>DateTime</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Date</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>DateTime</i>
     *             property
     */
    public Date getDateTime() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>byte[]</code> value of this property if and only if
     * the data type of this property is a single valued <i>Blob</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>byte[]</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Blob</i>
     *             property
     */
    public byte[] getBlob() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>OdmaObject</code> value of this property if and only if
     * the data type of this property is a single valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaObject</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Reference</i>
     *             property
     */
    public OdmaObject getReference() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>OdmaContent</code> value of this property if and only if
     * the data type of this property is a single valued <i>Content</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaContent</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Content</i>
     *             property
     */
    public OdmaContent getContent() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>OdmaId</code> value of this property if and only if
     * the data type of this property is a single valued <i>Id</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaId</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Id</i>
     *             property
     */
    public OdmaId getId() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>OdmaGuid</code> value of this property if and only if
     * the data type of this property is a single valued <i>Guid</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>OdmaGuid</code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a single valued <i>Guid</i>
     *             property
     */
    public OdmaGuid getGuid() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<String></code> value of this property if and only if
     * the data type of this property is a multi valued <i>String</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<String></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>String</i>
     *             property
     */
    public List<String> getStringList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<Integer></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Integer</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Integer></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Integer</i>
     *             property
     */
    public List<Integer> getIntegerList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<Short></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Short</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Short></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Short</i>
     *             property
     */
    public List<Short> getShortList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<Long></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Long</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Long></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Long</i>
     *             property
     */
    public List<Long> getLongList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<Float></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Float</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Float></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Float</i>
     *             property
     */
    public List<Float> getFloatList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<Double></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Double</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Double></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Double</i>
     *             property
     */
    public List<Double> getDoubleList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<Boolean></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Boolean</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Boolean></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Boolean</i>
     *             property
     */
    public List<Boolean> getBooleanList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<Date></code> value of this property if and only if
     * the data type of this property is a multi valued <i>DateTime</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<Date></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>DateTime</i>
     *             property
     */
    public List<Date> getDateTimeList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<byte[]></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Blob</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<byte[]></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Blob</i>
     *             property
     */
    public List<byte[]> getBlobList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>Iterable<? extends OdmaObject></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Reference</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>Iterable<? extends OdmaObject></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Reference</i>
     *             property
     */
    public Iterable<? extends OdmaObject> getReferenceIterable() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<OdmaContent></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Content</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<OdmaContent></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Content</i>
     *             property
     */
    public List<OdmaContent> getContentList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<OdmaId></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Id</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<OdmaId></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Id</i>
     *             property
     */
    public List<OdmaId> getIdList() throws OdmaInvalidDataTypeException;

    /**
     * Returns the <code>List<OdmaGuid></code> value of this property if and only if
     * the data type of this property is a multi valued <i>Guid</i>. Throws
     * an <code>OdmaInvalidDataTypeException</code> otherwise.
     * 
     * @return the <code>List<OdmaGuid></code> value of this property
     * 
     * @throws OdmaInvalidDataTypeException
     *             if and only if this property is not a multi valued <i>Guid</i>
     *             property
     */
    public List<OdmaGuid> getGuidList() throws OdmaInvalidDataTypeException;

}
