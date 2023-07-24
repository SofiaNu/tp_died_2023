package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public Connection conexion=null;

    private Conexion(){
        super();
    }

    private static Conexion _INSTANCE;

    public static Conexion getInstance(){
        if(_INSTANCE == null){
            _INSTANCE = new Conexion();
        }
        return _INSTANCE;

    }
    public void abrir() throws SQLException {
        String serverUrl= "jdbc:postgresql://164.152.54.0:5432/died_psql";
        String user="died_dev";
        String password="misterdatabasepeasant";
        if(conexion == null || conexion.isClosed()){
        try{
            Class.forName("org.postgresql.Driver"); //aparentemente innecesario
            conexion = DriverManager.getConnection(serverUrl,user,password);
            System.out.println("conexion exitosa");
            //conexion.close();
        }catch(SQLException | ClassNotFoundException e){
            System.out.println("error al intentar conectar al server");
            e.printStackTrace();
        }}

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


