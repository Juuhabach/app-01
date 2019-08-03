package br.upf.fabsoft.jmkidsnuvem.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.upf.fabsoft.jmkidsmodel.nuvem.Usuario;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

@ManagedBean 
@SessionScoped 
public class LoginBean implements Serializable {

	private String login;
	private String senha;
	private Usuario usuarioLogado = null;

	public LoginBean() {
	}

	public String entrar() {
		//return "/faces/Privado/Home.xhtml?faces-redirect=true";
		EntityManager em = JpaUtil.getEntityManager();
		Query qry = em.createQuery("from Usuario usuario where usuario.login = :login and usuario.senha = :senha");
		qry.setParameter("login", login);
		qry.setParameter("senha", senha);
		List<Usuario> list = qry.getResultList();
		if (list.size() <= 0) {
			usuarioLogado = null;
			FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou senha inválido!", "");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			em.close();
			return "";
		} else {
			usuarioLogado = list.get(0);
			em.close();
			return "/faces/Privado/Home.xhtml?faces-redirect=true";
		} 
		 
	}

	public String sair() {
		usuarioLogado = null;
		FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário Desconectado!", "");
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
		return "/faces/Login/LoginForm.xhtml";
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

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}