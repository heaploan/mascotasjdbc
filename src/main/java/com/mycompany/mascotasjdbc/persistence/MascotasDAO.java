package com.mycompany.mascotasjdbc.persistence;

import com.mycompany.mascotasjdbc.excepctions.MascotaException;
import com.mycompany.mascotasjdbc.model.ContadorMascotasTO;
import com.mycompany.mascotasjdbc.model.Mascota;
import com.mycompany.mascotasjdbc.model.Propietario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mfontana
 */
public class MascotasDAO {
    
    public ArrayList<ContadorMascotasTO> getQtyByPropietario() throws SQLException {
        ArrayList<ContadorMascotasTO> contadores = new ArrayList<>();
        Connection c = conectar();
        String query = "select propietario.nombre as propietario, count(*) as cantidad from propietario "
                + "join perro on perro.propietario = propietario.nombre group by propietario;";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String prop = rs.getString("propietario");
            int qty = rs.getInt("cantidad");
            contadores.add(new ContadorMascotasTO(prop, qty));
        }
        rs.close();
        st.close();
        desconectar(c);
        return contadores;
    }

    public Mascota getMascotaByNombreFull(String nombre) throws SQLException, MascotaException {
        Connection c = conectar();
        Mascota m = new Mascota();
        String query = "select perro.nombre as apodo, fecha_nacimiento, propietario.nombre as prop, poblacion from "
                + "perro join propietario on propietario = propietario.nombre where apodo='" + nombre +"'";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            String nombreMascota = rs.getString("nombre");
            Date nacimiento = new Date(rs.getDate("fecha_nacimiento").getTime());
            String nombrePropietario = rs.getString("propietario");
            String poblacion = rs.getString("poblado");
            m.setNombre(nombreMascota);
            m.setNacimiento(nacimiento);
            m.setPropietario(new Propietario(nombrePropietario, poblacion));
        } else {
            throw new MascotaException("No existe una mascota con ese nombre");
        }
        rs.close();
        st.close();
        desconectar(c);
        return m;
    }
    
    public ArrayList<Propietario> allPropietarios() throws SQLException {
        Connection c = conectar();
        ArrayList<Propietario> propietarios = new ArrayList<>();
        String query = "select * from propietario;";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String poblacion = rs.getString("poblado");
            propietarios.add(new Propietario(nombre, poblacion));
        }
        rs.close();
        st.close();
        desconectar(c);
        return propietarios;
    }

    public ArrayList<Mascota> allMascotas() throws SQLException, MascotaException {
        ArrayList<Mascota> mascotas = new ArrayList<>();
        Connection c = conectar();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM perro;");
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            Date fechaNacimiento = rs.getDate("fecha_nacimiento");
            String nombrePropietario = rs.getString("propietario");
            Propietario propietario = getPropietario(nombrePropietario);
            Mascota m = new Mascota(nombre, fechaNacimiento, propietario);
            mascotas.add(m);
        }
        rs.close();
        st.close();
        desconectar(c);
        return mascotas;
    }

    public Propietario getPropietario(String nombre) throws SQLException, MascotaException {
        Connection c = conectar();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM propietario WHERE nombre = ?;");
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

    public void insertarMascota(Mascota m) throws SQLException, MascotaException {
        if (existeMascota(m.getNombre())) {
            throw new MascotaException("ERROR: Ya existe una mascota con ese nombre");
        }
        Connection c = conectar();
        PreparedStatement ps = c.prepareStatement("insert into perro (nombre, fecha_nacimiento, propietario) values (?,?,?);");
        ps.setString(1, m.getNombre());
        ps.setDate(2, new java.sql.Date(m.getNacimiento().getTime()));
        ps.setString(3, m.getPropietario().getNombre());
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

    private boolean existeMascota(String nombre) throws SQLException {
        Connection c = conectar();
        Statement st = c.createStatement();
        String query = "select * from perro where nombre = '" + nombre + "';";
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

    public void delPropietario(String nombre) throws SQLException {
        Connection c = conectar();
        PreparedStatement ps = c.prepareStatement("DELETE FROM propietario WHERE nombre = ?;");
        ps.setString(1, nombre);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("No se encontró un propietario con el nombre: " + nombre);
        }
        ps.close();
        desconectar(c);
    }

    public void delMascota(String nombre) throws SQLException{
        Connection c = conectar();
        PreparedStatement ps = c.prepareStatement("DELETE FROM perro WHERE nombre = ?;");
        ps.setString(1, nombre);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("No se encontró una mascota con el nombre: " + nombre);
        }
    }

    private void desconectar(Connection c) throws SQLException {
        c.close();
    }

}
