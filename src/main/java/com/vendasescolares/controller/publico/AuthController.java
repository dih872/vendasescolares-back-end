package com.vendasescolares.controller.publico;

import com.vendasescolares.dto.request.LoginRequestDTO;
import com.vendasescolares.dto.response.LoginResponseDTO;
import com.vendasescolares.dto.response.MensagemResponse;
import com.vendasescolares.model.Usuario;
import com.vendasescolares.repository.UsuarioRepository;
import com.vendasescolares.security.JwtTokenProvider;
import com.vendasescolares.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            if (!"ADMIN".equals(usuario.getTipo())) {
                return ResponseEntity.status(403).body(new MensagemResponse("Acesso negado - Apenas administradores"));
            }

            String token = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new LoginResponseDTO(
                    token,
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getTipo()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new MensagemResponse("E-mail ou senha inválidos"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Usuario usuario = usuarioRepository.findByEmail(userPrincipal.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!"ADMIN".equals(usuario.getTipo())) {
            return ResponseEntity.status(403).body(new MensagemResponse("Acesso negado"));
        }

        return ResponseEntity.ok(usuario);
    }
}