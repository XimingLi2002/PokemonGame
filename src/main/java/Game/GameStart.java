package Game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Pokemon.Pokemon;
import java.io.*;
import java.net.URL;
import java.util.Objects;

public class GameStart extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("gameStart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Who's that pokemon?");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        //Lee el fichero pokemon.obj que contiene los objetos pokemon y lo almacena en un arrayList
        try {
            URL url = GameStart.class.getResource("Files/Pokemon.obj");
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(
                    Objects.requireNonNull(url).toString().substring(url.toString().indexOf("/") + 1)));
            try {
                while (true) {
                    GameController.pokemonArrayList.add((Pokemon) objectInputStream.readObject());
                }
            } catch (EOFException ignored) {
            }
            objectInputStream.close();
        } catch (NullPointerException | FileNotFoundException e) {
            System.err.println("Fichero no encontrado");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        launch();
    }
}
