package br.com.consultdg.boletos_api.domain.to;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import br.com.consultdg.boletos_api.domain.enums.TipoBoleto;
import lombok.Data;

@Data
public class ItemListProcessResponse {
    private Long id;
    private String nomeBoleto;
    private String nomeDoArquivo;
    private Integer pagina;
    private String codBarras;
    private String dataVencimento;
    private String valor;
    private String documento;
    private TipoBoleto tipoBoleto;
    private Instant dataCriacao;
    private Boolean processado;
    private Boolean salvo;
    private List<ItemBoletoTo> itens= new ArrayList<>();
}
