package br.com.framework.view.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.framework.view.FieldType;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VWColumn {
	String descricao() default "";//Tooltip
	boolean obrigatorio() default false;//nullable
	boolean userEdit() default true;
	boolean userView() default true;

	String name();//Label
	FieldType tipo() default FieldType.TEXT;//Criar Enum (Text, TextArea, Relation (Combobox), Combobox boolean, data, Secret, Number (Int/Real))
	//String[] relation();//Lista de opções -> criar classe de filtro
	String elementsList() default ""; //nome método que busca elementos

}
