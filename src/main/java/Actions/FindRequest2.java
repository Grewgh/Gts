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
import run.Controller;

import java.util.List;
//Здесь может находиться множетсво лишних циклов, которые правильно было бы заменить на запрос.
public class FindRequest2 implements Actions{
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();

    @FXML
    public Button findButton;

    @FXML
    public Label label;

    @FXML
    public ComboBox atsCombobox;

    @FXML
    public RadioButton radioButton1, radioButton2;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Controller controller;

    public FindRequest2(){}
    public FindRequest2(Controller c) {
        controller = c;
    }
    List result;
    City_ats city_ats;

    @FXML
    public void initialize() {
        session = hs.getSession();
        if (session == null) {
            return;
        }

        Query query = session.createNativeQuery("select id from city_ats");
        result = query.list();

        ToggleGroup group = new ToggleGroup();
        radioButton1.setToggleGroup(group);
        radioButton2.setToggleGroup(group);

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
        ObservableList<Request2> request2 = FXCollections.observableArrayList();
        ObservableList<Telephone_number>telephone_numberList = FXCollections.observableArrayList();
        String str  = String.valueOf(atsCombobox.getValue());
        int k = Integer.parseInt(str);

        if(radioButton1.isSelected()) {
            city_ats = (City_ats) session.load(City_ats.class, k);
            List listTelephone_number = city_ats.getTelephone_number();
            for (int i = 0; i < listTelephone_number.size(); i++) {
                Telephone_number telephone_number = (Telephone_number) listTelephone_number.get(i);
                if (telephone_number.isStatus()) {
                    telephone_numberList.add(telephone_number);
                }
            }
            for (Telephone_number telephone_number : telephone_numberList) {
                request2.add(new Request2(telephone_number.getNumber()));
            }
            controller.request2Number.setCellValueFactory(new PropertyValueFactory<Request2, Integer>("Number"));
            controller.request2TableView.setItems(request2);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запрос 2");
            alert.setHeaderText(null);
            alert.setContentText("Число свободных номеров на выбранной АТС: " + request2.size());
            alert.showAndWait();
        }

        if(radioButton2.isSelected()){
            for (int j=0;j<result.size();j++) {
                int q = (int) result.get(j);
                City_ats city_ats = (City_ats) session.load(City_ats.class, q);
                List listTelephone_number = city_ats.getTelephone_number();
                for (int i = 0; i < listTelephone_number.size(); i++) {
                    Telephone_number telephone_number = (Telephone_number) listTelephone_number.get(i);
                    if (telephone_number.isStatus()) {
                        telephone_numberList.add(telephone_number);
                    }
                }
            }
            for (Telephone_number telephone_number : telephone_numberList) {
                request2.add(new Request2(telephone_number.getNumber()));
            }
            controller.request2Number.setCellValueFactory(new PropertyValueFactory<Request2, Integer>("Number"));
            controller.request2TableView.setItems(request2);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запрос 2");
            alert.setHeaderText(null);
            alert.setContentText("Число свободных номеров на ГТС: " + request2.size());
            alert.showAndWait();
        }
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
