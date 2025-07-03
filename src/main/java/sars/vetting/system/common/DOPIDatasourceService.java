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


/**
 *
 * @author S2028398
 */
public class DOPIDatasourceService implements DatasourceService {

    @Override
    public Connection getDatasourceConnection() {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setInitializationFailTimeout(0);
            dataSource.setMaximumPoolSize(10);
            dataSource.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
            dataSource.addDataSourceProperty("url", "jdbc:sqlserver://PTADVSQC05SQL:1433;databaseName=DOPI"); //PTAQASQC05N1
            dataSource.addDataSourceProperty("user", "vetting_user");
            dataSource.addDataSourceProperty("password", "Passwords.1");
            dataSource.setConnectionTimeout(3000);
            return dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DOPIDatasourceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//    public Connection getDatasourceConnection() {
//        try {
//            InitialContext initialContext = new InitialContext();
////            DataSource dataSource = (javax.sql.DataSource) initialContext.lookup("jdbc/ibrdata");
//            DataSource dataSource = (javax.sql.DataSource) initialContext.lookup("java:/dopiDS");
//            return dataSource.getConnection();
//        } catch (NamingException | SQLException e) {
//            Logger.getLogger(EmployeeDatasourceService.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return null;
//    }

}
