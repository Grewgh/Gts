package Actions;

import HibSession.HibernateUtil;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.House;
import model.Region;
import model.Street;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Upgrade_house implements Actions {

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

    public Upgrade_house(){}
    public Upgrade_house(Controller c) {
        controller = c;
    }

    Street street;
    House house;
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
        house = controller.houseTableView.getSelectionModel().getSelectedItem();
        name.setText(house.getName());
        street = (Street) session.load(Street.class, house.getStreet());
        Query query = session.createSQLQuery(
                "select name from street");
        result = query.list();
        ObservableList<String> observableArrayList =
                FXCollections.observableArrayList(result);
        comboBox.setItems(observableArrayList);
        comboBox.setValue(street.getName());
        name.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\d+")  && change.getControlNewText().length() <= 4 ? change : null;
            }
            return change;
        }));
        name.setTooltip(new Tooltip("До 4 символов"));
        session.clear();
        session.close();
    }

    public void handleUpgrade(ActionEvent event) {
        if (name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать номер дома!");
            alert.showAndWait();
            return;
        }

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        house.setName(name.getText());
        String street_name;
        street_name = comboBox.getValue();

        Query query = session.createSQLQuery(
                "select id from street where name=:street_name");
        query.setString("street_name", street_name);
        result = query.list();
        Object obj =result.get(0);
        int i = (int) obj;
        street = (Street) session.load(Street.class, i);
        house.setStreet(street);
        session.update(house);
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
