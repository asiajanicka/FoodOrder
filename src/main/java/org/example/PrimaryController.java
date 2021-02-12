package org.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {

    @FXML
    private Label helloLabel;

    @FXML
    private void clickMeButtonAction() throws IOException {
        helloLabel.setText("Hello Asia! :)");
    }
}
