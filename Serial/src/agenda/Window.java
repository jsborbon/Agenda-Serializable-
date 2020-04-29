package agenda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Window extends JFrame implements ActionListener {

	private JPanel contentPane;
	private File fichero;
	private boolean restriccion1;
	private Persona person;
	private int lineasAdicionales;
	private ValidacionDatos datosValidos;
	private ContadorLineas cLineas;
	private DefaultTableModel modelo;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JButton btnNuevoContacto;
	private JButton btnSalir;
	private JButton btnEliminarArchivo;
	private boolean actualizacion;
	private JTable table;
	private int clave;
	private boolean restriccion2;

	public Window() {

		initComponents();

		lineasAdicionales = 0;
		actualizacion = false;
		cLineas = new ContadorLineas();
		datosValidos = new ValidacionDatos();

		// SE LLAMA AL ARCHIVO
		fichero = new File("Directorio.dat");

		// SE CREA EL ARCHIVO SI Aï¿½N NO EXISTE
		if (!fichero.exists()) {
			crearArchivo();
		}

		imprimir();
	}

	private void imprimir() {

		cLineas.contarLineas();
		if (cLineas.getConteo() > 18) {
			lineasAdicionales++;
			if (lineasAdicionales == 1) {
				for (int i = 18 + lineasAdicionales; i <= cLineas.getConteo() + (cLineas.getConteo() / 20); i++) {
					Object[] newRow = { null, null };
					modelo.addRow(newRow);
				}
			} else {
				Object[] newRow = { null, null };
				modelo.addRow(newRow);
			}

		}


		try {
			// si existe el fichero lo lee
			if (fichero.exists()) {

				ois = new ObjectInputStream(new FileInputStream(fichero));

				// SaldrÃ¡ cuando no haya mas datos que leer, EOFException
				int i = 0;
				while (true) {

					// leo el objeto
					person = (Persona) ois.readObject();
					// lo muestro

					table.setValueAt(person.getNombre(), i, 0);
					table.setValueAt(person.getTelefono(), i, 1);
					
					//CONTEO
					i++;
				}

			} else {
				System.out.println("Debes ingresar un registro");
			}
		} catch (EOFException ex) { // Excepcion para ObjectInputStream
			if (actualizacion == true) {
				JOptionPane.showMessageDialog(null, "La agenda ha sido actualizada");
			}
			actualizacion = true;
		} catch (IOException | ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());

		}

	}

	private void aniadirDatos() {

		datosValidos.ValidarDatos();
		person = new Persona(datosValidos.getName(), datosValidos.getTelefono());

		// Si el fichero esta vacio, escribiremos con Objectoutputstream
		// Sino con MiObjectOutputStream
		try {

			if (fichero.length() == 0) {
				oos = new ObjectOutputStream(new FileOutputStream(fichero));
			} else {
				oos = new MiObjectOutputStream(new FileOutputStream(fichero, true));
			}

			// lo inserta en el fichero
			oos.writeObject(person);

			JOptionPane.showMessageDialog(null, "Se ha añadido correctamente, \n\n\tPor favor espere...");

		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "No se ha podido añadir correctamente");

		}
		imprimir();
	}

	private void crearArchivo() {
		try {

			if (fichero.createNewFile()) {
				System.out.println("Se ha creado el fichero correctamente");
				restriccion1 = false;
			} else {
				restriccion1 = true;
			}
		} catch (IOException e) {
			if (restriccion1 == true) {
				System.out.println("Ha habido un error al crear el fichero");
			}
		}
	}

	private void crearTabla() {

		modelo = new DefaultTableModel(new Object[][] { // NOMBRE,TELEFONO
				{ null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, }, new String[] { "NOMBRE", "TELEFONO" });
		table.setModel(modelo);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);

	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		setUndecorated(true);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblDirectorioTelefnico = new JLabel("DIRECTORIO TELEF\u00D3NICO");
		lblDirectorioTelefnico.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 18));
		lblDirectorioTelefnico.setHorizontalAlignment(SwingConstants.CENTER);
		lblDirectorioTelefnico.setForeground(Color.RED);
		lblDirectorioTelefnico.setBounds(68, 11, 281, 40);
		panel.add(lblDirectorioTelefnico);

		JLabel lblPhone = new JLabel("");
		lblPhone.setIcon(new ImageIcon(Window.class.getResource("/recursos/tel.png")));
		lblPhone.setBounds(258, 56, 114, 58);
		panel.add(lblPhone);

		JLabel lblName = new JLabel("");
		lblName.setIcon(new ImageIcon(Window.class.getResource("/recursos/Person.png")));
		lblName.setBounds(34, 56, 114, 58);
		panel.add(lblName);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setForeground(Color.BLACK);
		panel_1.setBounds(34, 115, 373, 334);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 375, 334);
		panel_1.add(scrollPane);
		table = new JTable();
		crearTabla();
		scrollPane.setViewportView(table);

		btnEliminarArchivo = new JButton("ELIMINAR");
		btnEliminarArchivo.setFocusable(false);
		btnEliminarArchivo.setBackground(Color.RED);
		btnEliminarArchivo.setForeground(Color.WHITE);
		btnEliminarArchivo.addActionListener(this);
		btnEliminarArchivo.setBounds(157, 460, 89, 23);
		panel.add(btnEliminarArchivo);

		btnSalir = new JButton("SALIR");
		btnSalir.setFocusable(false);
		btnSalir.addActionListener(this);
		btnSalir.setBounds(276, 460, 89, 23);
		panel.add(btnSalir);

		btnNuevoContacto = new JButton("NUEVO");
		btnNuevoContacto.setFocusable(false);
		btnNuevoContacto.addActionListener(this);
		btnNuevoContacto.setBounds(40, 460, 89, 23);
		panel.add(btnNuevoContacto);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnSalir)) {
			dispose();
		}
		if (e.getSource().equals(btnNuevoContacto)) {
			aniadirDatos();
		}
		if (e.getSource().equals(btnEliminarArchivo)) {
			try {
				JOptionPane.showMessageDialog(null,
						"¡¡Al digitar la clave, sus datos serán borrados y el programa se cerrará!!", "ADVERTENCIA",
						JOptionPane.WARNING_MESSAGE);
				clave = Integer
						.parseInt(JOptionPane.showInputDialog("Por seguridad ingrese el año en el que estamos\n"));
			} catch (NumberFormatException nfe) {
				clave = 1;
			}
			if (clave == 2020) {
				fichero.delete();
				if (!fichero.exists()) {
					System.out.println("El fichero se ha borrado");
				} else {
					JOptionPane.showMessageDialog(null, "El archivo se eliminará al cerrar esta aplicación\n");
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("Directorio.dat"));
						bw.write("");
						bw.close();
						restriccion1 = false;
						restriccion2 = false;
						dispose();
					} catch (FileNotFoundException fnfe) { /* Archivo no encontrado */
						if (restriccion1 == false) {
							JOptionPane.showMessageDialog(null,
									"No se ha encontrado o podido crear el archivo,\nCompruebe que tenga permisos en la carpeta C:\\,\nhe intente nuevamente.");
							restriccion1 = true;
						}

					} catch (IOException ioe) { /* Error al escribir */
						if (restriccion2 == false) {
							JOptionPane.showMessageDialog(null, "Error al limpiar los registros");
							restriccion2 = true;
						}

					}
					fichero.deleteOnExit();
				}
				clave = 1;
			} else {
				JOptionPane.showMessageDialog(null, "Clave erronea\n");
			}
		}
	}


}
