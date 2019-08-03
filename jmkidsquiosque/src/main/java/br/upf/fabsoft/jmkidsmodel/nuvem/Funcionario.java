package br.upf.fabsoft.jmkidsmodel.nuvem;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;
import java.lang.Integer;
import java.lang.String;
import java.lang.Long;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(schema = "nuvem")
public class Funcionario implements Serializable {

	@Id
	// @GeneratedValue(strategy = SEQUENCE, generator = "FuncionarioID")
	// @SequenceGenerator(name = "FuncionarioID", sequenceName = "FuncionarioID",
	// allocationSize = 1, schema = "nuvem")
	private Long id;
	@NotEmpty(message = "O nome deve ser informado!")
	@Length(min = 2, max = 60, message = "O nome deve ter entre {min} e {max} caracteres!")
	private String nome;
	@NotEmpty(message = "O email deve ser informado!")
	@Column(nullable = false, unique = true, length = 150)
	@Length(max = 150)
	private String email;
	@Length(max = 64)
	@Column(length = 64)
	private String senha;
	@Length(max = 100)
	@Column(length = 100)
	private String endereco;
	@Length(max = 20)
	@Column(length = 20)
	private String numero;
	@Length(max = 50)
	@Column(length = 50)
	private String complemento;
	@Length(max = 50)
	@Column(length = 50)
	private String bairro;
	@Length(max = 10)
	@Column(length = 10)
	private String cep;
	@Length(max = 50)
	@Column(length = 50)
	private String nomeCidade;
	@Length(max = 2)
	@Column(length = 20)
	private String ufCidade;
	@NotEmpty
	@Length(min = 5, max = 15, message = "O número do RG possui {max} algarismos.")
	@Column(length = 15)
	private String rg;
	@NotEmpty
	@Length(min = 14, max = 14, message = "O número do CPF possui {max} algarismos.")
	@CPF
	@Column(unique = true, length = 14)
	private String cpf;
	@NotEmpty
	@Length(min = 2, max = 10, message = "O número da CTPS aceita de {min} a {max} algarismos.")
	@Column(length = 10)
	private String ctpsNumero;
	@NotEmpty
	@Length(min = 2, max = 10, message = "A série da CTPS aceita de {min} a {max} algarismos.")
	@Column(length = 10)
	private String ctpsSerie;
	@Length(max = 20)
	@Column(length = 20)
	private String foneUm;
	@Length(max = 20)
	@Column(length = 20)
	private String foneDois;
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataAdmissao;
	@Temporal(TemporalType.DATE)
	private Date dataDemissao;
	@Basic(optional = true)
	@Column(name = "foto", columnDefinition = "BYTEA")
	private byte[] foto;
	// @Version
	private Integer versao;

	public Funcionario() {
		super();
	}

	public Funcionario(Long id) {
		super();
		this.id = id;
	}

	public Funcionario(Long id,
			@NotEmpty(message = "O nome deve ser informado!") @Length(min = 2, max = 60, message = "O nome deve ter entre {min} e {max} caracteres!") String nome,
			@NotEmpty(message = "O email deve ser informado!") @Length(max = 150) String email,
			@Length(max = 64) String senha, @Length(max = 100) String endereco, @Length(max = 20) String numero,
			@Length(max = 50) String complemento, @Length(max = 50) String bairro, @Length(max = 10) String cep,
			@Length(max = 50) String nomeCidade, @Length(max = 2) String ufCidade,
			@NotEmpty @Length(min = 5, max = 15, message = "O número do RG possui {max} algarismos.") String rg,
			@NotEmpty @Length(min = 14, max = 14, message = "O número do CPF possui {max} algarismos.") @CPF String cpf,
			@NotEmpty @Length(min = 2, max = 10, message = "O número da CTPS aceita de {min} a {max} algarismos.") String ctpsNumero,
			@NotEmpty @Length(min = 2, max = 10, message = "A série da CTPS aceita de {min} a {max} algarismos.") String ctpsSerie,
			@Length(max = 20) String foneUm, @Length(max = 20) String foneDois, Date dataAdmissao, Date dataDemissao,
			byte[] foto, Integer versao) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.endereco = endereco;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.nomeCidade = nomeCidade;
		this.ufCidade = ufCidade;
		this.rg = rg;
		this.cpf = cpf;
		this.ctpsNumero = ctpsNumero;
		this.ctpsSerie = ctpsSerie;
		this.foneUm = foneUm;
		this.foneDois = foneDois;
		this.dataAdmissao = dataAdmissao;
		this.dataDemissao = dataDemissao;
		this.foto = foto;
		this.versao = versao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCtpsNumero() {
		return ctpsNumero;
	}

	public void setCtpsNumero(String ctpsNumero) {
		this.ctpsNumero = ctpsNumero;
	}

	public String getCtpsSerie() {
		return ctpsSerie;
	}

	public void setCtpsSerie(String ctpsSerie) {
		this.ctpsSerie = ctpsSerie;
	}

	public String getFoneUm() {
		return foneUm;
	}

	public void setFoneUm(String foneUm) {
		this.foneUm = foneUm;
	}

	public String getFoneDois() {
		return foneDois;
	}

	public void setFoneDois(String foneDois) {
		this.foneDois = foneDois;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Date getDataDemissao() {
		return dataDemissao;
	}

	public void setDataDemissao(Date dataDemissao) {
		this.dataDemissao = dataDemissao;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
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
		Funcionario other = (Funcionario) obj;
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