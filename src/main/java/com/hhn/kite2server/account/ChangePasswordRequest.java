package com.hhn.kite2server.account;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ChangePasswordRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}
