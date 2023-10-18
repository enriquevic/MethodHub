package RootHub.Sources;

import javafx.scene.control.Alert;

public class Alerts {
    public void mostrarAlerta(String mensagem,String title, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    } 
}
