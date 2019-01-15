package com.unlimited.oj.iface;

import com.unlimited.oj.model.*;
import java.util.*;

public interface IOwner {
   /** @param username */
   boolean isOwner(java.lang.String username);
   boolean isAuthor(java.lang.String username);
}