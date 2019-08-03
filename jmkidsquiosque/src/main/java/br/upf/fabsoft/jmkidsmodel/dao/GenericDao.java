package br.upf.fabsoft.jmkidsmodel.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

public class GenericDao<T> {

	public T merge(T objeto) throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			objeto = em.merge(objeto);
			em.getTransaction().commit();
			return objeto;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
	}

	public void remove(T objeto) throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(objeto));
			em.getTransaction().commit();
			objeto = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
	}

	public List<T> createQuery(String oql) throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			return em.createQuery(oql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null)
				em.close();
		}
	}

	public T find(Long id, Class<T> classe) throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			return em.find(classe, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}

	public void executeNativeSQLUpdate(String sql) throws Exception {
		EntityManager em = null;
		try {
			em = JpaUtil.getEntityManager();
			em.getTransaction().begin();
			em.createNativeQuery(sql).executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}
}