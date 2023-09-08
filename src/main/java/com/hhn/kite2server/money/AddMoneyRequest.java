package com.hhn.kite2server.money;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class AddMoneyRequest {
    private Long value;
}
