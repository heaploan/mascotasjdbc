package com.mycompany.mascotasjdbc.persistence;

import com.mycompany.mascotasjdbc.excepctions.MascotaException;
import com.mycompany.mascotasjdbc.model.Propietario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author mfontana
 */
public class MascotasDAO {

    public ArrayList<Propietario> allPropietarios() throws SQLException {
        Connection c = conectar();
        ArrayList<Propietario> propietarios = new ArrayList<>();
        String query = "select * from propietario;";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String poblacion = rs.getString("poblacion");
            propietarios.add(new Propietario(nombre, poblacion));
        }
        rs.close();
        st.close();
        desconectar(c);
        return propietarios;
    }

    public void insertarPropietario(Propietario p) throws SQLException, MascotaException {
        if (existePropietario(p.getNombre())) {
            throw new MascotaException("ERROR: Ya existe un propietario con ese nombre");
        }
        Connection c = conectar();
        PreparedStatement ps = c.prepareStatement("insert into propietario values (?,?);");
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getPoblacion());
        ps.executeUpdate();
        ps.close();
        desconectar(c);
    }

    private boolean existePropietario(String nombre) throws SQLException {
        Connection c = conectar();
        Statement st = c.createStatement();
        String query = "select * from propietario where nombre = '" + nombre + "';";
        ResultSet rs = st.executeQuery(query);
        boolean existe = false;
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        desconectar(c);
        return existe;
    }

    private Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mascotas";
        String user = "root";
        String pass = "root";
        Connection c = DriverManager.getConnection(url, user, pass);
        return c;
    }

    private void desconectar(Connection c) throws SQLException {
        c.close();
    }

}
