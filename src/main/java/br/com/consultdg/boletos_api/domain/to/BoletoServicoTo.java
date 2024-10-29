package br.com.consultdg.boletos_api.domain.to;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoletoServicoTo {
    private boolean processadoComSucesso;
    private boolean salvoNaBase;
    private String codBarras;
    private String dataVencimento;
    private String valor;
    private String nomeArquivo;
    private String numeroDocumento;
    private String paginaDigitalizada;
    private String parteComErro;
}
