package Actions;

import HibSession.HibernateUtil;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Ats;
import model.City_ats;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Upgrade_city_ats implements Actions {

    public Session session;
    @FXML
    Label label,label2;
    @FXML
    TextField name;
    @FXML
    Button upgradeButton;
    @FXML
    private ComboBox<String> comboBox;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_city_ats(){}
    public Upgrade_city_ats(Controller c) {
        controller = c;
    }

    City_ats city_ats;
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
        city_ats = controller.city_atsTableView.getSelectionModel().getSelectedItem();
        name.setText(city_ats.getName());
        ats = (Ats) session.load(Ats.class, city_ats.getAts());
        Query query = session.createSQLQuery(
                "select name from ats");
        result = query.list();
        ObservableList<String> observableArrayList =
                FXCollections.observableArrayList(result);
        comboBox.setItems(observableArrayList);
        comboBox.setValue(ats.getName());
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
        city_ats.setName(name.getText());
        String ats_name;
        ats_name = comboBox.getValue();
        Query query = session.createSQLQuery(
                "select id from ats where name=:ats_name");
        query.setString("ats_name", ats_name);
        result = query.list();
        Object obj =result.get(0);
        int i = (int) obj;
        ats = (Ats) session.load(Ats.class, i);
        city_ats.setAts(ats);
        session.update(city_ats);
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
