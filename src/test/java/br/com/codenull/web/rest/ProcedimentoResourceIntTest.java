package br.com.codenull.web.rest;

import br.com.codenull.HackathonApp;
import br.com.codenull.domain.Procedimento;
import br.com.codenull.repository.ProcedimentoRepository;
import br.com.codenull.service.ProcedimentoService;

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
 * Test class for the ProcedimentoResource REST controller.
 *
 * @see ProcedimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackathonApp.class)
public class ProcedimentoResourceIntTest {
    private static final String DEFAULT_DESCRICAO = "AAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBB";

    private static final Integer DEFAULT_DURACAO = 1;
    private static final Integer UPDATED_DURACAO = 2;
    private static final String DEFAULT_VALOR = "AAAAA";
    private static final String UPDATED_VALOR = "BBBBB";

    @Inject
    private ProcedimentoRepository procedimentoRepository;

    @Inject
    private ProcedimentoService procedimentoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProcedimentoMockMvc;

    private Procedimento procedimento;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProcedimentoResource procedimentoResource = new ProcedimentoResource();
        ReflectionTestUtils.setField(procedimentoResource, "procedimentoService", procedimentoService);
        this.restProcedimentoMockMvc = MockMvcBuilders.standaloneSetup(procedimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedimento createEntity(EntityManager em) {
        Procedimento procedimento = new Procedimento();
        procedimento = new Procedimento()
                .descricao(DEFAULT_DESCRICAO)
                .duracao(DEFAULT_DURACAO)
                .valor(DEFAULT_VALOR);
        return procedimento;
    }

    @Before
    public void initTest() {
        procedimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedimento() throws Exception {
        int databaseSizeBeforeCreate = procedimentoRepository.findAll().size();

        // Create the Procedimento

        restProcedimentoMockMvc.perform(post("/api/procedimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(procedimento)))
                .andExpect(status().isCreated());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentos = procedimentoRepository.findAll();
        assertThat(procedimentos).hasSize(databaseSizeBeforeCreate + 1);
        Procedimento testProcedimento = procedimentos.get(procedimentos.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProcedimento.getDuracao()).isEqualTo(DEFAULT_DURACAO);
        assertThat(testProcedimento.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedimentoRepository.findAll().size();
        // set the field null
        procedimento.setValor(null);

        // Create the Procedimento, which fails.

        restProcedimentoMockMvc.perform(post("/api/procedimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(procedimento)))
                .andExpect(status().isBadRequest());

        List<Procedimento> procedimentos = procedimentoRepository.findAll();
        assertThat(procedimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcedimentos() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get all the procedimentos
        restProcedimentoMockMvc.perform(get("/api/procedimentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(procedimento.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
                .andExpect(jsonPath("$.[*].duracao").value(hasItem(DEFAULT_DURACAO)))
                .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }

    @Test
    @Transactional
    public void getProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get the procedimento
        restProcedimentoMockMvc.perform(get("/api/procedimentos/{id}", procedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedimento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.duracao").value(DEFAULT_DURACAO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProcedimento() throws Exception {
        // Get the procedimento
        restProcedimentoMockMvc.perform(get("/api/procedimentos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedimento() throws Exception {
        // Initialize the database
        procedimentoService.save(procedimento);

        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Update the procedimento
        Procedimento updatedProcedimento = procedimentoRepository.findOne(procedimento.getId());
        updatedProcedimento
                .descricao(UPDATED_DESCRICAO)
                .duracao(UPDATED_DURACAO)
                .valor(UPDATED_VALOR);

        restProcedimentoMockMvc.perform(put("/api/procedimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProcedimento)))
                .andExpect(status().isOk());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentos = procedimentoRepository.findAll();
        assertThat(procedimentos).hasSize(databaseSizeBeforeUpdate);
        Procedimento testProcedimento = procedimentos.get(procedimentos.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProcedimento.getDuracao()).isEqualTo(UPDATED_DURACAO);
        assertThat(testProcedimento.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void deleteProcedimento() throws Exception {
        // Initialize the database
        procedimentoService.save(procedimento);

        int databaseSizeBeforeDelete = procedimentoRepository.findAll().size();

        // Get the procedimento
        restProcedimentoMockMvc.perform(delete("/api/procedimentos/{id}", procedimento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Procedimento> procedimentos = procedimentoRepository.findAll();
        assertThat(procedimentos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
