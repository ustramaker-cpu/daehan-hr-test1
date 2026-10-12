package com.gsitm.framework.data.mybatis;

import com.gsitm.framework.data.mybatis.domain.BaseDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * <pre>
 * kr.co.gsbill.core.config
 *
 * </pre>
 * Description :
 *
 * @author : Mercury
 * @version : 1.0
 * @date : 2017-06-03
 */
//@Configuration
//@Slf4j
//@MapperScan(basePackages = {"com.gsitm.framework.**.mapper"}, annotationClass = Mapper.class)
public class MybatisConfiguration {

    //@Autowired
    private MybatisProperties properties;

    //@Autowired(required = false)
    private Interceptor[] interceptors;

    //@Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    //@Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    //@Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }

        // mybatis-spring-boot 3.x 부터 MybatisProperties#getConfiguration 이
        // org.apache.ibatis.session.Configuration 이 아닌 CoreConfiguration 을 반환한다.
        if (this.properties.getConfiguration() != null) {
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            this.properties.getConfiguration().applyTo(configuration);
            factory.setConfiguration(configuration);
        }

        if (!ObjectUtils.isEmpty(this.interceptors)) {
            factory.setPlugins(this.interceptors);
        }
        if (this.databaseIdProvider != null) {
            factory.setDatabaseIdProvider(this.databaseIdProvider);
        }
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
            factory.setTypeAliasesSuperType(BaseDomain.class);
        }
        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            factory.setMapperLocations(this.properties.resolveMapperLocations());
        }
        return factory.getObject();
    }
}