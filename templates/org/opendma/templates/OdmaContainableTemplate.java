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
 * Full description follows.
 */
public class OdmaContainableTemplate extends OdmaObjectTemplate implements OdmaContainable
{

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaContainableTemplate

    /**
     * Returns the collection of <code>Container</code>s this <code>Containable</code> is contained in.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEDIN).getReferenceIterable()</code>.
     * 
     * <p>Property <b>ContainedIn</b> (opendma): <b>Reference to Container (opendma)</b><br/>
     * [MultiValue] [ReadOnly] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the collection of <code>Container</code>s this <code>Containable</code> is contained in
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaContainer> getContainedIn()
    {
        try
        {
            return (Iterable<OdmaContainer>)getProperty(OdmaCommonNames.PROPERTY_CONTAINEDIN).getReferenceIterable();
        }
        catch(ClassCastException cce)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
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
     * Returns the collection of <code>Association</code>s that bind this <code>Containable</code> in the <code>Container</code>s.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEDINASSOCIATIONS).getReferenceIterable()</code>.
     * 
     * <p>Property <b>ContainedInAssociations</b> (opendma): <b>Reference to Association (opendma)</b><br/>
     * [MultiValue] [ReadOnly] [NotRequired]<br/>
     * Full description follows.</p>
     * 
     * @return the collection of <code>Association</code>s that bind this <code>Containable</code> in the <code>Container</code>s
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaAssociation> getContainedInAssociations()
    {
        try
        {
            return (Iterable<OdmaAssociation>)getProperty(OdmaCommonNames.PROPERTY_CONTAINEDINASSOCIATIONS).getReferenceIterable();
        }
        catch(ClassCastException cce)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
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
