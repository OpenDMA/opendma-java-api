package org.opendma.api.collections;

import java.util.List;

/**
 * Type safe version of the <code>List</code> interface for the <i>String</i>
 * data type.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface StringList extends List
{

    /**
     * Returns the <code>String</code> element at the specified position in
     * this list.
     * 
     * @param index
     *            position of the element to return
     * 
     * @return the <code>String</code> element at the specified position in
     *         this list.
     * 
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (index < 0 || index >= size()).
     */
    public String getString(int index);

}
