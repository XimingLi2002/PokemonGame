module pokemon.pokemongame {
    requires javafx.controls;
    requires javafx.fxml;


    opens Pokemon to javafx.fxml;
    exports Pokemon;

    opens Game to javafx.fxml;
    exports Game;
}