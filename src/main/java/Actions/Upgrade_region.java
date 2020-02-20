package Actions;

import HibSession.HibernateUtil;
import alert.alertError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Ats;
import model.City_ats;
import model.Region;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Upgrade_region implements Actions {
    public Session session;
    @FXML
    Label label,label2;
    @FXML
    ComboBox comboBox;
    @FXML
    TextField name;
    @FXML
    Button upgradeButton;
    alertError err = new alertError();
    private Controller controller;

    public Upgrade_region(){}
    public Upgrade_region(Controller c) {
        controller = c;
    }

    Region region;
    Stage window = new Stage();
    List result;

    public void setController(Controller controller){
        this.controller = controller;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        if (session == null) {
            err.alertError();
            return;
        }
        region = controller.regionTableView.getSelectionModel().getSelectedItem();

        Query query = session.createSQLQuery(
                "select id from city_ats");
        result = query.list();
        if(!result.isEmpty()) {
            comboBox.getItems().addAll(result);
            comboBox.setValue(region.getCity_ats());
        }

        name.setText(region.getName());
        name.setTextFormatter(new TextFormatter<String>(change -> {
            if (!change.getText().isEmpty()) {
                return change.getText().matches("\\W+")  && change.getControlNewText().length() <= 25 ? change : null;
            }
            return change;
        }));
        name.setTooltip(new Tooltip("До 25 символов"));
        session.clear();
        session.close();
    }

    public void handleUpgrade(ActionEvent event) {
        if (name.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ой");
            alert.setHeaderText(null);
            alert.setContentText("Вы забыли указать название!");
            alert.showAndWait();
            return;
        }

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }

        Object obj = comboBox.getValue();
        int i = (int) obj;
        City_ats city_ats = (City_ats) session.load(City_ats.class, i);
        region.setCity_ats(city_ats);

        region.setName(name.getText());
        session.update(region);
        session.flush();
        tx.commit();
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
