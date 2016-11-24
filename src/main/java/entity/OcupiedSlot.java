/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author Daniel
 */
public interface OcupiedSlot {
    public Date getStart();
    public Date getEnd();
    public boolean isAllDay();
}
