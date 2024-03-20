package com.hhn.kite2server.user_role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserRoleService {

    private final String CODE_STANDARD_USER = "2579713217761";
    private final String CODE_NOVEL_TESTER = "09530779823";
    private final String CODE_AI_TESTER = "93515608172734";
    private final String CODE_TESTER = "6463423290";
    private final String CODE_INTERN = "436937269687567";
    private final String CODE_SUPER_USER = "602740213";

    public int getUserRoleByCode(String code) {
        switch (code) {
            case CODE_STANDARD_USER -> { return UserRole.STANDARD_USER.toInt(); }
            case CODE_NOVEL_TESTER  -> { return UserRole.NOVEL_TESTER.toInt(); }
            case CODE_AI_TESTER     -> { return UserRole.AI_TESTER.toInt(); }
            case CODE_TESTER        -> { return UserRole.TESTER.toInt(); }
            case CODE_INTERN        -> { return UserRole.INTERN.toInt(); }
            case CODE_SUPER_USER    -> { return UserRole.SUPER_USER.toInt(); }
            default                 -> { return -1; }
        }
    }
}
