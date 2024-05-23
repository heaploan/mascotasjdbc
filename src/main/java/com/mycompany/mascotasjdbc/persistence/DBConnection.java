package com.mycompany.mascotasjdbc.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mascotas";
        String user = "root";
        String pass = "root";
        Connection c = DriverManager.getConnection(url, user, pass);
        return c;
    }


    void desconectar(Connection c) throws SQLException {
        c.close();
    }
}
