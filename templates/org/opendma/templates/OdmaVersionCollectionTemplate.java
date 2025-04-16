package org.opendma.templates;

import org.opendma.api.OdmaVersionCollection;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaObjectNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaQName;
import org.opendma.api.OdmaDocument;
import java.util.Date;

/**
 * Template implementation of the interface <code>{@link OdmaVersionCollection}</code>.<p>
 * 
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class OdmaVersionCollectionTemplate extends OdmaObjectTemplate implements OdmaVersionCollection
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
    // The following template code is available as OdmaVersionCollectionTemplate

    /**
     * Returns collection of all versions of the <code>Document</code>.<br>
     * 
     * <p>Property <b>Versions</b> (opendma): <b>Reference to Document (opendma)</b><br>
     * [MultiValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return collection of all versions of the <code>Document</code>
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaDocument> getVersions()
    {
        try
        {
            return (Iterable<OdmaDocument>)getProperty(OdmaCommonNames.PROPERTY_VERSIONS).getReferenceIterable();
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
     * Returns the latest version of this <code>Document</code>.<br>
     * 
     * <p>Property <b>Latest</b> (opendma): <b>Reference to Document (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the latest version of this <code>Document</code>
     */
    public OdmaDocument getLatest()
    {
        try
        {
            return (OdmaDocument)getProperty(OdmaCommonNames.PROPERTY_LATEST).getReference();
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
     * Returns the last released version of this <code>Document</code>.<br>
     * 
     * <p>Property <b>Released</b> (opendma): <b>Reference to Document (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the last released version of this <code>Document</code>
     */
    public OdmaDocument getReleased()
    {
        try
        {
            return (OdmaDocument)getProperty(OdmaCommonNames.PROPERTY_RELEASED).getReference();
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
     * Returns the version of this <code>Document</code> currently beeing worked on during a checkout. Only valid if and only if the corresponding <code>Document</code> is checked out..<br>
     * 
     * <p>Property <b>InProgress</b> (opendma): <b>Reference to Document (opendma)</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the version of this <code>Document</code> currently beeing worked on during a checkout. Only valid if and only if the corresponding <code>Document</code> is checked out.
     */
    public OdmaDocument getInProgress()
    {
        try
        {
            return (OdmaDocument)getProperty(OdmaCommonNames.PROPERTY_INPROGRESS).getReference();
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
     * Returns the date when this <code>Document</code> has been created.<br>
     * 
     * <p>Property <b>CreatedAt</b> (opendma): <b>DateTime</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Document</code> has been created
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
     * Returns the user who has created this <code>Document</code>.<br>
     * 
     * <p>Property <b>CreatedBy</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the user who has created this <code>Document</code>
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

}
