package br.upf.fabsoft.jmkidsmodel.nuvem;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;;

@Entity
@Table(schema = "nuvem")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(generator = "UsuarioId", strategy = SEQUENCE)
	@SequenceGenerator(name = "UsuarioId", sequenceName = "UsuarioId", allocationSize = 1, schema = "nuvem")
	private Long id;
	
	@NotEmpty(message = "O nome deve ser informado!")
	@Length(max = 60, min = 3, message = "O nome deve ter entre 3 e 60 caracteres!")
	@Column(length = 60, nullable = false)
	private String nome;
	@NotEmpty(message = "O login deve ser informado!")
	@Length(max = 20, min = 5, message = "O nome deve ter entre 5 e 20 caracteres!")
	@Column(length = 20, nullable = false)
	private String login;
	@NotEmpty(message = "A senha deve ser informada!")
	@Length(max = 64, min = 4, message = "O nome deve ter entre 4 e 40 caracteres!")
	@Column(length = 64, nullable = false)
	private String senha;

	public Usuario() {

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
