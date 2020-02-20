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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Add_receipt implements Actions {
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();
    @FXML
    TextField number;
    @FXML
    DatePicker datePicker = new DatePicker();
    @FXML
    Button saveButton;
    @FXML
    private Label label,label2;
    @FXML
    private ComboBox comboBox;
    private Controller controller;
    public Add_receipt(){}
    public Add_receipt(Controller c) {
        controller = c;
    }

    Receipt receipt;
    Connection connection;
    Stage window = new Stage();
    List result,result2,result3;

    @FXML
    public void initialize() {
        session = hs.getSession();
        receipt = new Receipt();

        Query query = session.createSQLQuery(
                "select id from connection");
        List result = query.list();
        if (!result.isEmpty()) {

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
            datePicker.setValue(LocalDate.of(2019,11,10));

            ObservableList<Integer> observableArrayList =
                    FXCollections.observableArrayList(result);
            comboBox.setItems(observableArrayList);
            comboBox.setValue(result.get(0));
        }else{
            err.alertError();
        }
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
        receipt.setAccrual_date(date);

        int idCon = (int) comboBox.getValue();
        Connection connection = (Connection) session.load(Connection.class, idCon);
        receipt.setConnection(connection);

        int id_con=connection.getId();
        double sum=0;
        Query query = session.createSQLQuery(
                "select sum from call where id_con=:id_con");
        query.setInteger("id_con", id_con);
        List result = query.list();
        for(Object element : result) {
            sum+=(double) element;
        }
        receipt.setSum((float) sum);
        receipt.setStatus(false);
        receipt.setPayment_date(null);

        Transaction trans = session.beginTransaction();
        session.save(receipt);
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
