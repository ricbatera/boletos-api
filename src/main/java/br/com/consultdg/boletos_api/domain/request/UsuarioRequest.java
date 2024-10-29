package br.com.consultdg.boletos_api.domain.request;

import java.util.HashSet;
import java.util.Set;

import br.com.consultdg.boletos_api.domain.model.Permissoes;
import lombok.Data;

@Data
public class UsuarioRequest {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String senha;
    private Set<Permissoes> permissoes = new HashSet<>();
}
