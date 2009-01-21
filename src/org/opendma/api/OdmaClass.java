package org.opendma.api;

import org.opendma.api.collections.OdmaClassEnumeration;
import org.opendma.api.collections.OdmaPropertyInfoEnumeration;

/**
 * The <i>Class</i> specific version of the <code>{@link OdmaObject}</code>
 * interface that offers short cuts to all defined OpenDMA properties.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaClass extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the name of this <i>class</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * @return the name describing this <i>class</i>
     */
    public String getName();

    /**
     * Returns the qualifier of the name of this <i>class</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMEQUALIFIER).getString()</code>.
     * 
     * @return the qualifier of the the name describing this <i>class</i>
     */
    public String getNameQualifier();

    /**
     * Returns the qualified name of this <i>class</i>.<br>
     * Shortcut for <code>new OdmaQName(getNameQualifier(),getName())</code>.
     * 
     * @return the qualified name describing this <i>class</i>
     */
    public OdmaQName getQName();

    /**
     * Returns the name of this <i>class</i> that should be displayed to the user.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * @return the name of this <i>class</i> that should be displayed to the user
     */
    public String getDisplayName();

    /**
     * Returns the parent of this <i>class</i>.<br>
     * Shortcut for <code>(OdmaClass)getProperty(OdmaTypes.PROPERTY_PARENT).getObject()</code>.
     * 
     * @return the parent of this <i>class</i>
     */
    public OdmaClass getParent();

    /**
     * Returns all aspects of this <i>class</i>.<br>
     * Shortcut for <code>(OdmaClassEnumeration)getProperty(OdmaTypes.PROPERTY_ASPECTS).getObjectList()</code>.
     * 
     * @return all aspects of this <i>class</i>
     */
    public OdmaClassEnumeration getAspects();

    /**
     * Returns all properties declared in this <i>class</i>.<br>
     * The list off all effective properties also includes inherited properties and is
     * availible by the method <code>{@link #getProperties()}</code>.
     * Shortcut for <code>(OdmaPropertyInfoEnumeration) getProperty(OdmaTypes.PROPERTY_DECLAREDPROPERTIES) .getObjectList()</code>.
     * 
     * @return all properties declared in this <i>class</i>
     */
    public OdmaPropertyInfoEnumeration getDeclaredProperties();

    /**
     * Returns true if it is possible to create instances of this <i>class</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ISINSTANTIABLE).getBoolean()</code>.
     * 
     * @return true if it is possible to create instances of this <i>class</i>
     */
    public boolean isInstantiable();

    /**
     * Returns true if this <i>class</i> should not be displayed to the default user.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ISHIDDEN).getBoolean()</code>.
     * 
     * @return true if this <i>class</i> should not be displayed to the default user
     */
    public boolean isHidden();

    /**
     * Returns true if this <i>class</i> represents internal technical objects that are
     * of no interest to default users.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ISSYSTEM).getBoolean()</code>.
     * 
     * @return true if this <i>class</i> represents internal technical objects
     */
    public boolean isSystem();

    /**
     * Returns all sub classes of this <i>class</i>, i.e. all <i>class</i> objects that have
     * a reference to this class in their parent property.<br>
     * Shortcut for <code>(OdmaClassEnumeration)getProperty(OdmaTypes.PROPERTY_SUBCLASSES).getObjectList()</code>.
     * 
     * @return all sub classes of this <i>class</i>
     */
    public OdmaClassEnumeration getSubClasses();

    // =============================================================================================
    // Calculated data
    // =============================================================================================

    /**
     * Returns all properties of this <i>class</i>, inherited and declared once.<br>
     * 
     * @return all properties of this <i>class</i>
     */
    public OdmaPropertyInfoEnumeration getProperties();

}