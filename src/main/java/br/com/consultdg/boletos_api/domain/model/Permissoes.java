package br.com.consultdg.boletos_api.domain.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name= "tb_permissoes")
@Data
public class Permissoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="permissao_id")
    private Long id;
    private String permissao;
    private Instant dataCriacao = Instant.now();

    public enum PermissoesList {
        ADMIN(1L, "Administrador"),
        USUARIO(2L, "Usuário"),
        SUPORTE_N2(3L, "Suporte Nível 2"),
        SUPORTE_N3(4L, "Suporte Nível 3");

        long id;
        String descricao;

        PermissoesList(long id, String descricao){
            this.id = id;
            this.descricao = descricao;
        }

        public long getId(){
            return this.id;
        }
    }

}
