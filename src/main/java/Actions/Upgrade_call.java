package Actions;

import HibSession.HibernateUtil;
import alert.alertError;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Upgrade_call implements Actions {

    public Session session;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button saveButton;
    @FXML
    private Label label2;
    @FXML
    private Label label4;
    @FXML
    private Label label;
    @FXML
    private Label label3;
    @FXML
    private DatePicker date_start = new DatePicker();
    @FXML
    private DatePicker date_stop = new DatePicker();
    @FXML
    private JFXTimePicker start_call;
    @FXML
    private JFXTimePicker stop_call;
    @FXML
    private ComboBox<Integer> conCombobox;
    @FXML
    private ComboBox tariffCombobox;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_call(){}
    public Upgrade_call(Controller c) {
        controller = c;
    }

    Call call;
    Stage window = new Stage();
    List result;
    List tariffList;
    List conList;

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
        call = controller.callTableView.getSelectionModel().getSelectedItem();

        Query tariffQuery = session.createSQLQuery(
                "select id from tariff");
        tariffList = tariffQuery.list();
        Query conQuery = session.createSQLQuery(
                "select id from connection");
        conList = conQuery.list();
        if(tariffList.isEmpty() || conList.isEmpty())
        {
            err.alertError();
            return;
        }

        ObservableList<Integer> tariffObservableArrayList =
                FXCollections.observableArrayList(tariffList);
        tariffCombobox.setItems(tariffObservableArrayList);
        tariffCombobox.setValue(call.getTariff());

        ObservableList<Integer> conObservableArrayList =
                FXCollections.observableArrayList(conList);
        conCombobox.setItems(conObservableArrayList);
        conCombobox.setValue(call.getConnection());

        final Callback<DatePicker, DateCell> day_start = new Callback<DatePicker, DateCell>() {
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
        date_start.setDayCellFactory(day_start);

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = call.getCall_start().toInstant();
        LocalDate data = instant.atZone(defaultZoneId).toLocalDate();
        date_start.setValue(data);

        Instant instant2 = call.getCall_stop().toInstant();
        LocalDate data2 = instant2.atZone(defaultZoneId).toLocalDate();
        date_stop.setValue(data2);


        Instant instant3 = call.getCall_start().toInstant();
        LocalTime time = instant3.atZone(defaultZoneId).toLocalTime();
        start_call.setValue(time);

        Instant instant4 = call.getCall_stop().toInstant();
        LocalTime time2 = instant4.atZone(defaultZoneId).toLocalTime();
        stop_call.setValue(time2);


        final Callback<DatePicker, DateCell> day_stop = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate minDate = date_start.getValue();
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
        date_stop.setDayCellFactory(day_stop);

        session.clear();
        session.close();
    }

    public void handleUpgrade(ActionEvent event) {
        if(date_start.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату!");
            alert.showAndWait();
            return;
        }
        if (date_start.getValue().isAfter(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату не позднее сегодняшнего дня!");
            alert.showAndWait();
            return;
        }
        if (date_start.getValue().isBefore(LocalDate.of(1960, 1, 1))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату не ранее 01.01.1960 года");
            alert.showAndWait();
            return;
        }

        if(date_stop.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату!");
            alert.showAndWait();
            return;
        }
        if (date_stop.getValue().isAfter(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату не позднее сегодняшнего дня!");
            alert.showAndWait();
            return;
        }
        if (date_stop.getValue().isBefore(LocalDate.of(1960, 1, 1))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату не ранее 01.01.1960 года");
            alert.showAndWait();
            return;
        }

        if(start_call.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату!");
            alert.showAndWait();
            return;
        }
        if(stop_call.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Укажите дату!");
            alert.showAndWait();
            return;
        }

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }

        int conId = (int) conCombobox.getValue();
        Connection connection = (Connection) session.load(Connection.class, conId);
        call.setConnection(connection);

        int tariffId = (int) tariffCombobox.getValue();
        Tariff tariff = (Tariff) session.load(Tariff.class, tariffId);
        call.setTariff(tariff);

        LocalDate startLocalDate = date_start.getValue();
        LocalTime startLocalTime = start_call.getValue();
        Calendar calendarA = Calendar.getInstance();
        calendarA.setTime(java.sql.Date.valueOf(startLocalDate));
        Calendar calendarB = Calendar.getInstance();
        calendarB.setTime(java.sql.Time.valueOf(startLocalTime));
        calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
        calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
        calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
        calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));
        Date result = calendarA.getTime();
        System.out.println("123"+ result);
        call.setCall_start(result);
        System.out.println("124"+ call.getCall_start());
        LocalDate stopLocalDate = date_stop.getValue();
        LocalTime stopLocalTime = stop_call.getValue();
        calendarA = Calendar.getInstance();
        calendarA.setTime(java.sql.Date.valueOf(stopLocalDate));
        calendarB = Calendar.getInstance();
        calendarB.setTime(java.sql.Time.valueOf(stopLocalTime));
        calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
        calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
        calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
        calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));
        Date result2 = calendarA.getTime();
        call.setCall_stop(result2);

        long duration = result2.getTime() - result.getTime();
        duration = TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS);

        int minute = (int) (duration/60);
        float second = duration%60;
        if(second>30){
            minute+=1;
        }
        String call_duration = (minute + "мин. ");

        if(minute<=0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Неправильно указано время разговора");
            alert.showAndWait();
            return;
        }

        call.setCall_duration(call_duration);

        float sum = minute* tariff.getPrice_minute();
        sum= (float) ((double)Math.round(sum * 10d) / 10d);
        call.setSum(sum);

        session.update(call);
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
