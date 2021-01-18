package SpireDataMod.credentials;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Credentials {
    @SerializedName("oauth") @Expose
    private String oauth;

    @SerializedName("gist") @Expose
    private String gist_id;

    private static final transient String filename = "mods/spiredata_cred.json";

    public static Credentials loadFromJSON() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filename));
            Gson gson = new Gson();
            return gson.fromJson(reader, Credentials.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean hasGistID() { return gist_id != null && !gist_id.isEmpty(); }
    public boolean hasOauth() { return oauth != null && !oauth.isEmpty(); }

    public String getOauth() { return oauth; }
    public String getGistID() { return gist_id; }

    public void setGistID(String id) {
        this.gist_id = id;

        Gson gson = new Gson();

        try {
            Writer writer = new FileWriter(filename);
            gson.toJson(this, writer);
            writer.close();

            System.out.println("Successfully wrote " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
