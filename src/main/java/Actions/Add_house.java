package Actions;

import HibSession.HibSession;
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

public class Add_house implements Actions {
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();
    @FXML
    TextField name;
    @FXML
    Button saveButton;
    @FXML
    private Label label,label2;
    @FXML
    private ComboBox<String> comboBox;
    private Controller controller;
    public Add_house(){}
    public Add_house(Controller c) {
        controller = c;
    }

    House house;
    Street street;
    Stage window = new Stage();
    List result,result2,result3;

    @FXML
    public void initialize() {
        session = hs.getSession();
        name.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\d+")  && change.getControlNewText().length() <= 4 ? change : null;
            }
            return change;
        }));
        name.setTooltip(new Tooltip("До 4 символов"));
        street = new Street();
        house = new House();
        if (session == null) {
            return;
        }
            Query query = session.createSQLQuery(
                    "select name from street");
            result = query.list();
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(result);
            comboBox.setItems(observableArrayList);
            comboBox.setValue(String.valueOf(result.get(0)));
        session.close();

    }

    public void handleSave(ActionEvent event) {
        if(result.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Улица не существует!");
            alert.showAndWait();
            ((Stage)label.getScene().getWindow()).close();
            return;
        }
        if (name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать номер дома!");
            alert.showAndWait();
            return;
        }

        session = hs.getSession();
        house.setName(name.getText());
        String street_name;
        street_name = comboBox.getValue();

        Query query = session.createSQLQuery(
                "select id from street where name=:street_name");
        query.setString("street_name", street_name);
        List result = query.list();
        Object obj =result.get(0);
        int i = (int) obj;
        Street street = (Street) session.load(Street.class, i);
        house.setStreet(street);
        Transaction trans = session.beginTransaction();
        session.save(house);
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
