package org.opendma.api.collections;


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
public interface OdmaContentElementEnumeration extends OdmaObjectEnumeration
{

}
