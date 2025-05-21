package org.opendma.api;

/**
 * OpenDMA property data types.
 */
public enum OdmaType {

    STRING(1),
    INTEGER(2),
    SHORT(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    BOOLEAN(7),
    DATETIME(8),
    BLOB(9),
    REFERENCE(10),
    CONTENT(11),
    ID(100),
    GUID(101);

    private final int numericId;

    private OdmaType(int numericId) {
        this.numericId = numericId;
    }

    public int getNumericId() {
        return numericId;
    }

    public static OdmaType fromNumericId(int numericId) {
        for (OdmaType val : OdmaType.values()) {
            if (val.getNumericId() == numericId) {
                return val;
            }
        }
        throw new IllegalArgumentException("Unknown numericId " + numericId);
    }

    /**
     * Returns the OdmaType corresponding to the given name, ignoring case.
     *
     * @param name the name of the OdmaType (case-insensitive)
     * @return the matching OdmaType
     * @throws IllegalArgumentException if no matching OdmaType exists
     */
    public static OdmaType fromString(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("OdmaType name must not be null or empty");
        }
        for (OdmaType type : OdmaType.values()) {
            if (type.name().equalsIgnoreCase(name.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown OdmaType name: " + name);
    }

}
