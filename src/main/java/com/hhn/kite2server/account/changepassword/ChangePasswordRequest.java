package com.hhn.kite2server.account.changepassword;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
