package com.awrank.web.tools.init.user;

/**
 * User: a_polyakov
 */
public class InitUser {
//    public static void main(String args[]) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AwrankEMF");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//
//        UserDaoImpl userDao = new UserDaoImpl();
//        userDao.setEntityManager(em);
//
//        EntryPointDao entryPointDao = new EntryPointDaoImpl();
//        entryPointDao.setEntityManager(em);
//
//        initAnonymous(userDao);
//
//        initAdmin(userDao, entryPointDao);
//
//        em.getTransaction().commit();
//        em.close();
//    }
//
//    private static void initAnonymous(UserDaoImpl userDao) {
//        User user = userDao.select("anonymous@awrank.com");
//        if (user == null) {
//            user = new User();
//            user.setApiKey("ANONYMOUS");
//            user.setRefUser(null);
//            user.setEmail("anonymous@awrank.com");
//            user.setSkype(null);
//            user.setFirstName("anonymous");
//            user.setLastName("anonymous");
//            user.setBirthday(null);
//            user.setSecretQuestionDicCode(null);
//            user.setSecretAnswer(null);
//            user.setLanguage(ELanguage.RU);
//            user.setAuthorizationFailsCount(0);
//            user.setAuthorizationFailsLastDate(null);
//            user.setBanStartedDate(null);
//            user.setCreatedDate(new Date());
//            user.setCreatedBy(user);
//            user.setLastModifiedDate(user.getCreatedDate());
//            user.setLastModifiedBy(user);
//            userDao.persist(user);
//        }
//    }
//
//    private static void initAdmin(UserDaoImpl userDao, EntryPointDao entryPointDao) {
//        User user = userDao.select("admin@awrank.com");
//        if (user == null) {
//            user = new User();
//            user.setApiKey("ADMIN");
//            user.setRefUser(null);
//            user.setEmail("admin@awrank.com");
//            user.setSkype(null);
//            user.setFirstName("admin");
//            user.setLastName("admin");
//            user.setBirthday(null);
//            user.setSecretQuestionDicCode(null);
//            user.setSecretAnswer(null);
//            user.setLanguage(ELanguage.RU);
//            user.setAuthorizationFailsCount(0);
//            user.setAuthorizationFailsLastDate(null);
//            user.setBanStartedDate(null);
//            user.setCreatedDate(new Date());
//            user.setCreatedBy(user);
//            user.setLastModifiedDate(user.getCreatedDate());
//            user.setLastModifiedBy(user);
//            userDao.persist(user);
//
//            CurrentUserUtils.setCurrentUser(user);
//
//            EntryPoint entryPoint = new EntryPoint();
//            entryPoint.setUser(user);
//            entryPoint.setAuthenticationMethod(EAuthenticationMethod.LOGIN);
//            entryPoint.setUid("admin");
//            entryPoint.setPassword(PasswordUtils.hashPassword("1"));
//            entryPoint.setVerifiedDate(DateConst.SMALL_DATE);
//            entryPointDao.save(entryPoint);
//        }
//    }
}
