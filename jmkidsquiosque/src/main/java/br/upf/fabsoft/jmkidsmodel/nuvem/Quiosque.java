package br.upf.fabsoft.jmkidsmodel.nuvem;

import java.lang.String;
import java.lang.Integer;
import java.lang.Long;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(schema = "nuvem")
public class Quiosque implements Serializable {
	@Id
	// @GeneratedValue(strategy = SEQUENCE, generator = "QuiosqueID")
	// @SequenceGenerator(name = "QuiosqueID", sequenceName = "QuiosqueID",
	// allocationSize = 1, schema = "nuvem")
	private Long id;
	@NotEmpty
	@Length(min = 2, max = 60)
	@Column(length = 60)
	private String descricao;
	@Length(max = 100)
	@Column(length = 100)
	private String endereco;
	@Length(max = 20)
	@Column(length = 20)
	private String numero;
	@Length(max = 80)
	@Column(length = 80)
	private String complemento;
	@Length(max = 60)
	@Column(length = 60)
	private String bairro;
	@Length(max = 10)
	@Column(length = 10)
	private String cep;
	@Length(max = 60)
	@Column(length = 60)
	private String nomeCidade;
	@Length(max = 2)
	@Column(length = 2)
	private String ufCidade;
	@Length(max = 60)
	@Column(length = 60)
	private String local;
	@Length(max = 60)
	@Column(length = 60)
	private String chave;
	// @Version
	private Integer versao;

	public Quiosque() {
		super();
	}

	public Quiosque(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public String getUfCidade() {
		return ufCidade;
	}

	public void setUfCidade(String ufCidade) {
		this.ufCidade = ufCidade;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	@Override
	public String toString() {
		return "Quiosque [id=" + id + ", descricao=" + descricao + ", endereco=" + endereco + ", numero=" + numero
				+ ", complemento=" + complemento + ", bairro=" + bairro + ", cep=" + cep + ", nomeCidade=" + nomeCidade
				+ ", ufCidade=" + ufCidade + ", local=" + local + ", chave=" + chave + ", versao=" + versao + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quiosque other = (Quiosque) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}