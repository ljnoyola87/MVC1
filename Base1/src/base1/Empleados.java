 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base1;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LUIS NAVARRETE
 */
public class Empleados extends javax.swing.JFrame { //incio de clase empledos

    /**
     * Creates new form Empleados
     */
    public Empleados() {
        initComponents();
         llenarComboBoxDepartamentos();
         
    }
    
    public void llenarComboBoxDepartamentos() { //inicio metodo llenar deptos
    Connection con = ConexionDB.conectar();
    if (con != null) {
        try {
            String query = "SELECT nombre_departamento FROM departamentos";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            cbDepartamentos.removeAllItems(); // Limpiar antes de agregar nuevos datos

            while (rs.next()) {
                cbDepartamentos.addItem(rs.getString("nombre_departamento"));
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("❌ Error al llenar cbDepartamentos: " + e.getMessage());
        }
    }
}// fin metodo llenar desptos
    
    
// Método para actualizar el salario usando el Stored Procedure
    private void actualizarSalario() {
        Connection conexion = ConexionDB.conectar(); // Se usa la conexión desde la clase externa

        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            return;
        }

        try {
            // Validar que los campos no estén vacíos
            if (TxtIDEmpleado.getText().isEmpty() || TxtNuevoSalario.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID del empleado y el nuevo salario.");
                return;
            }

            int idEmpleado = Integer.parseInt(TxtIDEmpleado.getText().trim()); // Obtener ID del empleado
            double nuevoSalario = Double.parseDouble(TxtNuevoSalario.getText().trim()); // Obtener nuevo salario

            CallableStatement cs = conexion.prepareCall("{CALL ActualizarSalario(?, ?)}");
            cs.setInt(1, idEmpleado);
            cs.setDouble(2, nuevoSalario);
            cs.execute();

            JOptionPane.showMessageDialog(this, "Salario actualizado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Ingrese valores numéricos válidos.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar salario: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close(); // Cerrar conexión después de la consulta
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }//fin Actualizar el Salario de un Empleado
    
    
    public void llenarComboBoxEmpleados(String nombreDepartamento) {//ver empleados
    Connection con = ConexionDB.conectar();
    if (con != null) {
        try {
            CallableStatement cs = con.prepareCall("{CALL ObtenerEmpleadosPorDepartamento(?)}");
            cs.setString(1, nombreDepartamento);
            ResultSet rs = cs.executeQuery();

              cbEmpleados.removeAllItems();  // Limpiar antes de agregar nuevos datos

            while (rs.next()) {
                 cbEmpleados.addItem(rs.getString("nombre")); // Agregar empleados al JComboBox
               
            }

            rs.close();
            cs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("❌ Error al llenar cbEmpleados: " + e.getMessage());
        }
    }
}//fin ver empleados
    
    private void mostrarEmpleadoEnTabla() { // ver datos empleados
    try {
        Connection con = ConexionDB.conectar();
        String empleadoSeleccionado = (String) cbEmpleados.getSelectedItem();
        String sql = "SELECT * FROM empleados WHERE nombre = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, empleadoSeleccionado);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Nombre", "Salario", "Departamento"});

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getInt("id_empleado"),
                rs.getString("nombre"),                
                rs.getDouble("salario"),
                rs.getInt("departamento_id")
            });
        }

        tblEmpleado.setModel(modelo); // Asignar modelo a la tabla
        con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al mostrar empleado: " + e.getMessage());
    }
}// fin ver datos

    
    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbDepartamentos = new javax.swing.JComboBox<>();
        lblDepartamentos = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbEmpleados = new javax.swing.JComboBox<>();
        btnCargarEmpleados = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleado = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        TxtNuevoSalario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        TxtIDEmpleado = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbDepartamentos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbDepartamentos.setName(""); // NOI18N

        lblDepartamentos.setText("Departamento");

        jLabel2.setText("Empleado");

        cbEmpleados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEmpleadosActionPerformed(evt);
            }
        });

        btnCargarEmpleados.setText("Cargar");
        btnCargarEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarEmpleadosActionPerformed(evt);
            }
        });

        tblEmpleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblEmpleado);

        jLabel1.setText("SalarioNuevo");

        jButton1.setText("ActualizarSalario");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("IdEmpleado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(lblDepartamentos))
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbEmpleados, 0, 166, Short.MAX_VALUE)
                                    .addComponent(cbDepartamentos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(36, 36, 36)
                                .addComponent(btnCargarEmpleados)
                                .addGap(91, 91, 91)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtNuevoSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtIDEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 135, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(158, 158, 158))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDepartamentos)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TxtIDEmpleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCargarEmpleados)
                        .addComponent(jLabel1)
                        .addComponent(TxtNuevoSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarEmpleadosActionPerformed
        // TODO add your handling code here:
     btnCargarEmpleados.addActionListener(e -> {
    String departamentoSeleccionado = (String) cbDepartamentos.getSelectedItem();
    if (departamentoSeleccionado != null) {
        llenarComboBoxEmpleados(departamentoSeleccionado);
    }
});
    }//GEN-LAST:event_btnCargarEmpleadosActionPerformed

    private void cbEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEmpleadosActionPerformed
        // TODO add your handling code here:
        cbEmpleados.addActionListener(e -> mostrarEmpleadoEnTabla());
    }//GEN-LAST:event_cbEmpleadosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
          actualizarSalario();
          mostrarEmpleadoEnTabla();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Empleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Empleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TxtIDEmpleado;
    private javax.swing.JTextField TxtNuevoSalario;
    private javax.swing.JButton btnCargarEmpleados;
    private javax.swing.JComboBox<String> cbDepartamentos;
    private javax.swing.JComboBox<String> cbEmpleados;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDepartamentos;
    private javax.swing.JTable tblEmpleado;
    // End of variables declaration//GEN-END:variables
} // fin de clase empleado
