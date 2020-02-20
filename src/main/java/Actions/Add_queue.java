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
import run.Controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Add_queue implements Actions {
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
    private DatePicker datePicker;
    private Controller controller;
    public Add_queue(){}
    public Add_queue(Controller c) {
        controller = c;
    }

    Queue queue;
    Stage window = new Stage();
    List result,result2,result3;

    @FXML
    public void initialize() {
        session = hs.getSession();
        queue = new Queue();
        if (session == null) {
            return;
        }

        Query query = session.createSQLQuery(
                "select id from client");
        result = query.list();
        ObservableList<Integer> observableArrayList =
                FXCollections.observableArrayList(result);
        comboBox.setItems(observableArrayList);
        comboBox.setValue(result.get(0));

        LocalDate data = LocalDate.now();
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
        datePicker.setTooltip(new Tooltip("Выберите дату от 01.01.1960 г."));

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

        Transaction trans = session.beginTransaction();
        session.save(queue);
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
