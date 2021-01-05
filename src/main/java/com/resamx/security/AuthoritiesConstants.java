package com.resamx.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String SUPERVISOR = "ROLE_SUPERVISOR";
    
    public static final String CLIENTE = "ROLE_CLIENTE";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
