package br.com.gui.minicrm.v1.enterprise.auth.infra.adapter.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "O campo 'username' é obrigatório")
        String username,

        @NotBlank(message = "O campo 'password' é obrigatório")
        String password
) {}
