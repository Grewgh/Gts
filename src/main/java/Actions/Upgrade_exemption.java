package Actions;

import HibSession.HibernateUtil;
import alert.alertError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Exemption;
import model.Gts;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Upgrade_exemption implements Actions {
    public Session session;
    @FXML
    Label label,label2;
    @FXML
    TextField name,percent;
    @FXML
    Button upgradeButton;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_exemption(){}
    public Upgrade_exemption(Controller c) {
        controller = c;
    }

    Exemption exemption;
    Stage window = new Stage();
    List result;

    public void setController(Controller controller){
        this.controller = controller;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        if (session == null) {
            return;
        }
        exemption = controller.exemptionTableView.getSelectionModel().getSelectedItem();
        name.setText(exemption.getName());
        percent.setText(String.valueOf(exemption.getPercent()));

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

        session.clear();
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
        if (percent.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать значение скидки!");
            alert.showAndWait();
            return;
        }

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        exemption.setName(name.getText());
        exemption.setPercent(Integer.parseInt(percent.getText()));
        session.update(exemption);
        session.flush();
        tx.commit();
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
