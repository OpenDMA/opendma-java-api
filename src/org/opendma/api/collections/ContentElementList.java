package org.opendma.api.collections;

import java.util.List;

import org.opendma.api.OdmaContentElement;

/**
 * Type safe version of the <code>List</code> interface for <i>OdmaContentElement</i> objects.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface ContentElementList extends List
{

    /**
     * Returns the <code>{@link OdmaContentElement}</code> at the specified position in
     * this list.
     * 
     * @param index
     *            position of the element to return
     * 
     * @return the <code>{@link OdmaContentElement}</code> element at the specified position in
     *         this list.
     * 
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (index < 0 || index >= size()).
     */
    public OdmaContentElement getContentElement(int index);

}
