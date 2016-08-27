package br.com.codenull.service;

import br.com.codenull.domain.Procedimento;
import br.com.codenull.repository.ProcedimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Procedimento.
 */
@Service
@Transactional
public class ProcedimentoService {

    private final Logger log = LoggerFactory.getLogger(ProcedimentoService.class);
    
    @Inject
    private ProcedimentoRepository procedimentoRepository;

    /**
     * Save a procedimento.
     *
     * @param procedimento the entity to save
     * @return the persisted entity
     */
    public Procedimento save(Procedimento procedimento) {
        log.debug("Request to save Procedimento : {}", procedimento);
        Procedimento result = procedimentoRepository.save(procedimento);
        return result;
    }

    /**
     *  Get all the procedimentos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Procedimento> findAll(Pageable pageable) {
        log.debug("Request to get all Procedimentos");
        Page<Procedimento> result = procedimentoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one procedimento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Procedimento findOne(Long id) {
        log.debug("Request to get Procedimento : {}", id);
        Procedimento procedimento = procedimentoRepository.findOne(id);
        return procedimento;
    }

    /**
     *  Delete the  procedimento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Procedimento : {}", id);
        procedimentoRepository.delete(id);
    }
}
