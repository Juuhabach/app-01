package br.upf.fabsoft.jmkidsquiosque;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.nuvem.Usuario;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;


public class GerarBancoQuiosqueZerado {
	/**
	 * Primeiro criar as bases jmkidsquiosque e jmkidsnuvem com owner jmkids
	 * Em seguida em cada base criar os schemas nuvem e quiosque com owner jmkids
	 * Só após rodar o script para gerar banco e usuários default	 
	 * */
	
	@Test
	public void criarBanco(){
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();		
		Funcionario f = new Funcionario();
		f.setId(0L);
		f.setEmail("fabsoft@upf.br");
		f.setNome("Sysadmin"); 
		f.setSenha("fabsoftpwd");
		em.merge(f);
		em.getTransaction().commit();
	}
}