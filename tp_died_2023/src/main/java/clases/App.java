package clases;

import gui.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Inicio inicio = new Inicio();
		inicio.setVisible(true);
		conectar();
	}

	public static void conectar(){
		String serverUrl= "jdbc:postgresql://164.152.54.0:5432/died_psql";
		String user="died_dev";
		String password="misterdatabasepeasant";
		try{
			Class.forName("org.postgresql.Driver"); //aparentemente innecesario
			Connection conexion = DriverManager.getConnection(serverUrl,user,password);
			System.out.println("conexion exitosa");
			conexion.close();
		}catch(SQLException   | ClassNotFoundException e){
			System.out.println("error al intentar conectar al server");
			e.printStackTrace();
		}

	}

}
