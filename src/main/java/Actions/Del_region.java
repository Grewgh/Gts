package Actions;

import HibSession.HibernateUtil;
import alert.Confirm;
import alert.alertError;
import javafx.stage.Stage;
import model.Ats;
import model.Receipt;
import model.Region;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_region implements Actions{
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_region(){}
    public Del_region(Controller c) {
        controller = c;
    }

    Region region;
    Stage window = new Stage();
    List result;

    @Override
    public void save() {

    }

    public void delete() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        Confirm confirm = new Confirm();
        boolean result = confirm.check();
        if (result) {
            if (session == null)
                return;
            region = controller.regionTableView.getSelectionModel().getSelectedItem();
            if (region == null) {
                err.alertError();
                return;
            }
            session.delete(region);
            session.flush();
            tx.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
