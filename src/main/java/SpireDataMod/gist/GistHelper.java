package SpireDataMod.gist;

import SpireDataMod.credentials.Credentials;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GistHelper {
    private GistService service;
    private Gist gist;

    private static final String dataFilename = "spiredata.json";

    private GistHelper(GistService service, Gist existingGist) {
        this.service = service;
        this.gist = existingGist;
    }

    public static GistHelper build(Credentials credentials) {
        if (credentials.hasOauth()) {
            GistService service = new GistService();
            service.getClient().setOAuth2Token(credentials.getOauth());

            if (credentials.hasGistID())
                return loadExistingGist(credentials, service);
            else
                return constructNewGist(credentials, service);
        }

        return null;
    }

    private static GistHelper constructNewGist(Credentials credentials, GistService service) {
        // Setup the main data file we'll be using
        Map<String,GistFile> files = new HashMap<>();

        GistFile file = new GistFile();
        file.setContent("{}");
        files.put(dataFilename, file);

        // Construct the gist
        Gist gist = new Gist();
        gist.setFiles(files);
        gist.setDescription("Contains SpireData information");

        System.out.println("Attempting to construct new Gist");

        // Try and upload the gist
        try {
            Gist resultingGist = service.createGist(gist);

            // Since this constructNewGist() is only called when a gist doesn't exist in the credentials file, make sure
            // to update it so we don't do this next time.
            String gistID = resultingGist.getId();
            credentials.setGistID(gistID);

            System.out.println("Successfully constructed new Gist with ID: " + gistID);

            return new GistHelper(service, resultingGist);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static GistHelper loadExistingGist(Credentials credentials, GistService service) {
        System.out.println("Attempting to load existing Gist");

        try {
            Gist gist = service.getGist(credentials.getGistID());
            boolean hasFile = gist.getFiles().containsKey(dataFilename);
            System.out.println("Successfully loaded existing Gist, has file: " + hasFile);
            return new GistHelper(service, gist);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load Gist, so attempting to construct new one as a fallback.");
            return constructNewGist(credentials, service);
        }
    }
}
