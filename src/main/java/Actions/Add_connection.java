package Actions;

import HibSession.HibSession;
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
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import run.Controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Add_connection implements Actions {
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();
    @FXML
    private Button saveButton;
    @FXML
    private Label label,label2,label3,label4,label5,label6,label7;
    @FXML
    private DatePicker datePicker, datePicker2;
    @FXML
    private ComboBox combobox, combobox2,combobox3,combobox4,combobox5;

    private Controller controller;
    public Add_connection(){}
    public Add_connection(Controller c) {
        controller = c;
    }

    Connection connection;
    Stage window = new Stage();
    List result,result2,result3;

    @FXML
    public void initialize() {
        session = hs.getSession();

        connection = new Connection();
        if (session == null) {
            return;
        }
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
        datePicker.setValue(LocalDate.now());

        final Callback<DatePicker, DateCell> dayCellFactory2 = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker2) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate minDate = datePicker.getValue();
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
        datePicker2.setDisable(true);

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

        combobox2.getItems().addAll("Основной", "Параллельный", "Спаренный");
        combobox2.setValue("Основной");

        combobox3.getItems().addAll("Активен", "Заблокирован");
        combobox3.setValue("Активен");

        Query query3 = session.createSQLQuery(
                "select number from telephone_number where status=true");
        List result3 = query3.list();
        if (result3.isEmpty()) {
            err.alertError();
            return;
        }
        ObservableList<Integer> observableArrayList3 =
                FXCollections.observableArrayList(result3);
        combobox4.setItems(observableArrayList3);
        combobox4.setValue((int)result3.get(0));

        Query query4 = session.createSQLQuery(
                "SELECT id_client FROM queue order by queue_date asc");
        List result4 = query4.list();
        if(result4.isEmpty()) {
            err.alertError();
            return;
        }
        ObservableList<String> observableArrayList4 =
                FXCollections.observableArrayList(result4);
        combobox5.setItems(observableArrayList4);

        Query query5 = session.createSQLQuery(
                "SELECT id_client FROM queue WHERE exemption=true order by queue_date asc");
        List result5 = query5.list();
        if(result5.size()==0){
            query5 = session.createSQLQuery(
                    "SELECT id_client FROM queue WHERE exemption=false order by queue_date asc");
            result5 = query5.list();
        }
        combobox5.setValue(String.valueOf(result5.get(0)));



        session.close();

    }

    public void handleSave(ActionEvent event) {
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

        session = hs.getSession();

        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        connection.setConnection_date(date);

        String apt;
        apt = String.valueOf(combobox.getValue());
        Query query = session.createSQLQuery(
                "select id from apartment where name=:apt");
        query.setString("apt", apt);
        List result = query.list();
        Object obj = result.get(0);
        int i = (int) obj;
        Apartment apartment = (Apartment) session.load(Apartment.class, i);
        connection.setApartment(apartment);

        connection.setType_telephone(String.valueOf(combobox2.getValue()));

        int number;
        number = (int) combobox4.getValue();
        Query query2 = session.createSQLQuery(
                "select id from telephone_number where number=:number");
        query2.setInteger("number", number);
        result = query2.list();
        obj = result.get(0);
        i = (int) obj;
        Telephone_number telephone_number = (Telephone_number) session.load(Telephone_number.class, i);
        connection.setTelephone_number(telephone_number);

        String check = String.valueOf(combobox3.getValue());
        if (check == "Активен") {
            connection.setStatus(true);
        } else connection.setStatus(false);

        String client_id;
        client_id = String.valueOf(combobox5.getValue());
        int i5 = Integer.parseInt(client_id);
        Client client = (Client) session.load(Client.class, i5);
        connection.setClient(client);

        Transaction trans = session.beginTransaction();
        session.save(connection);
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
