package lk.ijse.dep9.app;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.annotation.RequestScope;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@EnableTransactionManagement
public class WebRootConfig {

    @Bean
    public JndiObjectFactoryBean dataSource() {
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
        jndi.setJndiName("java:comp/env/jdbc/task-app");
        jndi.setExpectedType(DataSource.class);
        return jndi;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource ds){
        return new JdbcTemplate(ds);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
