package br.com.consultdg.boletos_api.domain.services;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.consultdg.boletos_api.domain.build.model.BoletoComumTxtToS3;
import br.com.consultdg.boletos_api.domain.build.model.BoletoServicoTxtToS3;
import br.com.consultdg.boletos_api.domain.model.BoletoComum;
// import br.com.consultdg.boletos_api.domain.mapper.DefaultMapper;
import br.com.consultdg.boletos_api.domain.model.BoletoServicos;
import br.com.consultdg.boletos_api.domain.model.ItensBoleto;

@Service
public class GravaTxtToS3Service {

    @Value("${s3.config.bucket-name}")
    private String bucketName;

    public boolean gravaBoletoServico(BoletoServicos boleto, String nomeArquivo){
        try {
            BoletoServicoTxtToS3 response = new BoletoServicoTxtToS3();
            response.setLojaOrigem(boleto.getLojaOrigem());
            response.setCnpjPagador(boleto.getCnpjPagador());
            response.setCodBarras(boleto.getCodBarras());
            response.setDataVencimento(boleto.getDataVencimento());
            response.setNumeroDocumento(boleto.getNumeroDocumento());
            response.setValor(boleto.getValor());
            response.gerarTxtToS3(nomeArquivo, bucketName);
            return true;
        } catch (Exception e) {
            System.out.println("ERRO AO CONVERTER E SALVAR O BOLETO NA S3: " + e.getMessage());
            return false;
        }
    }

    public boolean gravaBoletoComum(BoletoComum boleto, String nomeArquivo){
        try {
            BoletoComumTxtToS3 response = new BoletoComumTxtToS3();
            List<ItensBoleto> itensboleto = boleto.getItensBoleto();
            StringBuilder itensList = new StringBuilder();
            itensList.append("\n");

            for(ItensBoleto i: itensboleto){
                itensList.append("\tItem: " + i.getNomeItem() + " - Valor: " + i.getValor()+"\n");
            }
            response.setItens(itensList.toString());
            response.setDataVencimento(boleto.getDataVencimento());
            response.setLinhaDigitavel(boleto.getLinhaDigitavel());
            response.setLojaOrigem(boleto.getLojaOrigem());
            response.setNomeArquivo(boleto.getNomeArquivo());
            response.setNumeroDocumento(boleto.getNumeroDocumento());
            response.setValor(boleto.getValor());
            response.gerarTxtToS3(nomeArquivo, bucketName);
            return true;
        } catch (Exception e) {
            System.out.println("ERRO AO CONVERTER E SALVAR O BOLETO NA S3: " + e.getMessage());
            return false;
        }
    }

}
