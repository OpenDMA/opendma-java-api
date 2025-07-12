package org.opendma.templates;

import org.opendma.api.OdmaContainable;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaContainer;
import org.opendma.api.OdmaAssociation;

/**
 * Template implementation of the interface <code>{@link OdmaContainable}</code>.<p>
 * 
 * The Containable aspect is used by all classes and aspects that can be contained in a Container.
 */
public class OdmaContainableTemplate extends OdmaObjectTemplate implements OdmaContainable {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaContainableTemplate

    /**
     * Returns the set of container objects this Containable is contained in.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEDIN).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>ContainedIn</b>: Reference to Container (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return the set of container objects this Containable is contained in
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaContainer> getContainedIn() {
        try {
            return (Iterable<OdmaContainer>)getProperty(OdmaCommonNames.PROPERTY_CONTAINEDIN).getReferenceIterable();
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
     * Returns the set of associations that bind this Containable in the opendma:Conatiner objects.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEDINASSOCIATIONS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>ContainedInAssociations</b>: Reference to Association (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return the set of associations that bind this Containable in the opendma:Conatiner objects
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaAssociation> getContainedInAssociations() {
        try {
            return (Iterable<OdmaAssociation>)getProperty(OdmaCommonNames.PROPERTY_CONTAINEDINASSOCIATIONS).getReferenceIterable();
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

}
