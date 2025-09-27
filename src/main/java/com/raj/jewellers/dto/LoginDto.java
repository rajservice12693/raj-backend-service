package com.raj.jewellers.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginDto {

    private Integer id;

    private String loginId;

    private String userName;

}
