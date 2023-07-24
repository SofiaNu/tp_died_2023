package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;

public class Inicio extends JFrame implements ActionListener{

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public Inicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 300);
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
		btnSucursales.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnSucursales);
		
		JButton btnProductos = new JButton("Productos");
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("botn productos");
				VentanaProductos ventanaProductos= new VentanaProductos();
				ventanaProductos.setVisible(true);
			}
		});
		btnProductos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnProductos);
		
		JButton btnCaminos = new JButton("Caminos");
		btnCaminos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCaminos ventanaCaminos= new VentanaCaminos();
				ventanaCaminos.setVisible(true);
			}
		});
		btnCaminos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnCaminos);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}
