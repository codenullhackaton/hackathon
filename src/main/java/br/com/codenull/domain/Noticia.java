package br.com.codenull.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Noticia.
 */
@Entity
@Table(name = "noticia")
public class Noticia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Column(name = "criado_em", nullable = false)
    private LocalDate criadoEm;

    @NotNull
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Noticia titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getCriadoEm() {
        return criadoEm;
    }

    public Noticia criadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
        return this;
    }

    public void setCriadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Noticia conteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Noticia noticia = (Noticia) o;
        if(noticia.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, noticia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Noticia{" +
            "id=" + id +
            ", titulo='" + titulo + "'" +
            ", criadoEm='" + criadoEm + "'" +
            ", conteudo='" + conteudo + "'" +
            '}';
    }
}
