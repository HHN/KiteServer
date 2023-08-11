package com.hhn.kite2server.account.resetpassword;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResetPasswordRequest {
    private String username;
}
