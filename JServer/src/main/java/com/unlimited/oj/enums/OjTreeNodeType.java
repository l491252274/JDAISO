/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unlimited.oj.enums;

/**
 *
 * @author benQ
 */
public enum OjTreeNodeType {
	UNKNOWN("enums.OjTreeNodeType.UNKNOWN", 0),
	CATALOG("enums.OjTreeNodeType.CATALOG", 1),
	PROBLEMNODE("enums.OjTreeNodeType.PROBLEMNODE", 100),
	BOARDNODE("enums.OjTreeNodeType.BOARDNODE", 101),
	EXAMNODE("enums.OjTreeNodeType.EXAMNODE", 102);
	
    private String name;
    private int value;

    public String getName()
    {
        return name;
    }

    public int getValue()
    {
        return value;
    }

    OjTreeNodeType(String name, int value)
    {
        this.name = name;
        this.value = value;
    }

    public static OjTreeNodeType getOjTreeNodeType(int i)
    {
        switch (i)
        {
            case 1:
                return CATALOG;
            case 100:
                return PROBLEMNODE;
            case 101:
                return BOARDNODE;
            case 102:
            	return EXAMNODE;
            default:
            	return UNKNOWN;
        }
    }
}
