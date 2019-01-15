package com.unlimited.oj.enums;

public enum SexType {
	UNKNOWN("enums.SexType.UNKNOWN", 0),
	MALE("enums.SexType.MALE", 1),
	FEMALE("enums.SexType.FEMALE", 2);
	
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

    SexType(String name, int value)
    {
        this.name = name;
        this.value = value;
    }

    public static SexType getSexType(int i)
    {
        switch (i)
        {
            case 1:
                return MALE;
            case 2:
                return FEMALE;
            default:
            	return UNKNOWN;
        }
    }
}
