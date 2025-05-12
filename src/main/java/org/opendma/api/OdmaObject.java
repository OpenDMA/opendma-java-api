package org.opendma.api;


/**
 * Full description follows.
 */
public interface OdmaObject extends OdmaCoreObject {

    /**
     * Returns the OpenDMA <code>Class</code> describing this <code>Object</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CLASS).getReference()</code>.
     * 
     * <p>Property <b>Class</b> (opendma): <b>Reference to Class (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the OpenDMA <code>Class</code> describing this <code>Object</code>
     */
    OdmaClass getOdmaClass();

    /**
     * Returns the <i>unique object identifier</i> identifying this <code>Object</code> inside its <code>Repository</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ID).getId()</code>.
     * 
     * <p>Property <b>Id</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>unique object identifier</i> identifying this <code>Object</code> inside its <code>Repository</code>
     */
    OdmaId getId();

    /**
     * Returns the <i>global unique object identifier</i> globally identifying this <code>Object</code>.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_GUID).getGuid()</code>.
     * 
     * <p>Property <b>Guid</b> (opendma): <b>String</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the <i>global unique object identifier</i> globally identifying this <code>Object</code>
     */
    OdmaGuid getGuid();

    /**
     * Returns the OpenDMA <code>Repository</code> where this <code>Object</code> resides.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_REPOSITORY).getReference()</code>.
     * 
     * <p>Property <b>Repository</b> (opendma): <b>Reference to Repository (opendma)</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the OpenDMA <code>Repository</code> where this <code>Object</code> resides
     */
    OdmaRepository getRepository();

}
