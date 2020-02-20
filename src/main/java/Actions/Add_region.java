package Actions;

import HibSession.HibSession;
import alert.alertError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Ats;
import model.City_ats;
import model.Gts;
import model.Region;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Add_region implements Actions {
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
    private Controller controller;
    public Add_region(){}
    public Add_region(Controller c) {
        controller = c;
    }

    City_ats city_ats;
    Region region;
    Stage window = new Stage();
    List result,result2;

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
        region = new Region();
        if (session == null) {
            return;
        }
        Query query = session.createSQLQuery(
                "select id from city_ats");
        result = query.list();
        Object obj=result.get(0);
        int id=(int)obj;
        if(!result.isEmpty()) {
            comboBox.getItems().addAll(result);
            comboBox.setValue(result.get(0));
        }
        session.close();
    }

    public void handleSave(ActionEvent event) {
        if(result.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Городская атс не существует!");
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
        region.setName(name.getText());

        Object obj = comboBox.getValue();
        int i = (int) obj;
        city_ats = (City_ats) session.load(City_ats.class, i);
        region.setCity_ats(city_ats);

        Transaction trans = session.beginTransaction();
        session.save(region);
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
