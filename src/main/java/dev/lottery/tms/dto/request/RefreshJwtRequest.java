package dev.lottery.tms.dto.request;

import lombok.Data;

@Data
public class RefreshJwtRequest {
    String refreshToken;
}
