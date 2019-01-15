/***********************************************************************
 * Module:  IOwner.java
 * Author:  benQ
 * Purpose: Defines the Interface IOwner
 ***********************************************************************/

package com.unlimited.oj.model;

import com.unlimited.oj.model.*;
import java.util.*;

public interface IOwner {
   /** @param username */
   boolean isOwner(java.lang.String username);

}