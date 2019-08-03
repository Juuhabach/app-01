package br.upf.fabsoft.jmkidsmodel.util;

public class ExceptionHandler {

	public static String exceptionHandler(Throwable e) {
		if (e.getCause() != null)
			return exceptionHandler(e.getCause());
		else
			return e.getMessage();
	}
}