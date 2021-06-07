package cn.steateam.lets_team_server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要鉴权的注解
 *
 * @author STEA_YY
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresLogin {
    boolean required() default true;

    int[] requiresRoles() default {};
}