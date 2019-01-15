/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unlimited.oj.util;

import java.util.*;

/**
 *
 * @author benQ
 */
public class PasswordContainer extends TreeSet<String>{
    private long fetchTime;
    private Set<String> passwds;

    public PasswordContainer ()
    {
        fetchTime = System.currentTimeMillis();
    }

    @Override
    public boolean add(String e)
    {
        fetchTime = System.currentTimeMillis();
        return super.add(e);
    }

    @Override
    public boolean contains(Object o)
    {
        fetchTime = System.currentTimeMillis();
        return super.contains(o);
    }
}
