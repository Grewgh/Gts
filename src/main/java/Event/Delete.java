package Event;

import model.Connection;
import model.Payphone;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.DeleteEvent;
import org.hibernate.event.spi.DeleteEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;

import java.util.Set;

public class Delete implements DeleteEventListener {
    @Override
    public void onDelete(DeleteEvent e) throws HibernateException {
        final  Object entity = e.getObject();
        if(entity instanceof Connection) {
            Connection connection = (Connection) entity;

            e.getSession().createNativeQuery(
                    "update telephone_number set status=true WHERE id = :id")
                    .setParameter("id", connection.getTelephone_number())
                    .setFlushMode(FlushMode.MANUAL)
                    .executeUpdate();
        }

        if(entity instanceof Payphone) {
            Payphone payphone = (Payphone) entity;

            e.getSession().createNativeQuery(
                    "update telephone_number set status=true WHERE id = :id")
                    .setParameter("id", payphone.getTelephone_number())
                    .setFlushMode(FlushMode.MANUAL)
                    .executeUpdate();
        }

    }

    @Override
    public void onDelete(DeleteEvent e, Set set) throws HibernateException {


    }
}