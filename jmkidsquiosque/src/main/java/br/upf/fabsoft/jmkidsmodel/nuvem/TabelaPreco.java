package br.upf.fabsoft.jmkidsmodel.nuvem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.CascadeType.ALL;

@Entity
@Table(schema = "nuvem")
public class TabelaPreco implements Serializable {
	@Id
	// @GeneratedValue(strategy = SEQUENCE, generator = "TabelaPrecoID")
	// @SequenceGenerator(name = "TabelaPrecoID", sequenceName = "TabelaPrecoID",
	// allocationSize = 1, schema = "nuvem")
	private Long id;
	@NotEmpty
	@Length(min = 2, max = 60)
	@Column(length = 60)
	private String descricao;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date dataHoraInicio;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date dataHoraFim;
	@NotNull
	private Integer tempoFlexibilizacao = 0;
	@NotNull
	private Float valorMinutoAdicional = 0F;
	@NotNull
	private Boolean geral;
	@OneToMany(fetch = EAGER, orphanRemoval = true, cascade = ALL, mappedBy = "tabelaPreco")
	private List<TabelaPrecoTempo> tempos;
	@ManyToOne(optional = true)
	private Quiosque quiosque;
	// @Version
	private Integer versao;

	public TabelaPreco() {
		super();
		tempos = new ArrayList<TabelaPrecoTempo>();
	}

	public TabelaPreco(Long id, @NotEmpty @Length(min = 2, max = 60) String descricao, Date dataHoraInicio,
			Date dataHoraFim, @NotNull Integer tempoFlexibilizacao, @NotNull Float valorMinutoAdicional,
			@NotNull Boolean geral, List<TabelaPrecoTempo> tempos, Quiosque quiosque, Integer versao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.tempoFlexibilizacao = tempoFlexibilizacao;
		this.valorMinutoAdicional = valorMinutoAdicional;
		this.geral = geral;
		this.tempos = tempos;
		this.quiosque = quiosque;
		this.versao = versao;
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

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public Integer getTempoFlexibilizacao() {
		return tempoFlexibilizacao;
	}

	public void setTempoFlexibilizacao(Integer tempoFlexibilizacao) {
		this.tempoFlexibilizacao = tempoFlexibilizacao;
	}

	public Float getValorMinutoAdicional() {
		return valorMinutoAdicional;
	}

	public void setValorMinutoAdicional(Float valorMinutoAdicional) {
		this.valorMinutoAdicional = valorMinutoAdicional;
	}

	public Boolean getGeral() {
		return geral;
	}

	public void setGeral(Boolean geral) {
		this.geral = geral;
	}

	public List<TabelaPrecoTempo> getTempos() {
		return tempos;
	}

	public void setTempos(List<TabelaPrecoTempo> tempos) {
		this.tempos = tempos;
	}

	public Quiosque getQuiosque() {
		return quiosque;
	}

	public void setQuiosque(Quiosque quiosque) {
		this.quiosque = quiosque;
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
		TabelaPreco other = (TabelaPreco) obj;
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