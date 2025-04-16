package org.opendma.templates;

import org.opendma.api.OdmaDataContentElement;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaContent;
import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * Template implementation of the interface <code>{@link OdmaDataContentElement}</code>.<p>
 * 
 * Full description follows.
 */
public class OdmaDataContentElementTemplate extends OdmaContentElementTemplate implements OdmaDataContentElement
{

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaDataContentElementTemplate

    /**
     * Returns the binary content of this <code>ContentElement</code> as octets.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).getContent()</code>.
     * 
     * <p>Property <b>Content</b> (opendma): <b>Content</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the binary content of this <code>ContentElement</code> as octets
     */
    public OdmaContent getContent()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_CONTENT).getContent();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the binary content of this <code>ContentElement</code> as octets.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).setValue(value)</code>.
     * 
     * <p>Property <b>Content</b> (opendma): <b>Content</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the binary content of this <code>ContentElement</code> as octets
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setContent(OdmaContent newValue) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaCommonNames.PROPERTY_CONTENT).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the number of octests the binary content of this <code>ContentElement</code> consists of.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SIZE).getLong()</code>.
     * 
     * <p>Property <b>Size</b> (opendma): <b>Long</b><br/>
     * [SingleValue] [ReadOnly] [Required]<br/>
     * Full description follows.</p>
     * 
     * @return the number of octests the binary content of this <code>ContentElement</code> consists of
     */
    public Long getSize()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_SIZE).getLong();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the optional file name of this <code>ContentElement</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).getString()</code>.
     * 
     * <p>Property <b>FileName</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the optional file name of this <code>ContentElement</code>
     */
    public String getFileName()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_FILENAME).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the optional file name of this <code>ContentElement</code>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).setValue(value)</code>.
     * 
     * <p>Property <b>FileName</b> (opendma): <b>String</b><br/>
     * [SingleValue] [Writable] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @param newValue
     *             The new value for the optional file name of this <code>ContentElement</code>
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setFileName(String newValue) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaCommonNames.PROPERTY_FILENAME).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
