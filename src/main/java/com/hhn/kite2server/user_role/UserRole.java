package com.hhn.kite2server.user_role;

public enum UserRole {

    STANDARD_USER,
    NOVEL_TESTER,
    AI_TESTER,
    TESTER,
    INTERN,
    SUPER_USER;

    public int toInt() {
        switch (this) {
            case STANDARD_USER -> { return 0; }
            case NOVEL_TESTER  -> { return 1; }
            case AI_TESTER     -> { return 2; }
            case TESTER        -> { return 3; }
            case INTERN        -> { return 4; }
            case SUPER_USER    -> { return 5; }
            default            -> { return -1; }
        }
    }
}
