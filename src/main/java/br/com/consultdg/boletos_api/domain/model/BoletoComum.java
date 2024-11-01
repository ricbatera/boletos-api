package br.com.consultdg.boletos_api.domain.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "tb_boleto_comum")
public class BoletoComum {

    @Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String lojaOrigem;
	private String dataVencimento;
	private String valor;
	private String linhaDigitavel;    
    private String numeroDocumento;
    private String nomeArquivo;
    private String cnpjPagador;
    private String tipoBoleto = "Alugueis e outros";
    private Boolean processadoDg = false;
    private Boolean enviadoParaBaseDg = false;
    private Instant dataProcemamentoDg;
    private Instant dataCriacao = Instant.now();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boleto", cascade = CascadeType.ALL)
    private List<ItensBoleto> itensBoleto= new ArrayList<>();
    // @ManyToOne
    // @JoinColumn(name = "usuario_criacao_id", referencedColumnName = "usuario_id")
    // private Usuario usuarioCriacao;

    @PrePersist
	public void setaEntradaSaidaNaLista() {
		itensBoleto.forEach(i -> i.setBoleto(this));
	}

}
