package org.opendma;

import org.opendma.api.OdmaQName;

/**
 * Static declaration of all type constants used in the OpenDMA spcification.
 *
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class OdmaTypes
{

    // =============================================================================================
    // Declaration of numeric data type IDs
    // =============================================================================================

    /**
     * Numeric type identifier of the data type <code>String</code>.<br>
     * Value is 1.
     */
    public final static int TYPE_STRING = 1;

    /**
     * Numeric type identifier of the data type <code>Integer</code>.<br>
     * Value is 2.
     */
    public final static int TYPE_INTEGER = 2;

    /**
     * Numeric type identifier of the data type <code>Short</code>.<br>
     * Value is 3.
     */
    public final static int TYPE_SHORT = 3;

    /**
     * Numeric type identifier of the data type <code>Long</code>.<br>
     * Value is 4.
     */
    public final static int TYPE_LONG = 4;

    /**
     * Numeric type identifier of the data type <code>Float</code>.<br>
     * Value is 5.
     */
    public final static int TYPE_FLOAT = 5;

    /**
     * Numeric type identifier of the data type <code>Double</code>.<br>
     * Value is 6.
     */
    public final static int TYPE_DOUBLE = 6;

    /**
     * Numeric type identifier of the data type <code>Boolean</code>.<br>
     * Value is 7.
     */
    public final static int TYPE_BOOLEAN = 7;

    /**
     * Numeric type identifier of the data type <code>DateTime</code>.<br>
     * Value is 8.
     */
    public final static int TYPE_DATETIME = 8;

    /**
     * Numeric type identifier of the data type <code>Blob</code>.<br>
     * Value is 9.
     */
    public final static int TYPE_BLOB = 9;

    /**
     * Numeric type identifier of the data type <code>Reference</code>.<br>
     * Value is 10.
     */
    public final static int TYPE_REFERENCE = 10;

    /**
     * Numeric type identifier of the data type <code>Content</code>.<br>
     * Value is 11.
     */
    public final static int TYPE_CONTENT = 11;

    /**
     * Numeric type identifier of the data type <code>Id</code>.<br>
     * Value is 100.
     */
    public final static int TYPE_ID = 100;

    /**
     * Numeric type identifier of the data type <code>Guid</code>.<br>
     * Value is 101.
     */
    public final static int TYPE_GUID = 101;

    // =============================================================================================
    // Declaration of pre-defined class names and property names
    // =============================================================================================

    // -----< class Object >------------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Object</code> */
    public final static OdmaQName CLASS_OBJECT = new OdmaQName("opendma", "Object");

    /** qualified name of the OpenDMA system property <code>Class</code> */
    public final static OdmaQName PROPERTY_CLASS = new OdmaQName("opendma", "Class");

    /** qualified name of the OpenDMA system property <code>Id</code> */
    public final static OdmaQName PROPERTY_ID = new OdmaQName("opendma", "Id");

    /** qualified name of the OpenDMA system property <code>Guid</code> */
    public final static OdmaQName PROPERTY_GUID = new OdmaQName("opendma", "Guid");

    /** qualified name of the OpenDMA system property <code>Repository</code> */
    public final static OdmaQName PROPERTY_REPOSITORY = new OdmaQName("opendma", "Repository");

    // -----< class Class >-------------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Class</code> */
    public final static OdmaQName CLASS_CLASS = new OdmaQName("opendma", "Class");

    /** qualified name of the OpenDMA system property <code>Name</code> */
    public final static OdmaQName PROPERTY_NAME = new OdmaQName("opendma", "Name");

    /** qualified name of the OpenDMA system property <code>NameQualifier</code> */
    public final static OdmaQName PROPERTY_NAMEQUALIFIER = new OdmaQName("opendma", "NameQualifier");

    /** qualified name of the OpenDMA system property <code>DisplayName</code> */
    public final static OdmaQName PROPERTY_DISPLAYNAME = new OdmaQName("opendma", "DisplayName");

    /** qualified name of the OpenDMA system property <code>Parent</code> */
    public final static OdmaQName PROPERTY_PARENT = new OdmaQName("opendma", "Parent");

    /** qualified name of the OpenDMA system property <code>Aspects</code> */
    public final static OdmaQName PROPERTY_ASPECTS = new OdmaQName("opendma", "Aspects");

    /** qualified name of the OpenDMA system property <code>DeclaredProperties</code> */
    public final static OdmaQName PROPERTY_DECLAREDPROPERTIES = new OdmaQName("opendma", "DeclaredProperties");

    /** qualified name of the OpenDMA system property <code>Properties</code> */
    public final static OdmaQName PROPERTY_PROPERTIES = new OdmaQName("opendma", "Properties");

    /** qualified name of the OpenDMA system property <code>Instantiable</code> */
    public final static OdmaQName PROPERTY_INSTANTIABLE = new OdmaQName("opendma", "Instantiable");

    /** qualified name of the OpenDMA system property <code>Hidden</code> */
    public final static OdmaQName PROPERTY_HIDDEN = new OdmaQName("opendma", "Hidden");

    /** qualified name of the OpenDMA system property <code>System</code> */
    public final static OdmaQName PROPERTY_SYSTEM = new OdmaQName("opendma", "System");

    /** qualified name of the OpenDMA system property <code>SubClasses</code> */
    public final static OdmaQName PROPERTY_SUBCLASSES = new OdmaQName("opendma", "SubClasses");

    // -----< class PropertyInfo >------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>PropertyInfo</code> */
    public final static OdmaQName CLASS_PROPERTYINFO = new OdmaQName("opendma", "PropertyInfo");

    // Property Name already defined previously

    // Property NameQualifier already defined previously

    // Property DisplayName already defined previously

    /** qualified name of the OpenDMA system property <code>DataType</code> */
    public final static OdmaQName PROPERTY_DATATYPE = new OdmaQName("opendma", "DataType");

    /** qualified name of the OpenDMA system property <code>ReferenceClass</code> */
    public final static OdmaQName PROPERTY_REFERENCECLASS = new OdmaQName("opendma", "ReferenceClass");

    /** qualified name of the OpenDMA system property <code>MultiValue</code> */
    public final static OdmaQName PROPERTY_MULTIVALUE = new OdmaQName("opendma", "MultiValue");

    /** qualified name of the OpenDMA system property <code>Required</code> */
    public final static OdmaQName PROPERTY_REQUIRED = new OdmaQName("opendma", "Required");

    /** qualified name of the OpenDMA system property <code>ReadOnly</code> */
    public final static OdmaQName PROPERTY_READONLY = new OdmaQName("opendma", "ReadOnly");

    // Property Hidden already defined previously

    // Property System already defined previously

    // -----< class Repository >--------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Repository</code> */
    public final static OdmaQName CLASS_REPOSITORY = new OdmaQName("opendma", "Repository");

    // Property Name already defined previously

    // Property DisplayName already defined previously

    /** qualified name of the OpenDMA system property <code>RootClass</code> */
    public final static OdmaQName PROPERTY_ROOTCLASS = new OdmaQName("opendma", "RootClass");

    /** qualified name of the OpenDMA system property <code>RootAspects</code> */
    public final static OdmaQName PROPERTY_ROOTASPECTS = new OdmaQName("opendma", "RootAspects");

    /** qualified name of the OpenDMA system property <code>RootFolder</code> */
    public final static OdmaQName PROPERTY_ROOTFOLDER = new OdmaQName("opendma", "RootFolder");

    // -----< class Document >----------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Document</code> */
    public final static OdmaQName CLASS_DOCUMENT = new OdmaQName("opendma", "Document");

    /** qualified name of the OpenDMA system property <code>VersionSpecificId</code> */
    public final static OdmaQName PROPERTY_VERSIONSPECIFICID = new OdmaQName("opendma", "VersionSpecificId");

    /** qualified name of the OpenDMA system property <code>VersionSpecificGuid</code> */
    public final static OdmaQName PROPERTY_VERSIONSPECIFICGUID = new OdmaQName("opendma", "VersionSpecificGuid");

    /** qualified name of the OpenDMA system property <code>Title</code> */
    public final static OdmaQName PROPERTY_TITLE = new OdmaQName("opendma", "Title");

    /** qualified name of the OpenDMA system property <code>Version</code> */
    public final static OdmaQName PROPERTY_VERSION = new OdmaQName("opendma", "Version");

    /** qualified name of the OpenDMA system property <code>VersionCollection</code> */
    public final static OdmaQName PROPERTY_VERSIONCOLLECTION = new OdmaQName("opendma", "VersionCollection");

    /** qualified name of the OpenDMA system property <code>ContentElements</code> */
    public final static OdmaQName PROPERTY_CONTENTELEMENTS = new OdmaQName("opendma", "ContentElements");

    /** qualified name of the OpenDMA system property <code>CombinedMimeType</code> */
    public final static OdmaQName PROPERTY_COMBINEDMIMETYPE = new OdmaQName("opendma", "CombinedMimeType");

    /** qualified name of the OpenDMA system property <code>PrimaryContentElement</code> */
    public final static OdmaQName PROPERTY_PRIMARYCONTENTELEMENT = new OdmaQName("opendma", "PrimaryContentElement");

    /** qualified name of the OpenDMA system property <code>CheckedOut</code> */
    public final static OdmaQName PROPERTY_CHECKEDOUT = new OdmaQName("opendma", "CheckedOut");

    /** qualified name of the OpenDMA system property <code>CreatedAt</code> */
    public final static OdmaQName PROPERTY_CREATEDAT = new OdmaQName("opendma", "CreatedAt");

    /** qualified name of the OpenDMA system property <code>VersionCreatedAt</code> */
    public final static OdmaQName PROPERTY_VERSIONCREATEDAT = new OdmaQName("opendma", "VersionCreatedAt");

    /** qualified name of the OpenDMA system property <code>LastModifiedAt</code> */
    public final static OdmaQName PROPERTY_LASTMODIFIEDAT = new OdmaQName("opendma", "LastModifiedAt");

    /** qualified name of the OpenDMA system property <code>CheckedOutAt</code> */
    public final static OdmaQName PROPERTY_CHECKEDOUTAT = new OdmaQName("opendma", "CheckedOutAt");

    /** qualified name of the OpenDMA system property <code>CreatedBy</code> */
    public final static OdmaQName PROPERTY_CREATEDBY = new OdmaQName("opendma", "CreatedBy");

    /** qualified name of the OpenDMA system property <code>VersionCreatedBy</code> */
    public final static OdmaQName PROPERTY_VERSIONCREATEDBY = new OdmaQName("opendma", "VersionCreatedBy");

    /** qualified name of the OpenDMA system property <code>LastModifiedBy</code> */
    public final static OdmaQName PROPERTY_LASTMODIFIEDBY = new OdmaQName("opendma", "LastModifiedBy");

    /** qualified name of the OpenDMA system property <code>CheckedOutBy</code> */
    public final static OdmaQName PROPERTY_CHECKEDOUTBY = new OdmaQName("opendma", "CheckedOutBy");

    // -----< class ContentElement >----------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>ContentElement</code> */
    public final static OdmaQName CLASS_CONTENTELEMENT = new OdmaQName("opendma", "ContentElement");

    /** qualified name of the OpenDMA system property <code>Content</code> */
    public final static OdmaQName PROPERTY_CONTENT = new OdmaQName("opendma", "Content");

    /** qualified name of the OpenDMA system property <code>Size</code> */
    public final static OdmaQName PROPERTY_SIZE = new OdmaQName("opendma", "Size");

    /** qualified name of the OpenDMA system property <code>MimeType</code> */
    public final static OdmaQName PROPERTY_MIMETYPE = new OdmaQName("opendma", "MimeType");

    /** qualified name of the OpenDMA system property <code>FileName</code> */
    public final static OdmaQName PROPERTY_FILENAME = new OdmaQName("opendma", "FileName");

    // -----< class VersionCollection >-------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>VersionCollection</code> */
    public final static OdmaQName CLASS_VERSIONCOLLECTION = new OdmaQName("opendma", "VersionCollection");

    /** qualified name of the OpenDMA system property <code>Versions</code> */
    public final static OdmaQName PROPERTY_VERSIONS = new OdmaQName("opendma", "Versions");

    /** qualified name of the OpenDMA system property <code>Latest</code> */
    public final static OdmaQName PROPERTY_LATEST = new OdmaQName("opendma", "Latest");

    /** qualified name of the OpenDMA system property <code>Released</code> */
    public final static OdmaQName PROPERTY_RELEASED = new OdmaQName("opendma", "Released");

    /** qualified name of the OpenDMA system property <code>InProgress</code> */
    public final static OdmaQName PROPERTY_INPROGRESS = new OdmaQName("opendma", "InProgress");

    // -----< class Container >---------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Container</code> */
    public final static OdmaQName CLASS_CONTAINER = new OdmaQName("opendma", "Container");

    // Property Title already defined previously

    /** qualified name of the OpenDMA system property <code>Containees</code> */
    public final static OdmaQName PROPERTY_CONTAINEES = new OdmaQName("opendma", "Containees");

    /** qualified name of the OpenDMA system property <code>Associations</code> */
    public final static OdmaQName PROPERTY_ASSOCIATIONS = new OdmaQName("opendma", "Associations");

    // Property CreatedAt already defined previously

    // Property LastModifiedAt already defined previously

    // Property CreatedBy already defined previously

    // Property LastModifiedBy already defined previously

    // -----< class Folder >------------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Folder</code> */
    public final static OdmaQName CLASS_FOLDER = new OdmaQName("opendma", "Folder");

    // Property Parent already defined previously

    /** qualified name of the OpenDMA system property <code>SubFolders</code> */
    public final static OdmaQName PROPERTY_SUBFOLDERS = new OdmaQName("opendma", "SubFolders");

    // -----< class Containable >-------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Containable</code> */
    public final static OdmaQName CLASS_CONTAINABLE = new OdmaQName("opendma", "Containable");

    /** qualified name of the OpenDMA system property <code>ContainedIn</code> */
    public final static OdmaQName PROPERTY_CONTAINEDIN = new OdmaQName("opendma", "ContainedIn");

    /** qualified name of the OpenDMA system property <code>ContainedInAssociations</code> */
    public final static OdmaQName PROPERTY_CONTAINEDINASSOCIATIONS = new OdmaQName("opendma", "ContainedInAssociations");

    // -----< class Association >-------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Association</code> */
    public final static OdmaQName CLASS_ASSOCIATION = new OdmaQName("opendma", "Association");

    // Property Name already defined previously

    /** qualified name of the OpenDMA system property <code>Container</code> */
    public final static OdmaQName PROPERTY_CONTAINER = new OdmaQName("opendma", "Container");

    /** qualified name of the OpenDMA system property <code>Containment</code> */
    public final static OdmaQName PROPERTY_CONTAINMENT = new OdmaQName("opendma", "Containment");

    // Property CreatedAt already defined previously

    // Property LastModifiedAt already defined previously

    // Property CreatedBy already defined previously

    // Property LastModifiedBy already defined previously

    // =============================================================================================
    // TECHNICAL: empty private default constructor to prevent instantiation
    // =============================================================================================

    /**
     * Empty private constructor to prevent instantiation of this class.
     */
    private OdmaTypes()
    {
        // empty constructor
    }

}
