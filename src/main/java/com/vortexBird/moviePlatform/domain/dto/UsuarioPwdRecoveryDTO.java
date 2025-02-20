package com.vortexBird.moviePlatform.domain.dto;

import lombok.Data;

@Data
public class UsuarioPwdRecoveryDTO {
    private String alias;
    private String newPwd;
}
