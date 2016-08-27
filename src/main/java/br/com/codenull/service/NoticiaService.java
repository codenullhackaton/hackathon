package br.com.codenull.service;

import br.com.codenull.domain.Noticia;
import br.com.codenull.repository.NoticiaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Noticia.
 */
@Service
@Transactional
public class NoticiaService {

    private final Logger log = LoggerFactory.getLogger(NoticiaService.class);
    
    @Inject
    private NoticiaRepository noticiaRepository;

    /**
     * Save a noticia.
     *
     * @param noticia the entity to save
     * @return the persisted entity
     */
    public Noticia save(Noticia noticia) {
        log.debug("Request to save Noticia : {}", noticia);
        Noticia result = noticiaRepository.save(noticia);
        return result;
    }

    /**
     *  Get all the noticias.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Noticia> findAll(Pageable pageable) {
        log.debug("Request to get all Noticias");
        Page<Noticia> result = noticiaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one noticia by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Noticia findOne(Long id) {
        log.debug("Request to get Noticia : {}", id);
        Noticia noticia = noticiaRepository.findOne(id);
        return noticia;
    }

    /**
     *  Delete the  noticia by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Noticia : {}", id);
        noticiaRepository.delete(id);
    }
}
