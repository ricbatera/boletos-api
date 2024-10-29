package br.com.consultdg.boletos_api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.consultdg.boletos_api.domain.model.LogItensBoleto;

public interface LogItensBoletoRepository extends JpaRepository<LogItensBoleto, Long>{

}
