package Pokemon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CreatePokemons {

    private static final String pokemonObjPath = "./src/main/resources/Game/Pokemon.obj";

    public static void main(String[] args) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(pokemonObjPath));
            File[] files = new File("./src/main/resources/Pokemon/MysteryEdition").listFiles();
            File[] orderedFiles = new File[files.length];

            for (File file : files) {
                //if (file.getName().contains(".png") && Integer.parseInt(file.getName().substring(0,3)) <= files.length) {
                orderedFiles[Integer.parseInt(file.getName().substring(0, 3)) - 1] = file;
            }
            for (int i = 0; i < files.length; i++) {
                objectOutputStream.writeObject(new Pokemon(orderedFiles[i].getName()));
            }
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
