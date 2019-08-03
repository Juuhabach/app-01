package br.upf.fabsoft.jmkidsmodel.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static EntityManagerFactory factory;

	public JpaUtil() {
		super();
	}

	public static EntityManager getEntityManager() {
		if (factory == null)
			factory = Persistence.createEntityManagerFactory("jmkidsquiosque");
		return factory.createEntityManager();
	}

	public static String exceptionHandler(Throwable e) {
		if (e.getCause() != null)
			return exceptionHandler(e.getCause());
		else
			return e.getMessage();
	}
}