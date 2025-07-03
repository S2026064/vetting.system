/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.common;

/**
 *
 * @author S2028398
 */
public class DatasourceFactory {

    public static DatasourceService getDatabase(ConnectionType connectionType) {
        if (connectionType.equals(ConnectionType.EMPLOYEE_DATABASE)) {
            return new EmployeeDatasourceService();
        } else if (connectionType.equals(ConnectionType.IBR_PCA_DATABASE)) {
            return new IbrPcaDatasourceService();
        } else if (connectionType.equals(ConnectionType.DOPI)) {
            return new DOPIDatasourceService();
        }
        return null;
    }
}
