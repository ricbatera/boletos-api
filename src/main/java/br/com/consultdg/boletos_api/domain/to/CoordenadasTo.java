package br.com.consultdg.boletos_api.domain.to;

import lombok.Data;

@Data
public class CoordenadasTo {
    private String nomeBoleto;
    private String areaBusca;
    private int x;
    private int y;
    private int height;
    private int width;
}
