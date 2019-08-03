package br.upf.fabsoft.jmkidsnuvem.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.nuvem.Usuario;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean {

	private List<Usuario> lista;
	private Usuario selecionado;
	private Boolean editando;
	private Boolean editandoSenha;

	public UsuarioBean() {
		carregarLista();
		editando = false;
		editandoSenha = false;
	}

	public void carregarLista() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			lista = em.createQuery("from Usuario", Usuario.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void incluir() {
		selecionado = new Usuario();
		editando = true;
		editandoSenha = true;
	}

	public void editar() {
		editando = true;
		editandoSenha = true;
	}

	public void editarSenha() {
		editando = true;
		editandoSenha = true;
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
			editandoSenha = false;
			selecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void cancelar() {
		selecionado = null;
		carregarLista();
		editando = false;
		editandoSenha = false;
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
			editandoSenha = false;
			selecionado = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	// --------------------------------------------------------
	public List<Usuario> getLista() {
		return lista;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public Usuario getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Usuario selecionado) {
		this.selecionado = selecionado;
	}

	public Boolean getEditando() {
		return editando;
	}

	public void setEditando(Boolean editando) {
		this.editando = editando;
	}

	public Boolean getEditandoSenha() {
		return editandoSenha;
	}

	public void setEditandoSenha(Boolean editandoSenha) {
		this.editandoSenha = editandoSenha;
	}
}
