package br.upf.fabsoft.jmkidsnuvem.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPreco;
import br.upf.fabsoft.jmkidsmodel.quiosque.BrinquedoOperacao;
import br.upf.fabsoft.jmkidsmodel.quiosque.Caixa;
import br.upf.fabsoft.jmkidsmodel.quiosque.CaixaMovimentoDois;
import br.upf.fabsoft.jmkidsmodel.quiosque.Cliente;
import br.upf.fabsoft.jmkidsmodel.quiosque.ClienteQuiosque;
import br.upf.fabsoft.jmkidsmodel.quiosque.InativacaoForcada;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

@Path("/quiosquetonuvem")
public class QuiosqueToNuvem {
	
	private static final String CHARSET_UTF8 = " ;charset=utf-8";
 
	//URL: http://localhost:8080/jmkidsnuvem/rest/quiosquetonuvem/addcliente
	@POST
    @Path("/addcliente")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    public void salvarCliente(Cliente cliente) throws Exception {
        salvar(cliente);
    }
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/quiosquetonuvem/addcaixa
	@POST
    @Path("/addcaixa")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    public void salvarCaixa(Caixa caixa) throws Exception {
        salvar(caixa);
    }	
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/quiosquetonuvem/addclientequiosque
	@POST
    @Path("/addclientequiosque")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    public void salvarClienteQuiosque(ClienteQuiosque clienteQuiosque) throws Exception {
        salvar(clienteQuiosque);
    }
		
	//URL: http://localhost:8080/jmkidsnuvem/rest/quiosquetonuvem/addcaixamovimentodois
	@POST
    @Path("/addcaixamovimentodois")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    public void salvarCaixaMovimentoDois(CaixaMovimentoDois caixaMovimentoDois) throws Exception {
        salvar(caixaMovimentoDois);
    }
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/quiosquetonuvem/addbrinquedooperacao
	@POST
    @Path("/addbrinquedooperacao")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    public void salvarBrinquedoOperacao(BrinquedoOperacao brinquedoOperacao) throws Exception {
       salvar(brinquedoOperacao);
    }
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/quiosquetonuvem/addinativacaoforcada
	@POST
    @Path("/addinativacaoforcada")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
    public void salvarBrinquedoOperacao(InativacaoForcada inativacaoForcada) throws Exception {
       salvar(inativacaoForcada);
    }
	
	public <T> void salvar(T t) throws Exception {
        try {
        	new GenericDao<T>().merge(t);
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
	}
}