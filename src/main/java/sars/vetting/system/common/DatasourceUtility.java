
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.vetting.system.common;

import com.zaxxer.hikari.HikariDataSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author S2028398
 */
public class DatasourceUtility {

    public static DataSource getDatasource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setInitializationFailTimeout(0);
        dataSource.setMaximumPoolSize(10);
        dataSource.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
////
//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTAXN73\\SQLEXPRESS14:2010;databaseName=VETTING_DB");
//        dataSource.addDataSourceProperty("user", "terry");
//        dataSource.addDataSourceProperty("password", "P@sswords.");

//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTAUX53\\SQLEXPRESS:2010;databaseName=VETTING_DB");
//        dataSource.addDataSourceProperty("user", "Mpelwane");
//        dataSource.addDataSourceProperty("password", "Mpelwane12345");

        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTAXX56\\SQLEXPRESS:2010;databaseName=VETTING_DB");
        dataSource.addDataSourceProperty("user", "tebx");
        dataSource.addDataSourceProperty("password", "tebx1234");

//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTAYA61:2010;databaseName=VETTING_DB");
//        dataSource.addDataSourceProperty("user", "amogelang");
//        dataSource.addDataSourceProperty("password", "amogelang");
//        
//         dataSource.addDataSourceProperty("url", "jdbc:sqlserver://LPTAXR60\\SQLEXPRESS:2010;databaseName=VETTING_DB");
//         dataSource.addDataSourceProperty("user", "Jiyeza");
//          dataSource.addDataSourceProperty("password", "P@ssw0rd");
//        DEV user
//        dataSource.addDataSourceProperty("url", "jdbc:sqlserver://PTADVSQC05SQL:1433;databaseName=Vetting_DB");
//        dataSource.addDataSourceProperty("user", "vetting_db_user");
//        dataSource.addDataSourceProperty("password", "pass");
        return dataSource;
    }

    public static DataSource getDatasourceLookup() {
        try {
            InitialContext initialContext = new InitialContext();
//            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/vettingDS");
            DataSource dataSource = (DataSource) initialContext.lookup("java:/vettingDS");
            return dataSource;
        } catch (NamingException ex) {
             Logger.getLogger(DatasourceUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
