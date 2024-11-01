package br.com.consultdg.boletos_api.domain.build.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoletoComumTxtToS3 {

    private String lojaOrigem;
    private String dataVencimento;
    private String valor;
    private String linhaDigitavel;
    private String numeroDocumento;
    private String nomeArquivo;
    private String tipoBoleto = "Alugueis e outros";
    private String cnpjPagador;
    private String itens;
    private final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    public void gerarTxtToS3(String nomeArquivo, String bucketName) {
        File arquivoTemp = null;
        String prefix = nomeArquivo.substring(0, nomeArquivo.length() - 4);
        try {
            Locale.setDefault(new Locale("pt", "BR"));
            // Crie um arquivo temporário
            arquivoTemp = File.createTempFile(prefix, ".txt");

            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(arquivoTemp),
                    StandardCharsets.UTF_8)) {
                Class<?> classe = this.getClass();
                Field[] campos = classe.getDeclaredFields();
                for (Field campo : campos) {
                    campo.setAccessible(true);
                    String nomeCampo = campo.getName();
                    if (nomeCampo.equals("s3Client"))
                        break;
                    Object valorCampo = campo.get(this);
                    String linha = String.format(new Locale("pt", "BR"), "%s: %s%n", nomeCampo, valorCampo);
                    writer.write(linha);
                }
            }

            // Envie o arquivo para o S3
            s3Client.putObject(bucketName, prefix + ".txt", arquivoTemp);
            System.out.println("Arquivo " + nomeArquivo + " salvo com sucesso no S3!");

        } catch (IllegalAccessException | IOException e) {
            System.out.println("ERRO AO SALVAR TXT NO S3: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
