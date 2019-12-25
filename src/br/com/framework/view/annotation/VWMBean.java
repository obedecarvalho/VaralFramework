package br.com.framework.view.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VWMBean {
	String entityName();
	String entityNamePlural();
	boolean menuAdd() default true;
	boolean menuSearch() default true;
}
