/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author danie
 */
public class DateNullException extends Exception {

    /**
     * Creates a new instance of <code>DateNullException</code> without detail
     * message.
     */
    public DateNullException() {
    }

    /**
     * Constructs an instance of <code>DateNullException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DateNullException(String msg) {
        super(msg);
    }

   
}
