package br.upf.fabsoft.jmkidsnuvem.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import br.upf.fabsoft.jmkidsmodel.util.JpaUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class RelatorioUtil {
	
	public static void rodarRelatorioPDF(String pathRelatorio, HashMap parameters) throws SQLException{
		try {
			Connection con = getEntityManagerJDBCConnection();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext sContext = (ServletContext) facesContext.getExternalContext().getContext();
			JasperPrint jasperPrint = JasperFillManager.fillReport(sContext.getRealPath("/"+pathRelatorio), parameters, con);
			byte[] b = JasperExportManager.exportReportToPdf(jasperPrint);
			HttpServletResponse servResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			servResponse.setContentType("application/pdf");
			servResponse.setHeader("Content-disposition", "inline;filename=relatorio.pdf");
			servResponse.getOutputStream().write(b);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getEntityManagerJDBCConnection() throws SQLException{
		EntityManager em = JpaUtil.getEntityManager();
		Session s = (Session) em;
		SessionImplementor sImpl = (SessionImplementor) s;
		Connection conexao = sImpl.connection();
		em.close();
		return conexao;
	}
}