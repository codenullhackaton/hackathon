package br.com.codenull.service;

import br.com.codenull.domain.Beneficiario;
import br.com.codenull.repository.BeneficiarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Beneficiario.
 */
@Service
@Transactional
public class BeneficiarioService {

    private final Logger log = LoggerFactory.getLogger(BeneficiarioService.class);
    
    @Inject
    private BeneficiarioRepository beneficiarioRepository;

    /**
     * Save a beneficiario.
     *
     * @param beneficiario the entity to save
     * @return the persisted entity
     */
    public Beneficiario save(Beneficiario beneficiario) {
        log.debug("Request to save Beneficiario : {}", beneficiario);
        Beneficiario result = beneficiarioRepository.save(beneficiario);
        return result;
    }

    /**
     *  Get all the beneficiarios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Beneficiario> findAll(Pageable pageable) {
        log.debug("Request to get all Beneficiarios");
        Page<Beneficiario> result = beneficiarioRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one beneficiario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Beneficiario findOne(Long id) {
        log.debug("Request to get Beneficiario : {}", id);
        Beneficiario beneficiario = beneficiarioRepository.findOne(id);
        return beneficiario;
    }

    /**
     *  Delete the  beneficiario by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Beneficiario : {}", id);
        beneficiarioRepository.delete(id);
    }
}
