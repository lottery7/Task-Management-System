package dev.lottery.tms.dto.request;

import lombok.Data;

@Data
public class CreateJwtRequest {
    private String email;
    private String password;
}
