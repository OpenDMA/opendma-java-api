package org.opendma.api;

/**
 * The <i>Repository</i> specific version of the <code>{@link OdmaObject}</code> interface that offers short cuts to
 * all defined OpenDMA properties.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaRepository extends OdmaObject
{

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    /**
     * Returns the internal name of this <i>repository</i>.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_NAME).getString()</code>.
     * 
     * @return the internal name of this <i>repository</i>
     */
    public String getName();

    /**
     * Returns the name of this <i>repository</i> that should be displayed to the user.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_DISPLAYNAME).getString()</code>.
     * 
     * @return the name of this <i>repository</i> that should be displayed to the user
     */
    public String getDisplayName();

    /**
     * Returns the root class of the class hierarchy of this <i>repository</i>.<br>
     * Shortcut for <code>(OdmaClass)getProperty(OdmaTypes.PROPERTY_ROOTCLASS).getObject()</code>.
     * 
     * @return the root class of the class hierarchy of this <i>repository</i>
     */
    public OdmaClass getRootClass();

    /**
     * Returns the root folder of this <i>repository</i> if this repository has a distinct single rooted folder tree.<br>
     * Shortcut for <code>(OdmaFolder)getProperty(OdmaTypes.PROPERTY_ROOTFOLDER).getObject()</code>.
     * 
     * @return the root folder of this <i>repository</i>
     */
    public OdmaFolder getRootFolder();

}