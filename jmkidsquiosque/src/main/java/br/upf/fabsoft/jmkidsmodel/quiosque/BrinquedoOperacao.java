package br.upf.fabsoft.jmkidsmodel.quiosque;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.upf.fabsoft.jmkidsmodel.nuvem.Brinquedo;
import br.upf.fabsoft.jmkidsmodel.nuvem.FormaPagamento;
import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPrecoTempo;

@Entity
@IdClass(BrinquedoOperacaoId.class)
@Table(schema = "quiosque")
public class BrinquedoOperacao implements Serializable {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "BrinquedoOperacaoID")
	@SequenceGenerator(name = "BrinquedoOperacaoID", sequenceName = "BrinquedoOperacaoID", allocationSize = 1, schema = "quiosque")
	private Long id = 0L;
	@Id
	@ManyToOne
	@NotNull
	@JoinColumns({ @JoinColumn(name = "caixa_id", referencedColumnName = "id", nullable = false),
			@JoinColumn(name = "quiosque_id", referencedColumnName = "quiosque_id", nullable = false) })
	private Caixa caixa;
	@ManyToOne(optional = true)
	private TabelaPrecoTempo tabelaPrecoTempo;
	@ManyToOne(optional = true)
	private Cliente cliente;
	@ManyToOne(optional = false)
	@NotNull
	private Brinquedo brinquedo;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brinquedoOperacao")
	private List<InativacaoForcada> inativacaoForcada;
	@ManyToOne
	private Funcionario funcionarioAtivou;
	@ManyToOne
	private Funcionario funcionarioInativou;
	@ManyToOne
	private Funcionario funcionarioCancelou;
	@ManyToOne
	private Funcionario funcionarioPausou;
	@ManyToOne(optional = true)
	private FormaPagamento formaPrePago;
	@ManyToOne(optional = true)
	private FormaPagamento formaPosPago;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraContratado = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraAtivacao;
	@Length(max = 150)
	@Column(length = 150)
	private String dadosSemCadastro;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraInativacao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraCancelamento;
	@Length(max = 150)
	@Column(length = 150)
	private String justificativaCancelamento = "";
	@NotNull
	@Min(value = 0)
	private Integer minutosContratados = 0;
	@NotNull
	@Min(value = 0)
	private Integer minutosDecorridos = 0;
	@NotNull
	private Integer minutosDiferenca = 0;
	@NotNull
	@Min(value = 0)
	private Integer tempoFlexibilizacao = 0;
	@NotNull
	@DecimalMin(value = "0", inclusive = true)
	private Double valorPrePago = 0D;
	@NotNull
	@DecimalMin(value = "0", inclusive = true)
	private Double valorPosPago = 0D;
	@NotNull
	@DecimalMin(value = "0", inclusive = true)
	private Double valorDesconto = 0D;
	@Length(max = 150)
	@Column(length = 150)
	private String justificativaDesconto = "";
	@NotNull
	@DecimalMin(value = "0", inclusive = true)
	private Double valorCreditoConcedido = 0D;
	@Length(max = 150)
	@Column(length = 150)
	private String justificativaCredito = "";
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraEmPausa;
	@NotNull
	private Integer tempoEmPausa = 0;
	@Column(insertable = false, updatable = false, columnDefinition = "text")
	private String log = "";
	@Version
	private Integer versao;

	public BrinquedoOperacao() {
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

	public TabelaPrecoTempo getTabelaPrecoTempo() {
		return tabelaPrecoTempo;
	}

	public void setTabelaPrecoTempo(TabelaPrecoTempo tabelaPrecoTempo) {
		this.tabelaPrecoTempo = tabelaPrecoTempo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Brinquedo getBrinquedo() {
		return brinquedo;
	}

	public void setBrinquedo(Brinquedo brinquedo) {
		this.brinquedo = brinquedo;
	}

	public List<InativacaoForcada> getInativacaoForcada() {
		return inativacaoForcada;
	}

	public void setInativacaoForcada(List<InativacaoForcada> inativacaoForcada) {
		this.inativacaoForcada = inativacaoForcada;
	}

	public Funcionario getFuncionarioAtivou() {
		return funcionarioAtivou;
	}

	public void setFuncionarioAtivou(Funcionario funcionarioAtivou) {
		this.funcionarioAtivou = funcionarioAtivou;
	}

	public Funcionario getFuncionarioInativou() {
		return funcionarioInativou;
	}

	public void setFuncionarioInativou(Funcionario funcionarioInativou) {
		this.funcionarioInativou = funcionarioInativou;
	}

	public Funcionario getFuncionarioCancelou() {
		return funcionarioCancelou;
	}

	public void setFuncionarioCancelou(Funcionario funcionarioCancelou) {
		this.funcionarioCancelou = funcionarioCancelou;
	}

	public Funcionario getFuncionarioPausou() {
		return funcionarioPausou;
	}

	public void setFuncionarioPausou(Funcionario funcionarioPausou) {
		this.funcionarioPausou = funcionarioPausou;
	}

	public FormaPagamento getFormaPrePago() {
		return formaPrePago;
	}

	public void setFormaPrePago(FormaPagamento formaPrePago) {
		this.formaPrePago = formaPrePago;
	}

	public FormaPagamento getFormaPosPago() {
		return formaPosPago;
	}

	public void setFormaPosPago(FormaPagamento formaPosPago) {
		this.formaPosPago = formaPosPago;
	}

	public Date getDataHoraContratado() {
		return dataHoraContratado;
	}

	public void setDataHoraContratado(Date dataHoraContratado) {
		this.dataHoraContratado = dataHoraContratado;
	}

	public Date getDataHoraAtivacao() {
		return dataHoraAtivacao;
	}

	public void setDataHoraAtivacao(Date dataHoraAtivacao) {
		this.dataHoraAtivacao = dataHoraAtivacao;
	}

	public String getDadosSemCadastro() {
		return dadosSemCadastro;
	}

	public void setDadosSemCadastro(String dadosSemCadastro) {
		this.dadosSemCadastro = dadosSemCadastro;
	}

	public Date getDataHoraInativacao() {
		return dataHoraInativacao;
	}

	public void setDataHoraInativacao(Date dataHoraInativacao) {
		this.dataHoraInativacao = dataHoraInativacao;
	}

	public Date getDataHoraCancelamento() {
		return dataHoraCancelamento;
	}

	public void setDataHoraCancelamento(Date dataHoraCancelamento) {
		this.dataHoraCancelamento = dataHoraCancelamento;
	}

	public String getJustificativaCancelamento() {
		return justificativaCancelamento;
	}

	public void setJustificativaCancelamento(String justificativaCancelamento) {
		this.justificativaCancelamento = justificativaCancelamento;
	}

	public Integer getMinutosContratados() {
		return this.minutosContratados;
	}

	public void setMinutosContratados(Integer minutosContratados) {
		this.minutosContratados = minutosContratados;
	}

	public Integer getMinutosDecorridos() {
		return this.minutosDecorridos;
	}

	public void setMinutosDecorridos(Integer minutosDecorridos) {
		this.minutosDecorridos = minutosDecorridos;
	}

	public Integer getMinutosDiferenca() {
		return minutosDiferenca;
	}

	public void setMinutosDiferenca(Integer minutosDiferenca) {
		this.minutosDiferenca = minutosDiferenca;
	}

	public Integer getTempoFlexibilizacao() {
		return this.tempoFlexibilizacao;
	}

	public void setTempoFlexibilizacao(Integer tempoFlexibilizacao) {
		this.tempoFlexibilizacao = tempoFlexibilizacao;
	}

	public Double getValorPrePago() {
		return this.valorPrePago;
	}

	public void setValorPrePago(Double valorPrePago) {
		this.valorPrePago = valorPrePago;
	}

	public Double getValorPosPago() {
		return this.valorPosPago;
	}

	public void setValorPosPago(Double valorPosPago) {
		this.valorPosPago = valorPosPago;
	}

	public Double getValorDesconto() {
		return this.valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public String getJustificativaDesconto() {
		return this.justificativaDesconto;
	}

	public void setJustificativaDesconto(String justificativaDesconto) {
		this.justificativaDesconto = justificativaDesconto;
	}

	public Double getValorCreditoConcedido() {
		return this.valorCreditoConcedido;
	}

	public void setValorCreditoConcedido(Double valorCreditoConcedido) {
		this.valorCreditoConcedido = valorCreditoConcedido;
	}

	public String getJustificativaCredito() {
		return this.justificativaCredito;
	}

	public void setJustificativaCredito(String justificativaCredito) {
		this.justificativaCredito = justificativaCredito;
	}

	public Date getDataHoraEmPausa() {
		return dataHoraEmPausa;
	}

	public void setDataHoraEmPausa(Date dataHoraEmPausa) {
		this.dataHoraEmPausa = dataHoraEmPausa;
	}

	public Integer getTempoEmPausa() {
		return tempoEmPausa;
	}

	public void setTempoEmPausa(Integer tempoEmPausa) {
		this.tempoEmPausa = tempoEmPausa;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
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
		BrinquedoOperacao other = (BrinquedoOperacao) obj;
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