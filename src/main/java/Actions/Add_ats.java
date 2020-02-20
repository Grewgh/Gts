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
import model.Gts;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Add_ats implements Actions {
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();
    @FXML
    TextField name;
    @FXML
    Button saveButton;
    @FXML
    private Label label;
    private Controller controller;
    public Add_ats(){}
    public Add_ats(Controller c) {
        controller = c;
    }

    Ats ats;
    Gts gts;
    Stage window = new Stage();
    List result,result2;

    @FXML
    public void initialize() {
        session = hs.getSession();
        ats = new Ats();
        if (session == null) {
            return;
        }
        Query query = session.createSQLQuery(
                "select * from ats")
                .addEntity(Ats.class);
        result = query.list();
        Query query2 = session.createSQLQuery(
                "select id from gts");
        result2 = query2.list();
        Object obj=result2.get(0);
        int id=(int)obj;
        gts = (Gts) session.load(Gts.class, id);
        name.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        name.setTooltip(new Tooltip("До 25 символов"));
        session.close();
    }

    public void handleSave(ActionEvent event) {
        if(result2.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("ГТС не существует!");
            alert.showAndWait();
            ((Stage)label.getScene().getWindow()).close();
            return;
        }
        if(result.size()>2){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Гтс не может иметь больше 3 атс!");
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
            ats.setName(name.getText());
            ats.setGts(gts);
            Transaction trans = session.beginTransaction();
            session.save(ats);
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
