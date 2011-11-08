package org.opendma.api;

import org.opendma.api.OdmaQName;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.collections.OdmaClassEnumeration;
import org.opendma.api.collections.OdmaPropertyInfoEnumeration;

/**
 * The <i>Class</i> specific version of the <code>{@link OdmaObject}</code> interface
 * that offers short cuts to all defined OpenDMA properties.<p>
 * 
 * The <i>Class</i> specific version of the <code>{@link OdmaObject}</code> interface that offers short cuts to all defined OpenDMA properties.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaClass extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the internal (technical) <i>name</i> of this <code>Class</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the internal (technical) <i>name</i> of this <code>Class</code>
     */
    public String getName();

    /**
     * Sets the internal (technical) <i>name</i> of this <code>Class</code>.<br>
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
     * Returns the name <i>qualifier</i> of this <code>Class</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMEQUALIFIER).getString()</code>.
     * 
     * <p>Property <b>NameQualifier</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the name <i>qualifier</i> of this <code>Class</code>
     */
    public String getNameQualifier();

    /**
     * Sets the name <i>qualifier</i> of this <code>Class</code>.<br>
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
     * Returns the <i>display name</i> of this <code>Class</code> to be displayed to end users.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property <b>DisplayName</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>display name</i> of this <code>Class</code> to be displayed to end users
     */
    public String getDisplayName();

    /**
     * Sets the <i>display name</i> of this <code>Class</code> to be displayed to end users.<br>
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
     * Returns the <i>parent</i> <code>Class</code> that is extended by this <code>Class</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PARENT).getReference()</code>.
     * 
     * <p>Property <b>Parent</b> (opendma): <b>Reference to Class (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the <i>parent</i> <code>Class</code> that is extended by this <code>Class</code>
     */
    public OdmaClass getParent();

    /**
     * Returns the list of <i>aspects</i> that are implemented by this <code>Class</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASPECTS).getReferenceEnumeration()</code>.
     * 
     * <p>Property <b>Aspects</b> (opendma): <b>Reference to Class (opendma)</b><br>
     * [MultiValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the list of <i>aspects</i> that are implemented by this <code>Class</code>
     */
    public OdmaClassEnumeration getAspects();

    /**
     * Returns the list of <i>properties</i> that are desclared by this <code>Class</code> (does not contain inherited properties)..<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DECLAREDPROPERTIES).getReferenceEnumeration()</code>.
     * 
     * <p>Property <b>DeclaredProperties</b> (opendma): <b>Reference to PropertyInfo (opendma)</b><br>
     * [MultiValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the list of <i>properties</i> that are desclared by this <code>Class</code> (does not contain inherited properties).
     */
    public OdmaPropertyInfoEnumeration getDeclaredProperties();

    /**
     * Returns the list of <i>properties</i> that are effective for objects of this <code>Class</code>. Contains inherited and declared properties..<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PROPERTIES).getReferenceEnumeration()</code>.
     * 
     * <p>Property <b>Properties</b> (opendma): <b>Reference to PropertyInfo (opendma)</b><br>
     * [MultiValue] [ReadOnly] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the list of <i>properties</i> that are effective for objects of this <code>Class</code>. Contains inherited and declared properties.
     */
    public OdmaPropertyInfoEnumeration getProperties();

    /**
     * Returns wheather this <code>Class</code> describes an Aspect or a valid class object.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASPECT).getBoolean()</code>.
     * 
     * <p>Property <b>Aspect</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return wheather this <code>Class</code> describes an Aspect or a valid class object
     */
    public Boolean getAspect();

    /**
     * Returns wheather <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INSTANTIABLE).getBoolean()</code>.
     * 
     * <p>Property <b>Instantiable</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return wheather <code>Object</code>s of this <code>Class</code> can be created or not
     */
    public Boolean getInstantiable();

    /**
     * Sets wheather <code>Object</code>s of this <code>Class</code> can be created or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INSTANTIABLE).setValue(value)</code>.
     * 
     * <p>Property <b>Instantiable</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setInstantiable(Boolean value) throws OdmaAccessDeniedException;

    /**
     * Returns wheather this <code>Class</code> should be displayed to end users or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).getBoolean()</code>.
     * 
     * <p>Property <b>Hidden</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return wheather this <code>Class</code> should be displayed to end users or not
     */
    public Boolean getHidden();

    /**
     * Sets wheather this <code>Class</code> should be displayed to end users or not.<br>
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
     * Returns wheather this <code>Class</code> is defined by the system (true) or by users (false).<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).getBoolean()</code>.
     * 
     * <p>Property <b>System</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return wheather this <code>Class</code> is defined by the system (true) or by users (false)
     */
    public Boolean getSystem();

    /**
     * Sets wheather this <code>Class</code> is defined by the system (true) or by users (false).<br>
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
     * Returns wheather objects of this class can be retrieved from a session by their id or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_RETRIEVABLE).getBoolean()</code>.
     * 
     * <p>Property <b>Retrievable</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return wheather objects of this class can be retrieved from a session by their id or not
     */
    public Boolean getRetrievable();

    /**
     * Returns wheather objects of this class can be found by a search query or not.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SEARCHABLE).getBoolean()</code>.
     * 
     * <p>Property <b>Searchable</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return wheather objects of this class can be found by a search query or not
     */
    public Boolean getSearchable();

    /**
     * Returns the list of <code>Class</code>es that extend this class (i.e. that contain a reference to this <code>Class</code> in their <i>parent</i> property).<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUBCLASSES).getReferenceEnumeration()</code>.
     * 
     * <p>Property <b>SubClasses</b> (opendma): <b>Reference to Class (opendma)</b><br>
     * [MultiValue] [ReadOnly] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the list of <code>Class</code>es that extend this class (i.e. that contain a reference to this <code>Class</code> in their <i>parent</i> property)
     */
    public OdmaClassEnumeration getSubClasses();

    /**
     * the qualified name of this <code>Class</code><br>
     * <p>Full description follows.</p>
     * 
     * @return the qualified name of this <code>Class</code>
     */
    public OdmaQName getQName();

}
