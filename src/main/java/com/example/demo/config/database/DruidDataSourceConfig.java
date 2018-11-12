package com.example.demo.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author laowang
 * @date 2018/11/9 3:53 PM
 * @Description: 将数据库配置信息注入DruidDataSource中，并开启事务
 */
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig {
    private static Logger logger = LoggerFactory.getLogger(DruidDataSourceConfig.class);

    @Autowired
    DruidDataSourceSettings dataSourceSettings;

    public static String DRIVER_CLASSNAME;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer () {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(dataSourceSettings.getDriverClassName());
        DRIVER_CLASSNAME = dataSourceSettings.getDriverClassName();
        ds.setUrl(dataSourceSettings.getUrl());
        ds.setUsername(dataSourceSettings.getUsername());
        ds.setPassword(dataSourceSettings.getPassword());
        ds.setInitialSize(dataSourceSettings.getInitialSize());
        ds.setMinIdle(dataSourceSettings.getMinIdle());
        ds.setMaxActive(dataSourceSettings.getMaxActive());
        ds.setTimeBetweenEvictionRunsMillis(dataSourceSettings.getTimeBetweenEvictionRunsMillis());
        ds.setMinEvictableIdleTimeMillis(dataSourceSettings.getMinEvictableIdleTimeMillis());
        ds.setValidationQuery(dataSourceSettings.getValidationQuery());
        ds.setTestWhileIdle(dataSourceSettings.isTestWhileIdle());
        ds.setTestOnBorrow(dataSourceSettings.isTestOnBorrow());
        ds.setTestOnReturn(dataSourceSettings.isTestOnReturn());
        ds.setPoolPreparedStatements(dataSourceSettings.isPoolPreparedStatements());
        ds.setMaxPoolPreparedStatementPerConnectionSize(dataSourceSettings.getMaxPoolPreparedStatementPerConnectionSize());
        ds.setFilters(dataSourceSettings.getFilters());

        String connectionPropertiesStr = dataSourceSettings.getConnectionProperties();
        if(connectionPropertiesStr != null && !"".equals(connectionPropertiesStr)){
            Properties connectProperties = new Properties();
            String[] propertiesList = connectionPropertiesStr.split(";");
            for(String propertiesTmp:propertiesList){
                String[] obj = propertiesTmp.split("=");
                String key = obj[0];
                String value = obj[1];
                connectProperties.put(key,value);
            }
            ds.setConnectProperties(connectProperties);
        }
        ds.setUseGlobalDataSourceStat(dataSourceSettings.isUseGlobalDataSourceStat());

        logger.info("druid datasource config : {}",ds);
        return ds;
    }

    /**
     * 开始事务
     * @return
     * @throws Exception
     */
    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource());
        return txManager;
    }
}
