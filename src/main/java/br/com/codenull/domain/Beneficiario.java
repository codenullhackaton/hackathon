package br.com.codenull.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Beneficiario.
 */
@Entity
@Table(name = "beneficiario")
public class Beneficiario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "endereco")
    private String endereco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Beneficiario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Beneficiario endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Beneficiario beneficiario = (Beneficiario) o;
        if(beneficiario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, beneficiario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Beneficiario{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", endereco='" + endereco + "'" +
            '}';
    }
}
