package de.signaliduna.parkinglot.quality.cobertura;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Über die pom.xml wird gesteuert, dass Methoden mit dieser Annotation bei der Instrumentierung ignoriert werden.
 *
 * @see
 * <a href="https://stackoverflow.com/questions/8225888/ignore-methods-in-class-cobertura-maven-plugin">Stackoverflow
 * </a>
 * @author U094915
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PACKAGE})
public @interface CoberturaIgnoreMethod {

}
