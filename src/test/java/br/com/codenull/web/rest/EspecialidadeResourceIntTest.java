package br.com.codenull.web.rest;

import br.com.codenull.HackathonApp;
import br.com.codenull.domain.Especialidade;
import br.com.codenull.repository.EspecialidadeRepository;
import br.com.codenull.service.EspecialidadeService;

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
 * Test class for the EspecialidadeResource REST controller.
 *
 * @see EspecialidadeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackathonApp.class)
public class EspecialidadeResourceIntTest {
    private static final String DEFAULT_DESCRICAO = "AAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBB";

    @Inject
    private EspecialidadeRepository especialidadeRepository;

    @Inject
    private EspecialidadeService especialidadeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEspecialidadeMockMvc;

    private Especialidade especialidade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EspecialidadeResource especialidadeResource = new EspecialidadeResource();
        ReflectionTestUtils.setField(especialidadeResource, "especialidadeService", especialidadeService);
        this.restEspecialidadeMockMvc = MockMvcBuilders.standaloneSetup(especialidadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especialidade createEntity(EntityManager em) {
        Especialidade especialidade = new Especialidade();
        especialidade = new Especialidade()
                .descricao(DEFAULT_DESCRICAO);
        return especialidade;
    }

    @Before
    public void initTest() {
        especialidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspecialidade() throws Exception {
        int databaseSizeBeforeCreate = especialidadeRepository.findAll().size();

        // Create the Especialidade

        restEspecialidadeMockMvc.perform(post("/api/especialidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(especialidade)))
                .andExpect(status().isCreated());

        // Validate the Especialidade in the database
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        assertThat(especialidades).hasSize(databaseSizeBeforeCreate + 1);
        Especialidade testEspecialidade = especialidades.get(especialidades.size() - 1);
        assertThat(testEspecialidade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = especialidadeRepository.findAll().size();
        // set the field null
        especialidade.setDescricao(null);

        // Create the Especialidade, which fails.

        restEspecialidadeMockMvc.perform(post("/api/especialidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(especialidade)))
                .andExpect(status().isBadRequest());

        List<Especialidade> especialidades = especialidadeRepository.findAll();
        assertThat(especialidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEspecialidades() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get all the especialidades
        restEspecialidadeMockMvc.perform(get("/api/especialidades?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(especialidade.getId().intValue())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getEspecialidade() throws Exception {
        // Initialize the database
        especialidadeRepository.saveAndFlush(especialidade);

        // Get the especialidade
        restEspecialidadeMockMvc.perform(get("/api/especialidades/{id}", especialidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(especialidade.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEspecialidade() throws Exception {
        // Get the especialidade
        restEspecialidadeMockMvc.perform(get("/api/especialidades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspecialidade() throws Exception {
        // Initialize the database
        especialidadeService.save(especialidade);

        int databaseSizeBeforeUpdate = especialidadeRepository.findAll().size();

        // Update the especialidade
        Especialidade updatedEspecialidade = especialidadeRepository.findOne(especialidade.getId());
        updatedEspecialidade
                .descricao(UPDATED_DESCRICAO);

        restEspecialidadeMockMvc.perform(put("/api/especialidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEspecialidade)))
                .andExpect(status().isOk());

        // Validate the Especialidade in the database
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        assertThat(especialidades).hasSize(databaseSizeBeforeUpdate);
        Especialidade testEspecialidade = especialidades.get(especialidades.size() - 1);
        assertThat(testEspecialidade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void deleteEspecialidade() throws Exception {
        // Initialize the database
        especialidadeService.save(especialidade);

        int databaseSizeBeforeDelete = especialidadeRepository.findAll().size();

        // Get the especialidade
        restEspecialidadeMockMvc.perform(delete("/api/especialidades/{id}", especialidade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        assertThat(especialidades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
