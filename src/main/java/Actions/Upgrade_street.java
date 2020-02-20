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
import model.Region;
import model.Street;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Upgrade_street implements Actions {

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

    public Upgrade_street(){}
    public Upgrade_street(Controller c) {
        controller = c;
    }

    Street street;
    Region region;
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
        street = controller.streetTableView.getSelectionModel().getSelectedItem();
        name.setText(street.getName());
        region = (Region) session.load(Region.class, street.getRegion());
        Query query = session.createSQLQuery(
                "select name from region");
        result = query.list();
        ObservableList<String> observableArrayList =
                FXCollections.observableArrayList(result);
        comboBox.setItems(observableArrayList);
        comboBox.setValue(region.getName());
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
        street.setName(name.getText());
        String region_name;
        region_name = comboBox.getValue();

        Query query = session.createSQLQuery(
                "select id from region where name=:region_name");
        query.setString("region_name", region_name);
        result = query.list();
        Object obj =result.get(0);
        int i = (int) obj;
        region = (Region) session.load(Region.class, i);
        street.setRegion(region);
        session.update(street);
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
