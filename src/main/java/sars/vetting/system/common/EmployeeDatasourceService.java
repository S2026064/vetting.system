/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.common;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author S2028398
 */
public class EmployeeDatasourceService implements DatasourceService {

    @Override
    public Connection getDatasourceConnection() {
        try {
            InitialContext initialContext = new InitialContext();
//            DataSource dataSource = (javax.sql.DataSource) initialContext.lookup("jdbc/users");
            DataSource dataSource = (javax.sql.DataSource) initialContext.lookup("java:/users");
            return dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            Logger.getLogger(EmployeeDatasourceService.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
