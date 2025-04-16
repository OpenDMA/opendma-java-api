package org.opendma.templates;

import org.opendma.api.OdmaDocument;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaObjectNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaProperty;
import org.opendma.api.OdmaQName;
import org.opendma.exceptions.OdmaAccessDeniedException;
import org.opendma.api.OdmaVersionCollection;
import org.opendma.api.OdmaId;
import org.opendma.api.OdmaGuid;
import org.opendma.api.OdmaContentElement;
import java.util.Date;

/**
 * Template implementation of the interface <code>{@link OdmaDocument}</code>.<p>
 * 
 * Full description follows.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class OdmaDocumentTemplate extends OdmaObjectTemplate implements OdmaDocument
{

    public OdmaProperty getProperty(OdmaQName propertyName) throws OdmaObjectNotFoundException
    {
        // TODO: implement me
        return null;
    }

    // =============================================================================================
    // Object specific property access
    // =============================================================================================

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaDocumentTemplate

    /**
     * Returns the <i>title</i> of this <code>Document</code>.<br>
     * 
     * <p>Property <b>Title</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @return the <i>title</i> of this <code>Document</code>
     */
    public String getTitle()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_TITLE).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the <i>title</i> of this <code>Document</code>.<br>
     * 
     * <p>Property <b>Title</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setTitle(String value) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaCommonNames.PROPERTY_TITLE).setValue(value);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the <i>version string</i> describing this <i>version</i> of this <code>Document</code> (e.g. 1.0, 1.1, 1.2, 2.0).<br>
     * 
     * <p>Property <b>Version</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @return the <i>version string</i> describing this <i>version</i> of this <code>Document</code> (e.g. 1.0, 1.1, 1.2, 2.0)
     */
    public String getVersion()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_VERSION).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns reference to a <i>VersionCollection</i> object containing the collection of all <i>versions</i> of this <code>Document</code> along with other information.<br>
     * 
     * <p>Property <b>VersionCollection</b> (opendma): <b>Reference to VersionCollection (opendma)</b><br>
     * [SingleValue] [ReadOnly] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @return reference to a <i>VersionCollection</i> object containing the collection of all <i>versions</i> of this <code>Document</code> along with other information
     */
    public OdmaVersionCollection getVersionCollection()
    {
        try
        {
            return (OdmaVersionCollection)getProperty(OdmaCommonNames.PROPERTY_VERSIONCOLLECTION).getReference();
        }
        catch(ClassCastException cce)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the <i>unique object identifier</i> identifying this logical document independent from the specific version inside its <code>Repository</code>.<br>
     * 
     * <p>Property <b>VersionIndependentId</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>unique object identifier</i> identifying this logical document independent from the specific version inside its <code>Repository</code>
     */
    public OdmaId getVersionIndependentId()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTID).getId();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the <i>global unique object identifier</i> globally identifying this logical document independent from the specific version.<br>
     * 
     * <p>Property <b>VersionIndependentGuid</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the <i>global unique object identifier</i> globally identifying this logical document independent from the specific version
     */
    public OdmaGuid getVersionIndependentGuid()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_VERSIONINDEPENDENTGUID).getGuid();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the collection of all <code>ContentElement</code>s this <code>Document</code> consists of.<br>
     * 
     * <p>Property <b>ContentElements</b> (opendma): <b>Reference to ContentElement (opendma)</b><br>
     * [MultiValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @return the collection of all <code>ContentElement</code>s this <code>Document</code> consists of
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaContentElement> getContentElements()
    {
        try
        {
            return (Iterable<OdmaContentElement>)getProperty(OdmaCommonNames.PROPERTY_CONTENTELEMENTS).getReferenceIterable();
        }
        catch(ClassCastException cce)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the combined mime type that has been build over all <code>ContentElement</code>s this <code>Document</code> consists of.<br>
     * 
     * <p>Property <b>CombinedContentType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @return the combined mime type that has been build over all <code>ContentElement</code>s this <code>Document</code> consists of
     */
    public String getCombinedContentType()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the combined mime type that has been build over all <code>ContentElement</code>s this <code>Document</code> consists of.<br>
     * 
     * <p>Property <b>CombinedContentType</b> (opendma): <b>String</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setCombinedContentType(String value) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaCommonNames.PROPERTY_COMBINEDCONTENTTYPE).setValue(value);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the primary <code>ContentElement</code>s that represents this <code>Document</code>.<br>
     * 
     * <p>Property <b>PrimaryContentElement</b> (opendma): <b>Reference to ContentElement (opendma)</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @return the primary <code>ContentElement</code>s that represents this <code>Document</code>
     */
    public OdmaContentElement getPrimaryContentElement()
    {
        try
        {
            return (OdmaContentElement)getProperty(OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT).getReference();
        }
        catch(ClassCastException cce)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",cce);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Sets the primary <code>ContentElement</code>s that represents this <code>Document</code>.<br>
     * 
     * <p>Property <b>PrimaryContentElement</b> (opendma): <b>Reference to ContentElement (opendma)</b><br>
     * [SingleValue] [Writable] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @throws OdmaAccessDeniedException
     *             if this property can not be set by the current user
     */
    public void setPrimaryContentElement(OdmaContentElement value) throws OdmaAccessDeniedException
    {
        try
        {
            getProperty(OdmaCommonNames.PROPERTY_PRIMARYCONTENTELEMENT).setValue(value);
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the date when this version of this <code>Document</code> has been created.<br>
     * 
     * <p>Property <b>CreatedAt</b> (opendma): <b>DateTime</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the date when this version of this <code>Document</code> has been created
     */
    public Date getCreatedAt()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_CREATEDAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the user who has created this version of this <code>Document</code>.<br>
     * 
     * <p>Property <b>CreatedBy</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the user who has created this version of this <code>Document</code>
     */
    public String getCreatedBy()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_CREATEDBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the date when this version of this <code>Document</code> has been modified the last time.<br>
     * 
     * <p>Property <b>LastModifiedAt</b> (opendma): <b>DateTime</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the date when this version of this <code>Document</code> has been modified the last time
     */
    public Date getLastModifiedAt()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_LASTMODIFIEDAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the user who has modified this version of this <code>Document</code> the last time.<br>
     * 
     * <p>Property <b>LastModifiedBy</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the user who has modified this version of this <code>Document</code> the last time
     */
    public String getLastModifiedBy()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_LASTMODIFIEDBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns <code>true</code> if and only if this <code>Document</code> is checked out.<br>
     * 
     * <p>Property <b>CheckedOut</b> (opendma): <b>Boolean</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return <code>true</code> if and only if this <code>Document</code> is checked out
     */
    public Boolean getCheckedOut()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_CHECKEDOUT).getBoolean();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the date when this <code>Document</code> has been checked out.<br>
     * 
     * <p>Property <b>CheckedOutAt</b> (opendma): <b>DateTime</b><br>
     * [SingleValue] [ReadOnly] [NotRequired]<br>
     * Full description follows.</p>
     * 
     * @return the date when this <code>Document</code> has been checked out
     */
    public Date getCheckedOutAt()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_CHECKEDOUTAT).getDateTime();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

    /**
     * Returns the user who has checked out this <code>Document</code>.<br>
     * 
     * <p>Property <b>CheckedOutBy</b> (opendma): <b>String</b><br>
     * [SingleValue] [ReadOnly] [Required]<br>
     * Full description follows.</p>
     * 
     * @return the user who has checked out this <code>Document</code>
     */
    public String getCheckedOutBy()
    {
        try
        {
            return getProperty(OdmaCommonNames.PROPERTY_CHECKEDOUTBY).getString();
        }
        catch(OdmaInvalidDataTypeException oidte)
        {
            throw new OdmaRuntimeException("Invalid data type of system property",oidte);
        }
        catch(OdmaObjectNotFoundException oonfe)
        {
            throw new OdmaRuntimeException("Predefined system property missing",oonfe);
        }
    }

}
