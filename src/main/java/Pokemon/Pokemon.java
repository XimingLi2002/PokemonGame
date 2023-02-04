package Pokemon;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private String imageMysteryPokemonPATH;
    private String imagePokemonPATH;
    private String name;

    Pokemon(String PATH) {
        imagePokemonPATH = String.valueOf(Pokemon.class.getResource("Pokemon/Pokemon-" + PATH));
        imageMysteryPokemonPATH = String.valueOf(Pokemon.class.getResource("MysteryEdition/" + PATH));
        name = PATH.substring(PATH.indexOf("-") + 1, PATH.indexOf("."));
    }

    public String getImageMysteryPokemonPATH() {
        return imageMysteryPokemonPATH;
    }

    public String getName() {
        return name;
    }

    public String getImagePokemonPATH() {
        return imagePokemonPATH;
    }

    public void setImagePokemonPATH(String imagePokemonPATH) {
        this.imagePokemonPATH = imagePokemonPATH;
    }
}
