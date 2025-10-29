package org.opendma.api;

import java.util.Date;

/**
 * Objects with this aspect record information about their creation and their last modification.
 */
public interface OdmaAuditStamped extends OdmaObject {

    /**
     * Returns the timestamp when this object has been created.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>CreatedAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the timestamp when this object has been created
     */
    Date getCreatedAt();

    /**
     * Returns the User who created this object.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_CREATEDBY).getString()</code>.
     * 
     * <p>Property opendma:<b>CreatedBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the User who created this object
     */
    String getCreatedBy();

    /**
     * Returns the timestamp when this object has been modified the last time.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDAT).getDateTime()</code>.
     * 
     * <p>Property opendma:<b>LastModifiedAt</b>: DateTime<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the timestamp when this object has been modified the last time
     */
    Date getLastModifiedAt();

    /**
     * Returns the user who modified this object the last time.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LASTMODIFIEDBY).getString()</code>.
     * 
     * <p>Property opendma:<b>LastModifiedBy</b>: String<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return the user who modified this object the last time
     */
    String getLastModifiedBy();

}
