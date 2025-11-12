package com.hhn.kite2server.response;

public enum ResultCode {
    FAILURE,
    NOT_AUTHORIZED,
    SUCCESSFULLY_GOT_COMPLETION,
    SUCCESSFULLY_GOT_ALL_NOVEL_REVIEWS,
    SUCCESSFULLY_ADDED_NOVEL_REVIEW,
    SUCCESSFULLY_DELETED_NOVEL_REVIEW,
    NO_SUCH_NOVEL_REVIEW,
    SUCCESSFULLY_GOT_ALL_AI_REVIEWS,
    SUCCESSFULLY_ADDED_AI_REVIEW,
    SUCCESSFULLY_DELETED_AI_REVIEW,
    NO_SUCH_AI_REVIEW,
    SUCCESSFULLY_GOT_ALL_REVIEW_OBSERVER,
    SUCCESSFULLY_ADDED_REVIEW_OBSERVER,
    SUCCESSFULLY_DELETED_REVIEW_OBSERVER,
    NO_SUCH_REVIEW_OBSERVER,
    REVIEW_OBSERVER_ALREADY_EXISTS,
    SUCCESSFULLY_GOT_VERSION,
    SUCCESSFULLY_GOT_USER_ROLE,
    SUCCESSFULLY_GOT_ALL_DATA_OBJECTS,
    SUCCESSFULLY_ADDED_DATA_OBJECT,
    SUCCESSFULLY_DELETED_DATA_OBJECT,
    NO_SUCH_DATA_OBJECT,
    SUCCESSFULLY_POSTET_EXPERT_FEEDBACK_QUESTION,
    SUCCESSFULLY_DELETED_EXPERT_FEEDBACK_QUESTION,
    SUCCESSFULLY_FOUND_EXPERT_FEEDBACK_QUESTION,
    NO_SUCH_EXPERT_FEEDBACK_QUESTION,
    SUCCESSFULLY_POSTET_EXPERT_FEEDBACK_ANSWER,
    SUCCESSFULLY_DELETED_EXPERT_FEEDBACK_ANSWER,
    SUCCESSFULLY_FOUND_EXPERT_FEEDBACK_ANSWER,
    NO_SUCH_EXPERT_FEEDBACK_ANSWER,
    SUCCESSFULLY_GOT_ALL_EXPERT_FEEDBACK_QUESTIONS,
    SUCCESSFULLY_GOT_ALL_EXPERT_FEEDBACK_ANSWERS;

    public int toInt() {
        System.out.println("Resultcode: " + this);
        switch (this) {
            case FAILURE                                        -> { return 1; }
            case NOT_AUTHORIZED                                 -> { return 2; }
            case SUCCESSFULLY_GOT_COMPLETION                    -> { return 3; }
            case SUCCESSFULLY_GOT_ALL_NOVEL_REVIEWS             -> { return 4; }
            case SUCCESSFULLY_ADDED_NOVEL_REVIEW                -> { return 5; }
            case SUCCESSFULLY_DELETED_NOVEL_REVIEW              -> { return 6; }
            case NO_SUCH_NOVEL_REVIEW                           -> { return 7; }
            case SUCCESSFULLY_GOT_ALL_AI_REVIEWS                -> { return 8; }
            case SUCCESSFULLY_ADDED_AI_REVIEW                   -> { return 9; }
            case SUCCESSFULLY_DELETED_AI_REVIEW                 -> { return 10;}
            case NO_SUCH_AI_REVIEW                              -> { return 11;}
            case SUCCESSFULLY_GOT_ALL_REVIEW_OBSERVER           -> { return 12;}
            case SUCCESSFULLY_ADDED_REVIEW_OBSERVER             -> { return 13;}
            case SUCCESSFULLY_DELETED_REVIEW_OBSERVER           -> { return 14;}
            case NO_SUCH_REVIEW_OBSERVER                        -> { return 15;}
            case REVIEW_OBSERVER_ALREADY_EXISTS                 -> { return 16;}
            case SUCCESSFULLY_GOT_ALL_DATA_OBJECTS              -> { return 19;}
            case SUCCESSFULLY_ADDED_DATA_OBJECT                 -> { return 20;}
            case SUCCESSFULLY_DELETED_DATA_OBJECT               -> { return 21;}
            case NO_SUCH_DATA_OBJECT                            -> { return 22;}
            default                                             -> { return -1;}
        }
    }
}
