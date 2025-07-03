/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sars.vetting.system.common;

import java.sql.Connection;

/**
 *
 * @author S2028398
 */
public interface DatasourceService {

    public Connection getDatasourceConnection();
}
