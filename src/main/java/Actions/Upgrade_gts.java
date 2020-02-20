package Actions;

import HibSession.HibSession;
import alert.alertError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Gts;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Upgrade_gts implements Actions {
    HibSession hs = new HibSession();
    public Session session;
    @FXML
    Label label;
    @FXML
    TextField name;
    @FXML
    Button upgradeButton;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_gts(){}
    public Upgrade_gts(Controller c) {
        controller = c;
    }

    Gts gts;
    Gts gts2 = new Gts();
    Stage window = new Stage();
    List result;

    public void setController(Controller controller){
        this.controller = controller;
        session = hs.getSession();
        if (session == null) {
            return;
        }
        gts = controller.gtsTableView.getSelectionModel().getSelectedItem();
        name.setText(gts.getName());
        name.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        name.setTooltip(new Tooltip("До 25 символов"));
        session.close();
    }

    public void handleUpgrade(ActionEvent event) {
        if (name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать название!");
            alert.showAndWait();
            return;
        }

        session = hs.getSession();
            gts.setName(name.getText());
            Transaction trans = session.beginTransaction();
            session.update(gts);
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
