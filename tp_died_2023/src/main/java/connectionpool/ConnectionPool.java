package connectionpool;

import DbSettings.DiedDbSettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

// based on https://www.baeldung.com/java-connection-pooling
public class ConnectionPool {
    private int POOL_SIZE = 5;//as this program does not (at least for now) run concurrent connections this can be 1... but for fun? => 3
    private ArrayList<Connection> availableConnections;
    private ArrayList<Connection> usedConnections;
    private static ConnectionPool _instance = null;
    private static DiedDbSettings _dbSettings;
    private ConnectionPool() {};

    public static void setup(){
        if(_instance == null){
            _instance = new ConnectionPool();
            _instance.initialize();
        }
    }

    public static Connection getConnection(){
        setup();

        Connection connection = null;
        if(_instance.availableConnections.size() > 0){
            connection = _instance.availableConnections.remove(0);
            _instance.usedConnections.add(connection);
        }

        return connection;
    }

    public static boolean releaseConnection(Connection connection){
        if(connection == null){
            return false;
        }

        _instance.availableConnections.add(connection);
        return _instance.usedConnections.remove(connection);
    }

    private void initialize(){
        _dbSettings = DiedDbSettings.GetDefaultDbSettings();
        availableConnections = new ArrayList<>();
        usedConnections = new ArrayList<>();

        String serverUrl= _dbSettings.getServerUri();
        String user= _dbSettings.getServerUser();
        String password= _dbSettings.getServerPassword();

        try{
            for(int i = 0; i < POOL_SIZE; i++){
                Connection connection = DriverManager.getConnection(serverUrl,user,password);
                availableConnections.add(connection);
            }
        }
        catch (SQLException exception){
            System.out.println("Cant Initialize ConnectionPool: " + exception.getMessage() + "(" + ")");
            exception.printStackTrace();
            System.exit(-1);
        }

    }
}
