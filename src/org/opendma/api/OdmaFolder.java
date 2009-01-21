package org.opendma.api;

import java.util.Date;

import org.opendma.api.collections.OdmaAssociationEnumeration;
import org.opendma.api.collections.OdmaObjectEnumeration;

/**
 * The <i>Folder</i> specific version of the <code>{@link OdmaObject}</code> interface that offers short cuts to all
 * defined OpenDMA properties.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaFolder extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the title of this <i>folder</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_TITLE).getString()</code>.
     * 
     * @return the title of this <i>folder</i>
     */
    public String getTitle();

    /**
     * Returns the enumeration over all <code>OdmaObject</code>s that are contained in this <i>folder</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CONTAINEES).getObjectList()</code>.
     * 
     * @return the enumeration over all <code>OdmaObject</code>s that are contained in this <i>folder</i>
     */
    public OdmaObjectEnumeration getContainees();

    /**
     * Returns the enumeration over all <code>OdmaAssociation</code>s between this <i>folder</i> and the
     * <code>OdmaObject</code>s that are contained in this <i>folder</i>.<br>
     * Shortcut for
     * <code>(OdmaAssociationEnumeration)getProperty(OdmaTypes.PROPERTY_ASSOCIATIONS).getObjectList()</code>.
     * 
     * @return the enumeration over all <code>OdmaAssociation</code>s between this <i>folder</i> and the
     *         <code>OdmaObject</code>s that are contained in this <i>folder</i>
     */
    public OdmaAssociationEnumeration getAssociations();

    /**
     * Returns the <code>Date</code> when this <i>folder</i> has been created.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * @return the <code>Date</code> when this <i>folder</i> has been created
     */
    public Date getCreatedAt();

    /**
     * Returns the <code>Date</code> when this <i>folder</i> has been modified the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * @return the <code>Date</code> when this <i>folder</i> has been modified the last time
     */
    public Date getLastModifiedAt();

    /**
     * Returns the name of the user who has created this <i>folder</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * @return the name of the user who has created this <i>folder</i>
     */
    public String getCreatedBy();

    /**
     * Returns the name of the user who has modified this <i>folder</i> the last time.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * @return the name of the user who has modified this <i>folder</i> the last time
     */
    public String getLastModifiedBy();

}