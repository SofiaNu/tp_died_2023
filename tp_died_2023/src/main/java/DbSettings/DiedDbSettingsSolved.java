package DbSettings;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DiedDbSettingsSolved {
    public String getServerUri() {
        return serverUri;
    }

    public String getServerUser() {
        return serverUser;
    }

    public String getServerPassword() {
        return serverPassword;
    }

    String serverUri;
    String serverUser;
    String serverPassword;

    public static DiedDbSettingsSolved GetDefaultDbSettings(){
        try{
            JsonReader jsonReader = new JsonReader(new FileReader("./dbSettings.json"));
            Gson gson = new Gson();
            return gson.fromJson(jsonReader, DiedDbSettingsSolved.class);
        } catch(FileNotFoundException fileNotFoundException){
            return null;
         }
    }
}
