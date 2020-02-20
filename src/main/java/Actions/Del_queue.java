package Actions;

import HibSession.HibernateUtil;
import alert.Confirm;
import alert.alertError;
import javafx.stage.Stage;
import model.Queue;
import model.Street;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_queue implements Actions{
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_queue(){}
    public Del_queue(Controller c) {
        controller = c;
    }

    Queue queue;
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
            queue = controller.queueTableView.getSelectionModel().getSelectedItem();
            if (queue == null) {
                err.alertError();
                return;
            }
            session.delete(queue);
            session.flush();
            tx.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
