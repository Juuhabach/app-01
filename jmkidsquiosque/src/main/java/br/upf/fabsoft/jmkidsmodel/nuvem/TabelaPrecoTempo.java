package br.upf.fabsoft.jmkidsmodel.nuvem;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(schema = "nuvem")
public class TabelaPrecoTempo implements Serializable {

	@Id
	// @GeneratedValue(strategy = SEQUENCE, generator = "TabelaPrecoTempoID")
	// @SequenceGenerator(name = "TabelaPrecoTempoID", sequenceName =
	// "TabelaPrecoTempoID", allocationSize = 1, schema = "nuvem")
	private Long id;
	@ManyToOne(optional = false)
	@JsonIgnore
	private TabelaPreco tabelaPreco;
	@NotNull
	private Integer minutos;
	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private Double valor;
	// @Version
	private Integer versao;

	public TabelaPrecoTempo() {
		super();
	}

	public TabelaPrecoTempo(Long id, TabelaPreco tabelaPreco, @NotNull Integer minutos,
			@NotNull @DecimalMin(value = "0", inclusive = false) Double valor, Integer versao) {
		super();
		this.id = id;
		this.tabelaPreco = tabelaPreco;
		this.minutos = minutos;
		this.valor = valor;
		this.versao = versao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMinutos() {
		return minutos;
	}

	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public TabelaPreco getTabelaPreco() {
		return tabelaPreco;
	}

	public void setTabelaPreco(TabelaPreco tabelaPreco) {
		this.tabelaPreco = tabelaPreco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabelaPrecoTempo other = (TabelaPrecoTempo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}