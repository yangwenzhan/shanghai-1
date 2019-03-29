package com.tianqiauto.textile.weaving.config.audit;

import org.springframework.data.auditing.DateTimeProvider;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

/**
 * @ClassName AuditDateTimeProvider
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-03-27 17:45
 * @Version 1.0
 **/
public class AuditDateTimeProvider implements DateTimeProvider {
    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(ZonedDateTime.now());
    }
}
