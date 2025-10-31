package org.opendma.api;

import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * The <i>Class</i> specific version of the <code>{@link OdmaObject}</code> interface
 * offering short-cuts to all defined OpenDMA properties.
 * 
 * Describes Classes and Aspects in OpenDMA. Every object in OpenDMA has a reference to an instance of this class describing it.
 */
public interface OdmaClass extends OdmaObject {

    /**
     * Returns the name part of the qualified name of the class described by this object.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return the name part of the qualified name of the class described by this object
     */
    String getName();

    /**
     * Sets the name part of the qualified name of the class described by this object.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @param newValue
     *             The new value for the name part of the qualified name of the class described by this object
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns the namespace part of the qualified name of the class described by this object.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMESPACE).getString()</code>.
     * 
     * <p>Property opendma:<b>Namespace</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return the namespace part of the qualified name of the class described by this object
     */
    String getNamespace();

    /**
     * Sets the namespace part of the qualified name of the class described by this object.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMESPACE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Namespace</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @param newValue
     *             The new value for the namespace part of the qualified name of the class described by this object
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setNamespace(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Text shown to end users to refer to this class.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return Text shown to end users to refer to this class
     */
    String getDisplayName();

    /**
     * Sets Text shown to end users to refer to this class.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @param newValue
     *             The new value for Text shown to end users to refer to this class
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setDisplayName(String newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Super class of this class or aspect..<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUPERCLASS).getReference()</code>.
     * 
     * <p>Property opendma:<b>SuperClass</b>: Reference to Class (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * OpenDMA guarantees this relationship to be loop-free. You can use it to explore the class hierarchy starting from the class described by this object. All opendma:PropertyInfo objects contained in the opendma:Properties set of the super class are also part of the opendma:Properties set of this class.</p>
     * 
     * @return Super class of this class or aspect.
     */
    OdmaClass getSuperClass();

    /**
     * Returns List of aspects that are included in this class.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INCLUDEDASPECTS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>IncludedAspects</b>: Reference to Class (opendma)<br/>
     * [MultiValue] [Writable] [Optional]<br/>
     * If this object describes an Aspect, i.e. the opendma:Aspect property is true, it cannot have any Aspects itself. For classes, this set contains all elements of the opendma:Aspects set of the super class. All opendma:PropertyInfo objects contained in the opendma:Properties set of any of the opendma:Class objects in this set are also part of the opendma:Properties set of this class.</p>
     * 
     * @return List of aspects that are included in this class
     */
    Iterable<OdmaClass> getIncludedAspects();

    /**
     * Returns List of properties declared by this class.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DECLAREDPROPERTIES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>DeclaredProperties</b>: Reference to PropertyInfo (opendma)<br/>
     * [MultiValue] [Writable] [Optional]<br/>
     * Set of opendma:PropertyInfo objects describing properties newly introduced by this level in the class hierarchy. All elements of this set are also contained in the opendma:Properties set. Properties cannot be overwritten, i.e. the qualified nyme of any property described by an opendma:PropertyInfo object in this set cannot be used in the opendma:Properties sets of the super class or any Aspect.</p>
     * 
     * @return List of properties declared by this class
     */
    Iterable<OdmaPropertyInfo> getDeclaredProperties();

    /**
     * Returns List of effective properties.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PROPERTIES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Properties</b>: Reference to PropertyInfo (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]<br/>
     * Set of opendma:PropertyInfo objects describing all properties of an object of this class. This set combines the opendma:DeclaredProperties set with the opendma:Properties of the super class as well as the opendma:Properties sets of all aspect objects listed in opendma:Aspects. Properties cannot be overwritten, i.e. the qualified nyme of any property described by an opendma:PropertyInfo object in the opendma:DeclaredProperties set cannot be used in the opendma:Properties sets of the super class or any Aspect.</p>
     * 
     * @return List of effective properties
     */
    Iterable<OdmaPropertyInfo> getProperties();

    /**
     * Returns Indicates if this object represents an Aspect (true) or a Class (false).<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASPECT).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Aspect</b>: Boolean<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return Indicates if this object represents an Aspect (true) or a Class (false)
     */
    Boolean isAspect();

    /**
     * Returns Indicates if this class should be hidden from end users and probably administrators.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Hidden</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if this class should be hidden from end users and probably administrators
     */
    Boolean isHidden();

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
    void setHidden(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Indicates if instances of this class are owned and managed by the system.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>System</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if instances of this class are owned and managed by the system
     */
    Boolean isSystem();

    /**
     * Sets Indicates if instances of this class are owned and managed by the system.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>System</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for Indicates if instances of this class are owned and managed by the system
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    void setSystem(Boolean newValue) throws OdmaAccessDeniedException;

    /**
     * Returns Indicates if instances of this class can by retrieved by their Id.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_RETRIEVABLE).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Retrievable</b>: Boolean<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return Indicates if instances of this class can by retrieved by their Id
     */
    Boolean isRetrievable();

    /**
     * Returns Indicates if instances of this class can be retrieved in a search.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SEARCHABLE).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Searchable</b>: Boolean<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return Indicates if instances of this class can be retrieved in a search
     */
    Boolean isSearchable();

    /**
     * Returns List of classes or aspects that extend this class.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUBCLASSES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>SubClasses</b>: Reference to Class (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]<br/>
     * The value of the `opendma:SubClasses` property is exactly the set of valid class objects whose `opendma:SuperClass` property contains a reference to this class info object</p>
     * 
     * @return List of classes or aspects that extend this class
     */
    Iterable<OdmaClass> getSubClasses();

    /**
     * Returns the qualified name of this class.<br/>
     * A convenience shortcut to getting the name and namespace separately
     * 
     * @return the qualified name of this class
     */
    public OdmaQName getQName();

}
