package com.yarm.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.yarm.dao.datasource.DataSourceUtils;
import com.yarm.dao.datasource.MultipleDataSource;
import com.yarm.dao.util.DBUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * author:Yarm.Yang
 * Date:2019/10/23
 * Time:15:43
 * Des:
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.jdbc")
public class DataSourceConfig {
    private Map<String, DruidDataSource> datasources = new HashMap<>();
    private String mode = null;

    @Value("${jdbc.default.datasource.id}")
    private String defaultDataSourceId;


    @Bean
    @Primary
    public DataSource multipleDataSource() {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        datasources.values().forEach(dataSource -> {
            dataSource.setMaxWait(60000);
            dataSource.setRemoveAbandoned(true);
            dataSource.setRemoveAbandonedTimeout(60);
            dataSource.setTestOnBorrow(true);
            dataSource.setTestOnReturn(false);
            dataSource.setTestWhileIdle(true);
            dataSource.setTimeBetweenEvictionRunsMillis(5000);
            dataSource.setMinEvictableIdleTimeMillis(300000);
            dataSource.setValidationQuery("select 1");
            dataSource.setRemoveAbandonedTimeout(1800);
            dataSource.setLogAbandoned(true);
            log.info(dataSource.getName()+"初始化 dataSource:"+ JSONObject.toJSONString(dataSource.getUrl()));
        });
        multipleDataSource.setTargetDataSources(new HashMap(datasources));
        multipleDataSource.setDefaultTargetDataSource(datasources.get(defaultDataSourceId));
        DataSourceUtils.setCurrentKey(defaultDataSourceId);
        DBUtils.setDatabaseNum(datasources.values().size());
        return multipleDataSource;
    }

    @PreDestroy
    public void close() {
        datasources.forEach((s, dataSource) -> dataSource.close());
    }
}