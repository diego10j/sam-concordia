/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teleton;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Diego
 */
public class cone {

    public cone() {
        final String fileName = "c:/liquidacion.mdb";
        Connection con = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+fileName;
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " ....");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " ....");
            }
        }
    }

    public static void main(String args[]) {
        cone c = new cone();

    }
}
