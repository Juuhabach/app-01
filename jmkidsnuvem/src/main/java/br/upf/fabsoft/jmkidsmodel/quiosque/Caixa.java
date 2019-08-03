package br.upf.fabsoft.jmkidsmodel.quiosque;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;

@Entity
@NamedQueries({
	@NamedQuery(name = "Abertos", query = "select c from Caixa c where c.dataHoraFechamento is null"),
	@NamedQuery(name = "Fechados", query = "select c from Caixa c where c.dataHoraFechamento is not null"),
	@NamedQuery(name = "Todos", query = "select c from Caixa c")
})
@IdClass(CaixaId.class)
@Table(schema = "quiosque")
public class Caixa implements Serializable {

	@Id
	//@GeneratedValue(strategy = SEQUENCE, generator = "CaixaID")
	//@SequenceGenerator(name = "CaixaID", sequenceName = "CaixaID", allocationSize = 1, schema = "quiosque")
	private Long id = 0L;
	
	@Id
	@ManyToOne(optional = false)
	@NotNull
	private Quiosque quiosque;
	@ManyToOne
	@NotNull
	private Funcionario funcionarioAbriu;
	@ManyToOne
	private Funcionario funcionarioFechou;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date dataHoraAbertura = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraFechamento;
	@NotNull
	@DecimalMin(value="0", inclusive=true)
	private Double valorAbertura = 0D;
	@DecimalMin(value="0", inclusive=true)
	private Double valorFechamento = 0D;
	@DecimalMin(value="0", inclusive=true)
	private Double valorContagem = 0D;
	private Double valorDiferenca = 0D;
	@Lob
	private String observacao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caixa")
	private List<CaixaMovimento> movimentos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caixa")
	private List<CaixaMovimentoDois> movimentosDois;	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caixa")
	private List<BrinquedoOperacao> operacoes;

	
	//@Version
	private Integer versao;
	
	public Caixa() {
		super();
	}

	public Date getDataHoraAbertura() {
		return dataHoraAbertura;
	}

	public void setDataHoraAbertura(Date dataHoraAbertura) {
		this.dataHoraAbertura = dataHoraAbertura;
	}

	public Date getDataHoraFechamento() {
		return dataHoraFechamento;
	}

	public void setDataHoraFechamento(Date dataHoraFechamento) {
		this.dataHoraFechamento = dataHoraFechamento;
	}

	public Double getValorAbertura() {
		return valorAbertura;
	}

	public void setValorAbertura(Double valorAbertura) {
		this.valorAbertura = valorAbertura;
	}

	public Double getValorFechamento() {
		return valorFechamento;
	}

	public void setValorFechamento(Double valorFechamento) {
		this.valorFechamento = valorFechamento;
	}

	public Double getValorContagem() {
		return valorContagem;
	}

	public void setValorContagem(Double valorContagem) {
		this.valorContagem = valorContagem;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public Quiosque getQuiosque() {
		return quiosque;
	}

	public void setQuiosque(Quiosque quiosque) {
		this.quiosque = quiosque;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((quiosque == null) ? 0 : quiosque.hashCode());
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
		Caixa other = (Caixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (quiosque == null) {
			if (other.quiosque != null)
				return false;
		} else if (!quiosque.equals(other.quiosque))
			return false;
		return true;
	}


	public Double getValorDiferenca() {
		valorDiferenca = valorContagem - valorFechamento;
		return valorDiferenca;
	}


	public void setValorDiferenca(Double valorDiferenca) {
		this.valorDiferenca = valorContagem - valorFechamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Funcionario getFuncionarioAbriu() {
		return funcionarioAbriu;
	}

	public void setFuncionarioAbriu(Funcionario funcionarioAbriu) {
		this.funcionarioAbriu = funcionarioAbriu;
	}

	public Funcionario getFuncionarioFechou() {
		return funcionarioFechou;
	}

	public void setFuncionarioFechou(Funcionario funcionarioFechou) {
		this.funcionarioFechou = funcionarioFechou;
	}

	public List<CaixaMovimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<CaixaMovimento> movimentos) {
		this.movimentos = movimentos;
	}

	public List<BrinquedoOperacao> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(List<BrinquedoOperacao> operacoes) {
		this.operacoes = operacoes;
	}

	public List<CaixaMovimentoDois> getMovimentosDois() {
		return movimentosDois;
	}

	public void setMovimentosDois(List<CaixaMovimentoDois> movimentosDois) {
		this.movimentosDois = movimentosDois;
	}


}