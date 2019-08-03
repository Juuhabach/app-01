package br.upf.fabsoft.jmkidsquiosque.beans;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.GrupoCliente;
import br.upf.fabsoft.jmkidsmodel.quiosque.Cliente;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;
import br.upf.fabsoft.jmkidsmodel.util.ValidaCNPJ;
import br.upf.fabsoft.jmkidsmodel.util.ValidaCPF;
import br.upf.fabsoft.jmkidsquiosque.util.JsfUtil;

@ManagedBean
@ViewScoped
public class ClienteBean {

	private List<Cliente> lista;
	private Cliente selecionado;
	private Boolean editando;

	public ClienteBean() {
		carregarLista();
		editando = false;
	}

	public List<GrupoCliente> completeGrupoCliente(String query) throws Exception {
		return new GenericDao<GrupoCliente>().createQuery("from GrupoCliente where upper(descricao) like " + "'"
				+ query.trim().toUpperCase() + "%' " + "order by descricao");
	}

	public void carregarLista() {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			lista = em.createQuery("from Cliente", Cliente.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void incluir() {
		selecionado = new Cliente();
		editando = true;
	}

	public void editar() {
		editando = true;
	}

	public void salvar() {
		if (selecionado.getPfOuPj().equals("PJ")) {
			String cnpj = ValidaCNPJ.removeMascara(selecionado.getCpfOuCnpj());
			if (!ValidaCNPJ.isCNPJ(cnpj)) {
				JsfUtil.addFacesMessageErro("CNPJ inválido");
				selecionado.setCpfOuCnpj("");
				return;
			}
			selecionado.setCpfOuCnpj(ValidaCNPJ.imprimeCNPJ(cnpj));
		} else if (selecionado.getPfOuPj().equals("PF")) {
			String cpf = ValidaCPF.removeMascara(selecionado.getCpfOuCnpj());
			if (!ValidaCPF.isCPF(cpf)) {
				selecionado.setCpfOuCnpj("");
				JsfUtil.addFacesMessageErro("CPF inválido");
				return;
			}
			selecionado.setCpfOuCnpj(ValidaCPF.imprimeCPF(cpf));
		}
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			selecionado.setDataHoraModificado(new Date());
			selecionado = em.merge(selecionado);
			em.getTransaction().commit();
			carregarLista();
			editando = false;
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
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Erro: " + JpaUtil.exceptionHandler(e)));
		} finally {
			if (em != null)
				em.close();
		}
	}

	// ———————————————————————————————————————————————————————————————————————————————————————————————————————————————————

	public List<Cliente> getLista() {
		return lista;
	}

	public void setLista(List<Cliente> lista) {
		this.lista = lista;
	}

	public Cliente getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Cliente selecionado) {
		this.selecionado = selecionado;
	}

	public Boolean getEditando() {
		return editando;
	}

	public void setEditando(Boolean editando) {
		this.editando = editando;
	}
}