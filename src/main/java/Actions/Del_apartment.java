package Actions;

import HibSession.HibernateUtil;
import alert.Confirm;
import alert.alertError;
import javafx.stage.Stage;
import model.Apartment;
import model.House;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_apartment implements Actions{
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_apartment(){}
    public Del_apartment(Controller c) {
        controller = c;
    }

    Apartment apartment;
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
            apartment = controller.apartmentTableView.getSelectionModel().getSelectedItem();
            if (apartment == null) {
                err.alertError();
                return;
            }
            session.delete(apartment);
            session.flush();
            tx.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
