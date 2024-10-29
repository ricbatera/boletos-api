package br.com.consultdg.boletos_api.domain.model;

import br.com.consultdg.boletos_api.domain.enums.TipoBoleto;
import jakarta.persistence.Column;
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
@Table(name = "tb_coordenadas")
public class Coordenadas {
    @Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    @Column(name = "nome_boleto")
    private String nomeBoleto;
    @Column(name = "area_busca")
    private String areaBusca;
    private int x;
    private int y;
    private int height;
    private int width;
    private String regex;
    @Column(name = "padrao_identificador_boleto")
    private String padraoIdentificadorBoleto;
    @Column(name = "tipo_boleto")
    private TipoBoleto tipoBoleto;
    private boolean master;

}
