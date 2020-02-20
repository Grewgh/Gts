package Actions;

import HibSession.HibSession;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.Iterator;
import java.util.List;

//Здесь может находиться множетсво лишних циклов, которые правильно было бы заменить на запрос.
public class FindRequest1 implements Actions{
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();

    @FXML
    public Button findButton;

    @FXML
    public Label label, label2, label3;

    @FXML
    public ComboBox atsCombobox;

    @FXML
    public TextField textField1,textField2;


    @FXML
    public CheckBox checkBox;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Controller controller;
    public FindRequest1(){}
    public FindRequest1(Controller c) {
        controller = c;
    }

    City_ats ats;

    @FXML
    public void initialize() {
        session = hs.getSession();
        if (session == null) {
            return;
        }

        textField2.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        textField2.setTooltip(new Tooltip("До 25 символов"));

        textField1.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String txt = change.getControlNewText();
            if (txt.matches("0\\d+") ) {
                return null;
            }
            try {
                int n = Integer.parseInt(txt);
                return 1 <= n && n <= 100 ? change : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }));
        textField1.setTooltip(new Tooltip("От 18 до 100 лет"));

        Query query = session.createNativeQuery("select id from city_ats");
        List result = query.list();

        if(!result.isEmpty()){
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(result);
            atsCombobox.setItems(observableArrayList);
            atsCombobox.setValue(String.valueOf(result.get(0)));
        }

        session.close();
    }

    public void handleFind(ActionEvent event) {
        session = hs.getSession();

        String str  = String.valueOf(atsCombobox.getValue());
        int k = Integer.parseInt(str);

        City_ats city_ats = (City_ats) session.load(City_ats.class, k);
        List listTelephone_number = city_ats.getTelephone_number();
        List listConnection;
        ObservableList<Request1> request1 = FXCollections.observableArrayList();
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        for(int i =0; i<listTelephone_number.size(); i++){
            Telephone_number telephone_number = (Telephone_number) listTelephone_number.get(i);
            listConnection = telephone_number.getConnection();
            for(int j = 0; j<listConnection.size(); j++){
                Connection connection = (Connection) listConnection.get(j);
                int id = connection.getClient();
                Client client = (Client) session.load(Client.class, id);
                clientList.add(client);
            }
        }
        if(checkBox.isSelected()){

            Iterator<Client> iterator = clientList.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                if (client.getExemption()==0) {
                    iterator.remove();
                }
            }
        }
        if(!textField1.getText().isEmpty()){
            Iterator<Client> iterator = clientList.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                if (client.getAge()!=Integer.parseInt(textField1.getText())) {
                    iterator.remove();
                }
            }
        }
        if(!textField2.getText().isEmpty()){
            Iterator<Client> iterator = clientList.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                if (!String.valueOf(client.getSurname()).startsWith(textField2.getText())) {
                    iterator.remove();
                }
            }
        }
        for (Client client:clientList) {
        request1.add(new Request1(city_ats.getId(), client.getSurname(),
                client.getName(), client.getMiddlename()));
        }

        controller.request1IdAts.setCellValueFactory(new PropertyValueFactory<Request1, Integer>("request1IdAts"));
        controller.request1Surname.setCellValueFactory(new PropertyValueFactory<Request1, String>("request1Surname"));
        controller.request1Name.setCellValueFactory(new PropertyValueFactory<Request1, String>("request1Name"));
        controller.request1Middlename.setCellValueFactory(new PropertyValueFactory<Request1, String>("request1Middlename"));
        controller.request1TableView.setItems(request1);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Запрос 1");
        alert.setHeaderText(null);
        alert.setContentText("Число абонентов: " + request1.size());
        alert.showAndWait();

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
