package br.upf.fabsoft.jmkidsnuvem;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import br.upf.fabsoft.jmkidsmodel.nuvem.Funcionario;
import br.upf.fabsoft.jmkidsmodel.nuvem.Usuario;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;


public class GerarBancoNuvemZerado {
	/**
	 * Primeiro criar as bases jmkidsquiosque e jmkidsnuvem com owner jmkids
	 * Em seguida em cada base criar os schemas nuvem e quiosque com owner jmkids
	 * Só após rodar o script para gerar banco e usuários default	 
	 * */
	
	@Test
	public void criarBanco(){
		EntityManager em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		Usuario u = new Usuario();
		u.setLogin("sysadmin");
		u.setNome("SysAdmin");
		u.setSenha("fabsoftpwd");
		em.merge(u);
		
		em.getTransaction().commit();
	}
	
}