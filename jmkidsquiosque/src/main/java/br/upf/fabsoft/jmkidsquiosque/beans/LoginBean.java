package br.upf.fabsoft.jmkidsquiosque.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.quiosque.ConfiguracaoQuiosque;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;
import br.upf.fabsoft.jmkidsquiosque.util.JsfUtil;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private String login;
	private String senha;
	private Funcionario usuarioLogado = null;
	private ConfiguracaoQuiosque configuracaoQuiosque;
	private Quiosque quiosqueSelecionado;

	public LoginBean() {
	}

	public void onLoad() {
		Long id;
		try {
			configuracaoQuiosque = new GenericDao<ConfiguracaoQuiosque>().find(1L, ConfiguracaoQuiosque.class);
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro(
					"Erro ao carregar configuração do quiosque. Detalhes: " + JpaUtil.exceptionHandler(e));
		}
		try {
			quiosqueSelecionado = new GenericDao<Quiosque>().find(configuracaoQuiosque.getIdQuiosque(), Quiosque.class);
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao carregar dados do quiosque. Detalhes: " + JpaUtil.exceptionHandler(e));
		}
	}

	public String entrar() {
		// return "/faces/Privado/Home.xhtml?faces-redirect=true";
		EntityManager em = JpaUtil.getEntityManager();
		Query qry = em.createQuery("from Funcionario o where o.email = :login and o.senha = :senha");
		qry.setParameter("login", login);
		qry.setParameter("senha", senha);
		List<Funcionario> list = qry.getResultList();
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

	//———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
	
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

	public Funcionario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Funcionario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public ConfiguracaoQuiosque getConfiguracaoQuiosque() {
		return configuracaoQuiosque;
	}

	public void setConfiguracaoQuiosque(ConfiguracaoQuiosque configuracaoQuiosque) {
		this.configuracaoQuiosque = configuracaoQuiosque;
	}

	public Quiosque getQuiosqueSelecionado() {
		return quiosqueSelecionado;
	}

	public void setQuiosqueSelecionado(Quiosque quiosqueSelecionado) {
		this.quiosqueSelecionado = quiosqueSelecionado;
	}
}