package org.opendma.api.collections;

import java.util.Iterator;

/**
 * The content of a multi-valued <code>reference</code> property in OpenDMA.<br>
 * While scalar multi-value properties typically contain a limited number of
 * items, reference properties might contain very large numbers of items. So
 * they can only be accessed by an <code>Iterator</code> over all contained
 * objects.<br>
 * An implementation of OpenDMA should load the items on demand in pages from
 * the server instead of retrieving all ietms at once.
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaDocumentEnumeration
{

    /**
     * Returns an iterator over all <code>OdmaDocument</code> elements.
     * 
     * @return an iterator over all <code>OdmaDocument</code> elements.
     */
    public Iterator iterator();

    /**
     * Returns <code>true</code> if and only if the collection is empty, i.e.
     * <code>iterator().hasNext()</code> returns <code>false</code>.
     * 
     * @return <code>true</code> if and only if the collection is empty.
     */
    public boolean isEmpty();

}
