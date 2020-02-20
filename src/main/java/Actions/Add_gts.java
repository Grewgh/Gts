package Actions;

import HibSession.HibSession;
import alert.Confirm;
import alert.alertError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Gts;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;
import run.LimitedTextField;

import java.util.List;
import java.util.regex.Pattern;

public class Add_gts implements Actions {

    HibSession hs = new HibSession();
    public Session session;
    @FXML
    Label label;
    @FXML
    TextField name;
    @FXML
    Button saveButton;
    alertError err = new alertError();
    private Controller controller;

    public Add_gts(){}
    public Add_gts(Controller c) {
        controller = c;
    }

    Gts gts;
    Stage window = new Stage();
    List result;

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

        gts = new Gts();
        if (session == null) {
            return;
        }
        Query query = session.createNativeQuery("select * from gts").addEntity(Gts.class);
        result = query.list();
        session.close();
    }

    public void handleSave(ActionEvent event) {
        if(!result.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("ГТС уже существует!");
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
            gts.setName(name.getText());
            Transaction trans = session.beginTransaction();
            session.save(gts);
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

    /*public void update() {
        if (session == null)
            return;
        Gts gts = controller.gtsTableView.getSelectionModel().getSelectedItem();
        if (gts == null) {
            err.alertError();
            return;
        }
        int id = gts.getId();
        Gts gts2 = (Gts) session.load(Gts.class, id);
        form();
        name.setText(gts2.getName());
        Button saveButton = new Button("Сохранить");
        saveButton.setLayoutX(100);
        saveButton.setLayoutY(300);
        Pane root = new Pane(saveButton, name, label);
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
                if (name != null) {
                    gts2.setName(name.getText());
                    Transaction trans = session.beginTransaction();
                    session.update(gts2);
                    session.flush();
                    trans.commit();
                    controller.load();
                    window.close();
                }
            }
        });
        controller.load();
    }*/
}
