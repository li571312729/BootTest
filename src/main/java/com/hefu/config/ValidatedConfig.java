package com.hefuaj.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * springValidate 参数校验的配置类
 * 默认情况下在对参数进行校验时Spring Validation会校验完所有字段然后才抛出异常，
 * 可以通过配置开启 Fali Fast模式，一旦校验失败就立即返回。
 *
 * @author lxq
 * @date 2021年05月19日 16:10
 */
@Configuration
public class ValidatedConfig {

    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 这里暂时不开启快速失败模式
                .failFast(false)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}

