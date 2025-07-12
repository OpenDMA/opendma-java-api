package org.opendma.templates;

import org.opendma.api.OdmaContainer;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.OdmaContainable;
import org.opendma.api.OdmaAssociation;
import java.util.Date;

/**
 * Template implementation of the interface <code>{@link OdmaContainer}</code>.<p>
 * 
 * A Container holds a set of containable objects that are said to be contained in this Container. This list of containees is build up with Association objects based on references to the containmer and the containee. This allows an object to be contained in multiple Containers or in no Container at all. A Container does not enforce a loop-free single rooted tree. Use a folder instead for this requirement.
 */
public class OdmaContainerTemplate extends OdmaObjectTemplate implements OdmaContainer {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaContainerTemplate

    /**
     * Returns the title of this container.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).getString()</code>.
     * 
     * <p>Property opendma:<b>Title</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the title of this container
     */
    public String getTitle() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_TITLE).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the title of this container.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Title</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the title of this container
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setTitle(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_TITLE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the set of containable objects contained in this container.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEES).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Containees</b>: Reference to Containable (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return the set of containable objects contained in this container
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaContainable> getContainees() {
        try {
            return (Iterable<OdmaContainable>)getProperty(OdmaCommonNames.PROPERTY_CONTAINEES).getReferenceIterable();
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
     * Returns the set of associations between this container and the contained objects.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_ASSOCIATIONS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Associations</b>: Reference to Association (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return the set of associations between this container and the contained objects
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaAssociation> getAssociations() {
        try {
            return (Iterable<OdmaAssociation>)getProperty(OdmaCommonNames.PROPERTY_ASSOCIATIONS).getReferenceIterable();
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
     * Returns the timestamp when this container has been created.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>CreatedAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the timestamp when this container has been created
     */
    public Date getCreatedAt() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_CREATEDAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the user who created this container.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * <p>Property opendma:<b>CreatedBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the user who created this container
     */
    public String getCreatedBy() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_CREATEDBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the timestamp when this container has been modified the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>LastModifiedAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * There is no definition what counts as a modification. Some systems update this timestamp when objects are added or removed, other systems only update this timestamp when properties of this object get changed.</p>
     * 
     * @return the timestamp when this container has been modified the last time
     */
    public Date getLastModifiedAt() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_LASTMODIFIEDAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the user who modified this container the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * <p>Property opendma:<b>LastModifiedBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]<br/>
     * There is no definition what counts as a modification. Some systems update this timestamp when objects are added or removed, other systems only update this timestamp when properties of this object get changed.</p>
     * 
     * @return the user who modified this container the last time
     */
    public String getLastModifiedBy() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_LASTMODIFIEDBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
