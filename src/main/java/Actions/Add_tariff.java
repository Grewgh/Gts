package Actions;

import HibSession.HibSession;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Receipt;
import model.Region;
import model.Street;
import model.Tariff;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;
import java.util.regex.Pattern;

public class Add_tariff implements Actions {
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();
    @FXML
    TextField price_minute;
    @FXML
    Button saveButton;
    @FXML
    private Label label,label2,label3;
    @FXML
    private ComboBox day_nightCombobox,typeCombobox;
    private Controller controller;
    public Add_tariff(){}
    public Add_tariff(Controller c) {
        controller = c;
    }

    Tariff tariff;
    Stage window = new Stage();
    List result,result2,result3;

    @FXML
    public void initialize() {
        session = hs.getSession();
        tariff = new Tariff();
        if (session == null) {
            return;
        }
        tariff = new Tariff();

        day_nightCombobox.getItems().addAll("День","Ночь");
        day_nightCombobox.setValue("День");

        typeCombobox.getItems().addAll("Город","Межгород");
        typeCombobox.setValue("Город");

        Pattern p = Pattern.compile("\\d*(\\.\\d*)?");
        price_minute.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) price_minute.setText(oldValue);
        });

        session.close();

    }

    public void handleSave(ActionEvent event) {
        if (price_minute.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли стоимость звонка за минуту разговора!");
            alert.showAndWait();
            return;
        }

            session = hs.getSession();

            tariff.setPrice_minute(Float.parseFloat(price_minute.getText()));

            tariff.setType(String.valueOf(typeCombobox.getValue()));

            tariff.setDay_night(String.valueOf(day_nightCombobox.getValue()));

            Transaction trans = session.beginTransaction();
            session.save(tariff);
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
