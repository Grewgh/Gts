package Actions;

import HibSession.HibernateUtil;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.City_ats;
import model.Region;
import model.Telephone_number;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;
import java.util.regex.Pattern;

public class Upgrade_telephone_number implements Actions {
    public Session session;
    alertError err = new alertError();
    @FXML
    TextField number;
    @FXML
    Button saveButton;
    @FXML
    private Label label,label2;
    @FXML
    private ComboBox comboBox,comboBox2;
    private Controller controller;
    public Upgrade_telephone_number(){}
    public Upgrade_telephone_number(Controller c) {
        controller = c;
    }

    Telephone_number telephone_number;
    City_ats city_ats;
    Stage window = new Stage();
    List result,result2,result3;

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
        telephone_number = controller.telephone_numberTableView.getSelectionModel().getSelectedItem();
        number.setText(String.valueOf(telephone_number.getNumber()));

        comboBox.getItems().addAll("Доступен","Недоступен");
        if(telephone_number.isStatus()==true){
            comboBox.setValue("Доступен");
        } else{
            comboBox.setValue("Недоступен");
        }

        Query query = session.createSQLQuery(
                "select id from city_ats");
        result = query.list();
        if(!result.isEmpty()) {
            comboBox2.getItems().addAll(result);
            comboBox2.setValue(telephone_number.getCity_ats());
        }

        Pattern p = Pattern.compile("\\d{0,6}");
        number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) number.setText(oldValue);
        });
        session.clear();
        session.close();
    }

    public void handleUpgrade(ActionEvent event) {
        if (number.getText().length()<=5){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Номер должен состоять из 6 цифр");
            alert.showAndWait();
            return;
        }

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        telephone_number.setNumber(Integer.parseInt(number.getText()));

        Object obj = comboBox2.getValue();
        int i = (int) obj;
        city_ats = (City_ats) session.load(City_ats.class, i);
        telephone_number.setCity_ats(city_ats);

        if(comboBox.getValue()=="Доступен"){
            telephone_number.setStatus(true);
        }else{
            telephone_number.setStatus(false);
        }

        session.update(telephone_number);
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
