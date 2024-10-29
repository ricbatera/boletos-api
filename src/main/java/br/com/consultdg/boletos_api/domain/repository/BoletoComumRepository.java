package br.com.consultdg.boletos_api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.consultdg.boletos_api.domain.model.BoletoComum;

public interface BoletoComumRepository extends JpaRepository<BoletoComum, Long>{

    public List<BoletoComum> findByEnviadoParaBaseDgFalse();

}
