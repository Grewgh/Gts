package run;

import Actions.*;
import HibSession.HibSession;
import HibSession.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import model.Request1;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import sun.misc.Request;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Add_gts add_Gts;
    public Del_gts del_Gts;
    public Upgrade_gts upgrade_Gts;
    public Add_ats add_Ats;
    public Del_ats del_Ats;
    public Upgrade_ats upgrade_Ats;
    public Add_city_ats add_City_ats;
    public Upgrade_city_ats upgrade_City_ats;
    public Add_region add_Region;
    public Del_region del_Region;
    public Upgrade_region upgrade_Region;
    public Add_street add_Street;
    public Del_street del_Street;
    public Upgrade_street upgrade_Street;
    public Add_house add_House;
    public Del_house del_House;
    public Upgrade_house upgrade_House;
    public Add_apartment add_Apartment;
    public Del_apartment del_Apartment;
    public Upgrade_apartment upgrade_Apartment;
    public Add_connection add_Connection;
    public Del_connection del_Connection;
    public Upgrade_connection upgrade_Connection;
    public Add_exemption add_Exemption;
    public Del_exemption del_Exemption;
    public Upgrade_exemption upgrade_Exemption;
    public Add_client add_Client;
    public Del_client del_Client;
    public Upgrade_client upgrade_Client;
    public Add_telephone_number add_Telephone_number;
    public Del_telephone_number del_Telephone_number;
    public Upgrade_telephone_number upgrade_Telephone_number;
    public Add_tariff add_Tariff;
    public Del_tariff del_Tariff;
    public Upgrade_tariff upgrade_Tariff;
    public Add_call add_Call;
    public Del_call del_Call;
    public Upgrade_call upgrade_Call;
    public Add_receipt add_Receipt;
    public Del_receipt del_Receipt;
    public Upgrade_receipt upgrade_Receipt;
    public Add_queue add_Queue;
    public Del_queue del_Queue;
    public Upgrade_queue upgrade_Queue;
    public Add_payphone add_Payphone;
    public Del_payphone del_Payphone;
    public Upgrade_payphone upgrade_Payphone;
    public FindRequest1 findRequest1;
    public FindRequest2 findRequest2;
    public FindRequest3 findRequest3;

    public aFind аfind;
    public HibSession hs = new HibSession();
    public Session session=HibernateUtil.
    getSessionFactory().openSession();



    public Controller() {
        add_Gts = new Add_gts(this);
        del_Gts = new Del_gts(this);
        upgrade_Gts = new Upgrade_gts(this);
        add_Ats = new Add_ats(this);
        del_Ats = new Del_ats(this);
        upgrade_Ats = new Upgrade_ats(this);
        add_City_ats = new Add_city_ats(this);
        upgrade_City_ats = new Upgrade_city_ats(this);
        add_Region = new Add_region(this);
        del_Region = new Del_region(this);
        upgrade_Region = new Upgrade_region(this);
        add_Street = new Add_street(this);
        del_Street = new Del_street(this);
        upgrade_Street = new Upgrade_street(this);
        add_House = new Add_house(this);
        del_House = new Del_house(this);
        upgrade_House = new Upgrade_house(this);
        add_Apartment = new Add_apartment(this);
        del_Apartment = new Del_apartment(this);
        upgrade_Apartment = new Upgrade_apartment(this);
        add_Connection = new Add_connection(this);
        del_Connection = new Del_connection(this);
        upgrade_Connection = new Upgrade_connection(this);
        add_Exemption = new Add_exemption(this);
        del_Exemption = new Del_exemption(this);
        upgrade_Exemption = new Upgrade_exemption(this);
        add_Client = new Add_client(this);
        del_Client = new Del_client(this);
        upgrade_Client = new Upgrade_client(this);
        add_Telephone_number = new Add_telephone_number(this);
        del_Telephone_number = new Del_telephone_number(this);
        upgrade_Telephone_number = new Upgrade_telephone_number(this);
        add_Tariff = new Add_tariff(this);
        del_Tariff = new Del_tariff(this);
        upgrade_Tariff = new Upgrade_tariff(this);
        add_Call = new Add_call(this);
        del_Call = new Del_call(this);
        upgrade_Call = new Upgrade_call(this);
        add_Receipt = new Add_receipt(this);
        del_Receipt = new Del_receipt(this);
        upgrade_Receipt = new Upgrade_receipt(this);
        add_Queue = new Add_queue(this);
        del_Queue = new Del_queue(this);
        upgrade_Queue = new Upgrade_queue(this);
        add_Payphone = new Add_payphone(this);
        del_Payphone = new Del_payphone(this);
        upgrade_Payphone = new Upgrade_payphone(this);
        findRequest1 = new FindRequest1(this);
        findRequest2 = new FindRequest2(this);
        findRequest3 = new FindRequest3(this);
        аfind = new aFind(this);
    }

    @FXML
    public TableView<Find> findTableView;

    @FXML
    public TableColumn<Find,Date> findCon_date;

    @FXML
    public TableColumn<Find, String> findName_apartment;

    @FXML
    public TableColumn<Find, Boolean> findStatus;

    @FXML
    public TableColumn<Find, Integer> find_number,findId_client;

    @FXML
    public TableView<Region> regionTableView;

    @FXML
    public TableColumn<Region, Integer> regionId,IdCity_ats;

    @FXML
    public TableColumn<Region, String> regionName;

    @FXML
    public TableView<Queue> queueTableView;

    @FXML
    public TableColumn<Queue, Integer> queueId,queueClientId;

    @FXML
    public TableColumn<Queue, Date> queueDate;

    @FXML
    public TableColumn<Queue, Boolean> queueExemption;

    @FXML
    public TableView<Exemption> exemptionTableView;

    @FXML
    public TableColumn<Exemption, Integer> exemptionId,exemptionPercent;

    @FXML
    public TableColumn<Exemption, String> exemptionName;

    @FXML
    public TableView<Tariff> tariffTableView;

    @FXML
    public TableColumn<Tariff, Integer> tariffId;

    @FXML
    public TableColumn<Tariff, String> tariffDay_night,tariffType;

    @FXML
    public TableColumn<Tariff, Float> tariffPrice_minute;

    @FXML
    public TableView<Call> callTableView;

    @FXML
    public TableColumn<Call, Integer> callId,callId_con,callId_tariff;

    @FXML
    public TableColumn<Call, Date> call_start,call_stop;

    @FXML
    public TableColumn<Call, String> call_duration;

    @FXML
    public TableColumn<Call, Float> callSum;

    @FXML
    public TableView<Connection> connectionTableView;

    @FXML
    public TableColumn<Connection, Integer> connectionId,connectionId_apartment,connectionId_number,connectionId_client;

    @FXML
    public TableColumn<Connection, Date> connectionCon_date,connectionDis_date;

    @FXML
    public TableColumn<Connection, String> connectionType_telephone;

    @FXML
    public TableColumn<Connection, Boolean> connectionStatus;

    @FXML
    public TableView<City_ats> city_atsTableView;

    @FXML
    public TableColumn<City_ats, Integer> city_atsId,city_atsId_ats;

    @FXML
    public TableColumn<City_ats, String> city_atsName;

    @FXML
    public TableView<Ats> atsTableView;

    @FXML
    public TableColumn<Ats, Integer> atsId,atsIdGts;

    @FXML
    public TableColumn<Ats, String> atsName;

    @FXML
    public TableView<Apartment> apartmentTableView;

    @FXML
    public TableColumn<Apartment, Integer> apartmentId,apartmentIdHouse;

    @FXML
    public TableColumn<Apartment, String> apartmentName;

    @FXML
    public TableView<Telephone_number> telephone_numberTableView;

    @FXML
    public TableColumn<Telephone_number, Integer> telephone_numberId,telephone_numberNumber,type_atsTelephone_number;

    @FXML
    public TableColumn<Telephone_number, Boolean> statusTelephone_number;

    @FXML
    public TableView<Street> streetTableView;

    @FXML
    public TableColumn<Street, Integer> streetId,streetIdRegion;

    @FXML
    public TableColumn<Street, String> streetName;

    @FXML
    public TableView<Payphone> payphoneTableView;

    @FXML
    public TableColumn<Payphone, Integer> payphoneId,payphoneId_house,payphoneId_telephone_number;

    @FXML
    public TableView<Client> clientTableView;

    @FXML
    public TableColumn<Client, Integer> clientId;

    @FXML
    public TableColumn<Client, String> clientSurname,clientName,clientMiddlename,clientGender;

    @FXML
    public TableColumn<Client, Integer> clientAge,clientIdExemption;

    @FXML
    public TableView<Receipt> receiptTableView;

    @FXML
    public TableColumn<Receipt, Integer> receiptId,receiptId_con;

    @FXML
    public TableColumn<Receipt, Date> receiptAccrual_date,receiptPayment_date;

    @FXML
    public TableColumn<Receipt, Float> receiptSum;

    @FXML
    public TableColumn<Receipt, Boolean> receiptStatus;

    @FXML
    public TableView<House> houseTableView;

    @FXML
    public TableColumn<House, Integer> houseId,houseIdStreet;

    @FXML
    public TableColumn<House, String> houseName;

    @FXML
    public TableView<Gts> gtsTableView;

    @FXML
    public TableColumn<Gts, Integer> gtsId;

    @FXML
    public TableColumn<Gts, String> gtsName;

    @FXML
    public TableView<Request1> request1TableView;

    @FXML
    public TableColumn<Request1, Integer> request1IdAts;

    @FXML
    public TableColumn<Request1, String> request1Surname, request1Name, request1Middlename;

    @FXML
    public TableView<Request2> request2TableView;

    @FXML
    public TableColumn<Request2, Integer> request2Number;

    @FXML
    public TableView<Request3> request3TableView;

    @FXML
    public TableColumn<Request3, Integer> request3idPayphone;

    @FXML
    private Button add_gts, del_gts, update_gts, add_ats, del_ats, update_ats, add_city_ats,del_city_ats,update_city_ats, add_region, del_region,
            update_region, add_street, del_street, update_street, add_house, del_house, update_house,
            add_apartment, del_apartment, update_apartment, add_connection, del_connection, update_connection,
            add_telephone_number, del_telephone_number, update_telephone_number, add_client, del_client, update_client,
            add_exemption, del_exemption, update_exemption,add_receipt, del_receipt, update_receipt,
            add_tariff, del_tariff, update_tariff, add_call, del_call, update_call, add_queue, del_queue, update_queue,
            add_payphone, del_payphone, update_payphone, request1Find,request2Find,request3Find;

    @FXML
    private AnchorPane anchorPane, gtsAnchorPane, atsAnchorPane, city_atsAnchorPane, regionAnchorPane,
        streetAnchorPane, houseAnchorPane, apartmentAnchorPane, connectionAnchorPane, telephone_numberAnchorPane,
            findAnchorPane, clientAnchorPane, exemptionAnchorPane,receiptAnchorPane, tariffAnchorPane,callAnchorPane,
                queueAnchorPane,request1AnchorPane,request2AnchorPane,request3AnchorPane,payphoneAnchorPane;

    @FXML
    private TabPane tabPane, tabPaneMain,tabPane2;

    @FXML
    private Tab gtsTab, atsTab, city_atsTab, regionTab, streetTab, houseTab, apartmentTab, connectionTab,
        telephone_numberTab, clientTab, exemptionTab,receiptTab, tariffTab,callTab,queueTab,findTab,tabTable,tabQuery,tabPayphone,
            request1Tab,request2Tab,request3Tab;

    public void loadGts(){
        gtsId.setCellValueFactory(new PropertyValueFactory<Gts, Integer>("id"));
        gtsName.setCellValueFactory(new PropertyValueFactory<Gts, String>("name"));
        gtsTableView.setItems(getGts());
    }

    public ObservableList<Gts> getGts() {
        ObservableList<Gts> gtsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Gts> gList = session.
                createCriteria(Gts.class).list();
        for (Gts gts : gList) {
            gtsList.add(gts);
        }
        session.clear();
        session.close();
        return gtsList;
    }

    public void loadStreet(){
        streetId.setCellValueFactory(new PropertyValueFactory<Street, Integer>("id"));
        streetName.setCellValueFactory(new PropertyValueFactory<Street, String>("name"));
        streetIdRegion.setCellValueFactory(new PropertyValueFactory<Street, Integer>("region"));
        streetTableView.setItems(getStreets());
    }

    public ObservableList<Street> getStreets() {
        ObservableList<Street> streetList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Street> sList = session.
                createCriteria(Street.class).list();
        for (Street street : sList) {
            streetList.add(street);
        }
        session.clear();
        session.close();
        return streetList;
    }

    public void loadApartment(){
        apartmentId.setCellValueFactory(new PropertyValueFactory<Apartment, Integer>("id"));
        apartmentName.setCellValueFactory(new PropertyValueFactory<Apartment, String>("name"));
        apartmentIdHouse.setCellValueFactory(new PropertyValueFactory<Apartment, Integer>("house"));
        apartmentTableView.setItems(getApartment());
    }

    public ObservableList<Apartment> getApartment() {
        ObservableList<Apartment> apartmentList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Apartment> aList = session.
                createCriteria(Apartment.class).list();
        for (Apartment apartment : aList) {
            apartmentList.add(apartment);
        }
        session.clear();
        session.close();
        return apartmentList;
    }

    public void loadAts(){
        atsId.setCellValueFactory(new PropertyValueFactory<Ats, Integer>("id"));
        atsName.setCellValueFactory(new PropertyValueFactory<Ats, String>("name"));
        atsIdGts.setCellValueFactory(new PropertyValueFactory<Ats, Integer>("gts"));
        atsTableView.setItems(getAts());
    }

    public ObservableList<Ats> getAts() {
        ObservableList<Ats> atsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Ats> aList = session.
                createCriteria(Ats.class).list();
        for (Ats ats : aList) {
            atsList.add(ats);
        }
        session.clear();
        session.close();
        return atsList;
    }

    public void loadCity_Ats(){
        city_atsId.setCellValueFactory(new PropertyValueFactory<City_ats, Integer>("id"));
        city_atsId_ats.setCellValueFactory(new PropertyValueFactory<City_ats, Integer>("ats"));
        city_atsName.setCellValueFactory(new PropertyValueFactory<City_ats, String>("name"));
        city_atsTableView.setItems(getCity_ats());
    }

    public ObservableList<City_ats> getCity_ats() {
        ObservableList<City_ats> city_atsList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<City_ats> cList = session.
                createCriteria(City_ats.class).list();
        for (City_ats city_ats : cList) {
            city_atsList.add(city_ats);
        }
        session.clear();
        session.close();
        return city_atsList;
    }

    public void loadConnection(){
        connectionId.setCellValueFactory(new PropertyValueFactory<Connection, Integer>("id"));
        connectionCon_date.setCellValueFactory(new PropertyValueFactory<Connection, Date>("connection_date"));
        connectionDis_date.setCellValueFactory(new PropertyValueFactory<Connection, Date>("disconnection_date"));
        connectionId_apartment.setCellValueFactory(new PropertyValueFactory<Connection, Integer>("apartment"));
        connectionType_telephone.setCellValueFactory(new PropertyValueFactory<Connection, String>("type_telephone"));
        connectionStatus.setCellValueFactory(new PropertyValueFactory<Connection, Boolean>("status"));
        connectionId_number.setCellValueFactory(new PropertyValueFactory<Connection, Integer>("telephone_number"));
        connectionId_client.setCellValueFactory(new PropertyValueFactory<Connection, Integer>("client"));
        connectionTableView.setItems(getConnection());
    }

    public ObservableList<Connection> getConnection() {
        ObservableList<Connection> connectionList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Connection> cList = session.
                createCriteria(Connection.class).list();
        for (Connection connection : cList) {
            connectionList.add(connection);
        }
        session.clear();
        session.close();
        return connectionList;
    }

    public void loadHouse(){
        houseId.setCellValueFactory(new PropertyValueFactory<House, Integer>("id"));
        houseName.setCellValueFactory(new PropertyValueFactory<House, String>("name"));
        houseIdStreet.setCellValueFactory(new PropertyValueFactory<House, Integer>("street"));
        houseTableView.setItems(getHouse());
    }

    public ObservableList<House> getHouse() {
        ObservableList<House> houseList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<House> hList = session.
                createCriteria(House.class).list();
        for (House house : hList) {
            houseList.add(house);
        }
        session.clear();
        session.close();
        return houseList;
    }

    public void loadRegion(){
        regionId.setCellValueFactory(new PropertyValueFactory<Region, Integer>("id"));
        regionName.setCellValueFactory(new PropertyValueFactory<Region, String>("name"));
        IdCity_ats.setCellValueFactory(new PropertyValueFactory<Region, Integer>("city_ats"));
        regionTableView.setItems(getRegion());
    }

    public ObservableList<Region> getRegion() {
        ObservableList<Region> regionList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Region> rList = session.
                createCriteria(Region.class).list();
        for (Region region : rList) {
            regionList.add(region);
        }
        session.clear();
        session.close();
        return regionList;
    }

    public void loadExemption(){
        exemptionId.setCellValueFactory(new PropertyValueFactory<Exemption, Integer>("id"));
        exemptionName.setCellValueFactory(new PropertyValueFactory<Exemption, String>("name"));
        exemptionPercent.setCellValueFactory(new PropertyValueFactory<Exemption, Integer>("percent"));
        exemptionTableView.setItems(getExemption());
    }

    public ObservableList<Exemption> getExemption() {
        ObservableList<Exemption> exemptionList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Exemption> eList = session.
                createCriteria(Exemption.class).list();
        for (Exemption exemption : eList) {
            exemptionList.add(exemption);
        }
        return exemptionList;
    }

    public void loadTariff(){
        tariffId.setCellValueFactory(new PropertyValueFactory<Tariff, Integer>("id"));
        tariffDay_night.setCellValueFactory(new PropertyValueFactory<Tariff, String>("day_night"));
        tariffType.setCellValueFactory(new PropertyValueFactory<Tariff, String>("type"));
        tariffPrice_minute.setCellValueFactory(new PropertyValueFactory<Tariff, Float>("price_minute"));
        tariffTableView.setItems(getTariff());
    }

    public ObservableList<Tariff> getTariff() {
        ObservableList<Tariff> tariffList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Tariff> tList = session.
                createCriteria(Tariff.class).list();
        for (Tariff tariff : tList) {
            tariffList.add(tariff);
        }
        return tariffList;
    }

    public void loadCall(){
        callId.setCellValueFactory(new PropertyValueFactory<Call, Integer>("id"));
        callId_con.setCellValueFactory(new PropertyValueFactory<Call, Integer>("connection"));
        call_start.setCellValueFactory(new PropertyValueFactory<Call, Date>("call_start"));
        call_stop.setCellValueFactory(new PropertyValueFactory<Call, Date>("call_stop"));
        callId_tariff.setCellValueFactory(new PropertyValueFactory<Call, Integer>("tariff"));
        call_duration.setCellValueFactory(new PropertyValueFactory<Call, String>("call_duration"));
        callSum.setCellValueFactory(new PropertyValueFactory<Call, Float>("sum"));
        callTableView.setItems(getCall());
    }

    public ObservableList<Call> getCall() {
        ObservableList<Call> callList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Call> cList = session.
                createCriteria(Call.class).list();
        for (Call call : cList) {
            callList.add(call);
        }
        return callList;
    }

    public void loadClient(){
        clientId.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        clientSurname.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
        clientName.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        clientMiddlename.setCellValueFactory(new PropertyValueFactory<Client, String>("middlename"));
        clientGender.setCellValueFactory(new PropertyValueFactory<Client, String>("gender"));
        clientAge.setCellValueFactory(new PropertyValueFactory<Client, Integer>("age"));
        clientIdExemption.setCellValueFactory(new PropertyValueFactory<Client, Integer>("exemption"));
        clientTableView.setItems(getClient());
    }

    public ObservableList<Client> getClient() {
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Client> cList = session.
                createCriteria(Client.class).list();
        for (Client client : cList) {
            clientList.add(client);
        }
        return clientList;
    }

    public void loadPayphone(){
        payphoneId.setCellValueFactory(new PropertyValueFactory<Payphone, Integer>("id"));
        payphoneId_house.setCellValueFactory(new PropertyValueFactory<Payphone, Integer>("house"));
        payphoneId_telephone_number.setCellValueFactory(new PropertyValueFactory<Payphone, Integer>("telephone_number"));
        payphoneTableView.setItems(getPayphone());
    }

    public ObservableList<Payphone> getPayphone() {
        ObservableList<Payphone> payphoneList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Payphone> pList = session.
                createCriteria(Payphone.class).list();
        for (Payphone payphone : pList) {
            payphoneList.add(payphone);
        }
        return payphoneList;
    }

    public void loadReceipt(){
        receiptId.setCellValueFactory(new PropertyValueFactory<Receipt, Integer>("id"));
        receiptId_con.setCellValueFactory(new PropertyValueFactory<Receipt, Integer>("connection"));
        receiptAccrual_date.setCellValueFactory(new PropertyValueFactory<Receipt, Date>("accrual_date"));
        receiptSum.setCellValueFactory(new PropertyValueFactory<Receipt, Float>("sum"));
        receiptStatus.setCellValueFactory(new PropertyValueFactory<Receipt, Boolean>("status"));
        receiptPayment_date.setCellValueFactory(new PropertyValueFactory<Receipt, Date>("payment_date"));
        receiptTableView.setItems(getReceipt());
    }

    public ObservableList<Receipt> getReceipt() {
        ObservableList<Receipt> receiptList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Receipt> rList = session.
                createCriteria(Receipt.class).list();
        for (Receipt receipt : rList) {
            receiptList.add(receipt);
        }
        return receiptList;
    }

    public void loadTelephone_number(){
        telephone_numberId.setCellValueFactory(new PropertyValueFactory<Telephone_number, Integer>("id"));
        telephone_numberNumber.setCellValueFactory(new PropertyValueFactory<Telephone_number, Integer>("number"));
        statusTelephone_number.setCellValueFactory(new PropertyValueFactory<Telephone_number, Boolean>("status"));
        type_atsTelephone_number.setCellValueFactory(new PropertyValueFactory<Telephone_number, Integer>("city_ats"));
        telephone_numberTableView.setItems(getTelephone_number());
    }

    public ObservableList<Telephone_number> getTelephone_number() {
        ObservableList<Telephone_number> telephone_numberList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Telephone_number> tList = session.
                createCriteria(Telephone_number.class).list();
        for (Telephone_number telephone_number : tList) {
            telephone_numberList.add(telephone_number);
        }
        return telephone_numberList;
    }

    public void loadQueue(){
        queueId.setCellValueFactory(new PropertyValueFactory<Queue, Integer>("id"));
        queueClientId.setCellValueFactory(new PropertyValueFactory<Queue, Integer>("client"));
        queueDate.setCellValueFactory(new PropertyValueFactory<Queue, Date>("queue_date"));
        queueExemption.setCellValueFactory(new PropertyValueFactory<Queue, Boolean>("exemption"));
        queueTableView.setItems(getQueue());
    }

    public ObservableList<Queue> getQueue() {
        ObservableList<Queue> queueList = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Queue> qList = session.
                createCriteria(Queue.class).list();
        for (Queue queue : qList) {
            queueList.add(queue);
        }
        return queueList;
    }

    public void loadRequest1(){
        request1IdAts.setCellValueFactory(new PropertyValueFactory<Request1, Integer>("request1IdAts"));
        request1Surname.setCellValueFactory(new PropertyValueFactory<Request1, String>("request1Surname"));
        request1Name.setCellValueFactory(new PropertyValueFactory<Request1, String>("request1Name"));
        request1Middlename.setCellValueFactory(new PropertyValueFactory<Request1, String>("request1Middlename"));
        request1TableView.setItems(getRequest1());
    }

    public ObservableList<Request1> getRequest1() {
        ObservableList<Request1> request1List = FXCollections.observableArrayList();
        Session session = HibernateUtil.
                getSessionFactory().getCurrentSession();
        Transaction tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        List<Request1> rList = session.
                createCriteria(Request1.class).list();
        for (Request1 request1 : rList) {
            request1List.add(request1);
        }
        return request1List;
    }

    public void load(){
        session = hs.createHibernateSession();
        if (session != null) {
            System.out.println("session is created");
            loadGts();
            loadAts();
            loadCity_Ats();
            loadRegion();
            loadStreet();
            loadHouse();
            loadApartment();
            loadConnection();
            loadTelephone_number();
            loadClient();
            loadExemption();
            loadReceipt();
            loadTariff();
            loadCall();
            loadQueue();
            loadPayphone();
        } else {
            System.out.println("session is not created");
        }
        session.close();
    }

    public void findDate(){
        JobDetail job = new JobDetail();
        job.setName("dummyJobName");
        job.setJobClass(FindDebtor.class);

        //configure the scheduler time
        SimpleTrigger trigger = new SimpleTrigger();
        trigger.setName("first");
        trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setRepeatInterval(300000);

        //schedule it
        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    public void initialize(URL location, ResourceBundle resources) {
        load();
        MainApp ma = new MainApp();
        findDate();

    }

    public void handleAdd_gts(ActionEvent event) throws IOException {
        System.out.println("Создание гтс");
        Stage window = new Stage();
        loadFXML("../actions/addGts.fxml",add_Gts,window);
        window.showAndWait();
        load();
    }

    public void handleDel_gts(ActionEvent event) {
        System.out.println("Удаление гтс");
        del_Gts.delete();
        load();
    }

    public void handleUpdate_gts(ActionEvent event){
        System.out.println("Редактирование гтс");
        Stage window = new Stage();
        Upgrade_gts controller = (Upgrade_gts) loadFXML("../actions/upgradeGts.fxml",upgrade_Gts,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_ats(ActionEvent event) throws IOException {
        System.out.println("Создание атс");
        Stage window = new Stage();
        loadFXML("../actions/addAts.fxml",add_Ats,window);
        window.showAndWait();
        load();
    }

    public void handleDel_ats(ActionEvent event) {
        System.out.println("Удаление aтс");
        del_Ats.delete();
        load();
    }

    public void handleUpdate_ats(ActionEvent event){
        System.out.println("Редактирование aтс");
        Stage window = new Stage();
        Upgrade_ats controller = (Upgrade_ats) loadFXML("../actions/upgradeAts.fxml",upgrade_Ats,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_city_ats(ActionEvent event) throws IOException {
        System.out.println("Создание типа атс");
        Stage window = new Stage();
        loadFXML("../actions/addCity_ats.fxml",add_City_ats,window);
        window.showAndWait();
        load();
    }

    public void handleUpdate_city_ats(ActionEvent event){
        System.out.println("Редактирование типа aтс");
        Stage window = new Stage();
        Upgrade_city_ats controller = (Upgrade_city_ats) loadFXML("../actions/upgradeCity_ats.fxml",upgrade_City_ats,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_region(ActionEvent event) throws IOException {
        System.out.println("Создание района");
        Stage window = new Stage();
        loadFXML("../actions/addRegion.fxml",add_Region,window);
        window.showAndWait();
        load();
    }

    public void handleDel_region(ActionEvent event) {
        System.out.println("Удаление района");
        del_Region.delete();
        load();
    }

    public void handleUpdate_region(ActionEvent event){
        System.out.println("Редактирование района");
        Stage window = new Stage();
        Upgrade_region controller = (Upgrade_region) loadFXML("../actions/upgradeRegion.fxml",upgrade_Region,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_street(ActionEvent event) throws IOException {
        System.out.println("Создание улицы");
        Stage window = new Stage();
        loadFXML("../actions/addStreet.fxml",add_Street,window);
        window.showAndWait();
        load();
    }

    public void handleDel_street(ActionEvent event) {
        System.out.println("Удаление улицы");
        del_Street.delete();
        load();
    }

    public void handleUpdate_street(ActionEvent event){
        System.out.println("Редактирование улицы");
        Stage window = new Stage();
        Upgrade_street controller = (Upgrade_street) loadFXML("../actions/upgradeStreet.fxml",upgrade_Street,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_house(ActionEvent event) throws IOException {
        System.out.println("Создание дома");
        Stage window = new Stage();
        loadFXML("../actions/addHouse.fxml",add_House,window);
        window.showAndWait();
        load();
    }

    public void handleDel_house(ActionEvent event) {
        System.out.println("Удаление дома");
        del_House.delete();
        load();
    }

    public void handleUpdate_house(ActionEvent event){
        System.out.println("Редактирование дома");
        Stage window = new Stage();
        Upgrade_house controller = (Upgrade_house) loadFXML("../actions/upgradeHouse.fxml",upgrade_House,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_apartment(ActionEvent event) throws IOException {
        System.out.println("Создание квартиры");
        Stage window = new Stage();
        loadFXML("../actions/addApartment.fxml",add_Apartment,window);
        window.showAndWait();
        load();
    }

    public void handleDel_apartment(ActionEvent event) {
        System.out.println("Удаление квартиры");
        del_Apartment.delete();
        load();
    }

    public void handleUpdate_apartment(ActionEvent event){
        System.out.println("Редактирование квартиры");
        Stage window = new Stage();
        Upgrade_apartment controller = (Upgrade_apartment) loadFXML("../actions/upgradeApartment.fxml",upgrade_Apartment,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_connection(ActionEvent event) throws IOException {
        System.out.println("Создание договора");
        Stage window = new Stage();
        loadFXML("../actions/addConnection.fxml",add_Connection,window);
        window.showAndWait();
        load();
    }

    public void handleDel_connection(ActionEvent event) {
        System.out.println("Удаление договора");
        del_Connection.delete();
        load();
    }

    public void handleUpdate_connection(ActionEvent event){
        System.out.println("Редактирование договора");
        Stage window = new Stage();
        Upgrade_connection controller = (Upgrade_connection) loadFXML("../actions/upgradeConnection.fxml",upgrade_Connection,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_exemption(ActionEvent event) throws IOException {
        System.out.println("Создание льготы");
        Stage window = new Stage();
        loadFXML("../actions/addExemption.fxml",add_Exemption,window);
        window.showAndWait();
        load();
    }

    public void handleDel_exemption(ActionEvent event) {
        System.out.println("Удаление льготы");
        del_Exemption.delete();
        load();
    }

    public void handleUpdate_exemption(ActionEvent event){
        System.out.println("Редактирование льготы");
        Stage window = new Stage();
        Upgrade_exemption controller = (Upgrade_exemption) loadFXML("../actions/upgradeExemption.fxml",upgrade_Exemption,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_client(ActionEvent event) throws IOException {
        System.out.println("Создание клиента");
        Stage window = new Stage();
        loadFXML("../actions/addClient.fxml",add_Client,window);
        window.showAndWait();
        load();
    }

    public void handleDel_client(ActionEvent event) {
        System.out.println("Удаление клиента");
        del_Client.delete();
        load();
    }

    public void handleUpdate_client(ActionEvent event){
        System.out.println("Редактирование клиента");
        Stage window = new Stage();
        Upgrade_client controller = (Upgrade_client) loadFXML("../actions/upgradeClient.fxml",upgrade_Client,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_telephone_number(ActionEvent event) throws IOException {
        System.out.println("Создание номера");
        Stage window = new Stage();
        loadFXML("../actions/addTelephone_number.fxml",add_Telephone_number,window);
        window.showAndWait();
        load();
    }

    public void handleDel_telephone_number(ActionEvent event) {
        System.out.println("Удаление номера");
        del_Telephone_number.delete();
        load();
    }

    public void handleUpdate_telephone_number(ActionEvent event){
        System.out.println("Редактирование номера");
        Stage window = new Stage();
        Upgrade_telephone_number controller = (Upgrade_telephone_number) loadFXML(
                "../actions/upgradeTelephone_number.fxml",upgrade_Telephone_number,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_tariff(ActionEvent event) throws IOException {
        System.out.println("Создание тарифа");
        Stage window = new Stage();
        loadFXML("../actions/addTariff.fxml",add_Tariff,window);
        window.showAndWait();
        load();
    }

    public void handleDel_tariff(ActionEvent event) {
        System.out.println("Удаление тарифа");
        del_Tariff.delete();
        load();
    }

    public void handleUpdate_tariff(ActionEvent event){
        System.out.println("Редактирование тарифа");
        Stage window = new Stage();
        Upgrade_tariff controller = (Upgrade_tariff) loadFXML(
                "../actions/upgradeTariff.fxml",upgrade_Tariff,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_call(ActionEvent event) {
        System.out.println("Создание звонка");
        Stage window = new Stage();
        loadFXML("../actions/addCall.fxml",add_Call,window);
        window.showAndWait();
        load();
    }

    public void handleDel_call(ActionEvent event) {
        System.out.println("Удаление звонка");
        del_Call.delete();
        load();
    }

    public void handleUpdate_call(ActionEvent event){
        System.out.println("Редактирование звонка");
        Stage window = new Stage();
        Upgrade_call controller = (Upgrade_call) loadFXML(
                "../actions/upgradeCall.fxml",upgrade_Call,window);
        controller.setController(this);
        window.showAndWait();
        load();
        callTableView.refresh();
    }

    public void handleAdd_receipt(ActionEvent event) {
        System.out.println("Создание квитанции");
        Stage window = new Stage();
        loadFXML("../actions/addReceipt.fxml",add_Receipt,window);
        window.showAndWait();
        load();
    }

    public void handleDel_receipt(ActionEvent event) {
        System.out.println("Удаление квитанции");
        del_Receipt.delete();
        load();
    }

    public void handleUpdate_receipt(ActionEvent event){
        System.out.println("Редактирование квитанции");
        Stage window = new Stage();
        Upgrade_receipt controller = (Upgrade_receipt) loadFXML(
                "../actions/upgradeReceipt.fxml",upgrade_Receipt,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_queue(ActionEvent event) {
        System.out.println("Создание очереди");
        Stage window = new Stage();
        loadFXML("../actions/addQueue.fxml",add_Queue,window);
        window.showAndWait();
        load();
    }

    public void handleDel_queue(ActionEvent event) {
        System.out.println("Удаление очереди");
        del_Queue.delete();
        load();
    }

    public void handleUpdate_queue(ActionEvent event){
        System.out.println("Редактирование очереди");
        Stage window = new Stage();
        Upgrade_queue controller = (Upgrade_queue) loadFXML(
                "../actions/upgradeQueue.fxml",upgrade_Queue,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleAdd_payphone(ActionEvent event) {
        System.out.println("Создание таксофона");
        Stage window = new Stage();
        loadFXML("../actions/addPayphone.fxml",add_Payphone,window);
        window.showAndWait();
        load();
    }

    public void handleDel_payphone(ActionEvent event) {
        System.out.println("Удаление таксофона");
        del_Payphone.delete();
        load();
    }

    public void handleUpdate_payphone(ActionEvent event){
        System.out.println("Редактирование таксофона");
        Stage window = new Stage();
        Upgrade_payphone controller = (Upgrade_payphone) loadFXML(
                "../actions/upgradePayphone.fxml",upgrade_Payphone,window);
        controller.setController(this);
        window.showAndWait();
        load();
    }

    public void handleFind_request1(ActionEvent event){
        System.out.println("Запрос 1");
        Stage window = new Stage();
        FindRequest1 fr = (FindRequest1)loadFXML("../actions/findRequest1.fxml",findRequest1,window);
        fr.setController(this);
        window.showAndWait();
        //loadRequest1();
    }

    public void handleFind_request2(ActionEvent event){
        System.out.println("Запрос 2");
        Stage window = new Stage();
        FindRequest2 fr = (FindRequest2)loadFXML("../actions/findRequest2.fxml",findRequest2,window);
        fr.setController(this);
        window.showAndWait();
    }

    public void handleFind_request3(ActionEvent event){
        System.out.println("Запрос 3");
        Stage window = new Stage();
        FindRequest3 fr = (FindRequest3)loadFXML("../actions/findRequest3.fxml",findRequest3,window);
        fr.setController(this);
        window.showAndWait();
    }

    public void handleFind(ActionEvent event){
        System.out.println("Поиск");
        аfind.find();
    }

    private Actions loadFXML(String fxml, Actions controller,Stage window){
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Actions contr = loader.getController();
            root = loader.load();
            Parent content = root;
            Scene scene = new Scene(content);
            scene.getStylesheets().add(MainApp.class.getResource("../bootstrap3.css").toExternalForm());
            window.setResizable(false);
            window.setScene(scene);
            return loader.getController();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}

