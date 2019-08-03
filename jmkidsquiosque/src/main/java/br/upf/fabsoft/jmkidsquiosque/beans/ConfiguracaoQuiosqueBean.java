package br.upf.fabsoft.jmkidsquiosque.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.quiosque.ConfiguracaoQuiosque;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;
import br.upf.fabsoft.jmkidsquiosque.integracao.IntegracaoNuvemToQuiosque;
import br.upf.fabsoft.jmkidsquiosque.integracao.IntegracaoQuiosqueToNuvem;
import br.upf.fabsoft.jmkidsquiosque.util.JsfUtil;

@ManagedBean
@ViewScoped
public class ConfiguracaoQuiosqueBean {
	
	private List<ConfiguracaoQuiosque> lista;
	private ConfiguracaoQuiosque selecionado;
	private Boolean editando;
	
	public void sincronizar() {
		IntegracaoNuvemToQuiosque integra = new IntegracaoNuvemToQuiosque();
		try {
			integra.pegarTodosOsDadosDaNuvem();
			JsfUtil.addFacesMessageInfo("Dados sincronizados com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao pegar dados da nuvem. \nDetalhes do erro: "+JpaUtil.exceptionHandler(e));
		}
	}
	
	public void sincronizarQuiosqueToNuvem() {
		IntegracaoQuiosqueToNuvem integra = new IntegracaoQuiosqueToNuvem();
		try {
			integra.enviarTodosOsDadosParaNuvem();
			JsfUtil.addFacesMessageInfo("Dados enviados com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtil.addFacesMessageErro("Erro ao enviar dados para a nuvem. \nDetalhes do erro: "+JpaUtil.exceptionHandler(e));
		}
	}	
	
	public ConfiguracaoQuiosqueBean() {
		carregarLista();
		editando = false;
	}

	public void carregarLista() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			lista = em.createQuery("from ConfiguracaoQuiosque where id = 1", ConfiguracaoQuiosque.class).getResultList();
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
		selecionado = new ConfiguracaoQuiosque();
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
	
	//—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
	
	public List<ConfiguracaoQuiosque> getLista() {
		return lista;
	}
	public void setLista(List<ConfiguracaoQuiosque> lista) {
		this.lista = lista;
	}
	public ConfiguracaoQuiosque getSelecionado() {
		return selecionado;
	}
	public void setSelecionado(ConfiguracaoQuiosque selecionado) {
		this.selecionado = selecionado;
	}
	public Boolean getEditando() {
		return editando;
	}
	public void setEditando(Boolean editando) {
		this.editando = editando;
	}
}