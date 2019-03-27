package com.tianqiauto.textile.weaving.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @ClassName PersistenceContext
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-03-27 17:36
 * @Version 1.0
 **/
@Configuration
@EnableJpaAuditing
class AuditConfiguration {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new UserAuditorAware();
    }

    @Bean
    DateTimeProvider dateTimeProvider() {
        return new AuditDateTimeProvider();
    }

}

