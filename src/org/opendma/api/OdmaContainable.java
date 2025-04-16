package org.opendma.api;


/**
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaContainable extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the collection of <code>Container</code>s this <code>Containable</code> is contained in.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEDIN).getReferenceIterable()</code>.
     * 
     * <p>Property <b>ContainedIn</b> (opendma): <b>Reference to Container (opendma)</b><br>
     * [MultiValue] [ReadOnly] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the collection of <code>Container</code>s this <code>Containable</code> is contained in
     */
    public Iterable<OdmaContainer> getContainedIn();

    /**
     * Returns the collection of <code>Association</code>s that bind this <code>Containable</code> in the <code>Container</code>s.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEDINASSOCIATIONS).getReferenceIterable()</code>.
     * 
     * <p>Property <b>ContainedInAssociations</b> (opendma): <b>Reference to Association (opendma)</b><br>
     * [MultiValue] [ReadOnly] [Nullable]<br>
     * Full description follows.</p>
     * 
     * @return the collection of <code>Association</code>s that bind this <code>Containable</code> in the <code>Container</code>s
     */
    public Iterable<OdmaAssociation> getContainedInAssociations();

}
