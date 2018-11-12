package com.example.demo.config.database;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * @author laowang
 * @date 2018/11/9 6:33 PM
 * @Description: 配置BasePackage，dao操作
 */
@Configuration
@AutoConfigureAfter(MybatisDataSourceConfig.class)    // 在MybatisDataSourceConfig类加载完才能加载本类
public class MabatisMapperScanerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.example.demo.mapper");
        return mapperScannerConfigurer;
    }
}
