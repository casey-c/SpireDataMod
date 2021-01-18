package SpireDataMod.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Run {
    @SerializedName("timestamp") @Expose
    private String timestamp;

    @SerializedName("class") @Expose
    private String className;

    @SerializedName("victory") @Expose
    private boolean victory;

    @SerializedName("floor_reached") @Expose
    private int floor_reached;

    @SerializedName("deck") @Expose
    private List<String> deck;

    @SerializedName("relics") @Expose
    private List<String> relics;

    @SerializedName("boss_relics") @Expose
    private List<BossRelicOption> boss_relics;

    // --------------------------------------------------------------------------------
    // This is the intermediary JSON in the format that the base game uses. We use it as a stepping stone to get
    // our final format (the real Run class).
    // TODO: might cut out this middleman as it makes things a bit confusing (it started useful, but i think it can be
    //  removed by now)

    private static class InGameRun {
        @SerializedName("relics") @Expose
        private List<String> relics;

        @SerializedName("master_deck") @Expose
        private List<String> master_deck;

        @SerializedName("floor_reached") @Expose
        private int floor_reached;

        @SerializedName("timestamp") @Expose
        private long timestamp;

        @SerializedName("victory") @Expose
        private boolean victory;

        @SerializedName("boss_relics") @Expose
        private List<BossRelicOption> boss_relics;
    }

    // Small helper class to handle the base game picked/not_picked boss_relics options
    private static class BossRelicOption {
        @SerializedName("picked") @Expose
        private String picked;

        @SerializedName("not_picked") @Expose
        private List<String> not_picked;
    }

    // This constructor converts from the intermediary InGame version to our final output version
    private Run(InGameRun r, String className) {
        this.floor_reached = r.floor_reached;
        this.timestamp = "" + r.timestamp;

        // "true" victory means beating the heart at a20 -- TODO: fallback to just r.victory to let lower ascensions play with this
        this.victory = (r.victory && floor_reached == 57);

        this.className = className;
        this.boss_relics = r.boss_relics;

        if (r.relics != null) {
            this.relics = new LinkedList<>();
            for (String relicID : r.relics) {
                RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(relicID);
                relics.add(relicStrings.NAME);
            }
        }

        if (r.master_deck != null) {
            this.deck = new LinkedList<>();
            for (String card : r.master_deck) {
                // Need to strip out the upgrades (e.g. SearingBlow+3 -> SearingBlow) to get the name
                String[] nameThenUpgrades = card.split("\\+", 2);

                CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(nameThenUpgrades[0]);

                // Readd the upgrade text to the localized name
                String finalCleanedName = cardStrings.NAME;
                if (nameThenUpgrades.length == 2) {
                    finalCleanedName = finalCleanedName + "+" + nameThenUpgrades[1];
                }
                deck.add(finalCleanedName);
            }
        }

        //this.relics = r.relics;
        //this.deck = r.master_deck;
    }

    // --------------------------------------------------------------------------------

    public static Run buildFromFile(String runFilename, String className) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(runFilename));

            Gson gson = new Gson();
            InGameRun r = gson.fromJson(reader, InGameRun.class);

            return new Run(r, className);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void printPretty() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);

        System.out.println("Run: ");
        System.out.println(json);
        System.out.println("-------");
    }

    public void print() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        System.out.print("Run: ");
        System.out.println(json);
    }
}
