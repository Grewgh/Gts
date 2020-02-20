package HibSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import run.Controller;

public class HibSession {

    public Session session = null;

    public Session createHibernateSession() {
        SessionFactory sf = null;

        ServiceRegistry srvc = null;
        try {
            Configuration conf = new Configuration();
            conf.configure().buildSessionFactory();

            srvc = new StandardServiceRegistryBuilder()
                    .applySettings(conf.getProperties()).build();
            sf = conf.buildSessionFactory();
            session = sf.withOptions().openSession();
            System.out.println("Создание сессии");

        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
        return session;
    }
    public Session getSession(){
        session=createHibernateSession();
        return session;
    }

}
