package Game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameStartController implements Initializable {
    @FXML
    private ProgressBar PG_startCheck;
    @FXML
    private Text start;
    private boolean progressFocus = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        PG_startCheck.setStyle("-fx-accent: #FF0000;");
    }

    @FXML
    private void openGame(Event event) throws IOException {
        if (PG_startCheck.getProgress() == 1){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Who's that pokemon?");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        }
    }


    /**
     * Método que cambia el cursor por defecto al cursor de tipo mano una vez que el progressBar llegue al 100%
     */
    @FXML
    private void updateCursor(){
        if (PG_startCheck.getProgress() == 1){
            start.setCursor(Cursor.HAND);
        }else {
            start.setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * Actualiza el progressBar incrementando o reduciendo
     */
    @FXML
    private void updateProgressBar() {
        progressFocus = !progressFocus;
        if (progressFocus) {
            progressBarTimeLine(PG_startCheck.getProgress(), 1);
        } else {
            progressBarTimeLine(PG_startCheck.getProgress(), 0);
        }
    }

    /**
     * Transición para el progressBar
     * @param firstValue valor inicial
     * @param secondValue valor final
     */
    private void progressBarTimeLine(double firstValue, double secondValue) {
        Timeline task = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(PG_startCheck.progressProperty(), firstValue)

                ),
                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(PG_startCheck.progressProperty(), secondValue)
                )
        );
        task.play();
    }
}
