/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Persona;
import Modelo.PersonaDAO;
import Vista.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author migue
 */
public class Controlador implements ActionListener  {

    PersonaDAO dao = new PersonaDAO();
    Persona p = new Persona();
    Vista v = new Vista();
    DefaultTableModel modelo = new DefaultTableModel();
                
    public Controlador(Vista v) {
        this.v = v;
        this.v.btnListar.addActionListener(this);
        this.v.btnGuardar.addActionListener(this);
        this.v.btnEditar.addActionListener(this);
        this.v.btnEliminar.addActionListener(this);
        this.v.btnListo.addActionListener(this);
        this.v.btnBuscar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == v.btnListar) {
            Listar(v.JTable);
        }
        if (e.getSource() == v.btnGuardar) {
            Agregar();
        }
        if (e.getSource() == v.btnEditar) {
        Editar();
        }
        if (e.getSource() == v.btnEliminar) {
        Eliminar();
        }
        if (e.getSource() == v.btnBuscar) {
        String nombre = v.tfNombre.getText();
        String correo = v.tfCorreo.getText(); 
        String telefono = v.tfTelefono.getText();
        Buscar(v.JTable, nombre, correo, telefono);
}
        if (e.getSource() == v.btnListo) {
        cerrarVentana();
    }
    }

    public void Listar(JTable table) {
        modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0);
        List<Persona> lista = dao.listar();
        Object[] object = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getCorreo();
            object[3] = lista.get(i).getTelefono();
            modelo.addRow(object);
        }
    }
    
    public void Agregar() {
        String nom = v.tfNombre.getText();
        String corr = v.tfCorreo.getText();
        String tel = v.tfTelefono.getText();

        if (nom.isEmpty() || corr.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Todos los campos deben ser completados");
            return;
        }

        p.setNombre(nom);
        p.setCorreo(corr);
        p.setTelefono(tel);

        int respuesta = dao.Agregar(p);
    
        if (respuesta == 1) {
            JOptionPane.showMessageDialog(v, "Usuario Agregado");
            Listar(v.JTable);
        } else {
            JOptionPane.showMessageDialog(v, "Error al agregar el usuario");
        }
    }
    public void Editar() {
    int fila = v.JTable.getSelectedRow(); 

    if (fila == -1) {
        JOptionPane.showMessageDialog(v, "Debe seleccionar una fila");
        return;
    }

    int id = Integer.parseInt(v.JTable.getValueAt(fila, 0).toString());
    String nom = v.tfNombre.getText();
    String corr = v.tfCorreo.getText();
    String tel = v.tfTelefono.getText();

    if (nom.isEmpty() || corr.isEmpty() || tel.isEmpty()) {
        JOptionPane.showMessageDialog(v, "Todos los campos deben ser completados");
        return;
    }

    p.setId(id);
    p.setNombre(nom);
    p.setCorreo(corr);
    p.setTelefono(tel);

    int respuesta = dao.Editar(p);

    if (respuesta == 1) {
        JOptionPane.showMessageDialog(v, "Usuario Editado");
        Listar(v.JTable); 
    } else {
        JOptionPane.showMessageDialog(v, "Error al editar el usuario");
    }
}
    public void Eliminar() {
    int fila = v.JTable.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(v, "Debe seleccionar una fila");
            return;
        }

        int id = Integer.parseInt(v.JTable.getValueAt(fila, 0).toString());

        int respuesta = JOptionPane.showConfirmDialog(v, "¿Está seguro de que desea eliminar este Usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            dao.Eliminar(id); // Eliminar el empleado
            JOptionPane.showMessageDialog(v, "Usuario Eliminado");
            Listar(v.JTable); 
        }
    }
    
    public void Buscar(JTable table, String nombre, String correo, String telefono) {
     modelo = (DefaultTableModel) table.getModel();

    try {
        modelo.setRowCount(0);

        List<Persona> lista = dao.Buscar(nombre, correo, telefono);

        Object[] fila = new Object[4];

        for (Persona persona : lista) {
            fila[0] = persona.getId();
            fila[1] = persona.getNombre();
            fila[2] = persona.getCorreo();
            fila[3] = persona.getTelefono();
            modelo.addRow(fila);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al realizar la búsqueda: " + e.getMessage());
    }
}

        public void cerrarVentana() {
        v.dispose(); // Cerrar la ventana
    }
    
}



