package org.opendma.api;

import java.util.Date;

/**
 * The <i>Association</i> specific version of the <code>{@link OdmaObject}</code> interface that offers short cuts to
 * all defined OpenDMA properties.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaAssociation extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the name of this <i>association</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * @return the name of this <i>association</i>
     */
    public String getName();

    /**
     * Returns the source of this <i>association</i>, i.e. the container.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINER).getString()</code>.
     * 
     * @return the source of this <i>association</i>, i.e. the conatiner
     */
    public OdmaFolder getContainer();

    /**
     * Returns the destination of this <i>association</i>, i.e. the containment.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINER).getString()</code>.
     * 
     * @return the source of this <i>association</i>, i.e. the conatiner
     */
    public OdmaObject getContainment();

    /**
     * Returns the <code>Date</code> when this <i>association</i> has been created.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * @return the <code>Date</code> when this <i>association</i> has been created
     */
    public Date getCreatedAt();

    /**
     * Returns the <code>Date</code> when this <i>association</i> has been modified the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * @return the <code>Date</code> when this <i>association</i> has been modified the last time
     */
    public Date getLastModifiedAt();

    /**
     * Returns the name of the user who has created this <i>association</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * @return the name of the user who has created this <i>association</i>
     */
    public String getCreatedBy();

    /**
     * Returns the name of the user who has modified this <i>association</i> the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * @return the name of the user who has modified this <i>association</i> the last time
     */
    public String getLastModifiedBy();

}