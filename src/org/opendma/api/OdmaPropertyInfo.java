package org.opendma.api;

/**
 * The <i>PropertyInfo</i> specific version of the <code>{@link OdmaObject}</code> interface that offers short cuts
 * to all defined OpenDMA properties.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaPropertyInfo extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the name of this <i>property info</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * @return the name describing this <i>property info</i>
     */
    public String getName();

    /**
     * Returns the qualifier of the name of this <i>property info</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMEQUALIFIER).getString()</code>.
     * 
     * @return the qualifier of the the name describing this <i>property info</i>
     */
    public String getNameQualifier();

    /**
     * Returns the qualified name of this <i>property info</i>.<br>
     * Shortcut for <code>new OdmaQName(getNameQualifier(),getName())</code>.
     * 
     * @return the qualified name describing this <i>property info</i>
     */
    public OdmaQName getQName();

    /**
     * Returns the name of this <i>property info</i> that should be displayed to the user.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * @return the name of this <i>property info</i> that should be displayed to the user
     */
    public String getDisplayName();

    /**
     * Returns the numeric ID of the data type described by this <i>property info</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DATATYPE).getInteger()</code>.
     * 
     * @return the numeric ID of the data type described by this <i>property info</i>
     */
    public int getDataType();

    /**
     * Returns the required <i>class</i> of the object referenced by this property if this <i>property info</i>
     * describes a <i>Reference</i> property.<br>
     * Shortcut for <code>(OdmaClass)getProperty(OdmaTypes.PROPERTY_REFERENCECLASS).getObject()</code>.
     * 
     * @return the required <i>class</i> of the object referenced by this property
     */
    public OdmaClass getReferenceClass();

    /**
     * Returns true if and only if this <i>property info</i> describes a multi valued property.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ISMULTIVALUE).getBoolean()</code>.
     * 
     * @return true if and only if this <i>property info</i> describes a multi valued property
     */
    public boolean isMultiValue();

    /**
     * Returns true if and only if the property described by this <i>property info</i> must not conatin null.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ISREQUIRED).getBoolean()</code>.
     * 
     * @return true if and only if the property described by this <i>property info</i> must not conatin null
     */
    public boolean isRequired();

    /**
     * Returns true if the current user is not allowed to modify the property described by this <i>property info</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ISREADONLY).getBoolean()</code>.
     * 
     * @return true if the current user is not allowed to modify the property described by this <i>property info</i>
     */
    public boolean isReadOnly();

    /**
     * Returns true if the property described by this <i>property info</i> should not be displayed to the default user.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ISHIDDEN).getBoolean()</code>.
     * 
     * @return true if the property described by this <i>property info</i> should not be displayed to the default user
     */
    public boolean isHidden();

    /**
     * Returns true if the property described by this <i>property info</i> represents internal technical objects that
     * are of no interest to default users.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ISSYSTEM).getBoolean()</code>.
     * 
     * @return true if the property described by this <i>property info</i> represents internal technical objects
     */
    public boolean isSystem();

}