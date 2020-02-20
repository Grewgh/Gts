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
public class FindRequest3 implements Actions{
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();

    @FXML
    public Button findButton;

    @FXML
    public Label label,label2;

    @FXML
    public ComboBox atsCombobox,regionCombobox;

    @FXML
    public RadioButton radioButton1, radioButton2,radioButton3;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Controller controller;

    public FindRequest3(){}
    public FindRequest3(Controller c) {
        controller = c;
    }
    List result,result2,result3;
    City_ats city_ats;

    @FXML
    public void initialize() {
        session = hs.getSession();
        if (session == null) {
            return;
        }

        Query query3 = session.createNativeQuery("select id from region");
        result3 = query3.list();

        Query query2 = session.createNativeQuery("select id from payphone");
        result2 = query2.list();

        Query query = session.createNativeQuery("select id from city_ats");
        result = query.list();

        ToggleGroup group = new ToggleGroup();
        radioButton1.setToggleGroup(group);
        radioButton2.setToggleGroup(group);
        radioButton3.setToggleGroup(group);

        if(!result.isEmpty()){
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(result);
            atsCombobox.setItems(observableArrayList);
            atsCombobox.setValue(String.valueOf(result.get(0)));
        }

        if(!result3.isEmpty()){
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(result3);
            regionCombobox.setItems(observableArrayList);
            regionCombobox.setValue(String.valueOf(result3.get(0)));
        }

        session.close();
    }



    public void handleFind(ActionEvent event) {
        session = hs.getSession();
        ObservableList<Request3> request3 = FXCollections.observableArrayList();
        ObservableList<Payphone>payphoneList = FXCollections.observableArrayList();
        String str  = String.valueOf(atsCombobox.getValue());
        int k = Integer.parseInt(str);

        if(radioButton1.isSelected()) {
                City_ats city_ats = (City_ats) session.load(City_ats.class, Integer.parseInt(String.valueOf(atsCombobox.getValue())));
                List listTelephone_number = city_ats.getTelephone_number();
                for (int i = 0; i < listTelephone_number.size(); i++) {
                    Telephone_number telephone_number = (Telephone_number) listTelephone_number.get(i);
                    if (telephone_number.getPayphone()!=null) {
                        payphoneList.add(telephone_number.getPayphone());
                    }
                }
            for (Payphone payphone : payphoneList) {
                request3.add(new Request3(payphone.getId()));
            }
            controller.request3idPayphone.setCellValueFactory(new PropertyValueFactory<Request3, Integer>("idPayphone"));
            controller.request3TableView.setItems(request3);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запрос 3");
            alert.setHeaderText(null);
            alert.setContentText("Число таксофонов на выбранной АТС: " + request3.size());
            alert.showAndWait();
        }

        if(radioButton2.isSelected()){
            for (Object  obj: result2) {
                int i = (int) obj;
                request3.add(new Request3(i));
            }
            System.out.println("123"+request3);
            controller.request3idPayphone.setCellValueFactory(new PropertyValueFactory<Request3, Integer>("idPayphone"));
            controller.request3TableView.setItems(request3);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запрос 3");
            alert.setHeaderText(null);
            alert.setContentText("Число таксофонов во всем городе: " + request3.size());
            alert.showAndWait();
        }

        if(radioButton3.isSelected()) {
            Region region = (Region) session.load(Region.class, Integer.parseInt(String.valueOf(regionCombobox.getValue())));
            List listStreet = region.getStreet();
            for (int i = 0; i < listStreet.size(); i++) {
                Street street = (Street) listStreet.get(i);
                if (street.getHouse()!=null) {
                    List listHouse = street.getHouse();
                    for (int j = 0; j < listHouse.size(); j++) {
                        House house = (House) listHouse.get(j);
                        if(house.getPayphone()!=null){
                            payphoneList.addAll(house.getPayphone());
                        }
                    }
                }
            }
            for (Payphone payphone : payphoneList) {
                request3.add(new Request3(payphone.getId()));
            }
            controller.request3idPayphone.setCellValueFactory(new PropertyValueFactory<Request3, Integer>("idPayphone"));
            controller.request3TableView.setItems(request3);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запрос 3");
            alert.setHeaderText(null);
            alert.setContentText("Число таксофонов в выбранном регионе: " + request3.size());
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
