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
import model.Region;
import model.Street;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Add_street implements Actions {
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
    public Add_street(){}
    public Add_street(Controller c) {
        controller = c;
    }

    Region region;
    Street street;
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
        street = new Street();
        region = new Region();
        if (session == null) {
            return;
        }
            Query query = session.createSQLQuery(
                    "select name from region");
            result = query.list();
            ObservableList<String> observableArrayList =
                    FXCollections.observableArrayList(result);
            comboBox.setItems(observableArrayList);
            comboBox.setValue(String.valueOf(result.get(0)));
        session.close();

    }

    public void handleSave(ActionEvent event) {
        if(result.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Не существует ни одного района!");
            alert.showAndWait();
            ((Stage)label.getScene().getWindow()).close();
            return;
        }
        if (name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать название!");
            alert.showAndWait();
            return;
        }

        session = hs.getSession();
        street.setName(name.getText());
        String region_name;
        region_name = comboBox.getValue();

        Query query = session.createSQLQuery(
                "select id from region where name=:region_name");
        query.setString("region_name", region_name);
        List result = query.list();
        Object obj =result.get(0);
        int i = (int) obj;
        Region region = (Region) session.load(Region.class, i);
        street.setRegion(region);
        Transaction trans = session.beginTransaction();
        session.save(street);
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
