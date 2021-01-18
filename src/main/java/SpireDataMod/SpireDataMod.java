package SpireDataMod;

import SpireDataMod.credentials.Credentials;
import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class SpireDataMod implements PostInitializeSubscriber {
    public static void initialize() { new SpireDataMod(); }
    public SpireDataMod() {
        BaseMod.subscribe(this);
    }

    public static final String credFilename = "mods/spiredata_cred.json";

    @Override
    public void receivePostInitialize() {
        System.out.println("SpireDataMod initialized");

        Credentials credentials = Credentials.loadFromJSON(credFilename);

        if (credentials != null) {
            System.out.println("Successfully loaded credentials file.");
            System.out.println("Credentials has oauth?: " + credentials.hasOauth());
            System.out.println("Credentials has gist?: " + credentials.hasGistID());
        }
        else {
            System.out.println("Credentials were null - file not successfully set up");
        }
    }
}
