package com.awrank.web.tools.init.user;

import com.awrank.web.model.domain.UserOrder;
import com.awrank.web.model.utils.select.SelectUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 *
 */
public class TestOrderWrapper {
    public static void main(String args[]) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AwrankEMF");
        EntityManager em = emf.createEntityManager();

//        em.getTransaction().begin();
//        Tariff tariff=new Tariff();
//        tariff.setNameDicCode("TARIFF_NAME_01");
//        tariff.setDescriptionDicCode("TARIFF_DESC_01");
//        tariff.setLengthInDays(30);
//        tariff.setCountDailyRequest(0);
//        tariff.setCountMonthlyRequest(0);
//        tariff.setService(false);
//        tariff.setTariffType(ETariffType.M);
//        em.persist(tariff);
//
//        TariffSettings tariffSettings=new TariffSettings();
//        tariffSettings.setTariff(tariff);
//        tariffSettings.setPrice(BigDecimal.TEN);
//        tariffSettings.setDiscount(BigDecimal.ZERO);
//        tariffSettings.setStartedDate(new Date());
//        em.persist(tariffSettings);
//
//        User user=new User();
//        user.setApiKey("USER_01");
//        user.setRefUser(null);
//        user.setEmail("email@mail.com");
//        user.setSkype(null);
//        user.setFirstName("firstName");
//        user.setLastName("lastName");
//        user.setBirthday(null);
//        user.setSecretAnswer("not");
//        user.setLanguage(ELanguage.RU);
//        user.setAuthorizationFailsCount(0);
//        user.setAuthorizationFailsLastDate(null);
//        user.setBanStartedDate(null);
//        em.persist(user);
//
//        UserOrder userOrder1=new UserOrder();
//        userOrder1.setUser(user);
//        userOrder1.setTariffSettings(tariffSettings);
//        userOrder1.setComplete(true);
//        userOrder1.setRefUser(null);
//        userOrder1.setGracePeriod(14);
//        userOrder1.setPayedDate(new Date());
//        em.persist(userOrder1);
//
//        UserOrder userOrder2=new UserOrder();
//        userOrder2.setUser(user);
//        userOrder2.setTariffSettings(tariffSettings);
//        userOrder2.setComplete(false);
//        userOrder2.setRefUser(null);
//        userOrder2.setGracePeriod(14);
//        userOrder2.setPayedDate(null);
//        em.persist(userOrder2);
//
//        em.getTransaction().commit();


//        List<OrderWrapper> list = SelectUtils.getWrapperList(em, OrderWrapper.class, " order by o." + UserOrder.H_ID, 0, 0);
//        for (OrderWrapper item : list) {
//            System.out.println(item);
//        }
        em.close();
    }
}
