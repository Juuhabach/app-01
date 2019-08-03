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

import br.upf.fabsoft.jmkidsmodel.nuvem.FormaPagamento;
import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.util.StringOptionsValid;

@Entity
@IdClass(CaixaMovimentoDoisId.class)
@Table(schema = "quiosque")
public class CaixaMovimentoDois implements Serializable {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "CaixaMovimentoDoisID")
	@SequenceGenerator(name = "CaixaMovimentoDoisID", sequenceName = "CaixaMovimentoDoisID", allocationSize = 1, schema = "quiosque")
	private Long id = 0L;
	@Id
	@ManyToOne(optional = false)
	@NotNull
	@JoinColumns({ @JoinColumn(name = "caixa_id", referencedColumnName = "id"),
			@JoinColumn(name = "quiosque_id", referencedColumnName = "quiosque_id") })
	private Caixa caixa;
	@ManyToOne(optional = false)
	@NotNull
	private FormaPagamento formaPagamento;
	@ManyToOne
	@NotNull
	private Funcionario funcionario;
	@StringOptionsValid(message = "Operação inválida!", opcoes = { "Recebimento", "Pagamento", "Entrada", "Saida" })
	@Length(max = 30)
	@Column(length = 30)
	private String operacao;
	@NotNull
	private Double valor = 0D;
	@NotEmpty
	@Length(min = 2, max = 100)
	@Column(length = 100)
	private String descricao = "";
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;
	@Version
	private Integer versao;

	public CaixaMovimentoDois() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
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
		CaixaMovimentoDois other = (CaixaMovimentoDois) obj;
		if (caixa == null) {
			if (other.caixa != null)
				return false;
		} else if (!caixa.equals(other.caixa))
			return false;
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
		result = prime * result + ((caixa == null) ? 0 : caixa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}