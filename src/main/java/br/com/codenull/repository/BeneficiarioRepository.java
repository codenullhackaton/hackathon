package br.com.codenull.repository;

import br.com.codenull.domain.Beneficiario;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Beneficiario entity.
 */
@SuppressWarnings("unused")
public interface BeneficiarioRepository extends JpaRepository<Beneficiario,Long> {

}
