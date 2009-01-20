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
    // Definition of the numeric data type id as defined in §2.5
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
     * Numeric type identifier of the data type <code>BLOB</code>.<br>
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
     * First defined numeric type identifier.<br>
     * Value is 1.
     */
    public final static int MIN_TYPE = TYPE_STRING;

    /**
     * Last defined numeric type identifier.<br>
     * Value is 11.
     */
    public final static int MAX_TYPE = TYPE_CONTENT;

    // =============================================================================================
    // Definition of the system classes of Section I.2
    // =============================================================================================

    // -----< class Object
    // >------------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Object</code> */
    public final static OdmaQName CLASS_OBJECT = new OdmaQName("opendma.org", "Object");

    /** qualified name of the OpenDMA system property <code>Class</code> */
    public final static OdmaQName PROPERTY_CLASS = new OdmaQName("opendma.org", "Class");

    /** qualified name of the OpenDMA system property <code>ID</code> */
    public final static OdmaQName PROPERTY_ID = new OdmaQName("opendma.org", "ID");

    /** qualified name of the OpenDMA system property <code>Repository</code> */
    public final static OdmaQName PROPERTY_REPOSITORY = new OdmaQName("opendma.org", "Repository"); // defined
                                                                                                    // in
                                                                                                    // Sextion
                                                                                                    // II.1

    // -----< class Class
    // >-------------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Class</code> */
    public final static OdmaQName CLASS_CLASS = new OdmaQName("opendma.org", "Class");

    /** qualified name of the OpenDMA system property <code>Name</code> */
    public final static OdmaQName PROPERTY_NAME = new OdmaQName("opendma.org", "Name");

    /** qualified name of the OpenDMA system property <code>NameQualifier</code> */
    public final static OdmaQName PROPERTY_NAMEQUALIFIER = new OdmaQName("opendma.org", "NameQualifier");

    /** qualified name of the OpenDMA system property <code>DisplayName</code> */
    public final static OdmaQName PROPERTY_DISPLAYNAME = new OdmaQName("opendma.org", "DisplayName");

    /** qualified name of the OpenDMA system property <code>Parent</code> */
    public final static OdmaQName PROPERTY_PARENT = new OdmaQName("opendma.org", "Parent");

    /** qualified name of the OpenDMA system property <code>Aspects</code> */
    public final static OdmaQName PROPERTY_ASPECTS = new OdmaQName("opendma.org", "Aspects");

    /** qualified name of the OpenDMA system property <code>Properties</code> */
    public final static OdmaQName PROPERTY_PROPERTIES = new OdmaQName("opendma.org", "Properties");

    /** qualified name of the OpenDMA system property <code>IsInstantiable</code> */
    public final static OdmaQName PROPERTY_ISINSTANTIABLE = new OdmaQName("opendma.org", "IsInstantiable");

    /** qualified name of the OpenDMA system property <code>IsHidden</code> */
    public final static OdmaQName PROPERTY_ISHIDDEN = new OdmaQName("opendma.org", "IsHidden");

    /** qualified name of the OpenDMA system property <code>IsSystem</code> */
    public final static OdmaQName PROPERTY_ISSYSTEM = new OdmaQName("opendma.org", "IsSystem");

    /** qualified name of the OpenDMA system property <code>SubClasses</code> */
    public final static OdmaQName PROPERTY_SUBCLASSES = new OdmaQName("opendma.org", "SubClasses");

    // -----< class Property
    // >----------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>PropertyInfo</code> */
    public final static OdmaQName CLASS_PROPERTYINFO = new OdmaQName("opendma.org", "PropertyInfo");

    // Name already defined above

    // NameQualifier already defined above

    // DisplayName already defined above

    /** qualified name of the OpenDMA system property <code>DataType</code> */
    public final static OdmaQName PROPERTY_DATATYPE = new OdmaQName("opendma.org", "DataType");

    /** qualified name of the OpenDMA system property <code>ReferenceClass</code> */
    public final static OdmaQName PROPERTY_REFERENCECLASS = new OdmaQName("opendma.org", "ReferenceClass");

    /** qualified name of the OpenDMA system property <code>Multivalue</code> */
    public final static OdmaQName PROPERTY_MULTIVALUE = new OdmaQName("opendma.org", "Multivalue");

    /** qualified name of the OpenDMA system property <code>IsRequired</code> */
    public final static OdmaQName PROPERTY_ISREQUIRED = new OdmaQName("opendma.org", "IsRequired");

    // IsHidden already defined above

    /** qualified name of the OpenDMA system property <code>IsReadonly</code> */
    public final static OdmaQName PROPERTY_ISREADONLY = new OdmaQName("opendma.org", "IsReadonly");

    // IsSystem already defined above

    // =============================================================================================
    // Definition of the system classes of Section II
    // =============================================================================================

    // -----< class Repository
    // >--------------------------------------------------------------------

    /** qualified name of the OpenDMA system class <code>Repository</code> */
    public final static OdmaQName CLASS_REPOSITORY = new OdmaQName("opendma.org", "Repository");

    // Name already defined above

    // DisplayName already defined above

    /** qualified name of the OpenDMA system property <code>RootClass</code> */
    public final static OdmaQName PROPERTY_ROOTCLASS = new OdmaQName("opendma.org", "RootClass");

    /** qualified name of the OpenDMA system property <code>RootFolder</code> */
    public final static OdmaQName PROPERTY_ROOTFOLDER = new OdmaQName("opendma.org", "RootFolder");

    // -----< aspect Document
    // >---------------------------------------------------------------------

    /** qualified name of the OpenDMA system aspect <code>Document</code> */
    public final static OdmaQName ASPECT_DOCUMENT = new OdmaQName("opendma.org", "Document");

    /** qualified name of the OpenDMA system property <code>Title</code> */
    public final static OdmaQName PROPERTY_TITLE = new OdmaQName("opendma.org", "Title");

    /** qualified name of the OpenDMA system property <code>Version</code> */
    public final static OdmaQName PROPERTY_VERSION = new OdmaQName("opendma.org", "Version");

    /**
     * qualified name of the OpenDMA system property
     * <code>VersionCollection</code>
     */
    public final static OdmaQName PROPERTY_VERSIONCOLLECTION = new OdmaQName("opendma.org", "VersionCollection");

    /**
     * qualified name of the OpenDMA system property
     * <code>ContentElements</code>
     */
    public final static OdmaQName PROPERTY_CONTENTELEMENTS = new OdmaQName("opendma.org", "ContentElements");

    /**
     * qualified name of the OpenDMA system property
     * <code>CombinedMimeType</code>
     */
    public final static OdmaQName PROPERTY_COMBINEDMIMETYPE = new OdmaQName("opendma.org", "CombinedMimeType");

    /**
     * qualified name of the OpenDMA system property
     * <code>PrimaryContentElement</code>
     */
    public final static OdmaQName PROPERTY_PRIMARYCONTENTELEMENT = new OdmaQName("opendma.org", "PrimaryContentElement");

    /** qualified name of the OpenDMA system property <code>PrimaryContent</code> */
    public final static OdmaQName PROPERTY_PRIMARYCONTENT = new OdmaQName("opendma.org", "PrimaryContent");

    /**
     * qualified name of the OpenDMA system property
     * <code>PrimaryMimeType</code>
     */
    public final static OdmaQName PROPERTY_PRIMARYMIMETYPE = new OdmaQName("opendma.org", "PrimaryMimeType");

    /** qualified name of the OpenDMA system property <code>PrimarySize</code> */
    public final static OdmaQName PROPERTY_PRIMARYSIZE = new OdmaQName("opendma.org", "PrimarySize");

    /**
     * qualified name of the OpenDMA system property
     * <code>PrimaryFileName</code>
     */
    public final static OdmaQName PROPERTY_PRIMARYFILENAME = new OdmaQName("opendma.org", "PrimaryFileName");

    /** qualified name of the OpenDMA system property <code>IsCheckedOut</code> */
    public final static OdmaQName PROPERTY_ISCHECKEDOUT = new OdmaQName("opendma.org", "IsCheckedOut");

    /** qualified name of the OpenDMA system property <code>CreatedAt</code> */
    public final static OdmaQName PROPERTY_CREATEDAT = new OdmaQName("opendma.org", "CreatedAt");

    /** qualified name of the OpenDMA system property <code>LastModifiedAt</code> */
    public final static OdmaQName PROPERTY_LASTMODIFIEDAT = new OdmaQName("opendma.org", "LastModifiedAt");

    /** qualified name of the OpenDMA system property <code>CeckedOutAt</code> */
    public final static OdmaQName PROPERTY_CHECKEDOUTAT = new OdmaQName("opendma.org", "CheckedOutAt");

    /** qualified name of the OpenDMA system property <code>CreatedBy</code> */
    public final static OdmaQName PROPERTY_CREATEDBY = new OdmaQName("opendma.org", "CreatedBy");

    /** qualified name of the OpenDMA system property <code>LastModifiedBy</code> */
    public final static OdmaQName PROPERTY_LASTMODIFIEDBY = new OdmaQName("opendma.org", "LastModifiedBy");

    /** qualified name of the OpenDMA system property <code>CheckedOutBy</code> */
    public final static OdmaQName PROPERTY_CHECKEDOUTBY = new OdmaQName("opendma.org", "CheckedOutBy");

    // -----< aspect ContentElement
    // >---------------------------------------------------------------

    /** qualified name of the OpenDMA system aspect <code>ContentElement</code> */
    public final static OdmaQName ASPECT_CONTENTELEMENT = new OdmaQName("opendma.org", "ContentElement");

    /** qualified name of the OpenDMA system property <code>Content</code> */
    public final static OdmaQName PROPERTY_CONTENT = new OdmaQName("opendma.org", "Content");

    /** qualified name of the OpenDMA system property <code>Size</code> */
    public final static OdmaQName PROPERTY_SIZE = new OdmaQName("opendma.org", "Size");

    /** qualified name of the OpenDMA system property <code>MimeType</code> */
    public final static OdmaQName PROPERTY_MIMETYPE = new OdmaQName("opendma.org", "MimeType");

    /** qualified name of the OpenDMA system property <code>FileName</code> */
    public final static OdmaQName PROPERTY_FILENAME = new OdmaQName("opendma.org", "FileName");

    // -----< aspect Folder
    // >-----------------------------------------------------------------------

    /** qualified name of the OpenDMA system aspect <code>Folder</code> */
    public final static OdmaQName ASPECT_FOLDER = new OdmaQName("opendma.org", "Folder");

    // Title already defined above

    /** qualified name of the OpenDMA system property <code>Containees</code> */
    public final static OdmaQName PROPERTY_CONTAINEES = new OdmaQName("opendma.org", "Containees");

    /** qualified name of the OpenDMA system property <code>Associations</code> */
    public final static OdmaQName PROPERTY_ASSOCIATIONS = new OdmaQName("opendma.org", "Associations");

    // CreatedAt already defined above

    // LastModifiedAt already defined above

    // CreatedBy already defined above

    // LastModifiedBy already defined above

    // -----< aspect Association
    // >------------------------------------------------------------------

    /** qualified name of the OpenDMA system aspect <code>Association</code> */
    public final static OdmaQName ASPECT_ASSOCIATION = new OdmaQName("opendma.org", "Association");

    // Name already defined above

    /** qualified name of the OpenDMA system property <code>Source</code> */
    public final static OdmaQName PROPERTY_SOURCE = new OdmaQName("opendma.org", "Source");

    /** qualified name of the OpenDMA system property <code>Destination</code> */
    public final static OdmaQName PROPERTY_DESTINATION = new OdmaQName("opendma.org", "Destination");

    // CreatedAt already defined above

    // LastModifiedAt already defined above

    // CreatedBy already defined above

    // LastModifiedBy already defined above

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