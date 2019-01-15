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
public class PasswordStore {
    private static Hashtable<Long, PasswordContainer> passwordStore = null;

    static
    {
        passwordStore = new Hashtable<Long, PasswordContainer>();
    }

    public static void addPasswordContainer(Long key, PasswordContainer pc)
    {
        if (pc!=null)
            passwordStore.put(key, pc);
    }

    public static PasswordContainer getPasswordContainer(Long key)
    {
        return passwordStore.get(key);
    }

    public static void removePasswordContainer(Long key)
    {
        passwordStore.remove(key);
    }

    private static void collectUsless()
    {
        long now = System.currentTimeMillis();
        List<PasswordContainer> list = new ArrayList<PasswordContainer>();
    }
}
