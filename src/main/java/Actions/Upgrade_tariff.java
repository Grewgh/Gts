package Actions;

import HibSession.HibernateUtil;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Region;
import model.Street;
import model.Tariff;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;
import java.util.regex.Pattern;

public class Upgrade_tariff implements Actions {

    public Session session;
    @FXML
    TextField price_minute;
    @FXML
    Button saveButton;
    @FXML
    private Label label,label2,label3;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox day_nightCombobox,typeCombobox;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_tariff(){}
    public Upgrade_tariff(Controller c) {
        controller = c;
    }

    Tariff tariff;
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
        tariff = controller.tariffTableView.getSelectionModel().getSelectedItem();

        price_minute.setText(String.valueOf(tariff.getPrice_minute()));

        day_nightCombobox.getItems().addAll("День","Ночь");
        if(tariff.getDay_night()=="День"){
            day_nightCombobox.setValue("День");
        }else{
            day_nightCombobox.setValue("Ночь");
        }

        typeCombobox.getItems().addAll("Город","Межгород");
        if(typeCombobox.getValue()=="Город") {
            typeCombobox.setValue("Город");
        }else {
            typeCombobox.setValue("Межгород");
        }

        Pattern p = Pattern.compile("\\d*(\\.\\d*)?");
        price_minute.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) price_minute.setText(oldValue);
        });

        session.clear();
        session.close();
    }

    public void handleUpgrade(ActionEvent event) {
        if (price_minute.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли стоимость звонка за минуту разговора!");
            alert.showAndWait();
            return;
        }

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.getTransaction();
            if(!tx.isActive()) {
                tx = session.beginTransaction();
            }
            tariff.setPrice_minute(Float.parseFloat(price_minute.getText()));

            tariff.setDay_night(String.valueOf(day_nightCombobox.getValue()));
            tariff.setType(String.valueOf(typeCombobox.getValue()));

            session.update(tariff);
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
