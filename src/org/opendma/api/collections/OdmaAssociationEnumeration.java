package org.opendma.api.collections;

/**
 * Type safe version of the <code>OdmaObjectEnumeration</code> interface that
 * does only return objects implementing <code>OdmaAssociation</code> by the
 * Iterator.<br>
 * 
 * 
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public interface OdmaAssociationEnumeration extends OdmaObjectEnumeration
{

    /**
     * Returns the elements of this enumeration as <code>List</code>.<br>
     * This is possible since <i>document</i>s are known to have only a small number of <i>content element</i>s.
     * 
     * @return the elements of this enumeration as <code>List</code>
     */
    public ContentElementList asList();

}