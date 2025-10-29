package org.opendma.templates;

import org.opendma.api.OdmaAssociation;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.OdmaContainer;
import org.opendma.api.OdmaContainable;

/**
 * Template implementation of the interface <code>{@link OdmaAssociation}</code>.<p>
 * 
 * An Association represents the directed link between an opendma:Container and an opendma:Containable object.
 */
public class OdmaAssociationTemplate extends OdmaObjectTemplate implements OdmaAssociation {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaAssociationTemplate

    /**
     * Returns the name of this association.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * This name is used to refer to this specific association in the context of it's container and tell tem apart. Many systems pose additional constraints on this name.</p>
     * 
     * @return the name of this association
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
     * Sets the name of this association.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Name</b>: String<br/>
     * [SingleValue] [Writable] [Required]<br/>
     * This name is used to refer to this specific association in the context of it's container and tell tem apart. Many systems pose additional constraints on this name.</p>
     * 
     * @param newValue
     *             The new value for the name of this association
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
     * Returns the source of this directed link.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINER).getReference()</code>.
     * 
     * <p>Property opendma:<b>Container</b>: Reference to Container (opendma)<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return the source of this directed link
     */
    public OdmaContainer getContainer() {
        try {
            return (OdmaContainer)getProperty(OdmaCommonNames.PROPERTY_CONTAINER).getReference();
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
     * Sets the source of this directed link.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINER).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Container</b>: Reference to Container (opendma)<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for the source of this directed link
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setContainer(OdmaContainer newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_CONTAINER).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the destination of this directed link.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINABLE).getReference()</code>.
     * 
     * <p>Property opendma:<b>Containable</b>: Reference to Containable (opendma)<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @return the destination of this directed link
     */
    public OdmaContainable getContainable() {
        try {
            return (OdmaContainable)getProperty(OdmaCommonNames.PROPERTY_CONTAINABLE).getReference();
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
     * Sets the destination of this directed link.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINABLE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Containable</b>: Reference to Containable (opendma)<br/>
     * [SingleValue] [Writable] [Required]</p>
     * 
     * @param newValue
     *             The new value for the destination of this directed link
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setContainable(OdmaContainable newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_CONTAINABLE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
