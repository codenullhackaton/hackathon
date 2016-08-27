package br.com.codenull.repository;

import br.com.codenull.domain.Procedimento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Procedimento entity.
 */
@SuppressWarnings("unused")
public interface ProcedimentoRepository extends JpaRepository<Procedimento,Long> {

}
