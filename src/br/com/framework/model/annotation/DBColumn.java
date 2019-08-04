package br.com.framework.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBColumn {
	boolean primaryKey() default false;
	boolean relationOneTo() default false;
	String columnName();
	DataType dataType();
	//TODO: not null/default value/unique
}
