package br.com.consultdg.boletos_api.domain.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.consultdg.boletos_api.domain.model.LogProcess;
// import br.com.consultdg.process_invoice.domain.model.Usuario;


public interface LogProcessRepository extends JpaRepository<LogProcess, Long>{

    // List<LogProcess> findByUsuarioCriacao(Usuario user);
    
    @Query(value="select * from tb_log_process where dataCriacao > ? and usuario_criacao_id = ? order by dataCriacao desc", nativeQuery = true)
    List<LogProcess> findByDataCriacaoHoje(String dataCriacao, UUID userID);
    
    @Query(value="select * from tb_log_process where dataCriacao > ? order by dataCriacao desc", nativeQuery = true)
    List<LogProcess> findByDataCriacaoHojeFull(String dataCriacao);
    
    @Query(value="select * from tb_log_process where dataCriacao between ? and ? and usuario_criacao_id = ? order by dataCriacao desc", nativeQuery = true)
    List<LogProcess> findByDataCriacaoStartEnd(String startDate, String endDate, UUID userID);
    
    @Query(value="select * from tb_log_process where dataCriacao between ? and ? order by dataCriacao desc", nativeQuery = true)
    List<LogProcess> findByDataCriacaoStartEndFull(String startDate, String endDate);   

}
