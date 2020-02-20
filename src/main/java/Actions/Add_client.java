package Actions;

import HibSession.HibSession;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Client;
import model.Exemption;
import model.Region;
import model.Street;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Add_client implements Actions {
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();
    @FXML
    TextField name,surname,middlename,age;
    @FXML
    Button saveButton;
    @FXML
    private Label label,label2,label3,label4,label5,label6;
    @FXML
    ComboBox combobox,combobox2;
    private Controller controller;
    public Add_client(){}
    public Add_client(Controller c) {
        controller = c;
    }

    Exemption exemption;
    Client client;
    Stage window = new Stage();
    List result,result2,result3;

    @FXML
    public void initialize() {
        session = hs.getSession();
        client = new Client();
        if (session == null) {
            return;
        }
            Query query = session.createSQLQuery(
                    "select name from exemption");
            result = query.list();
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(result);
            combobox2.setItems(observableArrayList);
            combobox2.getItems().addAll("Отсутствует");
            combobox2.setValue("Отсутствует");

            combobox.getItems().addAll("Мужской", "Женский", "Другой вариант");
            combobox.setValue("Мужской");

        name.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        name.setTooltip(new Tooltip("До 25 символов"));

        surname.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        surname.setTooltip(new Tooltip("До 25 символов"));

        middlename.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        middlename.setTooltip(new Tooltip("До 25 символов"));

        age.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String txt = change.getControlNewText();
            if (txt.matches("0\\d+") ) {
                return null;
            }
            try {
                int n = Integer.parseInt(txt);
                return 1 <= n && n <= 100 ? change : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }));
        age.setTooltip(new Tooltip("От 18 до 100 лет"));

        session.close();
    }

    public void handleSave(ActionEvent event) {
        if (name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать имя!");
            alert.showAndWait();
            return;
        }
        if (surname.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать фамилию!");
            alert.showAndWait();
            return;
        }
        if (middlename.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать отчетсво!");
            alert.showAndWait();
            return;
        }
        if (age.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать возраст!");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(age.getText())<=17){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Абонент должен являться совершеннолетним!");
            alert.showAndWait();
            return;
        }

        session = hs.getSession();
        client.setSurname(surname.getText());
        client.setName(name.getText());
        client.setMiddlename(middlename.getText());
        client.setAge(Integer.parseInt(age.getText()));
        client.setGender(String.valueOf(combobox.getValue()));

        String exemption_name = String.valueOf(combobox2.getValue());
        if(exemption_name!="Отсутствует") {
            Query query = session.createSQLQuery(
                    "select id from exemption where name=:exemption_name");
            query.setString("exemption_name", exemption_name);
            List result = query.list();
            Object obj = result.get(0);
            int i = (int) obj;
            Exemption exemption = (Exemption) session.load(Exemption.class, i);
            client.setExemption(exemption);
        }
        Transaction trans = session.beginTransaction();
        session.save(client);
        session.flush();
        trans.commit();

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
