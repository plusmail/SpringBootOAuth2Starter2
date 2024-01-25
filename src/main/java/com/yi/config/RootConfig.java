package com.yi.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {JpaConfiguration.class})
public class RootConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private  String password;

    //
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setJpaVendorAdapter(jpaVendorAdapter());
//        emf.setDataSource(dataSource);
//        emf.setPersistenceUnitName("persistenceJpa");
//        emf.setPackagesToScan("com.yi.entity.*");
//        emf.setJpaProperties(additionalProperties());
//        return emf;
//    }
//
//    private JpaVendorAdapter jpaVendorAdapter(){
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        jpaVendorAdapter.setShowSql(true);
//        return jpaVendorAdapter;
//    }
//
//
//    @Bean
//    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
//        return jpaTransactionManager;
//    }
//
//    private Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("spring.jpa.hibernate.ddl-auto", env.getProperty("ddl-auto"));
//        properties.setProperty("spring.jpa.show-sql", env.getProperty("show-sql"));
//        properties.setProperty("spring.jpa.properties.hibernate.format_sql",  env.getProperty("format_sql"));
//        return properties;
//    }
//
//    @Bean
//    public BasicDataSource dataSource() {
//
//        BasicDataSource basicDataSource = new BasicDataSource();
//        basicDataSource.setDriverClassName(driverClassName);
//        basicDataSource.setUrl(url);
//        basicDataSource.setUsername(username);
//        basicDataSource.setPassword(password);
//        return basicDataSource;
//    }
//
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
//        return new SqlSessionTemplate((SqlSessionFactory) sqlSessionFactoryBean());
//
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
//        sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
//        sqlSessionFactory.setDataSource(dataSource());
//        return (SqlSessionFactory)sqlSessionFactory.getObject();
//    }
//
//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        DataSourceTransactionManager transaction = new DataSourceTransactionManager();
//        transaction.setDataSource(dataSource());
//        return transaction;
//    }


}