/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unlimited.oj.util;

/**
 *
 * @author benQ
 */
public class RankColor {
    public static String getRankColor(int rank)
    {
        String color = "#606060";
        if(rank>=20000)
            color="darkorange";
        else if(rank>=10000)
            color="darkorange";
        else if(rank>=9000)
            color="red";
        else if(rank>=8000)
            color="darkred";
        else if(rank>=7000)
            color="purple";
        else if(rank>=6000)
            color="darkblue";
        else if(rank>=5000)
            color="blue";
        else if(rank>=4000)
            color="dimgray";
        else if(rank>=3000)
            color="navy";
        else if(rank>=2000)
            color="darkgreen";
        else if(rank>=1000)
            color="green";
        return color;
    }
}
