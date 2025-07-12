package org.opendma.templates;

import org.opendma.api.OdmaReferenceContentElement;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.exceptions.OdmaAccessDeniedException;

/**
 * Template implementation of the interface <code>{@link OdmaReferenceContentElement}</code>.<p>
 * 
 * A ReferenceContentElement represents a reference to external data. The reference is stored as URI to the content location.
 */
public class OdmaReferenceContentElementTemplate extends OdmaContentElementTemplate implements OdmaReferenceContentElement {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaReferenceContentElementTemplate

    /**
     * Returns the URI where the content is stored.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LOCATION).getString()</code>.
     * 
     * <p>Property opendma:<b>Location</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @return the URI where the content is stored
     */
    public String getLocation() {
        try {
            return getProperty(OdmaCommonNames.PROPERTY_LOCATION).getString();
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the URI where the content is stored.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LOCATION).setValue(value)</code>.
     * 
     * <p>Property opendma:<b>Location</b>: String<br/>
     * [SingleValue] [Writable] [Optional]</p>
     * 
     * @param newValue
     *             The new value for the URI where the content is stored
     * 
     * @throws OdmaAccessDeniedException
     *             If this OdmaProperty is read-only or cannot be set by the current user
     */
    public void setLocation(String newValue) throws OdmaAccessDeniedException {
        try {
            getProperty(OdmaCommonNames.PROPERTY_LOCATION).setValue(newValue);
        }
        catch(OdmaInvalidDataTypeException oidte) {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaPropertyNotFoundException oonfe) {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
