package com.hhn.kite2server.response;

public enum ResultCode {
    FAILURE,
    NOT_AUTHORIZED,
    SUCCESSFULLY_GOT_COMPLETION,
    SUCCESSFULLY_GOT_ALL_DATA_OBJECTS,
    SUCCESSFULLY_ADDED_DATA_OBJECT,
    SUCCESSFULLY_DELETED_DATA_OBJECT,
    NO_SUCH_DATA_OBJECT;

    public int toInt() {
        System.out.println("Resultcode: " + this);
        switch (this) {
            case FAILURE -> {
                return 1;
            }
            case NOT_AUTHORIZED -> {
                return 2;
            }
            case SUCCESSFULLY_GOT_COMPLETION -> {
                return 3;
            }
            case SUCCESSFULLY_GOT_ALL_DATA_OBJECTS -> {
                return 19;
            }
            case SUCCESSFULLY_ADDED_DATA_OBJECT -> {
                return 20;
            }
            case SUCCESSFULLY_DELETED_DATA_OBJECT -> {
                return 21;
            }
            case NO_SUCH_DATA_OBJECT -> {
                return 22;
            }
            default -> {
                return -1;
            }
        }
    }
}
