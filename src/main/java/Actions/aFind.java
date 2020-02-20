package Actions;

import HibSession.HibSession;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Apartment;
import model.Connection;
import model.Find;
import model.Telephone_number;
import org.hibernate.Query;
import org.hibernate.Session;
import run.Controller;
import run.MainApp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class aFind {
    HibSession hs = new HibSession();
    public Session session = hs.getSession();
    alertError err = new alertError();
    private Controller controller;

    public aFind(Controller c) {
        controller = c;
    }

    public void find() {
        if (session == null) {
            return;
        }

        Label label = new Label("Подключены с ");
        label.setFont(Font.font("Cambria", 16));
        label.setLayoutX(10);
        label.setLayoutY(20);
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.of(2018, 1, 1));
        datePicker.setShowWeekNumbers(true);
        datePicker.setLayoutX(180);
        datePicker.setLayoutY(20);
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

        Label label2 = new Label("По улице: ");
        label2.setFont(Font.font("Cambria", 16));
        label2.setLayoutX(10);
        label2.setLayoutY(50);
        Query query = session.createSQLQuery(
                "select name from street");
        List result2 = query.list();
        if (result2.isEmpty()) {
            err.alertError();
            return;
        }
        ObservableList<String> observableArrayList =
                FXCollections.observableArrayList(result2);
        ComboBox<String> comboBox = new ComboBox<String>(observableArrayList);
        comboBox.setLayoutX(180);
        comboBox.setLayoutY(50);

        Button findButton = new Button("Поиск");
        findButton.setLayoutX(10);
        findButton.setLayoutY(350);

        Pane root = new Pane(findButton, label, label2, comboBox, datePicker);
        root.setPrefSize(400, 400);
        Parent content = root;
        Scene scene = new Scene(content);
        scene.getStylesheets().add(MainApp.class.getResource("../bootstrap3.css").toExternalForm());
        Stage window = new Stage();
        window.setScene(scene);
        window.show();

        findButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String street_name;
                if (comboBox.getSelectionModel().isEmpty()) {
                    err.alertError();
                    return;
                } else {
                    street_name = comboBox.getValue();
                }
                LocalDate localDate = datePicker.getValue();
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Query query = session.createSQLQuery(
                        "select connection.id from connection,street where connection_date>=:date" +
                                " and name=:street_name");
                query.setDate("date", date);
                query.setString("street_name", street_name);
                List result = query.list();
                result.size();
                int j = 0;
                Object obj;
                int i;
                Connection connection;
                Apartment apartment;
                Telephone_number telephone_number;
                ObservableList<Find> dates = FXCollections.observableArrayList();
                while (j != result.size()) {
                    obj = result.get(j);
                    i = (int) obj;
                    connection = (Connection) session.load(Connection.class, i);
                    apartment = (Apartment) session.load(Apartment.class, connection.getApartment());
                    telephone_number = (Telephone_number) session.load(Telephone_number.class,
                            connection.getTelephone_number());
                    dates.add(new Find(connection.getConnection_date(), apartment.getName(),
                            connection.getStatus(), telephone_number.getNumber(), connection.getId()));
                    controller.findCon_date.setCellValueFactory(new PropertyValueFactory<Find, Date>("connection_date"));
                    controller.findName_apartment.setCellValueFactory(new PropertyValueFactory<Find, String>("name"));
                    controller.findStatus.setCellValueFactory(new PropertyValueFactory<Find, Boolean>("status"));
                    controller.find_number.setCellValueFactory(new PropertyValueFactory<Find, Integer>("number"));
                    controller.findId_client.setCellValueFactory(new PropertyValueFactory<Find, Integer>("id_client"));
                    controller.findTableView.setItems(dates);
                    j += 1;
                }
                window.close();
            }
        });
    }

}
