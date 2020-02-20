package Actions;

import HibSession.HibernateUtil;
import alert.Confirm;
import alert.alertError;
import javafx.stage.Stage;
import model.Street;
import model.Tariff;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_tariff implements Actions{
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_tariff(){}
    public Del_tariff(Controller c) {
        controller = c;
    }

    Tariff tariff;
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
            tariff = controller.tariffTableView.getSelectionModel().getSelectedItem();
            if (tariff == null) {
                err.alertError();
                return;
            }
            session.delete(tariff);
            session.flush();
            tx.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
