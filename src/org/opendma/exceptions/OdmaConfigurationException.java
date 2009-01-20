package org.opendma.exceptions;

/**
 * Exception thrown whenever an OpenDMA implementation is misconfigured
 * and unable to perform the action requested by the user.
 *
 * @author Stefan Kopf, xaldon Technologies GmbH, the OpenDMA architecture board
 */
public class OdmaConfigurationException extends Exception
{
    
    /** the serial version ID required for serialization */
    private static final long serialVersionUID = -7369113366370643820L;

    /**
     * Create a new <code>OdmaConfigurationException</code> with the given parameters.
     * 
     * @param msg a message string for the user
     */
    public OdmaConfigurationException(String msg)
    {
        super( msg );
    }

    /**
     * Create a new <code>OdmaConfigurationException</code> with the given parameters.
     * 
     * @param msg a message string for the user
     * @param t the <code>Throwable</code> that caused this Exception
     */
    public OdmaConfigurationException(String msg, Throwable t)
    {
        super( msg, t );
    }

}