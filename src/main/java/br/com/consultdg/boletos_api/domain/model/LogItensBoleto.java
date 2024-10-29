package br.com.consultdg.boletos_api.domain.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name= "tb_log_itens")
@EqualsAndHashCode
public class LogItensBoleto {
    @Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "log_item_id")
    private Long id;

    private String nomeItem;
    private String valor;
    private Instant dataCriacao = Instant.now();
    
    @ManyToOne
	@JoinColumn(name = "log_id")
    private LogProcess boletoLog;

}
