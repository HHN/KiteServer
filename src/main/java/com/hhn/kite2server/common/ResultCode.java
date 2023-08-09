package com.hhn.kite2server.common;

public enum ResultCode {
    NONE,
    SUCCESS,
    FAILURE,
    SUCCESSFULLY_REGISTERED_NEW_USER,
    USERNAME_ALREADY_TAKEN,
    EMAIL_ALREADY_REGISTERED,
    INVALID_EMAIL,
    TOKEN_NOT_FOUND,
    EMAIL_ALREADY_CONFIRMED,
    TOKEN_EXPIRED,
    SUCCESSFULLY_CONFIRMED,
    SUCCESSFULLY_LOGGED_IN,
    SUCCESSFULLY_LOGGED_OUT,
    INVALID_CREDENTIALS,
    SUCCESSFULLY_POSTED_NOVEL,
    LOG_OUT_FAILED,
    NOT_AUTHORIZED,
    EMAIL_NOT_CONFIRMED,
    NO_NOVEL_AVAILABLE,
    SUCCESSFULLY_GOT_NOVELS,
    SUCCESSFULLY_GOT_COMPLETION,
    NOVEL_NOT_FOUND,
    SUCCESSFULLY_DELETED_NOVEL,
    USER_NOT_FOUND,
    SUCCESSFULLY_DELETED_USER,
    SUCCESSFULLY_CHANGED_PASSWORD,
    CHANGE_OF_PASSWORD_FAILED;

    public int toInt() {
        switch (this) {
            case SUCCESS                            -> { return 1; }
            case FAILURE                            -> { return 2; }
            case SUCCESSFULLY_REGISTERED_NEW_USER   -> { return 3; }
            case USERNAME_ALREADY_TAKEN             -> { return 4; }
            case EMAIL_ALREADY_REGISTERED           -> { return 5; }
            case INVALID_EMAIL                      -> { return 6; }
            case TOKEN_NOT_FOUND                    -> { return 7; }
            case EMAIL_ALREADY_CONFIRMED            -> { return 8; }
            case TOKEN_EXPIRED                      -> { return 9; }
            case SUCCESSFULLY_CONFIRMED             -> { return 10;}
            case SUCCESSFULLY_LOGGED_IN             -> { return 11;}
            case SUCCESSFULLY_LOGGED_OUT            -> { return 12;}
            case INVALID_CREDENTIALS                -> { return 13;}
            case SUCCESSFULLY_POSTED_NOVEL          -> { return 14;}
            case LOG_OUT_FAILED                     -> { return 15;}
            case NOT_AUTHORIZED                     -> { return 16;}
            case EMAIL_NOT_CONFIRMED                -> { return 17;}
            case NO_NOVEL_AVAILABLE                 -> { return 18;}
            case SUCCESSFULLY_GOT_NOVELS            -> { return 19;}
            case SUCCESSFULLY_GOT_COMPLETION        -> { return 20;}
            case NOVEL_NOT_FOUND                    -> { return 21;}
            case SUCCESSFULLY_DELETED_NOVEL         -> { return 22;}
            case USER_NOT_FOUND                     -> { return 23;}
            case SUCCESSFULLY_DELETED_USER          -> { return 24;}
            case SUCCESSFULLY_CHANGED_PASSWORD      -> { return 25;}
            case CHANGE_OF_PASSWORD_FAILED          -> { return 26;}
            default                                 -> { return -1;}
        }
    }

    public ResultCode valueOf(int i) {
        switch (i) {
            case 1      -> { return SUCCESS; }
            case 2      -> { return FAILURE; }
            case 3      -> { return SUCCESSFULLY_REGISTERED_NEW_USER; }
            case 4      -> { return USERNAME_ALREADY_TAKEN; }
            case 5      -> { return EMAIL_ALREADY_REGISTERED; }
            case 6      -> { return INVALID_EMAIL; }
            case 7      -> { return TOKEN_NOT_FOUND; }
            case 8      -> { return EMAIL_ALREADY_CONFIRMED; }
            case 9      -> { return TOKEN_EXPIRED; }
            case 10     -> { return SUCCESSFULLY_CONFIRMED; }
            case 11     -> { return SUCCESSFULLY_LOGGED_IN; }
            case 12     -> { return SUCCESSFULLY_LOGGED_OUT; }
            case 13     -> { return INVALID_CREDENTIALS; }
            case 14     -> { return SUCCESSFULLY_POSTED_NOVEL; }
            case 15     -> { return LOG_OUT_FAILED; }
            case 16     -> { return NOT_AUTHORIZED; }
            case 17     -> { return EMAIL_NOT_CONFIRMED; }
            case 18     -> { return NO_NOVEL_AVAILABLE; }
            case 19     -> { return SUCCESSFULLY_GOT_NOVELS; }
            case 20     -> { return SUCCESSFULLY_GOT_COMPLETION; }
            case 21     -> { return NOVEL_NOT_FOUND; }
            case 22     -> { return SUCCESSFULLY_DELETED_NOVEL; }
            case 23     -> { return USER_NOT_FOUND; }
            case 24     -> { return SUCCESSFULLY_DELETED_USER; }
            case 25     -> { return SUCCESSFULLY_CHANGED_PASSWORD; }
            case 26     -> { return CHANGE_OF_PASSWORD_FAILED; }
            default     -> { return NONE; }
        }
    }
}
