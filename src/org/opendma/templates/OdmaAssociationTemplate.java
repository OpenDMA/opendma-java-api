package org.opendma.templates;

import org.opendma.api.OdmaAssociation;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaObjectNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaQName;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.OdmaContainer;
import org.opendma.api.OdmaContainable;
import java.util.Date;

/**
 * Template implementation of the interface <code>{@link OdmaAssociation}</code>.<p>
 * 
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class OdmaAssociationTemplate extends OdmaObjectTemplate implements OdmaAssociation
{

    public OdmaProperty getProperty(OdmaQName propertyName) throws OdmaObjectNotFoundException
    {
        // TODO: implement me
        return null;
    }

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaAssociationTemplate

    /**
     * Returns the <i>name</i> of this <code>Association</code>.<br>
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>name</i> of this <code>Association</code>
     */
    public String getName()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_NAME).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the <i>name</i> of this <code>Association</code>.<br>
     * 
     * <p>Property <b>Name</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setName(String value) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaCommonNames.PROPERTY_NAME).setValue(value);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the <code>Container</code> of this <code>Association</code> in which the containable is said to be contained.<br>
     * 
     * <p>Property <b>Container</b> (opendma): <b>Reference to Container (opendma)</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <code>Container</code> of this <code>Association</code> in which the containable is said to be contained
     */
    public OdmaContainer getContainer()
    {
        try
        {
            return (OdmaContainer)getProperty(OdmaCommonNames.PROPERTY_CONTAINER).getReference();
        }
        catch(ClassCastException cce)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the <code>Container</code> of this <code>Association</code> in which the containable is said to be contained.<br>
     * 
     * <p>Property <b>Container</b> (opendma): <b>Reference to Container (opendma)</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setContainer(OdmaContainer value) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaCommonNames.PROPERTY_CONTAINER).setValue(value);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the <code>Containable</code> of this <code>Association</code> which is said to be contained in the container.<br>
     * 
     * <p>Property <b>Containable</b> (opendma): <b>Reference to Containable (opendma)</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <code>Containable</code> of this <code>Association</code> which is said to be contained in the container
     */
    public OdmaContainable getContainable()
    {
        try
        {
            return (OdmaContainable)getProperty(OdmaCommonNames.PROPERTY_CONTAINABLE).getReference();
        }
        catch(ClassCastException cce)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the <code>Containable</code> of this <code>Association</code> which is said to be contained in the container.<br>
     * 
     * <p>Property <b>Containable</b> (opendma): <b>Reference to Containable (opendma)</b><br>
     * [SingleValue] [Writable] [Required]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setContainable(OdmaContainable value) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaCommonNames.PROPERTY_CONTAINABLE).setValue(value);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the date when this <code>Association</code> has been created.<br>
     * 
     * <p>Property <b>CreatedAt</b> (opendma): <b>DateTime</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Association</code> has been created
     */
    public Date getCreatedAt()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_CREATEDAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the user who has created this <code>Association</code>.<br>
     * 
     * <p>Property <b>CreatedBy</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the user who has created this <code>Association</code>
     */
    public String getCreatedBy()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_CREATEDBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the date when this <code>Association</code> has been modified the last time.<br>
     * 
     * <p>Property <b>LastModifiedAt</b> (opendma): <b>DateTime</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Association</code> has been modified the last time
     */
    public Date getLastModifiedAt()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_LASTMODIFIEDAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the user who has modified this <code>Association</code> the last time.<br>
     * 
     * <p>Property <b>LastModifiedBy</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the user who has modified this <code>Association</code> the last time
     */
    public String getLastModifiedBy()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_LASTMODIFIEDBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
