package br.com.codenull.repository;

import br.com.codenull.domain.Especialidade;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Especialidade entity.
 */
@SuppressWarnings("unused")
public interface EspecialidadeRepository extends JpaRepository<Especialidade,Long> {

}
