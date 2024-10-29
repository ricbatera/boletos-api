package br.com.consultdg.boletos_api.domain.request;

import lombok.Data;

@Data
public class PartesBoleto {
    private int x;
    private int y;
    private int width;
    private int height;
    private String regex;
    private String areaBusca;
    private String padraoIdentificadorBoleto;
}
