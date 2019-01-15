package com.unlimited.oj.service;

import java.util.List;

import com.unlimited.oj.model.LabelValue;
import com.unlimited.oj.model.Role;

/**
 * Business Service Interface to talk to persistence layer and
 * retrieve values for drop-down choice lists.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupManager extends GenericManager<Role, Long> {
    /**
     * Retrieves all possible roles from persistence layer
     * @return List of LabelValue objects
     */
    List<LabelValue> getAllRoles();
}
