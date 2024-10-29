package br.com.consultdg.boletos_api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.consultdg.boletos_api.domain.model.BoletoServicos;

public interface BoletoServicosRepository extends JpaRepository<BoletoServicos, Long>{

    public List<BoletoServicos> findByEnviadoParaBaseDgFalse();

}
