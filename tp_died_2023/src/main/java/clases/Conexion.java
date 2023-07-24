package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    Connection conexion=null;

    public Conexion(){
        super();
    }
    public void abrir(){
        String serverUrl= "jdbc:postgresql://164.152.54.0:5432/died_psql";
        String user="died_dev";
        String password="misterdatabasepeasant";
        try{
            Class.forName("org.postgresql.Driver"); //aparentemente innecesario
            conexion = DriverManager.getConnection(serverUrl,user,password);
            System.out.println("conexion exitosa");
            //conexion.close();
        }catch(SQLException | ClassNotFoundException e){
            System.out.println("error al intentar conectar al server");
            e.printStackTrace();
        }

    }

    public void cerrar() throws SQLException {
        try {
            conexion.close();
            System.out.println("conexion cerrada exitosamente");
        } catch (SQLException e) {
            System.out.println("error al intentar cerrar la conexion");
        }
    }

}


