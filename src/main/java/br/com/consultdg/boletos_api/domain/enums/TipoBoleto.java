package br.com.consultdg.boletos_api.domain.enums;

public enum TipoBoleto {
    SERVICOS(1L,"Boleto de Servicos", true, 0 ),
    BOLETO_COMUM_SEM_ITENS(3L, "Boleto Comum sem Itens", false, 1),
    BOLETO_COMUM_COM_ITENS(2L, "Boleto Comum com Itens", true, 2);

    long id;
    String descricao;
    boolean ativo;
    int value;

    TipoBoleto(long id, String descricao, boolean ativo, int value){
        this.id = id;
        this.descricao = descricao;
        this.ativo = ativo;
        this.value = value;
    }

    public long getId(){
        return this.id;
    }

}
