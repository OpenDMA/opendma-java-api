package org.opendma.api;


/**
 * A VersionCollection represents the set of all versions of a Document. Based on the actual document management system, it can represent a single series of versions, a tree of version, or any other versioning concept.
 */
public interface OdmaVersionCollection extends OdmaObject {

    /**
     * Returns Set of all versions of a document.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Versions</b>: Reference to Document (opendma)<br/>
     * [MultiValue] [ReadOnly] [Required]</p>
     * 
     * @return Set of all versions of a document
     */
    Iterable<OdmaDocument> getVersions();

    /**
     * Returns Latest version of a document.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LATEST).getReference()</code>.
     * 
     * <p>Property opendma:<b>Latest</b>: Reference to Document (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return Latest version of a document
     */
    OdmaDocument getLatest();

    /**
     * Returns Latest released version of a document if a version has been released.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_RELEASED).getReference()</code>.
     * 
     * <p>Property opendma:<b>Released</b>: Reference to Document (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return Latest released version of a document if a version has been released
     */
    OdmaDocument getReleased();

    /**
     * Returns Latest checked out working copy of a document.<br/>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INPROGRESS).getReference()</code>.
     * 
     * <p>Property opendma:<b>InProgress</b>: Reference to Document (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return Latest checked out working copy of a document
     */
    OdmaDocument getInProgress();

}
