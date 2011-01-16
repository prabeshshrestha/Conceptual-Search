/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.d2.cse.core.Odm;

import javax.datamining.resource.ConnectionSpec;
import oracle.dmt.jdm.resource.OraConnectionFactory;
import oracle.dmt.odm.Connection;
import oracle.dmt.odm.DataMiningServer;

/**
 *
 * @author Administrator
 */
public class test {
//Create a DMS object
    private static DataMiningServer m_dms = null;
    private static Connection m_dmsConn = null;
    //provide schema name that has the data tables/views
    private static final String DATA_SCHEMA_NAME = "search";

    public static void main(String args[]) {
        try {
            m_dms = new DataMiningServer(
                    "jdbc:oracle:thin:@dhaubaji:1521:search", //JDBC URL jdbc:oracle:thin:@<Host name>:<Port>:<SID>
                    "system", //User Name
                    "password" //Password 
                    );
            //Login to the DMS and create a DMS Connection
            m_dmsConn = m_dms.login();

        } catch (Exception anyExp) {
            anyExp.printStackTrace(System.out);
        } finally {
            
        }
    }
}
