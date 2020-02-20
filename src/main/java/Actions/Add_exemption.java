package Actions;

import HibSession.HibSession;
import HibSession.HibernateUtil;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Exemption;
import model.Gts;
import model.Region;
import model.Street;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Add_exemption implements Actions {
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();
    @FXML
    TextField name,percent;
    @FXML
    Button saveButton;
    @FXML
    private Label label,label2;
    private Controller controller;
    public Add_exemption(){}
    public Add_exemption(Controller c) {
        controller = c;
    }

    Exemption exemption;
    Stage window = new Stage();
    List result,result2,result3;

    @FXML
    public void initialize() {
        exemption = new Exemption();
        name.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        name.setTooltip(new Tooltip("До 25 символов"));

        percent.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String txt = change.getControlNewText();
            if (txt.matches("0\\d+")) {
                return null;
            }
            try {
                int n = Integer.parseInt(txt);
                return 1 <= n && n <= 100 ? change : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }));
        percent.setTooltip(new Tooltip("От 1 до 100 %"));
    }

    public void handleSave(ActionEvent event) {
        if (name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать название!");
            alert.showAndWait();
            return;
        }
        if (percent.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать размер скидки!");
            alert.showAndWait();
            return;
        }

        session = hs.getSession();
        exemption.setName(name.getText());
        exemption.setPercent(Integer.parseInt(percent.getText()));
        Transaction trans = session.beginTransaction();
        session.save(exemption);
        session.flush();
        trans.commit();
        session.close();
        ((Stage)label.getScene().getWindow()).close();
    }
    @Override
    public void save() {

    }
    @Override
    public void delete() {

    }
    @Override
    public void update() {

    }
}
