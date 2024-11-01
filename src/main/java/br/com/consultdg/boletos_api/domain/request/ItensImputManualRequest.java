package br.com.consultdg.boletos_api.domain.request;

import java.time.Instant;

import lombok.Data;

@Data
public class ItensImputManualRequest {
    private String nomeItem;
    private String valor;
    private Instant dataCriacao = Instant.now();
}
