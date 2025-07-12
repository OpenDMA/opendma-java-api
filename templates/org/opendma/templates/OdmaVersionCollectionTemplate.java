package org.opendma.templates;

import org.opendma.api.OdmaVersionCollection;
import org.opendma.api.OdmaCommonNames;
import org.opendma.exceptions.OdmaInvalidDataTypeException;
import org.opendma.exceptions.OdmaPropertyNotFoundException;
import org.opendma.exceptions.OdmaRuntimeException;
import org.opendma.api.OdmaDocument;

/**
 * Template implementation of the interface <code>{@link OdmaVersionCollection}</code>.<p>
 * 
 * A VersionCollection represents the set of all versions of a Document. Based on the actual document management system, it can represent a single series of versions, a tree of version, or any other versioning concept.
 */
public class OdmaVersionCollectionTemplate extends OdmaObjectTemplate implements OdmaVersionCollection {

    // ----- Object specific property access -------------------------------------------------------

    // CHECKTEMPLATE: the following code has most likely been copied from a class template. Make sure to keep this code up to date!
    // The following template code is available as OdmaVersionCollectionTemplate

    /**
     * Returns Set of all versions of a document.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_VERSIONS).getReferenceIterable()</code>.
     * 
     * <p>Property opendma:<b>Versions</b>: Reference to Document (opendma)<br/>
     * [MultiValue] [ReadOnly] [Required]</p>
     * 
     * @return Set of all versions of a document
     */
     @SuppressWarnings("unchecked")
    public Iterable<OdmaDocument> getVersions() {
        try {
            return (Iterable<OdmaDocument>)getProperty(OdmaCommonNames.PROPERTY_VERSIONS).getReferenceIterable();
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
     * Returns Latest version of a document.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_LATEST).getReference()</code>.
     * 
     * <p>Property opendma:<b>Latest</b>: Reference to Document (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return Latest version of a document
     */
    public OdmaDocument getLatest() {
        try {
            return (OdmaDocument)getProperty(OdmaCommonNames.PROPERTY_LATEST).getReference();
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
     * Returns Latest released version of a document if a version has been released.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_RELEASED).getReference()</code>.
     * 
     * <p>Property opendma:<b>Released</b>: Reference to Document (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return Latest released version of a document if a version has been released
     */
    public OdmaDocument getReleased() {
        try {
            return (OdmaDocument)getProperty(OdmaCommonNames.PROPERTY_RELEASED).getReference();
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
     * Returns Latest checked out working copy of a document.<br>
     * Shortcut for <code>getProperty(OdmaTypes.PROPERTY_INPROGRESS).getReference()</code>.
     * 
     * <p>Property opendma:<b>InProgress</b>: Reference to Document (opendma)<br/>
     * [SingleValue] [ReadOnly] [Optional]</p>
     * 
     * @return Latest checked out working copy of a document
     */
    public OdmaDocument getInProgress() {
        try {
            return (OdmaDocument)getProperty(OdmaCommonNames.PROPERTY_INPROGRESS).getReference();
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
