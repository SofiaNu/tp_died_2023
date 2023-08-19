package gui;

import routeviewer.RouteGUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;

public class Inicio extends JFrame{

	private JPanel contentPane;

	public Inicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		setTitle("Inicio");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnSucursales = new JButton("Sucursales");
		btnSucursales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaSucursales ventanaSucursales= new VentanaSucursales();
				ventanaSucursales.setVisible(true);
			}
		});
		contentPane.add(btnSucursales);
		
		JButton btnProductos = new JButton("Productos");
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaProductos ventanaProductos= new VentanaProductos();
				ventanaProductos.setVisible(true);
			}
		});
		contentPane.add(btnProductos);
		
		JButton btnCaminos = new JButton("Caminos");
		btnCaminos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCaminos ventanaCaminos = null;
				try {
					ventanaCaminos = new VentanaCaminos();
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
				ventanaCaminos.setVisible(true);
			}
		});
		contentPane.add(btnCaminos);

		JButton btnOrdenes = new JButton("Gestion de Ordenes de Provision");
		btnOrdenes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaOrden ventanaOrden = new VentanaOrden();
				ventanaOrden.setVisible(true);
			}
		});
		contentPane.add(btnOrdenes);
		RouteGUI rg = new RouteGUI();

		contentPane.add(rg);
	}


}
