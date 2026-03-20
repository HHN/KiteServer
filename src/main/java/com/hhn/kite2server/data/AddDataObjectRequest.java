package com.hhn.kite2server.data;

import lombok.*;

public record AddDataObjectRequest(
    String prompt,
    String completion
){}
