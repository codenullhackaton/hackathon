package br.com.codenull.web.rest;

import br.com.codenull.HackathonApp;
import br.com.codenull.domain.Beneficiario;
import br.com.codenull.repository.BeneficiarioRepository;
import br.com.codenull.service.BeneficiarioService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BeneficiarioResource REST controller.
 *
 * @see BeneficiarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackathonApp.class)
public class BeneficiarioResourceIntTest {
    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_ENDERECO = "AAAAA";
    private static final String UPDATED_ENDERECO = "BBBBB";

    @Inject
    private BeneficiarioRepository beneficiarioRepository;

    @Inject
    private BeneficiarioService beneficiarioService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBeneficiarioMockMvc;

    private Beneficiario beneficiario;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BeneficiarioResource beneficiarioResource = new BeneficiarioResource();
        ReflectionTestUtils.setField(beneficiarioResource, "beneficiarioService", beneficiarioService);
        this.restBeneficiarioMockMvc = MockMvcBuilders.standaloneSetup(beneficiarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficiario createEntity(EntityManager em) {
        Beneficiario beneficiario = new Beneficiario();
        beneficiario = new Beneficiario()
                .nome(DEFAULT_NOME)
                .endereco(DEFAULT_ENDERECO);
        return beneficiario;
    }

    @Before
    public void initTest() {
        beneficiario = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficiario() throws Exception {
        int databaseSizeBeforeCreate = beneficiarioRepository.findAll().size();

        // Create the Beneficiario

        restBeneficiarioMockMvc.perform(post("/api/beneficiarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(beneficiario)))
                .andExpect(status().isCreated());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarios = beneficiarioRepository.findAll();
        assertThat(beneficiarios).hasSize(databaseSizeBeforeCreate + 1);
        Beneficiario testBeneficiario = beneficiarios.get(beneficiarios.size() - 1);
        assertThat(testBeneficiario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testBeneficiario.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
    }

    @Test
    @Transactional
    public void getAllBeneficiarios() throws Exception {
        // Initialize the database
        beneficiarioRepository.saveAndFlush(beneficiario);

        // Get all the beneficiarios
        restBeneficiarioMockMvc.perform(get("/api/beneficiarios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiario.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())));
    }

    @Test
    @Transactional
    public void getBeneficiario() throws Exception {
        // Initialize the database
        beneficiarioRepository.saveAndFlush(beneficiario);

        // Get the beneficiario
        restBeneficiarioMockMvc.perform(get("/api/beneficiarios/{id}", beneficiario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBeneficiario() throws Exception {
        // Get the beneficiario
        restBeneficiarioMockMvc.perform(get("/api/beneficiarios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficiario() throws Exception {
        // Initialize the database
        beneficiarioService.save(beneficiario);

        int databaseSizeBeforeUpdate = beneficiarioRepository.findAll().size();

        // Update the beneficiario
        Beneficiario updatedBeneficiario = beneficiarioRepository.findOne(beneficiario.getId());
        updatedBeneficiario
                .nome(UPDATED_NOME)
                .endereco(UPDATED_ENDERECO);

        restBeneficiarioMockMvc.perform(put("/api/beneficiarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBeneficiario)))
                .andExpect(status().isOk());

        // Validate the Beneficiario in the database
        List<Beneficiario> beneficiarios = beneficiarioRepository.findAll();
        assertThat(beneficiarios).hasSize(databaseSizeBeforeUpdate);
        Beneficiario testBeneficiario = beneficiarios.get(beneficiarios.size() - 1);
        assertThat(testBeneficiario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testBeneficiario.getEndereco()).isEqualTo(UPDATED_ENDERECO);
    }

    @Test
    @Transactional
    public void deleteBeneficiario() throws Exception {
        // Initialize the database
        beneficiarioService.save(beneficiario);

        int databaseSizeBeforeDelete = beneficiarioRepository.findAll().size();

        // Get the beneficiario
        restBeneficiarioMockMvc.perform(delete("/api/beneficiarios/{id}", beneficiario.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Beneficiario> beneficiarios = beneficiarioRepository.findAll();
        assertThat(beneficiarios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
