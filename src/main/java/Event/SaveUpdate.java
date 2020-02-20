package Event;

import model.*;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.hibernate.sql.Update;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SaveUpdate implements SaveOrUpdateEventListener {

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent e) throws HibernateException {
        Object entity = e.getEntity();
        if(entity instanceof Connection) {
            Connection connection = (Connection) entity;
            e.getSession().createNativeQuery(
                    "delete from queue where id_client=:id_client")
                    .setParameter("id_client", connection.getClient())
                    .setFlushMode(FlushMode.MANUAL)
                    .executeUpdate();
            e.getSession().createNativeQuery(
                    "update telephone_number set status=false where id=:id")
                    .setParameter("id", connection.getTelephone_number())
                    .setFlushMode(FlushMode.MANUAL)
                    .executeUpdate();
        }

        if(entity instanceof Payphone) {
            Payphone payphone = (Payphone) entity;
            e.getSession().createNativeQuery(
                    "update telephone_number set status=false where id=:id")
                    .setParameter("id", payphone.getTelephone_number())
                    .setFlushMode(FlushMode.MANUAL)
                    .executeUpdate();
        }

        if(entity instanceof Client) {
            Client client = (Client) entity;
            Queue queue = new Queue();
            Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            queue.setQueue_date(date);
            queue.setClient(client);
            Exemption exemption;
            exemption = client.getEx();
            if(exemption!=null) {
                queue.setExemption(true);
            } else {
                queue.setExemption(false);
            }
            e.getSession().save(queue);
        }

    }
}