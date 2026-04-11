package com.vendasescolares;

import com.vendasescolares.model.Usuario;
import com.vendasescolares.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class VendasescolaresApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendasescolaresApplication.class, args);
    }

    @Bean
    public CommandLineRunner criarAdmins(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            criarAdminSeNaoExistir(usuarioRepository, passwordEncoder, "Diego", "ds1368833@gmail.com", "diego13633");
            criarAdminSeNaoExistir(usuarioRepository, passwordEncoder, "Laura", "laurabarbosacl332@gmail.com", "Laura123");
            criarAdminSeNaoExistir(usuarioRepository, passwordEncoder, "Ana", "Anav72833@gmail.com", "ana0608");
        };
    }

    private void criarAdminSeNaoExistir(UsuarioRepository repo, PasswordEncoder encoder, String nome, String email, String senha) {
        if (repo.findByEmail(email).isEmpty()) {
            Usuario u = new Usuario();
            u.setNome(nome);
            u.setEmail(email);
            u.setSenha(encoder.encode(senha));
            u.setTipo("ADMIN");
            repo.save(u);
            System.out.println("Admin criado: " + email);
        }
    }
}