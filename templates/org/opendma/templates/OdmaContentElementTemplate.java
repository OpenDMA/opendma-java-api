package org.opendma.templates;

import org.opendma.api.OdmaContentElement;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * Template implementation of the interface <code>{@link OdmaContentElement}</code>.<p>
 * 
 * A ContentElement represents one atomic content element the Documents are made of. This abstract (non instantiable) base class defines the type of content and the position of this element in the sequence of all content elements.
 */
public class OdmaContentElementTemplate extends OdmaObjectTemplate implements OdmaContentElement {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaContentElementTemplate

    /**
     * Returns the content type (aka MIME type) of the content represented by this element.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).getString()</code>.
     * 
     * <p>Property opendma:<b>ContentType</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the content type (aka MIME type) of the content represented by this element
     */
    public String getContentType() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_CONTENTTYPE).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the content type (aka MIME type) of the content represented by this element.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTENTTYPE).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>ContentType</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the content type (aka MIME type) of the content represented by this element
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setContentType(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_CONTENTTYPE).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the position of this element in the list of all content elements of the containing document.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_POSITION).getInteger()</code>.
     * 
     * <p>Property opendma:<b>Position</b>: Integer<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the position of this element in the list of all content elements of the containing document
     */
    public Integer getPosition() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_POSITION).getInteger();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
