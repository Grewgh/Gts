package Actions;

import HibSession.HibernateUtil;
import alert.Confirm;
import alert.alertError;
import javafx.stage.Stage;
import model.Apartment;
import model.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_connection implements Actions{
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_connection(){}
    public Del_connection(Controller c) {
        controller = c;
    }

    Connection connection;
    Stage window = new Stage();
    List result;

    @Override
    public void save() {

    }

    public void delete() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.clear();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        Confirm confirm = new Confirm();
        boolean result = confirm.check();
        if (result) {
            if (session == null)
                return;
            connection = controller.connectionTableView.getSelectionModel().getSelectedItem();
            if (connection == null) {
                err.alertError();
                return;
            }
            session.delete(connection);
            session.flush();
            tx.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
