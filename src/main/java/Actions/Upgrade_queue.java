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
import model.Client;
import model.Queue;
import model.Region;
import model.Street;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Upgrade_queue implements Actions {

    public Session session;
    @FXML
    Label label,label2;
    @FXML
    TextField name;
    @FXML
    Button upgradeButton;
    @FXML
    private ComboBox comboBox;
    @FXML
    private DatePicker datePicker;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_queue(){}
    public Upgrade_queue(Controller c) {
        controller = c;
    }

    Queue queue;
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
        queue = controller.queueTableView.getSelectionModel().getSelectedItem();

        comboBox.setValue(queue.getClient());
        Query query = session.createSQLQuery(
                "select id from client");
        result = query.list();
        ObservableList<String> observableArrayList =
                FXCollections.observableArrayList(result);
        comboBox.setItems(observableArrayList);

        Date date = queue.getQueue_date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePicker.setValue(localDate);
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

        session.clear();
        session.close();
    }

    public void handleUpgrade(ActionEvent event) {
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

        int i = (int) comboBox.getValue();
        Client client = (Client) session.load(Client.class, i);
        queue.setClient(client);

        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        queue.setQueue_date(date);

        if(client.getExemption()==0){
            queue.setExemption(false);
        }else{
            queue.setExemption(true);
        }

        session.update(queue);
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
