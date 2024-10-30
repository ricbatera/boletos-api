package br.com.consultdg.boletos_api.domain.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Util {
    // @Value("${consultdg.path.principal}")
    private String pathPrincipal;

    // @Value("${consultdg.path.processados}")
    private String processados;

    // @Value("${consultdg.path.naoprocessados}")
    private String naoProcessados;

    private float mmToInches = 25.4f;
    private int dpi = 300;

    public void validaDiretorioPrincipal() {
        // Defina o caminho do diretório que deseja criar
        Path path = Paths.get(pathPrincipal);

        try {
            // Cria o diretório e todos os diretórios necessários na hierarquia
            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.out
                    .println("Erro na criação da pasta principal do sistema: " + pathPrincipal + "\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void validaDiretorioProcessadosEnaoProcessados() {
        // Defina o caminho do diretório que deseja criar
        Path path = Paths.get(processados);
        Path path2 = Paths.get(naoProcessados);

        try {
            // Cria o diretório e todos os diretórios necessários na hierarquia
            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }
            if (Files.notExists(path2)) {
                Files.createDirectories(path2);
            }
        } catch (IOException e) {
            System.out
                    .println("Erro na criação da pasta principal do sistema: " + pathPrincipal + "\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean validaSePdf(String nomeDoArquivo) {
        String parte = nomeDoArquivo.substring(nomeDoArquivo.length() - 4);
        if (parte.equals(".pdf")) {
            return true;
        }else if(parte.equals(".PDF")){
            return true;
        }
     return false;
    }

    public String getPathPrincipal() {
        return pathPrincipal;
    }

    public String getPathProcessados() {
        return pathPrincipal;
    }

    public String getPathNaoProcessados() {
        return pathPrincipal;
    }

    public int getXByMilimeters(int x) {
        return (int) (x * dpi / mmToInches);
    }

    public int getYByMilimeters(int y) {
        return (int) (y * dpi / mmToInches);
    }

    public int getHeigthByMilimeters(int heigth, int y) {
        return (int) ((heigth - y) * dpi / mmToInches);
    }

    public int getWidthByMilimeters(int width, int x) {
        return (int) ((width - x) * dpi / mmToInches);
    }

    public String getNomeArquivo(String invoice) {
        String res[] = invoice.replace("\\", "/").split("/");
        int nome = res.length - 1;
        System.out.println(res[nome]);
        return res[nome];
    }

    public String montaPathArquivoRaiz(String nomeArquivo) {
        return pathPrincipal + "/" + nomeArquivo;
    }

    public List<LocalDate> getDatasInicialFinalAtualLocalDate(String data) {
        List<LocalDate> datas = new ArrayList<>();
        LocalDate dataBase = LocalDate.parse(data);

        datas.add(dataBase.withDayOfMonth(1)); // seta primeiro dia do mes
        datas.add(dataBase.withDayOfMonth(dataBase.lengthOfMonth())); // seta ultimo dia do mes
        datas.add(dataBase);

        return datas;
    }

    public List<OffsetDateTime> getDataInicialDataFinal(int mes) {
        List<OffsetDateTime> datas = new ArrayList<>();
        OffsetDateTime dataBase = OffsetDateTime.now();
        dataBase = dataBase.withMonth(mes);

        String convert = dataBase.toString().substring(0, 10);
        LocalDate ld = LocalDate.parse(convert);
        ld = ld.withDayOfMonth(ld.lengthOfMonth());

        convert = ld.toString().concat(dataBase.toString().substring(10));

        datas.add(dataBase.withDayOfMonth(1)); // seta o primeiro dia do mes
        datas.add(OffsetDateTime.parse(convert)); // seta o ultimo dia do mes

        return datas;

    }

}
