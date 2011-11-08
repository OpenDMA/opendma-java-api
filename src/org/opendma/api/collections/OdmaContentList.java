package org.opendma.api.collections;

import java.util.List;
import org.opendma.api.OdmaContent;

/**
 * Type safe version of the <code>List</code> interface for the <i>Content</i>
 * data type.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaContentList extends List
{

    /**
     * Returns the <code>OdmaContent</code> element at the specified position in
     * this list.
     * 
     * @param index
     *            position of the element to return
     * 
     * @return the <code>OdmaContent</code> element at the specified position in
     *         this list.
     * 
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (index < 0 || index >= size()).
     */
    public OdmaContent getContent(int index);

}
