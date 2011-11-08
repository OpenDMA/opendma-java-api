package org.opendma.impl.collections;

import java.util.ArrayList;
import org.opendma.api.collections.StringList;

/**
 * Implementation of the <code>{@link x}</code> interface based on an <code>ArrayList</code>.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class ArrayStringList extends ArrayList implements StringList
{

    /** serial version ID */
    private static final long serialVersionUID = -3190299231469094574L;

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
    public String getString(int index)
    {
        return (String)this.get(index);
    }

}
