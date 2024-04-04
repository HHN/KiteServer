package com.hhn.kite2server.data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class AddDataObjectRequest {
    private String prompt;
    private String completion;
}
