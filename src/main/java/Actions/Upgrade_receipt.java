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
import model.Connection;
import model.Receipt;
import model.Region;
import model.Street;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Upgrade_receipt implements Actions {

    public Session session;
    @FXML
    TextField number;
    @FXML
    DatePicker datePicker = new DatePicker();
    @FXML
    Button saveButton;
    @FXML
    private Label label,label2,label3;
    @FXML
    private ComboBox comboBox, comboBox2;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_receipt(){}
    public Upgrade_receipt(Controller c) {
        controller = c;
    }

    Receipt receipt;
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
        receipt = controller.receiptTableView.getSelectionModel().getSelectedItem();

        Query query = session.createSQLQuery(
                "select id from connection");
        result = query.list();
        ObservableList<String> observableArrayList =
                FXCollections.observableArrayList(result);
        comboBox.setItems(observableArrayList);
        comboBox.setValue(receipt.getConnection());

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = receipt.getAccrual_date().toInstant();
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

        comboBox2.getItems().addAll("Оплачено","Не оплачено");
        if(receipt.getStatus()==true) {
            comboBox2.setValue("Оплачено");
        }else{
            comboBox2.setValue("Не оплачено");
        }

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

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }

        int id_con= (int) comboBox.getValue();
        Connection connection = (Connection) session.load(Connection.class, id_con);
        receipt.setConnection(connection);

        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        receipt.setAccrual_date(date);

        if(comboBox2.getValue()=="Оплачено"){
            receipt.setStatus(true);
            LocalDate localDateNow = LocalDate.now();
            Date dateNow = Date.from(localDateNow.atStartOfDay(ZoneId.systemDefault()).toInstant());
            receipt.setPayment_date(dateNow);
        }else {
            receipt.setStatus(false);
            receipt.setPayment_date(null);
        }

        double sum=0;
        Query query = session.createSQLQuery(
                "select sum from call where id_con=:id_con");
        query.setInteger("id_con", id_con);
        List result = query.list();
        for(Object element : result) {
            sum+=(double) element;
        }
        receipt.setSum((float) sum);

            session.update(receipt);
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
