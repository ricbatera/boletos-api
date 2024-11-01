package br.com.consultdg.boletos_api.domain.build;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import br.com.consultdg.boletos_api.domain.model.ItensBoleto;

@Service
public class BuildBoleto {

    public String processaCodBarras(String dados, String nomeBoleto){
        return dados.replace(" ", "")
                    .replace(".", "")
                    .replace("-", "")
                    .replace("O", "0")
                    .replaceAll("\\D", "")
                    .replace(",", "")
                    .replace(")", "")
                    .replace("]", "");
    }
    public String processaLinhaDigitavel(String dados, String regex){
        if(regex != null){
            dados = getPadrao(regex, dados, null);
            if(dados!= null){
                dados = dados.replace("O", "0")
                // .replace("/", "7")
                .replaceAll("\\D", "");        
            }
        }
        if(dados != null){
            dados = dados.replace(" ", "")
                        .replace(".", "")
                        .replace("-", "")
                        .replace("O", "0")
                        .replaceAll("\\D", "")
                        .replace(",", "")
                        .replace(")", "")
                        // .replace("/", "7")                        
                        .replace("]", "");
            if(dados.length()==47){
                return dados;
            } else return null;
        }else return null;
    }

    public String processaCodBarrasServicos(String dados, String regex){
        if(dados == null){
            return null;
        }
        String res = "";
        res = dados
                .replace("O", "0")
                .replace("/", "");        
        res = getPadrao(regex, res, null);
        if(res != null) {
            res = res.replace(" ", "").replace("-", "").replace(".", "");
            return res;
        }else return null;        
    }

    public String getDataVencimentoBoletoServico(String dados){
        //System.out.println(dados);
        String regex = "(\\d{2}/\\d{2}/\\d{4})";
        dados = getPadrao(regex, dados, null);
        dados = convertUSADate(dados);
        return dados;
    }

    public String getDataVencimentoBoletoServico(String dados, String regex){
        //System.out.println(dados);
        //String regex = "(\\d{2}/\\d{2}/\\d{4})";
        if(dados == null){
            return null;
        }
        if(regex != null){
            dados = getPadrao(regex, dados, null);
        }
        dados = convertUSADate(dados);
        return dados;
    }

    private String convertUSADate(String data) {
        if(data != null){
            data = data.substring(data.length()-10).replace("/", ".");
            String novoPadrao = "yyyy-MM-dd";
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat formatoSaida = new SimpleDateFormat(novoPadrao);
    
            try {
                Date myData = formatoEntrada.parse(data);
                return formatoSaida.format(myData);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }

    public String getValorByCodBarras(String codBarras){
        // Limpa o códido de barras removendo espaços, pontos, traços, letras e pegando somente os 48 carecteres do código de barras
        if(codBarras != null){
            String valor ="";
            codBarras = codBarras.replace(" ", "")
                                    .replace(".", "").replace("-", "")
                                    .replace("O", "0")
                                    .replaceAll("\\D", "");
            
            if(codBarras.length()==48){
                codBarras = codBarras.substring(0,48);
    
                //recuperando o valor do código de barras
                valor = codBarras.substring(0, 11) + codBarras.substring(12);
                valor = valor.substring(5, 15);
                valor = valor.replaceFirst("^0+", "");
                String reais = valor.substring(0, valor.length() - 2);
                String centavos = valor.substring(valor.length() - 2);
                return reais+"."+centavos;
            }
            return null;
        }
        return null;
    }

    public String getDocumento(String dados, String regex){
        // System.out.println(dados);
        if(regex != null && dados != null){
            dados = getPadrao(regex, dados, null);
        }
        return dados;
    }

    public String getDate(String dados, String nomeBoleto){
        if(dados.length() == 47){
            dados = dados.substring(33,37);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate initialDate = LocalDate.parse("1997-10-07", formatter);
            LocalDate newDate = initialDate.plusDays(Integer.parseInt(dados));
            return newDate.format(formatter);

        }else {
            return null;
        }

    }

    public String getValor(String dados, String nomeBoleto){
        if(dados.length() == 47){
            dados = dados.substring(dados.length() -10);
            dados = dados.replaceFirst("^0+", "");

            //FORMATA O VALOR COM "." PARA SEPARAR REAIS DE CENTAVOS
            String reais = dados.substring(0, dados.length() - 2);
            String centavos = dados.substring(dados.length() - 2);
            return reais + "." + centavos;

        }else {
            return null;
        }
    }

    public ItensBoleto getItem(String areaBusca, String regex, String nomeItem){
        String linhas[] = areaBusca.split("\n");
        ItensBoleto i = new ItensBoleto();
        for(String linha : linhas){
            if(linha.contains(nomeItem)){
                String valor = getPadrao(regex, linha, null);
                if(valor != null){
                    valor = transformaValorMonetario(valor);
                    i.setValor(valor);
                    i.setNomeItem(nomeItem);
                }
            }
        }
        return i;
    }

    public List<ItensBoleto> itensBrutos (String dados, String nomeBoleto){
        String res[] = dados.split("\n");
        List<ItensBoleto> lista = new ArrayList<>();
        switch (nomeBoleto) {
            case "Shopping Leste Aricanduva":
                /* ITENS IDENTIFICADOS NESSE BOLETO:
                ENCARGOS
                AGUA/ESGOTO PROPRIA
                ENERGIA ELETRICA PROPRIA
                SEGUROS
                I.P.T.U
                */
                String regex = "((\\d{1}|\\d{2}|\\d{3})?(\\.)?(\\d{2}|\\d{3}),\\d{2}+$)";
                for(String linha: res){
                    ItensBoleto i = new ItensBoleto();
                    String valor;
                    if(linha.contains("ENCARGOS")){
                        i.setNomeItem("ENCARGOS");
                        valor = (getPadrao(regex, linha, "ENCARGOS")).replace(".", "").replace(",", "."); 
                        i.setValor(valor); 
                        lista.add(i);                  
                    }
                    if(linha.contains("AGUA/ESGOTO PROPRIA")){                       
                        i.setNomeItem("AGUA/ESGOTO PROPRIA");
                        valor = (getPadrao(regex, linha, "AGUA/ESGOTO PROPRIA")).replace(".", "").replace(",", "."); 
                        i.setValor(valor);
                        lista.add(i); 
                    }
                    if(linha.contains("ENERGIA ELETRICA PROPRIA")){
                        i.setNomeItem("ENERGIA ELETRICA PROPRIA");
                        valor = (getPadrao(regex, linha, "ENERGIA ELETRICA PROPRIA")).replace(".", "").replace(",", "."); 
                        i.setValor(valor);
                        lista.add(i);                       
                    }
                    if(linha.contains("SEGUROS")){
                        i.setNomeItem("SEGUROS");
                        valor = (getPadrao(regex, linha, "SEGUROS")).replace(".", "").replace(",", "."); 
                        i.setValor(valor);
                        lista.add(i);                     
                    }
                    if(linha.contains("IPTU")){
                        i.setNomeItem("IPTU");
                        valor = (getPadrao(regex, linha, "IPTU")).replace(".", "").replace(",", "."); 
                        i.setValor(valor);
                        lista.add(i);                     
                    }
                    
                }                
                break;
            case "Multiplan Arrecadadora":
                /*ITENS IDENTIFICADOS NESSE BOLETO:
                    Fundo de Promoção
                    Aluguel Mínimo
                    Encargos Comum
                    Ar Condicionado
                    Encargos Comuns Loca
                    Enegia Elétrica
                    Água / Esgoto
                    Foro
                    IPTU
                    Serviços
                */
                String regex1 = "((\\d{1}|\\d{2}|\\d{3})?(\\.)?(\\d{1}|\\d{2}|\\d{3}),\\d{2}+$)";
                for(String linha: res){
                    ItensBoleto i = new ItensBoleto();
                    String valor;
                    if(linha.contains("Serviços")){
                        i.setNomeItem("Servicos");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                    if(linha.contains("IPTU")){
                        i.setNomeItem("IPTU");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                    if(linha.contains("Foro")){
                        i.setNomeItem("Foro");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                    if(linha.contains("Água / Esgoto")){
                        i.setNomeItem("Agua / Esgoto");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                    if(linha.contains("Enegia Elétrica")){
                        i.setNomeItem("Enegia Eletrica");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                    if(linha.contains("Encargos Comuns Loca")){
                        i.setNomeItem("Encargos Comuns Loca");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                    if(linha.contains("Ar Condicionado")){
                        i.setNomeItem("Ar Condicionado");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                    if(linha.contains("Encargos Comum")){
                        i.setNomeItem("Encargos Comum");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                    if(linha.contains("Aluguel Mínimo")){
                        i.setNomeItem("Aluguel Minimo");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                    if(linha.contains("Fundo de Promoção")){
                        i.setNomeItem("Fundo de Promocao");
                        valor = getPadrao(regex1, linha, null).replace(".", "").replace(",", ".");
                        i.setValor(valor);
                        lista.add(i);  
                    }
                }
                break;
            case "BEIRAMAR SHOPPING":
                /*ITENS IDENTIFICADOS NESSE BOLETO:
                    Fundo de Promocao
                    Taxa Associacao
                    Condomínio
                    Água Privativa
                */
                String regex2 = "((\\d{1}|\\d{2}|\\d{3})?(\\.:|\\.)?(\\d{1}|\\d{2}|\\d{3}),\\d{2}+$)";
                for(String linha: res){
                    ItensBoleto i = new ItensBoleto();
                    String valor;
                    if(linha.contains("Taxa Associacao")){
                        i.setNomeItem("Taxa Associacao");
                        valor = getPadrao(regex2, linha, null).replace(".", "").replace(",", ".").replace(":", "");
                        i.setValor(valor);
                        lista.add(i); 
                    }
                    if(linha.contains("Fundo de Promocao")){
                        i.setNomeItem("Fundo de Promocao");
                        valor = getPadrao(regex2, linha, null).replace(".", "").replace(",", ".").replace(":", "");
                        i.setValor(valor);
                        lista.add(i); 
                    }
                    if(linha.contains("Condomínio")){
                        i.setNomeItem("Condominio");
                        valor = getPadrao(regex2, linha, null).replace(".", "").replace(",", ".").replace(":", "");
                        i.setValor(valor);
                        lista.add(i); 
                    }
                    if(linha.contains("Água Privativa")){
                        i.setNomeItem("Agua Privativa");
                        valor = getPadrao(regex2, linha, null).replace(".", "").replace(",", ".").replace(":", "");
                        i.setValor(valor);
                        lista.add(i); 
                    }
                    
                }
                break;
            default:;
                break;
        }

        //System.out.println(dados);
        return lista;
    }

    private String getPadrao(String regex, String linha, String tipoItem){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(linha);
        if (matcher.find()) {
            String res = matcher.group(1);
            return res;
        }
        return null;
    }

    private String transformaValorMonetario(String valor){
        return valor.replace(".", "").replace(",", "."); 
    }

}
