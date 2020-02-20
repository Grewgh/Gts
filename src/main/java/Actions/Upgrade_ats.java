package Actions;

import HibSession.HibernateUtil;
import alert.alertError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Ats;
import model.Gts;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Upgrade_ats implements Actions {
    public Session session;
    @FXML
    Label label;
    @FXML
    TextField name;
    @FXML
    Button upgradeButton;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_ats(){}
    public Upgrade_ats(Controller c) {
        controller = c;
    }

    Ats ats;
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
            err.alertError();
            return;
        }
        ats = controller.atsTableView.getSelectionModel().getSelectedItem();
        name.setText(ats.getName());
        name.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        name.setTooltip(new Tooltip("До 25 символов"));
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

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.getTransaction();
            if(!tx.isActive()) {
                tx = session.beginTransaction();
            }
            ats.setName(name.getText());
            session.update(ats);
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
