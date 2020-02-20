package Actions;

import HibSession.HibSession;
import alert.Confirm;
import alert.alertError;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Gts;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_gts implements Actions{
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_gts(){}
    public Del_gts(Controller c) {
        controller = c;
    }

    Gts gts = new Gts();
    Stage window = new Stage();
    List result;

    @Override
    public void save() {

    }

    public void delete() {
        session=hs.getSession();
        Confirm confirm = new Confirm();
        boolean result = confirm.check();
        if (result) {
            if (session == null)
                return;
            Gts gts = controller.gtsTableView.getSelectionModel().getSelectedItem();
            if (gts == null) {
                err.alertError();
                return;
            }
            int id = gts.getId();
            Gts gts2 = (Gts) session.load(Gts.class, id);
            Transaction trans = session.beginTransaction();
            session.delete(gts2);
            session.flush();
            trans.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
