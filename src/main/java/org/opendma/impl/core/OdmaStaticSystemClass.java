package org.opendma.impl.core;

import java.util.ArrayList;
import java.util.Iterator;

import org.opendma.api.OdmaClass;
import org.opendma.api.OdmaCommonNames;
import org.opendma.api.OdmaObject;
import org.opendma.api.OdmaPropertyInfo;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaType;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.impl.OdmaPropertyImpl;

/**
 * Template implementation of the interface <code>{@link OdmaClass}</code>.<p>
 *
 * The <i>Class</i> specific version of the <code>{@link OdmaObject}</code> interface that offers short cuts to all defined OpenDMA properties.
 */
public abstract class OdmaStaticSystemClass extends OdmaStaticSystemObject implements OdmaClass {

    protected OdmaStaticSystemClass(Iterable<OdmaClass> subClasses) throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        properties.put(OdmaCommonNames.PROPERTY_SUBCLASSES,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_SUBCLASSES,subClasses,OdmaType.REFERENCE,true,true));
    }

    protected void buildProperties() throws OdmaInvalidDataTypeException, OdmaAccessDeniedException {
        ArrayList<OdmaPropertyInfo> props = new ArrayList<OdmaPropertyInfo>();
        if(getSuperClass() != null) {
            Iterable<OdmaPropertyInfo> superClassProps = getSuperClass().getProperties();
            Iterator<OdmaPropertyInfo> itSuperClassProps = superClassProps.iterator();
            while(itSuperClassProps.hasNext()) {
                props.add(itSuperClassProps.next());
            }
        }
        for(OdmaClass aspect : getIncludedAspects()) {
            Iterable<OdmaPropertyInfo> aspectProps = aspect.getProperties();
            Iterator<OdmaPropertyInfo> itAspectProps = aspectProps.iterator();
            while(itAspectProps.hasNext()) {
                props.add(itAspectProps.next());
            }
        }
        Iterator<OdmaPropertyInfo> itDeclaredProperties = getDeclaredProperties().iterator();
        while(itDeclaredProperties.hasNext()) {
            props.add(itDeclaredProperties.next());
        }
        properties.put(OdmaCommonNames.PROPERTY_PROPERTIES,OdmaPropertyImpl.fromValue(OdmaCommonNames.PROPERTY_PROPERTIES,props,OdmaType.REFERENCE,true,true));
    }

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaClassTemplate

    /**
     * Returns the name part of the qualified name of the class described by this object.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return the name part of the qualified name of the class described by this object
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
     * Returns the namespace part of the qualified name of the class described by this object.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAMESPACE).getString()</code>.
     * 
     * <p>Property opendma:<b>Namespace</b>: String<br/>
     * [SingleValue] [Writable] [Optional]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return the namespace part of the qualified name of the class described by this object
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
     * Returns Text shown to end users to refer to this class.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * <p>Property opendma:<b>DisplayName</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * The qualified name is a technical identifier that typically has some restrictions, e.g. for database table names. The DisplayName in contrast is tailored for end users.</p>
     * 
     * @return Text shown to end users to refer to this class
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
     * Returns Super class of this class or aspect..<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUPERCLASS).getReference()</code>.
     * 
     * <p>Property opendma:<b>SuperClass</b>: Reference to Class (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * OpenDMA guarantees this relationship to be loop-free. You can use it to explore the class hierarchy starting from the class described by this object. All opendma:PropertyInfo objects contained in the opendma:Properties set of the super class are also part of the opendma:Properties set of this class.</p>
     * 
     * @return Super class of this class or aspect.
     */
    public OdmaClass getSuperClass() {
        try {
            return (OdmaClass)getProperty(OdmaCommonNames.PROPERTY_SUPERCLASS).getReference();
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
     * Returns List of aspects that are included in this class.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INCLUDEDASPECTS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>IncludedAspects</b>: Reference to Class (opendma)<br/>
     * [MultiValue] [Writable] [Optional]<br/>
     * If this object describes an Aspect, i.e. the opendma:Aspect property is true, it cannot have any Aspects itself. For classes, this set contains all elements of the opendma:Aspects set of the super class. All opendma:PropertyInfo objects contained in the opendma:Properties set of any of the opendma:Class objects in this set are also part of the opendma:Properties set of this class.</p>
     * 
     * @return List of aspects that are included in this class
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaClass> getIncludedAspects() {
        try {
            return (Iterable<OdmaClass>)getProperty(OdmaCommonNames.PROPERTY_INCLUDEDASPECTS).getReferenceIterable();
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
     * Returns List of properties declared by this class.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DECLAREDPROPERTIES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>DeclaredProperties</b>: Reference to PropertyInfo (opendma)<br/>
     * [MultiValue] [Writable] [Optional]<br/>
     * Set of opendma:PropertyInfo objects describing properties newly introduced by this level in the class hierarchy. All elements of this set are also contained in the opendma:Properties set. Properties cannot be overwritten, i.e. the qualified nyme of any property described by an opendma:PropertyInfo object in this set cannot be used in the opendma:Properties sets of the super class or any Aspect.</p>
     * 
     * @return List of properties declared by this class
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaPropertyInfo> getDeclaredProperties() {
        try {
            return (Iterable<OdmaPropertyInfo>)getProperty(OdmaCommonNames.PROPERTY_DECLAREDPROPERTIES).getReferenceIterable();
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
     * Returns List of effective properties.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_PROPERTIES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Properties</b>: Reference to PropertyInfo (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]<br/>
     * Set of opendma:PropertyInfo objects describing all properties of an object of this class. This set combines the opendma:DeclaredProperties set with the opendma:Properties of the super class as well as the opendma:Properties sets of all aspect objects listed in opendma:Aspects. Properties cannot be overwritten, i.e. the qualified nyme of any property described by an opendma:PropertyInfo object in the opendma:DeclaredProperties set cannot be used in the opendma:Properties sets of the super class or any Aspect.</p>
     * 
     * @return List of effective properties
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaPropertyInfo> getProperties() {
        try {
            return (Iterable<OdmaPropertyInfo>)getProperty(OdmaCommonNames.PROPERTY_PROPERTIES).getReferenceIterable();
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
     * Returns Indicates if this object represents an Aspect (true) or a Class (false).<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASPECT).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Aspect</b>: Boolean<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return Indicates if this object represents an Aspect (true) or a Class (false)
     */
    public Boolean isAspect() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_ASPECT).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Indicates if this class should be hidden from end users and probably administrators.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_HIDDEN).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Hidden</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if this class should be hidden from end users and probably administrators
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
     * Returns Indicates if instances of this class are owned and managed by the system.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SYSTEM).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>System</b>: Boolean<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return Indicates if instances of this class are owned and managed by the system
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
     * Returns Indicates if instances of this class can by retrieved by their Id.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_RETRIEVABLE).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Retrievable</b>: Boolean<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return Indicates if instances of this class can by retrieved by their Id
     */
    public Boolean isRetrievable() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_RETRIEVABLE).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns Indicates if instances of this class can be retrieved in a search.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SEARCHABLE).getBoolean()</code>.
     * 
     * <p>Property opendma:<b>Searchable</b>: Boolean<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return Indicates if instances of this class can be retrieved in a search
     */
    public Boolean isSearchable() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_SEARCHABLE).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns List of classes or aspects that extend this class.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SUBCLASSES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>SubClasses</b>: Reference to Class (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]<br/>
     * The value of the `opendma:SubClasses` property is exactly the set of valid class objects whose `opendma:SuperClass` property contains a reference to this class info object</p>
     * 
     * @return List of classes or aspects that extend this class
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaClass> getSubClasses() {
        try {
            return (Iterable<OdmaClass>)getProperty(OdmaCommonNames.PROPERTY_SUBCLASSES).getReferenceIterable();
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
     * the qualified name of this class<br>
     * <p>A convenience shortcut to getting the name and namespace separately</p>
     * 
     * @return the qualified name of this class
     */
    public OdmaQName getQName() {
        return new OdmaQName(getNamespace(),getName());
    }

}
