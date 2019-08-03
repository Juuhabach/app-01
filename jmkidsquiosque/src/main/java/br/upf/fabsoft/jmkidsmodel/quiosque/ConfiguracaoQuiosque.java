package br.upf.fabsoft.jmkidsmodel.quiosque;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(schema = "quiosque")
public class ConfiguracaoQuiosque implements Serializable {

	@Id
	private Long id;
	@NotNull
	@Length(max = 200)
	@Column(length = 200)
	private String urlIntegracao;
	@NotNull
	@Min(value = 1)
	private Long idQuiosque;
	@NotNull
	@Min(value = 1)
	private Integer tempoParaSincronizacao;
	@NotNull
	@Length(max = 60)
	@Column(length = 60)
	private String chaveQuiosque;
	@Version
	private Integer versao;

	public ConfiguracaoQuiosque() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrlIntegracao() {
		return urlIntegracao;
	}

	public void setUrlIntegracao(String urlIntegracao) {
		this.urlIntegracao = urlIntegracao;
	}

	public Long getIdQuiosque() {
		return idQuiosque;
	}

	public void setIdQuiosque(Long idQuiosque) {
		this.idQuiosque = idQuiosque;
	}

	public String getChaveQuiosque() {
		return chaveQuiosque;
	}

	public void setChaveQuiosque(String chaveQuiosque) {
		this.chaveQuiosque = chaveQuiosque;
	}

	public Integer getTempoParaSincronizacao() {
		return tempoParaSincronizacao;
	}

	public void setTempoParaSincronizacao(Integer tempoParaSincronizacao) {
		this.tempoParaSincronizacao = tempoParaSincronizacao;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfiguracaoQuiosque other = (ConfiguracaoQuiosque) obj;
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