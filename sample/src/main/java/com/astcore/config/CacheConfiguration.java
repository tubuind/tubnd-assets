package com.astcore.config;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.cache.CacheManager;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;
import org.elasticsearch.common.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.jhipster.config.JHipsterProperties;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.astcore.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdDevicetype.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdDevicetype.class.getName() + ".deviceInfos", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdDevicegroup.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdDevicegroup.class.getName() + ".deviceInfos", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdCountry.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdCountry.class.getName() + ".mtdProvinces", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdProvince.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdProvince.class.getName() + ".mtdDistricts", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdDistrict.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdDistrict.class.getName() + ".mtdWards", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdWard.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdWard.class.getName() + ".cifMasters", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdUnit.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdUnit.class.getName() + ".mtdDevicegroups", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdOrganization.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdOrganization.class.getName() + ".cifMasters", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdEcosectors.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdEcosectors.class.getName() + ".cifMasters", jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdCustomergroup.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.MtdCustomergroup.class.getName() + ".cifMasters", jcacheConfiguration);
            cm.createCache(com.astcore.domain.CifMaster.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.CifMaster.class.getName() + ".cifDevices", jcacheConfiguration);
            cm.createCache(com.astcore.domain.DeviceInfo.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.DeviceInfo.class.getName() + ".deviceTransactions", jcacheConfiguration);
            cm.createCache(com.astcore.domain.DeviceInfo.class.getName() + ".cifDevices", jcacheConfiguration);
            cm.createCache(com.astcore.domain.DeviceTransaction.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.CifDevice.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.CifArea.class.getName(), jcacheConfiguration);
            cm.createCache(com.astcore.domain.CifArea.class.getName() + ".cifAreaDevices", jcacheConfiguration);
            cm.createCache(com.astcore.domain.CifAreaDevice.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
