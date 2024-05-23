package com.mycompany.mascotasjdbc.persistence;

import com.mycompany.mascotasjdbc.excepctions.MascotaException;
import com.mycompany.mascotasjdbc.model.ContadorMascotasTO;
import com.mycompany.mascotasjdbc.model.Propietario;

import java.sql.*;
import java.util.ArrayList;

public class PropietarioDAO {
    private DBConnection c = new DBConnection();

    private boolean existePropietario(String nombre) throws SQLException {
        Connection con = c.conectar();
        Statement st = con.createStatement();
        String query = "select * from propietario where nombre = '" + nombre + "';";
        ResultSet rs = st.executeQuery(query);
        boolean existe = false;
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        c.desconectar(con);
        return existe;
    }

    public void insertarPropietario(Propietario p) throws SQLException, MascotaException {
        if (existePropietario(p.getNombre())) {
            throw new MascotaException("ERROR: Ya existe un propietario con ese nombre");
        }
        Connection con = c.conectar();
        PreparedStatement ps = con.prepareStatement("insert into propietario values (?,?);");
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getPoblacion());
        ps.executeUpdate();
        ps.close();
        c.desconectar(con);
    }

    public ArrayList<Propietario> allPropietarios() throws SQLException {
        Connection con = c.conectar();
        ArrayList<Propietario> propietarios = new ArrayList<>();
        String query = "select * from propietario;";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String poblacion = rs.getString("poblado");
            propietarios.add(new Propietario(nombre, poblacion));
        }
        rs.close();
        st.close();
        c.desconectar(con);
        return propietarios;
    }


    public Propietario getPropietario(String nombre) throws SQLException, MascotaException {
        Connection con = c.conectar();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM propietario WHERE nombre = ?;");
        ps.setString(1,nombre);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            String poblado = rs.getString("poblado");
            Propietario p = new Propietario(nombre,poblado);
            return p;
        } else {
            throw new MascotaException("No existe un propietario con ese nombre");
        }
    }

    public ArrayList<ContadorMascotasTO> getQtyByPropietario() throws SQLException {
        ArrayList<ContadorMascotasTO> contadores = new ArrayList<>();
        Connection con = c.conectar();
        String query = "select propietario.nombre as propietario, count(*) as cantidad from propietario "
                + "join perro on perro.propietario = propietario.nombre group by propietario;";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String prop = rs.getString("propietario");
            int qty = rs.getInt("cantidad");
            contadores.add(new ContadorMascotasTO(prop, qty));
        }
        rs.close();
        st.close();
        c.desconectar(con);
        return contadores;
    }

    public void delPropietario(String nombre) throws SQLException {
        Connection con = c.conectar();
        PreparedStatement ps = con.prepareStatement("DELETE FROM propietario WHERE nombre = ?;");
        ps.setString(1, nombre);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("No se encontró un propietario con el nombre: " + nombre);
        }
        ps.close();
        c.desconectar(con);
    }
}
