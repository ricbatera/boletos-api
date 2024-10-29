package br.com.consultdg.boletos_api.domain.to;

import java.time.Instant;

import lombok.Data;

@Data
public class ItemBoletoTo {
    private Long id;
    private String nomeItem;
    private String valor;
    private Instant dataCriacao;
}
