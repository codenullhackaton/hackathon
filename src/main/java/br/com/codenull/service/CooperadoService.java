package br.com.codenull.service;

import br.com.codenull.domain.Cooperado;
import br.com.codenull.repository.CooperadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Cooperado.
 */
@Service
@Transactional
public class CooperadoService {

    private final Logger log = LoggerFactory.getLogger(CooperadoService.class);
    
    @Inject
    private CooperadoRepository cooperadoRepository;

    /**
     * Save a cooperado.
     *
     * @param cooperado the entity to save
     * @return the persisted entity
     */
    public Cooperado save(Cooperado cooperado) {
        log.debug("Request to save Cooperado : {}", cooperado);
        Cooperado result = cooperadoRepository.save(cooperado);
        return result;
    }

    /**
     *  Get all the cooperados.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Cooperado> findAll(Pageable pageable) {
        log.debug("Request to get all Cooperados");
        Page<Cooperado> result = cooperadoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one cooperado by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cooperado findOne(Long id) {
        log.debug("Request to get Cooperado : {}", id);
        Cooperado cooperado = cooperadoRepository.findOneWithEagerRelationships(id);
        return cooperado;
    }

    /**
     *  Delete the  cooperado by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cooperado : {}", id);
        cooperadoRepository.delete(id);
    }
}
