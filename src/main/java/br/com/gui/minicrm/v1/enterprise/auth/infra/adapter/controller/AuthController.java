package br.com.gui.minicrm.v1.enterprise.auth.infra.adapter.controller;

import br.com.gui.minicrm.v1.enterprise.auth.infra.adapter.dto.AuthRequest;
import br.com.gui.minicrm.v1.enterprise.auth.infra.adapter.dto.AuthResponse;
import br.com.gui.minicrm.v1.enterprise.auth.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwt;

    public AuthController(AuthenticationManager authManager, UserDetailsService uds, JwtService jwt) {
        this.authManager = authManager;
        this.userDetailsService = uds;
        this.jwt = jwt;
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );
        var roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String access = jwt.generateAccessToken(req.username(), roles);
        String refresh = jwt.generateRefreshToken(req.username());
        return ResponseEntity.ok(new AuthResponse(access, refresh));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody String refreshToken) {
        var claims = jwt.parse(refreshToken).getBody();
        String username = claims.getSubject();
        var roles = List.of("ROLE_USER"); // opcional: recarregar do banco
        String access = jwt.generateAccessToken(username, roles);
        return ResponseEntity.ok(new AuthResponse(access, refreshToken));
    }
}
