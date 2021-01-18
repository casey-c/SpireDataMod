package SpireDataMod;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class SpireDataMod implements PostInitializeSubscriber {
    public static void initialize() { new SpireDataMod(); }

    public SpireDataMod() {
        BaseMod.subscribe(this);
    }

    @Override
    public void receivePostInitialize() {
        System.out.println("SpireDataMod initialized");
    }
}
