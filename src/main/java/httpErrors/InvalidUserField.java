/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpErrors;

/**
 *
 * @author Daniel
 */
public class InvalidUserField extends Exception {

    /**
     * Creates a new instance of <code>InvalidUserField</code> without detail
     * message.
     */
    public InvalidUserField() {
    }

    /**
     * Constructs an instance of <code>InvalidUserField</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidUserField(String msg) {
        super(msg);
    }
}
