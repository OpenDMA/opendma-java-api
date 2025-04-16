package org.opendma.templates;

import org.opendma.api.OdmaReferenceContentElement;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaObjectNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaQName;
import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * Template implementation of the interface <code>{@link OdmaReferenceContentElement}</code>.<p>
 * 
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class OdmaReferenceContentElementTemplate extends OdmaContentElementTemplate implements OdmaReferenceContentElement
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
    // The following template code is available as OdmaReferenceContentElementTemplate

    /**
     * Returns the URI where the content is stored.<br>
     * 
     * <p>Property <b>Location</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @return the URI where the content is stored
     */
    public String getLocation()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_LOCATION).getString();
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
     * Sets the URI where the content is stored.<br>
     * 
     * <p>Property <b>Location</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setLocation(String value) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaCommonNames.PROPERTY_LOCATION).setValue(value);
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
