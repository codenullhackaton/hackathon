package br.com.codenull.web.rest;

import br.com.codenull.HackathonApp;
import br.com.codenull.domain.Noticia;
import br.com.codenull.repository.NoticiaRepository;
import br.com.codenull.service.NoticiaService;

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
 * Test class for the NoticiaResource REST controller.
 *
 * @see NoticiaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackathonApp.class)
public class NoticiaResourceIntTest {
    private static final String DEFAULT_TITULO = "AAAAA";
    private static final String UPDATED_TITULO = "BBBBB";

    private static final LocalDate DEFAULT_CRIADO_EM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CRIADO_EM = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CONTEUDO = "AAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBB";

    @Inject
    private NoticiaRepository noticiaRepository;

    @Inject
    private NoticiaService noticiaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restNoticiaMockMvc;

    private Noticia noticia;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NoticiaResource noticiaResource = new NoticiaResource();
        ReflectionTestUtils.setField(noticiaResource, "noticiaService", noticiaService);
        this.restNoticiaMockMvc = MockMvcBuilders.standaloneSetup(noticiaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noticia createEntity(EntityManager em) {
        Noticia noticia = new Noticia();
        noticia = new Noticia()
                .titulo(DEFAULT_TITULO)
                .criadoEm(DEFAULT_CRIADO_EM)
                .conteudo(DEFAULT_CONTEUDO);
        return noticia;
    }

    @Before
    public void initTest() {
        noticia = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoticia() throws Exception {
        int databaseSizeBeforeCreate = noticiaRepository.findAll().size();

        // Create the Noticia

        restNoticiaMockMvc.perform(post("/api/noticias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noticia)))
                .andExpect(status().isCreated());

        // Validate the Noticia in the database
        List<Noticia> noticias = noticiaRepository.findAll();
        assertThat(noticias).hasSize(databaseSizeBeforeCreate + 1);
        Noticia testNoticia = noticias.get(noticias.size() - 1);
        assertThat(testNoticia.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testNoticia.getCriadoEm()).isEqualTo(DEFAULT_CRIADO_EM);
        assertThat(testNoticia.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setTitulo(null);

        // Create the Noticia, which fails.

        restNoticiaMockMvc.perform(post("/api/noticias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noticia)))
                .andExpect(status().isBadRequest());

        List<Noticia> noticias = noticiaRepository.findAll();
        assertThat(noticias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCriadoEmIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setCriadoEm(null);

        // Create the Noticia, which fails.

        restNoticiaMockMvc.perform(post("/api/noticias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noticia)))
                .andExpect(status().isBadRequest());

        List<Noticia> noticias = noticiaRepository.findAll();
        assertThat(noticias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConteudoIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticiaRepository.findAll().size();
        // set the field null
        noticia.setConteudo(null);

        // Create the Noticia, which fails.

        restNoticiaMockMvc.perform(post("/api/noticias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(noticia)))
                .andExpect(status().isBadRequest());

        List<Noticia> noticias = noticiaRepository.findAll();
        assertThat(noticias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNoticias() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        // Get all the noticias
        restNoticiaMockMvc.perform(get("/api/noticias?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(noticia.getId().intValue())))
                .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
                .andExpect(jsonPath("$.[*].criadoEm").value(hasItem(DEFAULT_CRIADO_EM.toString())))
                .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())));
    }

    @Test
    @Transactional
    public void getNoticia() throws Exception {
        // Initialize the database
        noticiaRepository.saveAndFlush(noticia);

        // Get the noticia
        restNoticiaMockMvc.perform(get("/api/noticias/{id}", noticia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(noticia.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.criadoEm").value(DEFAULT_CRIADO_EM.toString()))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNoticia() throws Exception {
        // Get the noticia
        restNoticiaMockMvc.perform(get("/api/noticias/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoticia() throws Exception {
        // Initialize the database
        noticiaService.save(noticia);

        int databaseSizeBeforeUpdate = noticiaRepository.findAll().size();

        // Update the noticia
        Noticia updatedNoticia = noticiaRepository.findOne(noticia.getId());
        updatedNoticia
                .titulo(UPDATED_TITULO)
                .criadoEm(UPDATED_CRIADO_EM)
                .conteudo(UPDATED_CONTEUDO);

        restNoticiaMockMvc.perform(put("/api/noticias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedNoticia)))
                .andExpect(status().isOk());

        // Validate the Noticia in the database
        List<Noticia> noticias = noticiaRepository.findAll();
        assertThat(noticias).hasSize(databaseSizeBeforeUpdate);
        Noticia testNoticia = noticias.get(noticias.size() - 1);
        assertThat(testNoticia.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testNoticia.getCriadoEm()).isEqualTo(UPDATED_CRIADO_EM);
        assertThat(testNoticia.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
    }

    @Test
    @Transactional
    public void deleteNoticia() throws Exception {
        // Initialize the database
        noticiaService.save(noticia);

        int databaseSizeBeforeDelete = noticiaRepository.findAll().size();

        // Get the noticia
        restNoticiaMockMvc.perform(delete("/api/noticias/{id}", noticia.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Noticia> noticias = noticiaRepository.findAll();
        assertThat(noticias).hasSize(databaseSizeBeforeDelete - 1);
    }
}
