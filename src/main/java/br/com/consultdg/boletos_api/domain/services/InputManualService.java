package br.com.consultdg.boletos_api.domain.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.consultdg.boletos_api.domain.build.model.BoletoComumTxtToS3;
import br.com.consultdg.boletos_api.domain.build.model.BoletoServicoTxtToS3;
import br.com.consultdg.boletos_api.domain.enums.TipoBoleto;
import br.com.consultdg.boletos_api.domain.model.BoletoComum;
import br.com.consultdg.boletos_api.domain.model.BoletoServicos;
import br.com.consultdg.boletos_api.domain.model.ItensBoleto;
import br.com.consultdg.boletos_api.domain.model.LogItensBoleto;
import br.com.consultdg.boletos_api.domain.model.LogProcess;
import br.com.consultdg.boletos_api.domain.repository.BoletoComumRepository;
import br.com.consultdg.boletos_api.domain.repository.BoletoServicosRepository;
import br.com.consultdg.boletos_api.domain.repository.LogProcessRepository;
import br.com.consultdg.boletos_api.domain.request.InputManualRequest;

@Service
public class InputManualService {

    @Autowired
    private BoletoComumRepository comumRepo;

    @Autowired
    private BoletoServicosRepository servicoRepo;

    @Autowired
    private LogProcessRepository logRepo;

    @Autowired 
    private GravaTxtToS3Service gravaTxt;

    private String caminhoBucket = "boletos/";

    private ModelMapper modelMapper = new ModelMapper();

    public String imputManual(InputManualRequest request){

        if(request.getTipoBoleto().equals(TipoBoleto.BOLETO_COMUM_COM_ITENS)){
            return imputBoletoComItens(request);
        } else if(request.getTipoBoleto().equals(TipoBoleto.SERVICOS)){
            return imputBoletoservicos(request);
        }
        return "Tipo de boleto não encontrado, nada foi salvo";
    }

    private String imputBoletoservicos(InputManualRequest request) {
        BoletoServicos boletoServicos = modelMapper.map(request, BoletoServicos.class);
        LogProcess logProcess = modelMapper.map(request, LogProcess.class);
        String nomeArquivo = caminhoBucket + request.getNomeArquivo();
        
        logProcess.setNomeBoleto("Input Manual");
        logProcess.setNomeDoArquivo(nomeArquivo);
        logProcess.setProcessado(true);
        logProcess.setSalvo(true);
        logRepo.save(logProcess);

        boletoServicos.setLojaOrigem("Input Manual");
        servicoRepo.save(boletoServicos);
        gravaTxt.gravaBoletoServico(boletoServicos, nomeArquivo);
        return "Input Manual do boleto "+ request.getNomeArquivo() + " efetuado com sucesso!";
    }
    
    private String imputBoletoComItens(InputManualRequest request) {
        BoletoComum boletoComum = new BoletoComum();
        List<ItensBoleto> itens = new ArrayList<>();
        BoletoComumTxtToS3 boletoComumTxtToS3 = new BoletoComumTxtToS3();
        LogProcess logProcess = new LogProcess();
        List<LogItensBoleto> itensLog = new ArrayList<>();
        return "Input Manual do boleto "+ request.getNomeArquivo() + " efetuado com sucesso!";
    }

}
