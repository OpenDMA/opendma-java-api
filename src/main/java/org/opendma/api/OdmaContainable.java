package org.opendma.api;


/**
 * The Containable aspect is used by all classes and aspects that can be contained in a Container.
 */
public interface OdmaContainable extends OdmaObject {

    /**
     * Returns the set of container objects this Containable is contained in.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEDIN).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>ContainedIn</b>: Reference to Container (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return the set of container objects this Containable is contained in
     */
    Iterable<OdmaContainer> getContainedIn();

    /**
     * Returns the set of associations that bind this Containable in the opendma:Conatiner objects.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEDINASSOCIATIONS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>ContainedInAssociations</b>: Reference to Association (opendma)<br/>
     * [MultiValue] [ReadOnly] [Optional]</p>
     * 
     * @return the set of associations that bind this Containable in the opendma:Conatiner objects
     */
    Iterable<OdmaAssociation> getContainedInAssociations();

}
