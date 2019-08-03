package br.upf.fabsoft.jmkidsnuvem.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;
import br.upf.fabsoft.jmkidsmodel.util.ValidaCPF;
import br.upf.fabsoft.jmkidsnuvem.util.JsfUtil;

@ManagedBean
@ViewScoped
public class FuncionarioBean {

	private List<Funcionario> lista;
	private Funcionario selecionado;
	private Boolean editando;
	private Boolean editandoSenha;

	public FuncionarioBean() {
		carregarLista();
		editando = false;
		editandoSenha = false;
	}

	public void carregarLista() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			lista = em.createQuery("from Funcionario", Funcionario.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void incluir() {
		selecionado = new Funcionario();
		editando = true;
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
			
			String cpf = ValidaCPF.removeMascara(selecionado.getCpf());
			if (!ValidaCPF.isCPF(cpf)) {
				selecionado.setCpf("");
				JsfUtil.addFacesMessageErro("CPF inválido");
				return;
			}
			selecionado.setCpf(ValidaCPF.imprimeCPF(cpf));
			
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
			if (em != null) {
				em.close();
			}
		}
	}

	// --------------------------------------------------------
	public List<Funcionario> getLista() {
		return lista;
	}

	public void setLista(List<Funcionario> lista) {
		this.lista = lista;
	}

	public Funcionario getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Funcionario selecionado) {
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
