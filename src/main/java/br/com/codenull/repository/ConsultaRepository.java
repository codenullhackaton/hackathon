package br.com.codenull.repository;

import br.com.codenull.domain.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * Spring Data JPA repository for the Consulta entity.
 */
@SuppressWarnings("unused")
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    public List<Consulta> findByCooperadoId(Long cooperadoId);

    public List<Consulta> findByBeneficiarioId(Long cooperadoId);

}
