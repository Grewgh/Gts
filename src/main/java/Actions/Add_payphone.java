package Actions;

import HibSession.HibSession;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Add_payphone implements Actions {
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
    private ComboBox comboBox;
    @FXML
    private ComboBox comboBox3;
    private Controller controller;
    public Add_payphone(){}
    public Add_payphone(Controller c) {
        controller = c;
    }
    Payphone payphone;
    Stage window = new Stage();
    List result,result2,result3;

    @FXML
    public void initialize() {
        session = hs.getSession();
        if (session == null) {
            return;
        }
        payphone = new Payphone();
        Query query = session.createSQLQuery(
                "select id from house");
        result = query.list();
        ObservableList<String> observableArrayList =
                FXCollections.observableArrayList(result);
        comboBox.setItems(observableArrayList);
        comboBox.setValue(String.valueOf(result.get(0)));

        Query query3 = session.createSQLQuery(
                "select number from telephone_number where status=true");
        result3 = query3.list();
        ObservableList<Integer> observableArrayList3 =
                FXCollections.observableArrayList(result3);
        if(!result3.isEmpty()) {
            comboBox3.setItems(observableArrayList3);
            comboBox3.setValue((int) result3.get(0));
        }
        session.close();

    }

    public void handleSave(ActionEvent event) {
        if(result.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Не существует ни одного дома!");
            alert.showAndWait();
            ((Stage)label.getScene().getWindow()).close();
            return;
        }
        if(result3.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Не существует ни одного свободного номера!");
            alert.showAndWait();
            ((Stage)label.getScene().getWindow()).close();
            return;
        }

        session = hs.getSession();

        int id;
        id = Integer.parseInt(String.valueOf(comboBox.getValue()));
        House house = (House) session.load(House.class, id);
        System.out.println("house "+ house.toString());
        payphone.setHouse(house);

        int number;
        number =  Integer.parseInt(String.valueOf(comboBox3.getValue()));
        Query query2 = session.createSQLQuery(
                "select * from telephone_number where number=:number").addEntity(Telephone_number.class);
        query2.setInteger("number", number);
        List result2 = query2.list();
        Telephone_number telephone_number = (Telephone_number) result2.get(0);
        payphone.setTelephone_number(telephone_number);


        Transaction trans = session.beginTransaction();
        session.save(payphone);
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
