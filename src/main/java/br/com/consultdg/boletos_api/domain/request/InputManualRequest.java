package br.com.consultdg.boletos_api.domain.request;

import br.com.consultdg.boletos_api.domain.enums.TipoBoleto;
import lombok.Data;
import java.time.Instant;

import java.util.List;
import java.util.ArrayList;

@Data
public class InputManualRequest {
    
    private Long id;
    private Long idTabelaLog;
    private String codBarras;
    private String dataVencimento;
    private String valor;
    private String numeroDocumento;
    private String cnpjPagador;
    private TipoBoleto tipoBoleto;
    private Boolean processadoDg = false;
    private Boolean enviadoParaBaseDg = false;
    private Instant dataProcemamentoDg;
    private String nomeArquivo;
    private Instant dataCriacao = Instant.now();
    List<ItensImputManualRequest> itensBoleto = new ArrayList<>();
}
