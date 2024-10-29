package br.com.consultdg.boletos_api.domain.to;

import lombok.Data;

@Data
public class BoletoComumTo {
    private boolean processadoComSucesso;
    private boolean salvoNaBase;
    private String linhaDigitavel;
    private String dataVencimento;
    private String valor;
    private String nomeArquivo;
    private String numeroDocumento;
    private String paginaDigitalizada;
    private String parteComErro;

}
