package br.com.codenull.repository;

import br.com.codenull.domain.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Beneficiario entity.
 */
@SuppressWarnings("unused")
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {

    @Query("select ben from Beneficiario ben where ben.nome = (select nome from Cooperado where id = :id)")
    Beneficiario getBeneficiarioPorCooperado(@Param("id") Long id);
}
