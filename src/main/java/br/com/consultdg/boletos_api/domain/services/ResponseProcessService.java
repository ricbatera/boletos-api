package br.com.consultdg.boletos_api.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.consultdg.boletos_api.domain.model.LogItensBoleto;
import br.com.consultdg.boletos_api.domain.model.LogProcess;
import br.com.consultdg.boletos_api.domain.model.Usuario;
import br.com.consultdg.boletos_api.domain.repository.LogProcessRepository;
import br.com.consultdg.boletos_api.domain.repository.UsuarioRepository;
import br.com.consultdg.boletos_api.domain.to.ItemBoletoTo;
import br.com.consultdg.boletos_api.domain.to.ItemListProcessResponse;
import br.com.consultdg.boletos_api.domain.to.ListProcessResponse;

@Service
public class ResponseProcessService {

    @Autowired
    private LogProcessRepository logRepo;

    @Autowired
    private UsuarioRepository userRepo;

    public ListProcessResponse getProcessByUserId(String userId, String startDate, String endDate){
        ListProcessResponse res = new ListProcessResponse();
        List<LogProcess> lista = new ArrayList<>();
        if(userId == null){
            if(startDate != null && endDate == null){
                lista = logRepo.findByDataCriacaoHojeFull(startDate);
            }else if(startDate != null && endDate != null){
                lista = logRepo.findByDataCriacaoStartEndFull(startDate, endDate);
            }else {
                lista = logRepo.findAll();
            }
        }else{
            Usuario user = userRepo.findById(UUID.fromString(userId)).get();
            if(startDate != null && endDate == null){
                lista = logRepo.findByDataCriacaoHoje(startDate, UUID.fromString(userId));
            }else if(startDate != null && endDate != null){
                lista = logRepo.findByDataCriacaoStartEnd(startDate, endDate, UUID.fromString(userId));
            }else {
                // lista = logRepo.findByUsuarioCriacao(user);
            }

        }
        List<ItemListProcessResponse> boletos = new ArrayList<>();       

        for(LogProcess log: lista){
            ItemListProcessResponse boleto = new ItemListProcessResponse();
            boleto.setCodBarras(log.getCodBarras());
            boleto.setDataCriacao(log.getDataCriacao());
            boleto.setDataVencimento(log.getDataVencimento());
            boleto.setDocumento(log.getDocumento());
            boleto.setId(log.getId());
            boleto.setNomeBoleto(log.getNomeBoleto());
            boleto.setNomeDoArquivo(log.getNomeDoArquivo());
            boleto.setPagina(log.getPagina());
            boleto.setProcessado(log.getProcessado());
            boleto.setSalvo(log.getSalvo());
            boleto.setTipoBoleto(log.getTipoBoleto());
            boleto.setValor(log.getValor());
            List<LogItensBoleto> itens = log.getItensBoleto();
            if(!itens.isEmpty()){
                List<ItemBoletoTo> lis = new ArrayList<>();
                for(LogItensBoleto i : itens){
                    ItemBoletoTo item = new ItemBoletoTo();
                    item.setDataCriacao(i.getDataCriacao());
                    item.setId(i.getId());
                    item.setNomeItem(i.getNomeItem());
                    item.setValor(i.getValor());
                    lis.add(item);
                }
                boleto.setItens(lis);
            }
            boletos.add(boleto);
        }
        Long pro = lista.stream().filter(LogProcess::getSalvo).count(); // filtrando somente os processados ok e salvos na base
        res.setPaginasProcessadasOk((int)(long)pro);
        pro = lista.stream().filter(a -> !a.getSalvo()).filter(b-> b.getProcessado()).count(); // filtrando apenas o processados mas não salvos por qualquer motivo
        res.setPaginasProcessadasNaoOk((int)(long)pro);
        pro = lista.stream().filter(a-> !a.getProcessado()).count(); // filtrando apenas o que não foram reconhecidos
        res.setNaoIdentificados((int)(long)pro);
        pro = lista.stream().filter(a-> a.getPagina() == 1).count(); // filtrando documentos únicos
        res.setTotalDeBoletos((int)(long)pro);
        res.setTotalDePaginas(lista.size()); // setando total de páginas em todos os boletos procesados
        res.setBoletos(boletos);
        return res;
    }


}
