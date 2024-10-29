package br.com.consultdg.boletos_api.domain.to;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ListProcessResponse {
    private int totalDeBoletos;
    private int totalDePaginas;
    private int paginasProcessadasOk;
    private int paginasProcessadasNaoOk;
    private int naoIdentificados;
    private List<ItemListProcessResponse> boletos = new ArrayList<>();

}
