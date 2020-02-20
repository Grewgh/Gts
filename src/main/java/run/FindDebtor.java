package run;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import HibSession.HibernateUtil;
import javafx.scene.layout.VBox;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.cglib.core.Local;

public class FindDebtor implements Job
{
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        System.out.println("Hello Quartz!");
        LocalDate dateNow;
        dateNow=LocalDate.now();
        dateNow= dateNow.minusDays(20);
        Date date = Date.from(dateNow.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String str = String.valueOf(dateNow);

        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        Query query = session.createSQLQuery(
                "Update connection set status=false from receipt where accrual_date<=:date " +
                        "and connection.id=receipt.id_con and receipt.sum>0");
        query.setDate("date", date);
        System.out.println("Обновлены договора за  "+str);
        session.flush();
        query.executeUpdate();
        trans.commit();
        session.clear();
        session.close();
    }

}