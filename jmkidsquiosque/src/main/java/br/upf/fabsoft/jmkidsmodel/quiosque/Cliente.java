package br.upf.fabsoft.jmkidsmodel.quiosque;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.upf.fabsoft.jmkidsmodel.nuvem.GrupoCliente;
import br.upf.fabsoft.jmkidsmodel.util.StringOptionsValid;

@Entity
@Table(schema = "quiosque")
public class Cliente implements Serializable {

	@Id
	@Length(max = 20)
	@Column(length = 20)
	private String cpfOuCnpj;
	@Length(max = 2)
	@StringOptionsValid(opcoes = {"PF", "PJ"})
	@Column(length = 2)
	private String pfOuPj;
	@NotEmpty
	@Length(min=2, max=80)
	@Column(length = 80)
	private String nome;
	@Length(max=120)
	@Column(length = 120)
	private String email;
	@Length(max=20)
	@Column(length = 20)
	private String telefoneFixo;
	@Length(max=20)
	@Column(length = 20)
	private String celular;
	@Length(max=120)
	@Column(length = 120)
	private String endereco;
	@Length(max=20)
	@Column(length = 20)
	private String numero;
	@Length(max=50)
	@Column(length = 50)
	private String complemento;
	@Length(max=50)
	@Column(length = 50)
	private String bairro;
	@Length(max=10) 
	@Column(length = 10)
	private String cep;
	@Length(max=60)
	@Column(length = 60)
	private String nomeCidade;
	@Length(max=2)
	@Column(length = 2)
	private String ufCidade;
	@Temporal(TemporalType.DATE)
	private Date nascimento;
	@NotNull
	@ManyToOne
	private GrupoCliente grupoCliente;
	@Column(insertable = true, updatable = false)
	@NotNull
	private Integer numLocacoes = 0;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraModificado;

	@Version
	private Integer versao;

	public Cliente() {
		super();
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getPfOuPj() {
		return pfOuPj;
	}

	public void setPfOuPj(String pfOuPj) {
		this.pfOuPj = pfOuPj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
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

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public GrupoCliente getGrupoCliente() {
		return grupoCliente;
	}

	public void setGrupoCliente(GrupoCliente grupoCliente) {
		this.grupoCliente = grupoCliente;
	}

	public Integer getNumLocacoes() {
		return numLocacoes;
	}

	public void setNumLocacoes(Integer numLocacoes) {
		this.numLocacoes = numLocacoes;
	}

	public Date getDataHoraModificado() {
		return dataHoraModificado;
	}

	public void setDataHoraModificado(Date dataHoraModificado) {
		this.dataHoraModificado = dataHoraModificado;
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
		Cliente other = (Cliente) obj;
		if (cpfOuCnpj == null) {
			if (other.cpfOuCnpj != null)
				return false;
		} else if (!cpfOuCnpj.equals(other.cpfOuCnpj))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpfOuCnpj == null) ? 0 : cpfOuCnpj.hashCode());
		return result;
	}
}