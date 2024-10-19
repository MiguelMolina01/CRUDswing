/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class PersonaDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List<Persona> listar() {
        List<Persona> datos = new ArrayList<>();
        String sql = "SELECT ID, Nombre, Correo, Telefono FROM users";
        
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCorreo(rs.getString(3));
                p.setTelefono(rs.getString(4));
                datos.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return datos;
    }
    public int Agregar(Persona p) {
    String sql = "insert into users (Nombre, Correo, Telefono) values (?, ?, ?)";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return 1;
    }
    
    public int Editar(Persona p) {
    String sql = "update users set Nombre = ?, Correo = ?, Telefono = ? where id = ?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return 1;
    }
    
    public int Eliminar(int id) {
    String sql = "delete from users where id = ?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id); 
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return 1;
    }
    
    public List<Persona> Buscar(String nombre, String correo, String telefono) {
    List<Persona> datos = new ArrayList<>();
    String sql = "SELECT ID, Nombre, Correo, Telefono FROM users WHERE 1=1";

    if (nombre != null && !nombre.isEmpty()) {
        sql += " AND Nombre = ?";
    }
    if (correo != null && !correo.isEmpty()) {
        sql += " AND Correo = ?";
    }
    if (telefono != null && !telefono.isEmpty()) {
        sql += " AND Telefono = ?";
    }

        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);

            int index = 1;
            if (nombre != null && !nombre.isEmpty()) {
                ps.setString(index++, nombre);
            }
            if (correo != null && !correo.isEmpty()) {
                ps.setString(index++, correo );
            }
            if (telefono != null && !telefono.isEmpty()) {
                ps.setString(index++, telefono);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt("ID"));
                p.setNombre(rs.getString("Nombre"));
                p.setCorreo(rs.getString("Correo"));
                p.setTelefono(rs.getString("Telefono"));
                datos.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return datos;
    }

}
