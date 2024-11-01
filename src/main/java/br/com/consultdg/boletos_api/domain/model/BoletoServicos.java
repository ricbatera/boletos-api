package br.com.consultdg.boletos_api.domain.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "tb_boleto_servicos")
public class BoletoServicos {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lojaOrigem;
	private String dataVencimento;
	private String valor;
    private String codBarras;        
    private String numeroDocumento;
    private String cnpjPagador;
    private String nomeArquivo;
    private Instant dataCriacao = Instant.now();
    private Instant dataProcemamentoDg;
    private Boolean processadoDg = false;
    private Boolean enviadoParaBaseDg = false;
    private String tipoBoleto = "Agua - Luz - Telefone e outros servicos";
    // @ManyToOne
    // @JoinColumn(name = "usuario_criacao_id", referencedColumnName = "usuario_id")
    // private Usuario usuarioCriacao;

}
