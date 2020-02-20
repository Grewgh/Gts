package Actions;

import HibSession.HibSession;
import alert.alertError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Ats;
import model.City_ats;
import model.Gts;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Add_city_ats implements Actions {
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
    private ComboBox<String> comboBox;
    private Controller controller;
    public Add_city_ats(){}
    public Add_city_ats(Controller c) {
        controller = c;
    }

    Ats ats;
    City_ats city_ats;
    Stage window = new Stage();
    List result,result2,result3;

    @FXML
    public void initialize() {
        session = hs.getSession();
        name.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        name.setTooltip(new Tooltip("До 25 символов"));
        city_ats = new City_ats();
        ats = new Ats();
        if (session == null) {
            return;
        }
        Query query3 = session.createSQLQuery(
                "select * from city_ats")
                .addEntity(City_ats.class);
        result3 = query3.list();
            Query query = session.createSQLQuery(
                    "select * from ats")
                    .addEntity(Ats.class);
            result = query.list();
            Query query2 = session.createSQLQuery(
                    "select name from ats");
            result2 = query2.list();
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(result2);
            comboBox.setItems(observableArrayList);
            comboBox.setValue(String.valueOf(result2.get(0)));
        session.close();

    }

    public void handleSave(ActionEvent event) {
        if(result.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("АТС не существует!");
            alert.showAndWait();
            ((Stage)label.getScene().getWindow()).close();
            return;
        }
        /*if(!result3.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Городская атс уже существует!");
            alert.showAndWait();
            ((Stage)label.getScene().getWindow()).close();
            return;
        }*/
        if (name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать название!");
            alert.showAndWait();
            return;
        }

        session = hs.getSession();
        city_ats.setName(name.getText());
        String ats_name;
        ats_name = comboBox.getValue();
        Query query = session.createSQLQuery(
                "select id from ats where name=:ats_name");
        query.setString("ats_name", ats_name);
        List result = query.list();
        Object obj =result.get(0);
        int i = (int) obj;
        Ats ats = (Ats) session.load(Ats.class, i);
        city_ats.setAts(ats);
        Transaction trans = session.beginTransaction();
        session.save(city_ats);
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
