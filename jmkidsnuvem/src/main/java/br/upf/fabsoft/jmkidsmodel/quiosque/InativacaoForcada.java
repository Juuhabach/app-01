package br.upf.fabsoft.jmkidsmodel.quiosque;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;

/**
 * Entity implementation class for Entity: InativacaoForcada
 *
 */
@Entity
@IdClass(InativacaoForcadaId.class)
@Table(schema = "quiosque")
public class InativacaoForcada implements Serializable {

	@Id
	//@GeneratedValue(strategy = SEQUENCE, generator = "InativacaoForcadaID")
	//@SequenceGenerator(name = "InativacaoForcadaID", sequenceName = "InativacaoForcadaID", allocationSize = 1, schema = "quiosque")
	private Long id = 0L;
	
	@Id
	@ManyToOne(optional=false)
	@NotNull
	@JoinColumns({
		@JoinColumn(name = "brinquedoOperacao_id", referencedColumnName = "id", nullable = false),
		@JoinColumn(name = "quiosque_id", referencedColumnName = "quiosque_id", nullable = false),
		@JoinColumn(name = "caixa_id", referencedColumnName = "caixa_id", nullable = false)})
	private BrinquedoOperacao brinquedoOperacao;
	
	@ManyToOne
	@NotNull
	private Funcionario funcionario;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;
	@Length(min=5, max=150)
	@NotEmpty
	@Column(length = 150)
	private String observacao;
	//@Version
	private Integer versao;
		
	public InativacaoForcada() {
		super();
	}

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BrinquedoOperacao getBrinquedoOperacao() {
		return brinquedoOperacao;
	}

	public void setBrinquedoOperacao(BrinquedoOperacao brinquedoOperacao) {
		this.brinquedoOperacao = brinquedoOperacao;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brinquedoOperacao == null) ? 0 : brinquedoOperacao.hashCode());
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
		InativacaoForcada other = (InativacaoForcada) obj;
		if (brinquedoOperacao == null) {
			if (other.brinquedoOperacao != null)
				return false;
		} else if (!brinquedoOperacao.equals(other.brinquedoOperacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
  
}
