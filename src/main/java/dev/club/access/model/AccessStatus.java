package dev.club.access.model;

public enum AccessStatus {
    /** QR найден, вход разрешён. */
    GRANTED,
    /** QR не зарегистрирован в системе. */
    UNKNOWN,
    /** QR уже был использован для входа. */
    ALREADY_USED
}
