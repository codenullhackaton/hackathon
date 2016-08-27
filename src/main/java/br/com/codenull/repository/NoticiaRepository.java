package br.com.codenull.repository;

import br.com.codenull.domain.Noticia;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Noticia entity.
 */
@SuppressWarnings("unused")
public interface NoticiaRepository extends JpaRepository<Noticia,Long> {

}
