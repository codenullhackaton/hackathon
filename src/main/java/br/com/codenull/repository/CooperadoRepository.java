package br.com.codenull.repository;

import br.com.codenull.domain.Cooperado;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Cooperado entity.
 */
@SuppressWarnings("unused")
public interface CooperadoRepository extends JpaRepository<Cooperado,Long> {

    @Query("select distinct cooperado from Cooperado cooperado left join fetch cooperado.especialidades")
    List<Cooperado> findAllWithEagerRelationships();

    @Query("select cooperado from Cooperado cooperado left join fetch cooperado.especialidades where cooperado.id =:id")
    Cooperado findOneWithEagerRelationships(@Param("id") Long id);

    Cooperado findFirstByUserLogin(String login);
}
