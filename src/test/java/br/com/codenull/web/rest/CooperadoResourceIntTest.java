package br.com.codenull.web.rest;

import br.com.codenull.HackathonApp;
import br.com.codenull.domain.Cooperado;
import br.com.codenull.repository.CooperadoRepository;
import br.com.codenull.service.CooperadoService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CooperadoResource REST controller.
 *
 * @see CooperadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackathonApp.class)
public class CooperadoResourceIntTest {
    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_CRM = "AAAAA";
    private static final String UPDATED_CRM = "BBBBB";

    private static final BigDecimal DEFAULT_VALOR_COTA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_COTA = new BigDecimal(2);

    private static final LocalDate DEFAULT_ADESAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADESAO = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private CooperadoRepository cooperadoRepository;

    @Inject
    private CooperadoService cooperadoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCooperadoMockMvc;

    private Cooperado cooperado;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CooperadoResource cooperadoResource = new CooperadoResource();
        ReflectionTestUtils.setField(cooperadoResource, "cooperadoService", cooperadoService);
        this.restCooperadoMockMvc = MockMvcBuilders.standaloneSetup(cooperadoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cooperado createEntity(EntityManager em) {
        Cooperado cooperado = new Cooperado();
        cooperado = new Cooperado()
                .nome(DEFAULT_NOME)
                .crm(DEFAULT_CRM)
                .valorCota(DEFAULT_VALOR_COTA)
                .adesao(DEFAULT_ADESAO);
        return cooperado;
    }

    @Before
    public void initTest() {
        cooperado = createEntity(em);
    }

    @Test
    @Transactional
    public void createCooperado() throws Exception {
        int databaseSizeBeforeCreate = cooperadoRepository.findAll().size();

        // Create the Cooperado

        restCooperadoMockMvc.perform(post("/api/cooperados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cooperado)))
                .andExpect(status().isCreated());

        // Validate the Cooperado in the database
        List<Cooperado> cooperados = cooperadoRepository.findAll();
        assertThat(cooperados).hasSize(databaseSizeBeforeCreate + 1);
        Cooperado testCooperado = cooperados.get(cooperados.size() - 1);
        assertThat(testCooperado.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCooperado.getCrm()).isEqualTo(DEFAULT_CRM);
        assertThat(testCooperado.getValorCota()).isEqualTo(DEFAULT_VALOR_COTA);
        assertThat(testCooperado.getAdesao()).isEqualTo(DEFAULT_ADESAO);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cooperadoRepository.findAll().size();
        // set the field null
        cooperado.setNome(null);

        // Create the Cooperado, which fails.

        restCooperadoMockMvc.perform(post("/api/cooperados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cooperado)))
                .andExpect(status().isBadRequest());

        List<Cooperado> cooperados = cooperadoRepository.findAll();
        assertThat(cooperados).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCrmIsRequired() throws Exception {
        int databaseSizeBeforeTest = cooperadoRepository.findAll().size();
        // set the field null
        cooperado.setCrm(null);

        // Create the Cooperado, which fails.

        restCooperadoMockMvc.perform(post("/api/cooperados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cooperado)))
                .andExpect(status().isBadRequest());

        List<Cooperado> cooperados = cooperadoRepository.findAll();
        assertThat(cooperados).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorCotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cooperadoRepository.findAll().size();
        // set the field null
        cooperado.setValorCota(null);

        // Create the Cooperado, which fails.

        restCooperadoMockMvc.perform(post("/api/cooperados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cooperado)))
                .andExpect(status().isBadRequest());

        List<Cooperado> cooperados = cooperadoRepository.findAll();
        assertThat(cooperados).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdesaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cooperadoRepository.findAll().size();
        // set the field null
        cooperado.setAdesao(null);

        // Create the Cooperado, which fails.

        restCooperadoMockMvc.perform(post("/api/cooperados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cooperado)))
                .andExpect(status().isBadRequest());

        List<Cooperado> cooperados = cooperadoRepository.findAll();
        assertThat(cooperados).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCooperados() throws Exception {
        // Initialize the database
        cooperadoRepository.saveAndFlush(cooperado);

        // Get all the cooperados
        restCooperadoMockMvc.perform(get("/api/cooperados?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cooperado.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].crm").value(hasItem(DEFAULT_CRM.toString())))
                .andExpect(jsonPath("$.[*].valorCota").value(hasItem(DEFAULT_VALOR_COTA.intValue())))
                .andExpect(jsonPath("$.[*].adesao").value(hasItem(DEFAULT_ADESAO.toString())));
    }

    @Test
    @Transactional
    public void getCooperado() throws Exception {
        // Initialize the database
        cooperadoRepository.saveAndFlush(cooperado);

        // Get the cooperado
        restCooperadoMockMvc.perform(get("/api/cooperados/{id}", cooperado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cooperado.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.crm").value(DEFAULT_CRM.toString()))
            .andExpect(jsonPath("$.valorCota").value(DEFAULT_VALOR_COTA.intValue()))
            .andExpect(jsonPath("$.adesao").value(DEFAULT_ADESAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCooperado() throws Exception {
        // Get the cooperado
        restCooperadoMockMvc.perform(get("/api/cooperados/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCooperado() throws Exception {
        // Initialize the database
        cooperadoService.save(cooperado);

        int databaseSizeBeforeUpdate = cooperadoRepository.findAll().size();

        // Update the cooperado
        Cooperado updatedCooperado = cooperadoRepository.findOne(cooperado.getId());
        updatedCooperado
                .nome(UPDATED_NOME)
                .crm(UPDATED_CRM)
                .valorCota(UPDATED_VALOR_COTA)
                .adesao(UPDATED_ADESAO);

        restCooperadoMockMvc.perform(put("/api/cooperados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCooperado)))
                .andExpect(status().isOk());

        // Validate the Cooperado in the database
        List<Cooperado> cooperados = cooperadoRepository.findAll();
        assertThat(cooperados).hasSize(databaseSizeBeforeUpdate);
        Cooperado testCooperado = cooperados.get(cooperados.size() - 1);
        assertThat(testCooperado.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCooperado.getCrm()).isEqualTo(UPDATED_CRM);
        assertThat(testCooperado.getValorCota()).isEqualTo(UPDATED_VALOR_COTA);
        assertThat(testCooperado.getAdesao()).isEqualTo(UPDATED_ADESAO);
    }

    @Test
    @Transactional
    public void deleteCooperado() throws Exception {
        // Initialize the database
        cooperadoService.save(cooperado);

        int databaseSizeBeforeDelete = cooperadoRepository.findAll().size();

        // Get the cooperado
        restCooperadoMockMvc.perform(delete("/api/cooperados/{id}", cooperado.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cooperado> cooperados = cooperadoRepository.findAll();
        assertThat(cooperados).hasSize(databaseSizeBeforeDelete - 1);
    }
}
