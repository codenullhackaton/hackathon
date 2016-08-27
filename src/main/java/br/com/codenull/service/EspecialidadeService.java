package br.com.codenull.service;

import br.com.codenull.domain.Especialidade;
import br.com.codenull.repository.EspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Especialidade.
 */
@Service
@Transactional
public class EspecialidadeService {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeService.class);
    
    @Inject
    private EspecialidadeRepository especialidadeRepository;

    /**
     * Save a especialidade.
     *
     * @param especialidade the entity to save
     * @return the persisted entity
     */
    public Especialidade save(Especialidade especialidade) {
        log.debug("Request to save Especialidade : {}", especialidade);
        Especialidade result = especialidadeRepository.save(especialidade);
        return result;
    }

    /**
     *  Get all the especialidades.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Especialidade> findAll(Pageable pageable) {
        log.debug("Request to get all Especialidades");
        Page<Especialidade> result = especialidadeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one especialidade by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Especialidade findOne(Long id) {
        log.debug("Request to get Especialidade : {}", id);
        Especialidade especialidade = especialidadeRepository.findOne(id);
        return especialidade;
    }

    /**
     *  Delete the  especialidade by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Especialidade : {}", id);
        especialidadeRepository.delete(id);
    }
}
