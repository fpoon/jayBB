package com.fpoon.jaybb.constant;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Map;
import java.util.Set;

public class UserRoles {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String MODERATOR = "ROLE_MODERATOR";
    public static final String USER = "ROLE_USER";

    public static final Set<String> adminRoles     = ImmutableSet.of(ADMIN, MODERATOR, USER);
    public static final Set<String> moderatorRoles = ImmutableSet.of(MODERATOR, USER);
    public static final Set<String> userRoles      = ImmutableSet.of(USER);

    public static final Map<String, Set<String>> roles  = ImmutableMap.of(
            ADMIN, adminRoles,
            MODERATOR, moderatorRoles,
            USER, userRoles
    );
}
