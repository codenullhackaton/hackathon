package br.com.codenull.web.rest;

import br.com.codenull.HackathonApp;
import br.com.codenull.domain.Consulta;
import br.com.codenull.domain.Procedimento;
import br.com.codenull.domain.Cooperado;
import br.com.codenull.domain.Beneficiario;
import br.com.codenull.repository.ConsultaRepository;
import br.com.codenull.service.ConsultaService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConsultaResource REST controller.
 *
 * @see ConsultaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackathonApp.class)
public class ConsultaResourceIntTest {

    private static final LocalDate DEFAULT_DATA_CONSULTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CONSULTA = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LOCALIDADE = "AAAAA";
    private static final String UPDATED_LOCALIDADE = "BBBBB";

    private static final LocalDate DEFAULT_CRIADO_EM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CRIADO_EM = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ConsultaRepository consultaRepository;

    @Inject
    private ConsultaService consultaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restConsultaMockMvc;

    private Consulta consulta;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConsultaResource consultaResource = new ConsultaResource();
        ReflectionTestUtils.setField(consultaResource, "consultaService", consultaService);
        this.restConsultaMockMvc = MockMvcBuilders.standaloneSetup(consultaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consulta createEntity(EntityManager em) {
        Consulta consulta = new Consulta();
        consulta = new Consulta()
                .dataConsulta(DEFAULT_DATA_CONSULTA)
                .localidade(DEFAULT_LOCALIDADE)
                .criadoEm(DEFAULT_CRIADO_EM);
        // Add required entity
        Procedimento procedimento = ProcedimentoResourceIntTest.createEntity(em);
        em.persist(procedimento);
        em.flush();
        consulta.setProcedimento(procedimento);
        // Add required entity
        Cooperado cooperado = CooperadoResourceIntTest.createEntity(em);
        em.persist(cooperado);
        em.flush();
        consulta.setCooperado(cooperado);
        // Add required entity
        Beneficiario beneficiario = BeneficiarioResourceIntTest.createEntity(em);
        em.persist(beneficiario);
        em.flush();
        consulta.setBeneficiario(beneficiario);
        return consulta;
    }

    @Before
    public void initTest() {
        consulta = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsulta() throws Exception {
        int databaseSizeBeforeCreate = consultaRepository.findAll().size();

        // Create the Consulta

        restConsultaMockMvc.perform(post("/api/consultas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consulta)))
                .andExpect(status().isCreated());

        // Validate the Consulta in the database
        List<Consulta> consultas = consultaRepository.findAll();
        assertThat(consultas).hasSize(databaseSizeBeforeCreate + 1);
        Consulta testConsulta = consultas.get(consultas.size() - 1);
        assertThat(testConsulta.getDataConsulta()).isEqualTo(DEFAULT_DATA_CONSULTA);
        assertThat(testConsulta.getLocalidade()).isEqualTo(DEFAULT_LOCALIDADE);
        assertThat(testConsulta.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
    }

    @Test
    @Transactional
    public void checkDataConsultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaRepository.findAll().size();
        // set the field null
        consulta.setDataConsulta(null);

        // Create the Consulta, which fails.

        restConsultaMockMvc.perform(post("/api/consultas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consulta)))
                .andExpect(status().isBadRequest());

        List<Consulta> consultas = consultaRepository.findAll();
        assertThat(consultas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaRepository.findAll().size();
        // set the field null
        consulta.setLocalidade(null);

        // Create the Consulta, which fails.

        restConsultaMockMvc.perform(post("/api/consultas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consulta)))
                .andExpect(status().isBadRequest());

        List<Consulta> consultas = consultaRepository.findAll();
        assertThat(consultas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultas() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultas
        restConsultaMockMvc.perform(get("/api/consultas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(consulta.getId().intValue())))
                .andExpect(jsonPath("$.[*].dataConsulta").value(hasItem(DEFAULT_DATA_CONSULTA.toString())))
                .andExpect(jsonPath("$.[*].localidade").value(hasItem(DEFAULT_LOCALIDADE.toString())))
                .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())));
    }

    @Test
    @Transactional
    public void getConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get the consulta
        restConsultaMockMvc.perform(get("/api/consultas/{id}", consulta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consulta.getId().intValue()))
            .andExpect(jsonPath("$.dataConsulta").value(DEFAULT_DATA_CONSULTA.toString()))
            .andExpect(jsonPath("$.localidade").value(DEFAULT_LOCALIDADE.toString()))
            .andExpect(jsonPath("$.criadoEm").value(DEFAULT_CRIADO_EM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsulta() throws Exception {
        // Get the consulta
        restConsultaMockMvc.perform(get("/api/consultas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsulta() throws Exception {
        // Initialize the database
        consultaService.save(consulta);

        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();

        // Update the consulta
        Consulta updatedConsulta = consultaRepository.findOne(consulta.getId());
        updatedConsulta
                .dataConsulta(UPDATED_DATA_CONSULTA)
                .localidade(UPDATED_LOCALIDADE)
                .criadoEm(UPDATED_CRIADO_EM);

        restConsultaMockMvc.perform(put("/api/consultas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedConsulta)))
                .andExpect(status().isOk());

        // Validate the Consulta in the database
        List<Consulta> consultas = consultaRepository.findAll();
        assertThat(consultas).hasSize(databaseSizeBeforeUpdate);
        Consulta testConsulta = consultas.get(consultas.size() - 1);
        assertThat(testConsulta.getDataConsulta()).isEqualTo(UPDATED_DATA_CONSULTA);
        assertThat(testConsulta.getLocalidade()).isEqualTo(UPDATED_LOCALIDADE);
        assertThat(testConsulta.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
    }

    @Test
    @Transactional
    public void deleteConsulta() throws Exception {
        // Initialize the database
        consultaService.save(consulta);

        int databaseSizeBeforeDelete = consultaRepository.findAll().size();

        // Get the consulta
        restConsultaMockMvc.perform(delete("/api/consultas/{id}", consulta.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Consulta> consultas = consultaRepository.findAll();
        assertThat(consultas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
