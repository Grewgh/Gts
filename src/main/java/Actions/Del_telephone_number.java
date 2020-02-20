package Actions;

import HibSession.HibernateUtil;
import alert.Confirm;
import alert.alertError;
import javafx.stage.Stage;
import model.Street;
import model.Telephone_number;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_telephone_number implements Actions{
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_telephone_number(){}
    public Del_telephone_number(Controller c) {
        controller = c;
    }

    Telephone_number telephone_number;
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
            telephone_number = controller.telephone_numberTableView.getSelectionModel().getSelectedItem();
            if (telephone_number == null) {
                err.alertError();
                return;
            }
            session.delete(telephone_number);
            session.flush();
            tx.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
