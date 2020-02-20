package Actions;

import HibSession.HibSession;
import alert.Confirm;
import alert.alertError;
import javafx.stage.Stage;
import model.Ats;
import model.Gts;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_ats implements Actions{
    HibSession hs = new HibSession();
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_ats(){}
    public Del_ats(Controller c) {
        controller = c;
    }

    Ats ats;
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
            ats = controller.atsTableView.getSelectionModel().getSelectedItem();
            if (ats == null) {
                err.alertError();
                return;
            }
            Transaction trans = session.beginTransaction();
            session.delete(ats);
            session.flush();
            trans.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
