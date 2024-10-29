package br.com.consultdg.boletos_api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_tipo_boleto")
public class TipoBoleto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tipo_boleto_id")
    private Long id;
    private String descricao;
    private boolean ativo;
    private int value;
   

}
