package SpireDataMod.data;

import SpireDataMod.gist.GistHelper;

public class DataManager {

    private static String getContent() {
        // TODO, obv
        return "{ \"runs\": [ { \"class\": \"defect\", \"timestamp\": \"20210114175040\", \"relics\": [ \"Astrolabe\", \"PreservedInsect\", \"Golden Idol\", \"CaptainsWheel\", \"StoneCalendar\", \"Molten Egg 2\", \"Bag of Preparation\", \"Lantern\", \"Coffee Dripper\", \"Enchiridion\", \"Dream Catcher\", \"Strawberry\", \"Bottled Tornado\", \"Omamori\", \"Runic Capacitor\", \"Fusion Hammer\", \"Ice Cream\", \"Old Coin\", \"Happy Flower\", \"HornCleat\", \"Membership Card\", \"InkBottle\", \"Toolbox\", \"Symbiotic Virus\" ], \"boss_relics\": [ { \"not_picked\": [ \"Tiny House\", \"Pandora's Box\" ], \"picked\": \"Coffee Dripper\" }, { \"not_picked\": [ \"Philosopher's Stone\", \"Velvet Choker\" ], \"picked\": \"Fusion Hammer\" } ], \"deck\": [ \"AscendersBane\", \"Defend_B\", \"Defend_B\", \"Defend_B\", \"Zap\", \"Dualcast\", \"Rip and Tear+1\", \"White Noise+1\", \"Fusion+1\", \"Steam\", \"Chaos+1\", \"Barrage+1\", \"FTL\", \"Aggregate+1\", \"Compile Driver+1\", \"Hologram+1\", \"Beam Cell+1\", \"Echo Form\", \"Hyperbeam+1\", \"Cold Snap+1\", \"Bite+1\", \"Bite+1\", \"Bite+1\", \"Bite+1\", \"Ghostly+1\", \"Ghostly\", \"Ghostly\", \"RitualDagger+1\", \"Electrodynamics+1\", \"BootSequence\", \"Compile Driver+1\", \"Conserve Battery+1\", \"Biased Cognition\", \"Cold Snap+1\", \"Compile Driver+1\", \"Compile Driver+1\", \"Coolheaded\", \"Hologram+1\", \"Buffer\", \"Madness\", \"Madness\", \"Go for the Eyes+1\", \"Hologram\", \"Compile Driver+1\", \"Compile Driver+1\", \"Glacier\", \"Defragment\" ], \"victory\": true, \"floor_reached\": 57 } ] }";
    }

    public static void writeToGist(GistHelper gistHelper) {
        // TODO: other stuff instead

        Run r = Run.buildFromFile("runs/1610664640.run", "DEFECT");
        r.printPretty();

        //gistHelper.updateContent(getContent());
    }
}
