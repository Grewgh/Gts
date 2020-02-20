package alert;

import javafx.scene.control.Alert;

public class alertError {
    public void alertError(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("Что-то пошло не так!");
        alert.showAndWait();
        return;
    }
}
