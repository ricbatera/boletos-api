package br.com.consultdg.boletos_api.domain.request;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class NovoBoletoTo {
    private String nomeBoleto;
    private List<String> padraoIdentificadorBoleto = new ArrayList<>();
    private String tipoBoleto;
    private List<PartesBoleto> partes = new ArrayList<>();
}
