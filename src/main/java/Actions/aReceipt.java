package Actions;

import HibSession.HibSession;
import alert.Confirm;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;
import run.MainApp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class aReceipt implements Actions {

    HibSession hs = new HibSession();
    public Session session = hs.getSession();
    alertError err = new alertError();
    private Controller controller;
    public aReceipt(Controller c) {
        controller = c;
    }

    TextField number = new TextField();
    Button saveButton = new Button("Сохранить");
    Label label = new Label("Укажите договор");
    Label label2 = new Label("Дата начисления");
    DatePicker datePicker = new DatePicker();

    public void form() {
        label.setFont(Font.font("Cambria", 16));
        label.setLayoutX(10);
        label.setLayoutY(10);

        label2.setFont(Font.font("Cambria", 16));
        label2.setLayoutX(10);
        label2.setLayoutY(40);

        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        datePicker.setLayoutX(180);
        datePicker.setLayoutY(40);

        saveButton.setLayoutX(10);
        saveButton.setLayoutY(300);
    }

    public void save() {
        if (session == null) {
            return;
        }
        Receipt receipt = new Receipt();
        Query query = session.createSQLQuery(
                "select id from connection");
        List result = query.list();
        if (!result.isEmpty()) {
            form();
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

            Query query2 = session.createSQLQuery(
                    "select id from connection");
            List result2 = query2.list();
            if (result2.isEmpty()) {
                err.alertError();
                return;
            }
            ObservableList<Integer> observableArrayList =
                    FXCollections.observableArrayList(result2);
            ComboBox<Integer> comboBox = new ComboBox<Integer>(observableArrayList);
            comboBox.setLayoutX(180);
            comboBox.setLayoutY(10);

            Pane root = new Pane(saveButton, label, label2, comboBox, datePicker);
            root.setPrefSize(400, 400);
            Parent content = root;
            Scene scene = new Scene(content);
            scene.getStylesheets().add(MainApp.class.getResource("../bootstrap3.css").toExternalForm());
            Stage window = new Stage();
            window.setScene(scene);
            window.show();
            saveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    LocalDate localDate = datePicker.getValue();
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    receipt.setAccrual_date(date);

                    int id_con;
                    if (comboBox.getSelectionModel().isEmpty()) {
                        id_con = (int) result2.get(0);
                    } else {
                        id_con = comboBox.getSelectionModel().getSelectedItem();
                    }
                    Connection connection = (Connection) session.load(Connection.class, id_con);
                    receipt.setConnection(connection);
                    receipt.setPayment_date(date);
                    receipt.setStatus(false);
                    receipt.setSum(55555.0f);

                    Transaction trans = session.beginTransaction();
                    session.save(receipt);
                    session.flush();
                    trans.commit();
                    controller.load();
                    window.close();
                }
            });
        } else {
            err.alertError();
        }
        controller.load();
    }

    @Override
    public void delete() {
        Confirm confirm = new Confirm();
        boolean result = confirm.check();
        if (result) {
            if (session == null) {
                return;
            }
            Receipt receipt = controller.receiptTableView.getSelectionModel().getSelectedItem();
            if (receipt == null) {
                err.alertError();
                return;
            }
            int id = receipt.getId();
            Receipt receipt2 = session.load(Receipt.class, id);
            Transaction trans = session.beginTransaction();
            session.delete(receipt2);
            session.flush();
            trans.commit();
            controller.load();
        } else {
            return;
        }
    }

    @Override
    public void update() {
        if (session == null) {
            return;
        }
        Receipt receipt = controller.receiptTableView.getSelectionModel().getSelectedItem();
        if (receipt == null) {
            err.alertError();
            return;
        }
        int id = receipt.getId();
        Receipt receipt2 = (Receipt) session.load(Receipt.class, id);
        form();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = receipt2.getAccrual_date().toInstant();
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

        Query query2 = session.createSQLQuery(
                "select id from connection");
        List result2 = query2.list();
        if (result2.isEmpty()) {
            err.alertError();
            return;
        }
        int id_con=receipt2.getConnection();
        ObservableList<Integer> observableArrayList =
                FXCollections.observableArrayList(result2);
        ComboBox<Integer> comboBox = new ComboBox<Integer>(observableArrayList);
        comboBox.getSelectionModel().select(id_con);
        comboBox.setLayoutX(180);
        comboBox.setLayoutY(10);

        Pane root = new Pane(saveButton, label, label2,comboBox, datePicker);
        root.setPrefSize(400, 400);
        Parent content = root;
        Scene scene = new Scene(content);
        scene.getStylesheets().add(MainApp.class.getResource("../bootstrap3.css").toExternalForm());
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id_con;
                if (comboBox.getSelectionModel().isEmpty()) {
                    id_con = (int) result2.get(0);
                } else {
                    id_con = comboBox.getSelectionModel().getSelectedItem();
                }
                Connection connection = (Connection) session.load(Connection.class, id_con);
                receipt2.setConnection(connection);

                LocalDate localDate = datePicker.getValue();
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                receipt2.setAccrual_date(date);

                Transaction trans = session.beginTransaction();
                session.update(receipt2);
                session.flush();
                trans.commit();
                controller.load();
                window.close();
            }
        });
        controller.load();
    }
}

