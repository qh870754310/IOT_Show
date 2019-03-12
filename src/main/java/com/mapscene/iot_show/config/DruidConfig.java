package com.mapscene.iot_show.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author: qh
 * @Date: 2018/10/27 16:08
 * @Description: druid连接池的配置,配置如何处理慢sql,配置监控统计功能
 */
/*
@Configuration
public class DruidConfig {

    private static Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    //容器的开启与关闭
    @Bean(initMethod = "init", destroyMethod = "close")
    //这个注解读取配置文件前缀为prefix的配置，将外部的配置文件与这里绑定
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Primary
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return druidDataSource;
    }

    */
/**
     * bean注解，成为spring的bean，利用filter将慢sql的日志打印出来
     * @return
     *//*

    @Bean
    public Filter statFilter() {
        StatFilter statFilter = new StatFilter();
        //多长时间定义为慢sql，这里定义为5s
        statFilter.setSlowSqlMillis(5000);
        //是否打印出慢日志
        statFilter.setLogSlowSql(true);
        //是否将日志合并起来
        statFilter.setMergeSql(true);
        return statFilter;
    }

    */
/**
     * Druid监控配置
     * 注册ServletRegistrationBean
     * @return
     *//*

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        */
/*ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");*//*

        */
/** 初始化参数配置，initParams**//*

        //白名单(没有配置或者为空，则允许所有访问)
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");//多个ip逗号隔开
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        //bean.addInitParameter("deny", "192.168.1.73");
        //登录查看信息的账号密码
        servletRegistrationBean.addInitParameter("loginUsername", "druid");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据.禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }
}
*/
