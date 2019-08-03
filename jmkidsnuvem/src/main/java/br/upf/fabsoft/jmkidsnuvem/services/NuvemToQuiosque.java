package br.upf.fabsoft.jmkidsnuvem.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.upf.fabsoft.jmkidsmodel.dao.GenericDao;
import br.upf.fabsoft.jmkidsmodel.nuvem.Brinquedo;
import br.upf.fabsoft.jmkidsmodel.nuvem.BrinquedoNoQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.FormaPagamento;
import br.upf.fabsoft.jmkidsmodel.nuvem.FuncionarioQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.GrupoCliente;
import br.upf.fabsoft.jmkidsmodel.nuvem.PromocaoQuiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.Quiosque;
import br.upf.fabsoft.jmkidsmodel.nuvem.TabelaPreco;
import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;

@Path("/nuvemtoquiosque")
public class NuvemToQuiosque {
 
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/nuvemtoquiosque/getQuiosque/1
	@GET
    @Path("/getQuiosque/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Quiosque getQuiosque(@PathParam("id") Long id) throws Exception {
        try {
			return new GenericDao<Quiosque>().find(id, Quiosque.class);
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
    }	

	//URL: http://localhost:8080/jmkidsnuvem/rest/nuvemtoquiosque/getFuncionarios/1
	@GET
    @Path("/getFuncionarios/{id}")
    @Produces("application/json;charset=utf-8") 
    public List<FuncionarioQuiosque> getFuncionarios(@PathParam("id") Long id) throws Exception {
        try {
        	String oql = "from FuncionarioQuiosque o where o.quiosque.id = "+id;
        	List<FuncionarioQuiosque> list = new GenericDao<FuncionarioQuiosque>().createQuery(oql);
			return list;
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
    }		
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/nuvemtoquiosque/getTabelaPreco/1
	@GET
    @Path("/getTabelaPreco/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTabelaPreco(@PathParam("id") Long id) throws Exception {
        try {
        	String oql = "from TabelaPreco o where o.dataHoraInicio is not null and o.dataHoraFim is null and o.quiosque.id = "+id;
        	List<TabelaPreco> list = new GenericDao<TabelaPreco>().createQuery(oql);
        	if (list.size() <= 0) {
        		oql = "from TabelaPreco o where o.dataHoraInicio is not null and o.dataHoraFim is null and o.geral = true ";
        		list = new GenericDao<TabelaPreco>().createQuery(oql);
        	}
			//return list;
        	ObjectMapper mapper = new ObjectMapper();
        	String jsonString = mapper.writeValueAsString(list);
        	return jsonString;
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
    }	
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/nuvemtoquiosque/getFormaPagamento
	@GET
    @Path("/getFormaPagamento")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FormaPagamento> getFormaPagamento() throws Exception {
        try {
        	String oql = "from FormaPagamento o";
        	List<FormaPagamento> list = new GenericDao<FormaPagamento>().createQuery(oql);
			return list;
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
    }	
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/nuvemtoquiosque/getPromocaoQuiosque/1
	@GET
    @Path("/getPromocaoQuiosque/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PromocaoQuiosque> getPromocaoQuiosque(@PathParam("id") Long id) throws Exception {
        try {
        	String oql = "from PromocaoQuiosque o where o.quiosque.id = "+id;
        	List<PromocaoQuiosque> list = new GenericDao<PromocaoQuiosque>().createQuery(oql);
			return list;
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
    }		
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/nuvemtoquiosque/getGrupoCliente
	@GET
    @Path("/getGrupoCliente")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GrupoCliente> getGrupoCliente() throws Exception {
        try {
        	String oql = "from GrupoCliente o";
        	List<GrupoCliente> list = new GenericDao<GrupoCliente>().createQuery(oql);
			return list;
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
    }	
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/nuvemtoquiosque/getBrinquedoNoQuiosque/1
	@GET
    @Path("/getBrinquedoNoQuiosque/{id}")
    @Produces("application/json;charset=utf-8")  
    public List<BrinquedoNoQuiosque> getBrinquedoNoQuiosque(@PathParam("id") Long id) throws Exception {
        try {
        	String oql = "from BrinquedoNoQuiosque o where o.quiosque.id = "+id;
        	List<BrinquedoNoQuiosque> list = new GenericDao<BrinquedoNoQuiosque>().createQuery(oql);
			return list;
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
    }		
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/nuvemtoquiosque/getBrinquedoNoQuiosqueComGson/1
	@GET
    @Path("/getBrinquedoNoQuiosqueComGson/{id}")
	@Produces(MediaType.APPLICATION_JSON) 
    public String getBrinquedoNoQuiosqueComGson(@PathParam("id") Long id) throws Exception {
        try {
        	String oql = "from BrinquedoNoQuiosque o where o.quiosque.id = "+id;
        	List<BrinquedoNoQuiosque> list = new GenericDao<BrinquedoNoQuiosque>().createQuery(oql);
        	GsonBuilder builder = new GsonBuilder();
            builder.registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter());
            Gson gson = builder.create();
			return gson.toJson(list);
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
    }
	
	//URL: http://localhost:8080/jmkidsnuvem/rest/nuvemtoquiosque/getBrinquedoImage/1
	@GET
    @Path("/getBrinquedoImage/{id}")
	@Produces("image/png") 
    public byte[] getBrinquedoImage(@PathParam("id") Long id) throws Exception {
        try {
        	Brinquedo b = new GenericDao<Brinquedo>().find(id, Brinquedo.class);
        	System.out.println("foto do BD: ");
        	System.out.println(b.getFoto());
			return b.getFoto();
		} catch (Exception e) {
			throw new Exception(JpaUtil.exceptionHandler(e)); 
		}
    }	
}