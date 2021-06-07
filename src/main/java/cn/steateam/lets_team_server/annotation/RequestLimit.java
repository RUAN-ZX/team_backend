package cn.steateam.lets_team_server.annotation;

import java.lang.annotation.*;

/**
 * 访问次数限制注解
 *
 * @author STEA_YY
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {
    int amount() default 30;

    int time() default 60;
}
