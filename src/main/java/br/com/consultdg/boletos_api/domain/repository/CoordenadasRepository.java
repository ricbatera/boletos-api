package br.com.consultdg.boletos_api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.consultdg.boletos_api.domain.model.Coordenadas;

public interface CoordenadasRepository extends JpaRepository<Coordenadas, Long>{

    public Coordenadas findByNomeBoleto(String nomeBoleto);
    public Coordenadas findFirstByNomeBoleto(String nomeBoleto);

    public List<Coordenadas> findAllByNomeBoleto(String nomeBoleto);

    public List<Coordenadas> findByMasterTrue();

}
