package br.com.consultdg.boletos_api.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.consultdg.boletos_api.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{

    Optional<Usuario> findByEmail(String email);

    Usuario findByEmail(Usuario userAdmin);

}
