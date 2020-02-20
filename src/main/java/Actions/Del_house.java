package Actions;

import HibSession.HibernateUtil;
import alert.Confirm;
import alert.alertError;
import javafx.stage.Stage;
import model.House;
import model.Street;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.Controller;

import java.util.List;

public class Del_house implements Actions{
    public Session session;
    alertError err = new alertError();
    private Controller controller;

    public Del_house(){}
    public Del_house(Controller c) {
        controller = c;
    }

    House house;
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
            house = controller.houseTableView.getSelectionModel().getSelectedItem();
            if (house == null) {
                err.alertError();
                return;
            }
            session.delete(house);
            session.flush();
            tx.commit();
            session.close();
        } else return;
    }

    @Override
    public void update() {

    }
}
