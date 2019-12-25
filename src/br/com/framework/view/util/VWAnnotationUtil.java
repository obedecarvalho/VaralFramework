package br.com.framework.view.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.framework.controller.AbstractMBean;
import br.com.framework.core.AbstractEntidade;
import br.com.framework.core.util.ValidatorUtil;
import br.com.framework.view.annotation.VWColumn;
import br.com.framework.view.annotation.VWMBean;
import br.com.framework.view.domain.ViewRepresentation;
import br.com.framework.view.domain.ViewColumnRepresentation;;

public class VWAnnotationUtil {

	/**
	 * Realiza o cache das definições já processadas.
	 */
	static private Map<Class<? extends AbstractMBean>, ViewRepresentation> cachedRepresentations 
		= new HashMap<Class<? extends AbstractMBean>, ViewRepresentation>();

	/**
	 * Retorna o mapeamento de DBColumn declarada em uma entidade e o Field correspondente.
	 *
	 * @param clazz
	 * @return
	 */
	static public ViewRepresentation getViewRepresentation(AbstractMBean mBean) {
		Class<? extends AbstractMBean> clazz = mBean.getClass();
		if (ValidatorUtil.isEmpty(clazz)) {
			return null;
		}
		
		if(cachedRepresentations.containsKey(clazz)) {
			return cachedRepresentations.get(clazz);
		}
		ViewRepresentation viewRepresentation = new ViewRepresentation();
		viewRepresentation.setClazz(clazz);
		viewRepresentation.setVwEntity(getVWEntity(clazz));//TODO: null validation

		List<ViewColumnRepresentation> vwColumns = new ArrayList<ViewColumnRepresentation>();
		Field[] fields = null;

		// Buscar campos da Super Classe
		//fields = AbstractEntidade.class.getDeclaredFields();

		/*
		 * for (Field f : fields) { //se for column declarada if
		 * (f.isAnnotationPresent(VWColumn.class)) { VWColumn vwColumn =
		 * f.getAnnotation(VWColumn.class); vwColumns.add(new
		 * ViewColumnRepresentation(vwColumn, f)); } }
		 */

		fields = mBean.getObj().getClass().getDeclaredFields();

		for (Field f : fields) {
			//se for column declarada
			if (f.isAnnotationPresent(VWColumn.class)) {
				VWColumn dbColumn = f.getAnnotation(VWColumn.class);
				vwColumns.add(new ViewColumnRepresentation(dbColumn, f));
			}
		}

		viewRepresentation.setVwColumns(vwColumns);

		cachedRepresentations.put(clazz, viewRepresentation);

		return viewRepresentation;
	}
	
	/**
	 * Busca a annotation de uma Classe específica.
	 * @param clazz
	 * @return
	 */
	static private VWMBean getVWEntity(Class<? extends AbstractMBean> clazz) {
		return clazz.getDeclaredAnnotation(VWMBean.class);
	}

	/**
	 * Valida regras da EntityRepresentation.
	 * @return
	 */
	static private boolean validate() {
		//TODO
		/*
		 *	Validar:
		 *		Tipos de dados compativeis com tipos da Annotation.
		 *		Se Colunas com RelationOneToX são instancia de AbstractEntidade.
		 */
		return true;
	}
}
