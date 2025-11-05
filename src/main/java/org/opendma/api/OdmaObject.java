package org.opendma.api;


/**
 * Root of the class hierarchy. Every class in OpenDMA extends this class. All objects in OpenDMA have the properties defined for this class.
 */
public interface OdmaObject extends OdmaCoreObject {

    /**
     * Returns Reference to a valid class object describing this object.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CLASS).getReference()</code>.
     * 
     * <p>Property opendma:<b>Class</b>: Reference to Class (opendma)<br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * The opendma:Class describes the layout and features of this object. Here you can find a set of odma:PropertyInfo objects that describe all the properties with their qualified name, data type and cardinality. It also provides the text to be displayed to users to refer to these objects as well as flags indicating if these objects are system owner or should be hidden from end users.</p>
     * 
     * @return Reference to a valid class object describing this object
     */
    OdmaClass getOdmaClass();

    /**
     * Returns References to valid aspect objects describing this object.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASPECTS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Aspects</b>: Reference to Class (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]<br/>
     * The opendma:Aspects can augment the layout and features defined by opendma:Class for this object.</p>
     * 
     * @return References to valid aspect objects describing this object
     */
    Iterable<OdmaClass> getAspects();

    /**
     * Returns the unique object identifier.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ID).getId()</code>.
     * 
     * <p>Property opendma:<b>Id</b>: String<br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * This identifier is unique within it's Repository and  must be immutable during the lifetime of this object. You can use it to refer to this object and retrieve it again at a later time.</p>
     * 
     * @return the unique object identifier
     */
    OdmaId getId();

    /**
     * Returns the global unique object identifier.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_GUID).getGuid()</code>.
     * 
     * <p>Property opendma:<b>Guid</b>: String<br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * A combination of the unique object identifier and the unique repository identifier. Use it to refer to this object across multiple repositories.</p>
     * 
     * @return the global unique object identifier
     */
    OdmaGuid getGuid();

    /**
     * Returns the Repository this object belongs to.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REPOSITORY).getReference()</code>.
     * 
     * <p>Property opendma:<b>Repository</b>: Reference to Repository (opendma)<br/>
     * [SingleValue] [ReadOnly] [Required]</p>
     * 
     * @return the Repository this object belongs to
     */
    OdmaRepository getRepository();

}
