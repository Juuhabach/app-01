package br.upf.fabsoft.jmkidsmodel.nuvem;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(schema = "nuvem")
public class Promocao implements Serializable {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "PromocaoID")
	@SequenceGenerator(name = "PromocaoID", sequenceName = "PromocaoID", allocationSize = 1, schema = "nuvem")
	private Long id;
	
	@NotEmpty
	@Length(min=2, max=60)
	@Column(length = 60)
	private String descricao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraInicio;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraFim;
	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	@DecimalMax(value = "100", inclusive = true)
	private Float percentualDesconto;
	@Length(min=18, max=18) 
	@Column(length = 18)
	private String cnpj;
	@Length(min=14, max=14)
	@Column(length = 14)
	private String cpf;
	
	private Integer diaDaSemana;
	@NotNull
	private Boolean geral;
	@ManyToOne
	private GrupoCliente grupoCliente;
	@Version
	private Integer versao;
	
	
	public Promocao() {
		super();
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


	public Float getPercentualDesconto() {
		return percentualDesconto;
	}


	public void setPercentualDesconto(Float percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}


	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public Integer getDiaDaSemana() {
		return diaDaSemana;
	}


	public void setDiaDaSemana(Integer diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}


	public Boolean getGeral() {
		return geral;
	}


	public void setGeral(Boolean geral) {
		this.geral = geral;
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
		Promocao other = (Promocao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public GrupoCliente getGrupoCliente() {
		return grupoCliente;
	}


	public void setGrupoCliente(GrupoCliente grupoCliente) {
		this.grupoCliente = grupoCliente;
	}
   
}
