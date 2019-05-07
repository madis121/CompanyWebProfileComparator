package ee.tlu.cwpc.configuration;

import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

  @Value("${postgresql.database.url}")
  private String url;

  @Value("${postgresql.database.username}")
  private String username;

  @Value("${postgresql.database.password}")
  private String password;

  @Value("${postgresql.database.schema}")
  private String schema;

  @Bean
  public PlatformTransactionManager transactionManager() throws IOException {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory());
    return transactionManager;
  }

  @Bean
  public SessionFactory sessionFactory() throws IOException {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("ee.tlu.cwpc.model");
    sessionFactory.setHibernateProperties(properties());
    sessionFactory.afterPropertiesSet();
    return sessionFactory.getObject();
  }

  @Bean
  public HibernateTemplate hibernateTemplate() throws IOException {
    return new HibernateTemplate(sessionFactory());
  }

  @Bean
  public DataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  private final Properties properties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", "update");
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
    properties.setProperty("hibernate.default_schema", schema);
    //properties.setProperty("hibernate.show_sql", "true");
    properties.setProperty("jadira.usertype.autoRegisterUserTypes", "true");
    return properties;
  }

}
