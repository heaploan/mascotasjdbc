package com.mycompany.mascotasjdbc.persistence;

import com.mycompany.mascotasjdbc.excepctions.MascotaException;
import com.mycompany.mascotasjdbc.model.Mascota;
import com.mycompany.mascotasjdbc.model.Propietario;

import java.sql.*;
import java.util.ArrayList;

public class PerroDAO {

    private DBConnection c = new DBConnection();
    private PropietarioDAO pd = new PropietarioDAO();

    private boolean existeMascota(String nombre) throws SQLException {
        Connection con = c.conectar();
        Statement st = con.createStatement();
        String query = "select * from perro where nombre = '" + nombre + "';";
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

    public void insertarMascota(Mascota m) throws SQLException, MascotaException {
        if (existeMascota(m.getNombre())) {
            throw new MascotaException("ERROR: Ya existe una mascota con ese nombre");
        }
        Connection con = c.conectar();
        PreparedStatement ps = con.prepareStatement("insert into perro values (?,?,?);");
        ps.setString(1, m.getNombre());
        ps.setDate(2, new java.sql.Date(m.getNacimiento().getTime()));
        ps.setString(3, m.getPropietario().getNombre());
        ps.executeUpdate();
        ps.close();
        c.desconectar(con);
    }

    public ArrayList<Mascota> allMascotas() throws SQLException, MascotaException {
        ArrayList<Mascota> mascotas = new ArrayList<>();
        Connection con = c.conectar();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM perro;");
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            java.util.Date fechaNacimiento = rs.getDate("fecha_nacimiento");
            String nombrePropietario = rs.getString("propietario");
            Propietario propietario = pd.getPropietario(nombrePropietario);
            Mascota m = new Mascota(nombre, fechaNacimiento, propietario);
            mascotas.add(m);
        }
        rs.close();
        st.close();
        c.desconectar(con);
        return mascotas;
    }

    // Obtiene toda la información por nombre de la mascota
    public Mascota getMascotaByNombre(String nombre) throws SQLException, MascotaException {
        Connection con = c.conectar();
        Mascota m = new Mascota();
        String query = "select perro.nombre as apodo, fecha_nacimiento, propietario.nombre as propietario, poblacion from "
                + "perro join propietario on propietario = propietario.nombre where apodo='" + nombre +"'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            String nombreMascota = rs.getString("nombre");
            java.util.Date nacimiento = new java.util.Date(rs.getDate("fecha_nacimiento").getTime());
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
        c.desconectar(con);
        return m;
    }

    public void delMascota(String nombre) throws SQLException{
        Connection con = c.conectar();
        PreparedStatement ps = con.prepareStatement("DELETE FROM perro WHERE nombre = ?;");
        ps.setString(1, nombre);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("No se encontró una mascota con el nombre: " + nombre);
        }
    }
}
