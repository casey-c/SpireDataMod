package SpireDataMod.data;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class RunDatabase {
    @SerializedName("runs") @Expose
    private List<Run> runs;

    private static String getCleanedCharacterName(String directory) {
        if (directory.equals("IRONCLAD") || directory.equals("1_IRONCLAD") || directory.equals("2_IRONCLAD"))
            return "Ironclad";
        if (directory.equals("THE_SILENT") || directory.equals("1_THE_SILENT") || directory.equals("2_THE_SILENT"))
            return "Silent";
        if (directory.equals("DEFECT") || directory.equals("1_DEFECT") || directory.equals("2_DEFECT"))
            return "Defect";
        if (directory.equals("WATCHER") || directory.equals("1_WATCHER") || directory.equals("2_WATCHER"))
            return "Watcher";
        return directory;
    }

    // TODO: className can be extracted from the directory name (probably)
    public static RunDatabase loadRuns() {
        RunDatabase db = new RunDatabase();
        db.runs = new LinkedList<>();

        File directoryPath = new File("runs");

        // List of all files and directories
        File[] contents = directoryPath.listFiles();

        for (File potentialCharacterFolder : contents) {
            if (!potentialCharacterFolder.isDirectory())
                continue;

            // TODO: better handling of other profiles. for now, try and skip directories from other profiles (only look at the
            //   profile 0, e.g. the main profile)
            if (potentialCharacterFolder.getName().startsWith("1_"))
                continue;
            if (potentialCharacterFolder.getName().startsWith("2_"))
                continue;

            String characterName = getCleanedCharacterName(potentialCharacterFolder.getName());

            for (String s : potentialCharacterFolder.list()) {
                String runPath = "runs/" + potentialCharacterFolder.getName() + "/" + s;

                Run run = Run.buildFromFile(runPath, characterName);
                db.runs.add(run);
            }
        }

        System.out.println("Loaded " + db.runs.size() + " runs.");
        return db;
    }

    public String getAsJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
