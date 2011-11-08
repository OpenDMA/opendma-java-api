package org.opendma.api.collections;

import java.util.List;
import java.util.Date;

/**
 * Type safe version of the <code>List</code> interface for the <i>DateTime</i>
 * data type.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface DateTimeList extends List
{

    /**
     * Returns the <code>Date</code> element at the specified position in
     * this list.
     * 
     * @param index
     *            position of the element to return
     * 
     * @return the <code>Date</code> element at the specified position in
     *         this list.
     * 
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (index < 0 || index >= size()).
     */
    public Date getDateTime(int index);

}
