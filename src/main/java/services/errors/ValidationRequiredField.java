package services.errors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ValidationRequiredField {
	public static List<ErrorsData> validarCampoRequerido(final Object objeto) {
		
		List<ErrorsData> errorsData = new ArrayList<ErrorsData>();
		
		final Class<?> classe = objeto.getClass();
		
		final Field[] fields = classe.getDeclaredFields();
		
		for (int i = 0; i < fields.length; i++) {
			if(fields[i].isAnnotationPresent(CampoRequerido.class)){
				CampoRequerido campoRequerido = fields[i].getDeclaredAnnotation(CampoRequerido.class);
				int valor = campoRequerido.valor();
				String mensagemErro = campoRequerido.mensagem();
				
				fields[i].setAccessible(true);
				
				try {
					Object value = fields[i].get(objeto);
					if(conteudoInformadoNoCampo(value) == true) {
						ErrorsData erro = new ErrorsData();
						erro.setShowMensagemError(mensagemErro);
						erro.setNumeroCampo(valor);
						errorsData.add(erro);
					}
				}
				catch(IllegalArgumentException | IllegalAccessException e){
					e.printStackTrace();
				}
			}
		}
		
		return errorsData;
	}
	
	private static boolean conteudoInformadoNoCampo(Object objeto) {
		if(Objects.isNull(objeto)) {
			return true;
		}
		
		if(objeto instanceof String) {
			String campo = (String) objeto;
			return "".equals(campo.trim()) ? true:false;
		}
		
		if(objeto instanceof Integer) {
			Integer campo = (Integer) objeto;
			return campo == 0 ? true:false;
		}
		
		if(objeto instanceof Long) {
			Long campo = (Long) objeto;
			return campo == 0L ? true:false;
		}
		
		
		
		return false;
	}
}
