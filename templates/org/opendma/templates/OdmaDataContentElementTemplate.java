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
 * A DataContentElement represents one atomic octet stream. The binary data is stored together with meta data like size and filename
 */
public class OdmaDataContentElementTemplate extends OdmaContentElementTemplate implements OdmaDataContentElement {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaDataContentElementTemplate

    /**
     * Returns the binary data of this content element.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).getContent()</code>.
     * 
     * <p>Property opendma:<b>Content</b>: Content<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the binary data of this content element
     */
    public OdmaContent getContent() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_CONTENT).getContent();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the binary data of this content element.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENT).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Content</b>: Content<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the binary data of this content element
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setContent(OdmaContent newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_CONTENT).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the size of the data in number of octets.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_SIZE).getLong()</code>.
     * 
     * <p>Property opendma:<b>Size</b>: Long<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the size of the data in number of octets
     */
    public Long getSize() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_SIZE).getLong();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the optional file name of the data.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).getString()</code>.
     * 
     * <p>Property opendma:<b>FileName</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the optional file name of the data
     */
    public String getFileName() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_FILENAME).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the optional file name of the data.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_FILENAME).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>FileName</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the optional file name of the data
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setFileName(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_FILENAME).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
