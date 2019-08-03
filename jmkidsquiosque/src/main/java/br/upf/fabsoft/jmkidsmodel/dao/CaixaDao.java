package br.upf.fabsoft.jmkidsmodel.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.nuvem.BrinquedoNoQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.quiosque.Caixa;
import br.upf.fabsoft.jmkidsmodel.quiosque.CaixaMovimento;
import br.upf.fabsoft.jmkidsmodel.quiosque.CaixaMovimentoDois;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;
import br.upf.fabsoft.jmkidsquiosque.util.JsfUtil;

public class CaixaDao extends GenericDao<Caixa> {

	public Boolean temBrinquedoAtivo(Quiosque quiosque) {
		try {
			List<BrinquedoNoQuiosque> listaBrinquedoNoQuiosque = new GenericDao<BrinquedoNoQuiosque>()
					.createQuery("from BrinquedoNoQuiosque where quiosque.id = " + quiosque.getId()
							+ " and (emUso=true or emPausa=true or contratado=true) ");
			if (listaBrinquedoNoQuiosque.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<CaixaMovimento> getCaixaMovimentoList(Caixa caixa) throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			String oql = "select c from CaixaMovimento c where c.caixa.quiosque.id = " + caixa.getQuiosque().getId()
					+ " and c.caixa.id = " + caixa.getId();
			return em.createQuery(oql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
	}

	public List<CaixaMovimentoDois> getCaixaMovimentoDoisList(Caixa caixa) throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			String oql = "select c from CaixaMovimentoDois c where c.caixa.quiosque.id = " + caixa.getQuiosque().getId()
					+ " and c.caixa.id = " + caixa.getId();
			return em.createQuery(oql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
	}

	public Integer getCaixaAbertosQtd(Quiosque quiosque) throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			int abertos = em.createQuery(
					"from Caixa c where c.quiosque.id = " + quiosque.getId() + " and c.dataHoraFechamento is null")
					.getResultList().size();
			return abertos;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
	}

	public Caixa getCaixaAberto(Quiosque quiosque) throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			List<Caixa> caixas = em.createQuery(
					"from Caixa c where c.quiosque.id = " + quiosque.getId() + " and c.dataHoraFechamento is null")
					.getResultList();
			if (caixas.size() > 1) {
				throw new Exception("Mais de um caixa aberto. Deixar somente um caixa aberto, fechar os anteriores!");
			} else if (caixas.size() < 1) {
				throw new Exception("Precisa ter um caixa aberto!");
			}
			return caixas.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
	}
}