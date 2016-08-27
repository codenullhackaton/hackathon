package br.com.codenull.repository;

import br.com.codenull.domain.Consulta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Consulta entity.
 */
@SuppressWarnings("unused")
public interface ConsultaRepository extends JpaRepository<Consulta,Long> {

}
