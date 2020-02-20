package Actions;

import HibSession.HibernateUtil;
import alert.Confirm;
import alert.alertError;
import javafx.stage.Stage;
import model.Receipt;
import model.Street;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_receipt implements Actions{
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_receipt(){}
    public Del_receipt(Controller c) {
        controller = c;
    }

    Receipt receipt;
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
            receipt = controller.receiptTableView.getSelectionModel().getSelectedItem();
            if (receipt == null) {
                err.alertError();
                return;
            }
            session.delete(receipt);
            session.flush();
            tx.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
