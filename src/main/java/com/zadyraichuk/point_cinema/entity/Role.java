package com.zadyraichuk.point_cinema.entity;

/**
 * Represents the role of a user in the system.
 * Defines the user's level of access and permissions.
 */
public enum Role {

    /**
     * Regular user with standard permissions.
     * Has read access to main cinema represented entities, read/write access to personal User entity.
     */
    USER,

    /**
     * Administrator with elevated privileges.
     * Has full read/write access to any entity.
     */
    ADMIN,

    /**
     * Cinema worker with specific operational permissions.
     * Has read/write access to main cinema represented entities, has no access to any User entity.
     */
    WORKER;
}
