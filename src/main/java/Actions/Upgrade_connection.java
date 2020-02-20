package Actions;

import HibSession.HibernateUtil;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Upgrade_connection implements Actions {

    public Session session;
    @FXML
    private Button saveButton;
    @FXML
    private Label label,label2,label3,label4,label5,label6,label7;
    @FXML
    private DatePicker datePicker, datePicker2;
    @FXML
    private ComboBox combobox, combobox2,combobox3,combobox4,combobox5;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_connection(){}
    public Upgrade_connection(Controller c) {
        controller = c;
    }

    Connection connection;
    Stage window = new Stage();
    List result,result4;

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

        connection = controller.connectionTableView.getSelectionModel().getSelectedItem();
        if (connection == null) {
            err.alertError();
            return;
        }
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = connection.getConnection_date().toInstant();
        LocalDate data = instant.atZone(defaultZoneId).toLocalDate();

        datePicker.setValue(data);

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate minDate = LocalDate.of(1960, 1, 1);
                        LocalDate maxDate = LocalDate.now();
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        } else if (item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);

        datePicker2.setDisable(false);
        datePicker2.setValue(LocalDate.now());

        final Callback<DatePicker, DateCell> dayCellFactory2 = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker2) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate minDate = LocalDate.of(1960, 1, 1);
                        LocalDate maxDate = LocalDate.now();
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        } else if (item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        datePicker2.setDayCellFactory(dayCellFactory2);

        Query query2 = session.createSQLQuery(
                "select name from apartment");
        List result2 = query2.list();
        if (result2.isEmpty()) {
            err.alertError();
            return;
        }
        ObservableList<String> observableArrayList =
                FXCollections.observableArrayList(result2);
        combobox.setItems(observableArrayList);
        combobox.setValue(String.valueOf(result2.get(0)));

        Query query3 = session.createSQLQuery(
                "select number from telephone_number");
        List result3 = query3.list();
        if (result3.isEmpty()) {
            err.alertError();
            return;
        }
        ObservableList<Integer> observableArrayList2 =
                FXCollections.observableArrayList(result3);
        combobox4.setItems(observableArrayList2);
        combobox4.setValue(result3.get(0));

        combobox2.getItems().addAll("Основной", "Параллельный", "Спаренный");
        combobox2.setValue("Основной");

        combobox3.getItems().addAll("Активен", "Заблокирован");
        combobox3.setValue("Активен");

        Query query4 = session.createSQLQuery(
                "select surname from client");
        List result4 = query4.list();
        if(result4.isEmpty()) {
            err.alertError();
            return;
        }
        ObservableList<String> observableArrayList4 =
                FXCollections.observableArrayList(result4);
        combobox5.setItems(observableArrayList4);
        combobox5.setValue(result4.get(0));

        session.clear();
        session.close();
    }

    public void handleUpgrade(ActionEvent event) {
        if(datePicker.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату!");
            alert.showAndWait();
            return;
        }
        if (datePicker.getValue().isAfter(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату не позднее сегодняшнего дня!");
            alert.showAndWait();
            return;
        }
        if (datePicker.getValue().isBefore(LocalDate.of(1960, 1, 1))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату не ранее 01.01.1960 года");
            alert.showAndWait();
            return;
        }

        if (datePicker2.getValue().isAfter(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату не позднее сегодняшнего дня!");
            alert.showAndWait();
            return;
        }
        if (datePicker2.getValue().isBefore(LocalDate.of(1960, 1, 1))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату не ранее 01.01.1960 года");
            alert.showAndWait();
            return;
        }

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        connection.setType_telephone(String.valueOf(combobox2.getValue()));

        String check = String.valueOf(combobox3.getValue());
        if (check == "Активен") {
            connection.setStatus(true);
        } else connection.setStatus(false);

        String client_surname;
        client_surname = String.valueOf(combobox5.getValue());
        Query query5 = session.createSQLQuery(
                "select id from client where surname=:client_surname");
        query5.setString("client_surname", client_surname);
        List result5 = query5.list();
        Object obj5 = result5.get(0);
        int i5 = (int) obj5;
        Client client = (Client) session.load(Client.class, i5);
        connection.setClient(client);

        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        connection.setConnection_date(date);

        if(datePicker2.getValue()!=null) {
            localDate = datePicker2.getValue();
            date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            connection.setDisconnection_date(date);
        }

        int number;
        number = (int) combobox4.getValue();
        Query query3 = session.createSQLQuery(
                "select id from telephone_number where number=:number");
        query3.setInteger("number", number);
        List result2 = query3.list();
        Object obj2 = result2.get(0);
        int i2 = (int) obj2;
        Telephone_number telephone_number = (Telephone_number) session.load(Telephone_number.class, i2);
        connection.setTelephone_number(telephone_number);

        String name;
        name = String.valueOf(combobox.getValue());
        Query query2 = session.createSQLQuery(
                "select id from apartment where name=:name");
        query2.setString("name", name);
        List result = query2.list();
        Object obj = result.get(0);
        int i = (int) obj;
        Apartment apartment = (Apartment) session.load(Apartment.class, i);
        connection.setApartment(apartment);

        session.update(apartment);
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
