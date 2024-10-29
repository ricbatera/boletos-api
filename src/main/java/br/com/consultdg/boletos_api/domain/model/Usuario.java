package br.com.consultdg.boletos_api.domain.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.consultdg.boletos_api.domain.to.LoginRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@Table(name= "tb_usuario")
public class Usuario {
    @Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "usuario_id")
    private UUID id;
    private String nome;
    private String sobrenome;
    @Column(unique = true)
    private String email;
    private String telefone;
    private String senha;
    private Instant dataCriacao = Instant.now();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_usuarios_permissoes",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private Set<Permissoes> permissoes;
    public boolean isLoginCorreto(LoginRequest request, PasswordEncoder pass) {
        return pass.matches(request.senha(), this.senha);
    }

    // @OneToMany(mappedBy = "usuarioCriacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<LogProcess> logProcessList = new ArrayList<>();

    // @OneToMany(mappedBy = "usuarioCriacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<BoletoServicos> boletoServicosList = new ArrayList<>();

    // @OneToMany(mappedBy = "usuarioCriacao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<BoletoComum> boletoComunList = new ArrayList<>();

    
}
