package gui;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VentanaSucursales extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaSucursales frame = new VentanaSucursales();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaSucursales() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Gestion de Sucursales");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JButton altabtn = new JButton("Agregar Sucursal");
		JButton buscarbtn = new JButton("Buscar Sucursal");
		JButton bajabtn = new JButton("Dar de Baja Sucursal");
		JButton stockbtn = new JButton("Manejo de Stock");
		JButton ordenbtn = new JButton("Generar Orden de Provision");

		setContentPane(contentPane);




	}


}
