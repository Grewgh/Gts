package alert;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class Confirm {
    String message = "Возможно удаление ссылающихся полей";
    public Boolean check() {
        Dialog<ButtonType> confirm = new Dialog<>();
        confirm.getDialogPane().setContentText(message);
        confirm.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        boolean result = confirm.showAndWait().filter(ButtonType.YES::equals).isPresent();
        return result;
    }
}
