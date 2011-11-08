package org.opendma.templates;

import org.opendma.api.OdmaContentElement;
import org.opendma.OdmaTypes;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaObjectNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaQName;
import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * Template implementation of the interface <code>{@link OdmaContentElement}</code>.<p>
 * 
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class OdmaContentElementTemplate extends OdmaObjectTemplate implements OdmaContentElement
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
    // The following template code is available as OdmaContentElementTemplate

    /**
     * Returns the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted.<br>
     * 
     * <p>Property <b>ContentType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted
     */
    public String getContentType()
    {
        try
        {
            return getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).getString();
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
     * Sets the mime type describing how the <code>Content</code> of this <code>ContentElement</code> has to be interpreted.<br>
     * 
     * <p>Property <b>ContentType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setContentType(String value) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).setValue(value);
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
     * Returns the position of this element in the list of all content elements of the containing document.<br>
     * 
     * <p>Property <b>Position</b> (opendma): <b>Integer</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the position of this element in the list of all content elements of the containing document
     */
    public Integer getPosition()
    {
        try
        {
            return getProperty(OdmaTypes.PROPERTY_POSITION).getInteger();
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
