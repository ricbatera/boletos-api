package br.com.consultdg.boletos_api.domain.model;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import br.com.consultdg.boletos_api.domain.enums.TipoBoleto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name= "tb_log_process")
@Data
public class LogProcess {
    @Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    private String nomeBoleto;
    private String nomeDoArquivo;
    private Integer pagina;
    private String codBarras;
    private String dataVencimento;
    private String valor;
    private String documento;
    private TipoBoleto tipoBoleto;
    private Instant dataCriacao = Instant.now();
    private Boolean processadoDg = false;
    private Instant dataProcemamentoDg;
    private Boolean enviadoParaBaseDg = false;
    @Lob
    @Column(name = "texto_extraido", columnDefinition = "BLOB")
    private byte[] textoExtraido;
    private Boolean processado;
    private Boolean salvo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boletoLog", cascade = CascadeType.ALL)
    private List<LogItensBoleto> itensBoleto= new ArrayList<>();
    
    // @ManyToOne
    // @JoinColumn(name = "usuario_criacao_id", referencedColumnName = "usuario_id")
    // private Usuario usuarioCriacao;
    
    @PrePersist
	public void setaItens() {
		itensBoleto.forEach(i -> i.setBoletoLog(this));
	}

}
