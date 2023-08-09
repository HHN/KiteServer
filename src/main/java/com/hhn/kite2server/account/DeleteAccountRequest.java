package com.hhn.kite2server.account;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DeleteAccountRequest {
    private Long accountId;
}
