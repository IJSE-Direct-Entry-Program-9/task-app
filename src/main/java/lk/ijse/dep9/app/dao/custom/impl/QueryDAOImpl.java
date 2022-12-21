package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.QueryDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueryDAOImpl implements QueryDAO {

    private final JdbcTemplate jdbc;

    public QueryDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
