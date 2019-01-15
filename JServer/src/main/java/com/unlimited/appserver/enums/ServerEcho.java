package com.unlimited.appserver.enums;

/**
echo	说明
0	操作正常结束
1	参数错误
2	返回结果为NULL
3	Token错误
4	不够权限
5	没有该用户
6	密码错误
9999	未知错误
 */
public enum ServerEcho
{
    Success("enums.ServerEcho.Success", 0),
    ParemeterError("enums.ServerEcho.ParemeterError", 1),
    IsNull("enums.ServerEcho.IsNull", 2),
    TokenWrong("enums.ServerEcho.TokenWrong", 3),
    NoAccess("enums.ServerEcho.NoAccess", 4),
    NoUser("enums.ServerEcho.NoUser", 5),
    WrongPassword("enums.ServerEcho.WrongPassword", 6),
    Unknown("enums.ServerEcho.Unknown", 9999);

    private String name;
    private int value;

    public String getName()
    {
        return name;
    }

    public String getToken()
    {
        return name.substring(name.lastIndexOf('.') + 1);
    }

    public int getValue()
    {
        return value;
    }

    ServerEcho(String name, int value)
    {
        this.name = name;
        this.value = value;
    }

    public static ServerEcho getServerEcho(int i)
    {
        switch (i)
        {
            case 0:
                return Success;
            case 1:
                return ParemeterError;
            case 2:
                return IsNull;
            case 3:
                return TokenWrong;
            case 4:
                return NoAccess;
            case 5:
            	return NoUser;
            case 6:
            	return WrongPassword;
            case 7:
            	return Unknown;
            default:
                return null;
        }
    }
}
