package org.opendma.impl.collections;

import java.util.ArrayList;
import org.opendma.api.collections.LongList;

/**
 * Implementation of the <code>{@link x}</code> interface based on an <code>ArrayList</code>.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class ArrayLongList extends ArrayList implements LongList
{

    /** serial version ID */
    private static final long serialVersionUID = -3190299231469094574L;

    /**
     * Returns the <code>Long</code> element at the specified position in
     * this list.
     * 
     * @param index
     *            position of the element to return
     * 
     * @return the <code>Long</code> element at the specified position in
     *         this list.
     * 
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (index < 0 || index >= size()).
     */
    public Long getLong(int index)
    {
        return (Long)this.get(index);
    }

}
