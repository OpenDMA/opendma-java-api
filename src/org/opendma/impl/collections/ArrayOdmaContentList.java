package org.opendma.impl.collections;

import java.util.ArrayList;
import org.opendma.api.OdmaContent;
import org.opendma.api.collections.OdmaContentList;

/**
 * Implementation of the <code>{@link x}</code> interface based on an <code>ArrayList</code>.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class ArrayOdmaContentList extends ArrayList implements OdmaContentList
{

    /** serial version ID */
    private static final long serialVersionUID = -3190299231469094574L;

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
    public OdmaContent getContent(int index)
    {
        return (OdmaContent)this.get(index);
    }

}
