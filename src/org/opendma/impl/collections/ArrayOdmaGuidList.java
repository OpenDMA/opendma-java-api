package org.opendma.impl.collections;

import java.util.ArrayList;
import org.opendma.api.OdmaGuid;
import org.opendma.api.collections.OdmaGuidList;

/**
 * Implementation of the <code>{@link x}</code> interface based on an <code>ArrayList</code>.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class ArrayOdmaGuidList extends ArrayList implements OdmaGuidList
{

    /** serial version ID */
    private static final long serialVersionUID = -3190299231469094574L;

    /**
     * Returns the <code>OdmaGuid</code> element at the specified position in
     * this list.
     * 
     * @param index
     *            position of the element to return
     * 
     * @return the <code>OdmaGuid</code> element at the specified position in
     *         this list.
     * 
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (index < 0 || index >= size()).
     */
    public OdmaGuid getGuid(int index)
    {
        return (OdmaGuid)this.get(index);
    }

}
