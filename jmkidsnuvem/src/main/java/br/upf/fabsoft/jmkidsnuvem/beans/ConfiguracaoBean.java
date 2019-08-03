package br.upf.fabsoft.jmkidsnuvem.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.nuvem.Configuracao;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

@ManagedBean
@ViewScoped
public class ConfiguracaoBean {
	
	private List<Configuracao> lista;
	private Configuracao selecionado;
	private Boolean editando;
	
	public ConfiguracaoBean() {
		carregarLista();
		editando = false;
	}

	public void carregarLista() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			lista = em.createQuery("from Configuracao where id = 1", Configuracao.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage("Erro: "+JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
			   em.close();
		}
	}

	public void incluir() {
		selecionado = new Configuracao();
		selecionado.setId(1L);
		editando = true;
	}
	
	public void editar() {
		editando = true;
	}
	
	public void salvar() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			selecionado = em.merge(selecionado);
			em.getTransaction().commit();
			carregarLista();
			editando = false;
			selecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage("Erro: "+JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
			   em.close();
		}
	}
	
	public void cancelar() {
		selecionado = null;
		carregarLista();
		editando = false;
	}
	
	public void excluir() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(selecionado));
			em.getTransaction().commit();
			carregarLista();
			editando = false;
			selecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", 
					new FacesMessage("Erro: "+JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
			   em.close();
		}		
	}
	
	public List<Configuracao> getLista() {
		return lista;
	}
	public void setLista(List<Configuracao> lista) {
		this.lista = lista;
	}
	public Configuracao getSelecionado() {
		return selecionado;
	}
	public void setSelecionado(Configuracao selecionado) {
		this.selecionado = selecionado;
	}
	public Boolean getEditando() {
		return editando;
	}
	public void setEditando(Boolean editando) {
		this.editando = editando;
	}
	
}
