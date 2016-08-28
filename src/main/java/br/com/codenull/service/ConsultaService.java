package br.com.codenull.service;

import br.com.codenull.domain.Consulta;
import br.com.codenull.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Consulta.
 */
@Service
@Transactional
public class ConsultaService {

    private final Logger log = LoggerFactory.getLogger(ConsultaService.class);
    
    @Inject
    private ConsultaRepository consultaRepository;

    /**
     * Save a consulta.
     *
     * @param consulta the entity to save
     * @return the persisted entity
     */
    public Consulta save(Consulta consulta) {
        log.debug("Request to save Consulta : {}", consulta);
        Consulta result = consultaRepository.save(consulta);
        return result;
    }

    /**
     *  Get all the consultas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Consulta> findAll(Pageable pageable) {
        log.debug("Request to get all Consultas");
        Page<Consulta> result = consultaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one consulta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Consulta findOne(Long id) {
        log.debug("Request to get Consulta : {}", id);
        Consulta consulta = consultaRepository.findOne(id);
        return consulta;
    }

    /**
     *  Delete the  consulta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Consulta : {}", id);
        consultaRepository.delete(id);
    }
}