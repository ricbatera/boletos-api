package br.com.consultdg.boletos_api.domain.model;

import java.time.Instant;

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
@EqualsAndHashCode
@Table(name = "tb_itens_boleto")
public class ItensBoleto {
    @Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String nomeItem;
    private String valor;
    private Instant dataCriacao = Instant.now();

    @ManyToOne
	@JoinColumn(name = "boleto_id")
	private BoletoComum boleto;

}
