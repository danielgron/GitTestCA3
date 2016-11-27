/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;

/**
 *
 * @author Daniel
 */
public interface Eventable {
    public Event getEvent();
    public void setEvent(Event e);
}
