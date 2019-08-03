/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.upf.fabsoft.jmkidsmodel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Datas {

	/**
	 * Método que obtém a data e hora do S.O. Retorna uma instância de Calendar
	 */
	public static Calendar getDataHoraAtual() {
		return Calendar.getInstance();
	}

	/**
	 * Método que obtém a data a partir de uma String. Retorna uma instância de
	 * Date.
	 * 
	 * @param data no formato "dd/MM/yyyy"
	 */
	public static Date getData(String data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		sdf.setLenient(false);
		try {
			Date d = sdf.parse(data);
			c.setTime(d);
		} catch (Exception e) {
			throw new ParseException("Erro na Data", 1);
		}
		return c.getTime();
	}

	public static Date getHora(String data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar c = Calendar.getInstance();
		sdf.setLenient(false);
		Date d = sdf.parse(data);
		c.setTime(d);
		return c.getTime();
	}

	public static Date getDataHora(String data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar c = Calendar.getInstance();
		sdf.setLenient(false);
		Date d = sdf.parse(data);
		c.setTime(d);
		return c.getTime();
	}

	/**
	 * Metodo que formata um Calendar para String Formato: dd/MM/yyyy
	 */
	public static String toDiaMesAno(Date c) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(c);
	}

	public static String toHoraMinSeg(Date c) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(c);
	}

	public static String toDiaMesAnoHoraMinSeg(Date c) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(c);
	}

	public static String toDiaSemana(Calendar c) {
		String[] s = { "Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sabado" };
		return s[c.get(c.DAY_OF_WEEK) - 1];
	}

	public static String toMesDoAno(Calendar c) {
		String[] s = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
				"Outubro", "Novembro", "Dezembro" };
		return s[c.get(c.MONTH)];
	}

	public static String diaSemanaExtenso(Integer diaSemana) {
		String[] s = { "Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sabado" };
		return s[diaSemana];
	}

	public static String mesPorExtenso(Integer mes) {
		String[] s = { "", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
				"Outubro", "Novembro", "Dezembro" };
		return s[mes];
	}

	public static void addDias(Calendar c, int dias) {
		c.add(Calendar.DAY_OF_MONTH, dias);
	}

	public static void addDias(Date c, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(c);
		cal.add(Calendar.DAY_OF_MONTH, dias);
		c.setTime(cal.getTimeInMillis());
	}

	public static void addMes(Date c, int meses) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(c);
		cal.add(Calendar.MONTH, meses);
		c.setTime(cal.getTimeInMillis());
	}

	public static void addAno(Date c, int anos) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(c);
		cal.add(Calendar.YEAR, anos);
		c.setTime(cal.getTimeInMillis());
	}

	public static void addHora(Date c, int horas) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(c);
		cal.add(Calendar.HOUR_OF_DAY, horas);
		c.setTime(cal.getTimeInMillis());
	}

	public static void addMinutos(Date c, int min) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(c);
		cal.add(Calendar.MINUTE, min);
		c.setTime(cal.getTimeInMillis());
	}

	/**
	 * Testa se a data um é maior que a data dois
	 * 
	 * @param data1
	 * @param data2
	 * @return Retorna true se (data1 > data2)
	 */
	public static boolean dataMaior(Date data1, Date data2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
		try {
			Date d1 = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(data1) + " 00:00:00:000");
			Date d2 = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(data2) + " 00:00:00:000");
			if (d1.after(d2))
				// String sdata1 = new SimpleDateFormat("dd/MM/yyyy").format(data1);
				return true;
			else
				return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Testa se a data um é maior ou igual a data dois
	 * 
	 * @param data1
	 * @param data2
	 * @return Retorna true se (data1 >= data2)
	 */
	public static boolean dataMaiorIgual(Date data1, Date data2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
		try {
			Date d1 = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(data1) + " 00:00:00:000");
			Date d2 = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(data2) + " 00:00:00:000");
			if ((d1.equals(d2)) || (d1.after(d2)))
				return true;
			else
				return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Testa se a data um é menor que a data dois
	 * 
	 * @param data1
	 * @param data2
	 * @return Retorna true se (data1 < data2)
	 */
	public static boolean dataMenor(Date data1, Date data2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
		try {
			Date d1 = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(data1) + " 00:00:00:000");
			Date d2 = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(data2) + " 00:00:00:000");
			if (d1.before(d2))
				return true;
			else
				return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Testa se a data um é menor ou igual a data dois
	 * 
	 * @param data1
	 * @param data2
	 * @return Retorna true se (data1 <= data2)
	 */
	public static boolean dataMenorIgual(Date data1, Date data2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
		try {
			Date d1 = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(data1) + " 00:00:00:000");
			Date d2 = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(data2) + " 00:00:00:000");
			if ((d1.equals(d2)) || (d1.before(d2)))
				return true;
			else
				return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int getDiferencaMinutos(Calendar ini, Calendar fim) {
		long i = ini.getTimeInMillis();
		long f = fim.getTimeInMillis();
		return (int) ((f - i) / 1000 / 60);
		/*
		 * / 1000 para obter <> em segundos / 60 para obter <> em minutos / 60 para
		 * obter <> em horas / 24 para obter <> em dias / 365 para obter <> em anos
		 */
	}

	public static int getDiferencaMinutos(Date ini, Date fim) {
		long i = ini.getTime();
		long f = fim.getTime();
		return (int) ((f - i) / 1000 / 60);
		/*
		 * / 1000 para obter <> em segundos / 60 para obter <> em minutos / 60 para
		 * obter <> em horas / 24 para obter <> em dias / 365 para obter <> em anos
		 */
	}

	public static int getDiferencaHoras(Calendar ini, Calendar fim) {
		return getDiferencaMinutos(ini, fim) / 60;
	}

	public static int getDiferencaHoras(Date ini, Date fim) {
		return getDiferencaMinutos(ini, fim) / 60;
	}

	public static int getDiferencaDias(Calendar ini, Calendar fim) {
		return getDiferencaMinutos(ini, fim) / 60 / 24;
	}

	public static int getDiferencaDias(Date ini, Date fim) {
		return getDiferencaMinutos(ini, fim) / 60 / 24;
	}

	public static int getDiferencaAnos(Calendar ini, Calendar fim) {
		return getDiferencaMinutos(ini, fim) / 60 / 24 / 365;
	}

	public static int getDiferencaAnos(Date ini, Date fim) {
		return getDiferencaMinutos(ini, fim) / 60 / 24 / 365;
	}

	public static Date getPrimeiroDiaMes(Integer ano, Integer mes) throws ParseException {
		Calendar cal = new GregorianCalendar(ano, mes - 1, 1);
		String aux = cal.getActualMinimum(Calendar.DAY_OF_MONTH) + "/" + mes.toString() + "/" + ano.toString();
		return getData(aux);
	}

	public static Date getUltimoDiaMes(Integer ano, Integer mes) throws ParseException {
		Calendar cal = new GregorianCalendar(ano, mes - 1, 1);
		String aux = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + "/" + mes.toString() + "/" + ano.toString();
		return getData(aux);
	}

	public static Integer calcularIdade(Date dataNascimento) {
		Calendar hoje = Calendar.getInstance();
		int diaNoAnoHoje = hoje.get(Calendar.DAY_OF_YEAR);
		int anoHoje = hoje.get(Calendar.YEAR);
		Calendar nascimento = Calendar.getInstance();
		nascimento.setTime(dataNascimento);
		int diaNoAnoNasc = nascimento.get(Calendar.DAY_OF_YEAR);
		int anoNasc = nascimento.get(Calendar.YEAR);
		int idade = anoHoje - anoNasc;
		if (diaNoAnoHoje < diaNoAnoNasc)
			idade--; // Ainda não fez aniversário esse ano.
		return idade;
	}
}