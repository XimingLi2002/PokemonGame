package OtherResources;

import java.io.File;

public class RenameFiles {
    private static final String dir = "./src/main/resources/Pokemon/MysteryEdition/";

    public static void main(String[] args) {
        File[] files = new File(dir).listFiles();
        for (File file : files) {
            if (file.getName().contains("Silhouette") || file.getName().contains("Pokemon")) {
                file.renameTo(new File(dir + file.getName().substring(file.getName().indexOf("-") + 1)));
            }
        }
    }
}
