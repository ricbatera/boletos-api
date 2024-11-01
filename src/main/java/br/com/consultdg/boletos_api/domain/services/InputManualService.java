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
import br.com.consultdg.boletos_api.domain.request.ItensImputManualRequest;

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
        BoletoComum boletoComum = modelMapper.map(request, BoletoComum.class);        
        LogProcess logProcess = modelMapper.map(request, LogProcess.class);
        List<ItensImputManualRequest> itensInputManual = request.getItensBoleto();
        List<ItensBoleto> itens = new ArrayList<>();
        List<LogItensBoleto> itensLog = new ArrayList<>();

        for(ItensImputManualRequest i : itensInputManual){
            // ItensBoleto a =  modelMapper.map(i, ItensBoleto.class);
            ItensBoleto a =  new ItensBoleto();
            a.setNomeItem(i.getNomeItem());
            a.setValor(i.getValor());
            itens.add(a);

            LogItensBoleto b = modelMapper.map(i, LogItensBoleto.class);
            itensLog.add(b);
        }
        boletoComum.setLojaOrigem("Input Manual");
        boletoComum.setItensBoleto(itens);
        logProcess.setItensBoleto(itensLog);
        boletoComum.setLinhaDigitavel(request.getCodBarras());
        comumRepo.save(boletoComum);
        String nomeArquivo = caminhoBucket + request.getNomeArquivo();


        logProcess.setNomeBoleto("Input Manual");
        logProcess.setNomeDoArquivo(nomeArquivo);
        logProcess.setProcessado(true);
        logProcess.setSalvo(true);
        if(request.getIdTabelaLog() != null){
            salvaNaoProcesados(request, nomeArquivo);
        }else {
            logRepo.save(logProcess);
        }
        

        gravaTxt.gravaBoletoComum(boletoComum, nomeArquivo);
        return "Input Manual do boleto "+ request.getNomeArquivo() + " efetuado com sucesso!";
    }

    private void salvaNaoProcesados(InputManualRequest request, String nomeArquivo) {
        LogProcess logProcess = logRepo.findById(request.getIdTabelaLog()).get();
        List<LogItensBoleto> itensLog = logProcess.getItensBoleto();
        List<ItensImputManualRequest> itensInputManual = request.getItensBoleto();
        logProcess.setNomeBoleto("Input Manual");
        logProcess.setNomeDoArquivo(nomeArquivo);
        logProcess.setProcessado(true);
        logProcess.setSalvo(true);
        logProcess.setCodBarras(request.getCodBarras());
        logProcess.setDataVencimento(request.getDataVencimento());
        logProcess.setValor(request.getValor());
        logProcess.setProcessadoDg(true);

        for(ItensImputManualRequest i: itensInputManual){
            if(i.getId() == null){
                LogItensBoleto b = modelMapper.map(i, LogItensBoleto.class);
                b.setBoletoLog(logProcess);
                itensLog.add(b);
            }
        }
        logProcess.setItensBoleto(itensLog);
        logRepo.save(logProcess);
    }

}
