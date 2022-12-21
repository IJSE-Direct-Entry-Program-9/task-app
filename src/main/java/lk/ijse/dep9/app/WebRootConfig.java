package lk.ijse.dep9.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class WebRootConfig {

    @Bean
    public JndiObjectFactoryBean dataSource(){
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
        jndi.setJndiName("java:comp/env/jdbc/task-app");
        jndi.setExpectedType(DataSource.class);
        return jndi;
    }

    @Bean(destroyMethod = "close")
    @Scope("request")
    public Connection connection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }
}
