package com.awrank.web.model.utils.user;

import com.awrank.web.model.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * User: a_polyakov
 */
@Component
public class CurrentUserUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;
    private static final ThreadLocal<User> CURRENT_USER = new ThreadLocal<User>();
    private static User anonymous = null;

    /**
     * get user linked thread or ANONYMOUS
     *
     * @return
     */
    public static User getCurrentUser() {
        User user = CURRENT_USER.get();
        if (user == null && applicationContext != null) {
            if (anonymous == null) {
                // TODO
//                UserDao userDao = applicationContext.getBean(UserDao.class);
//                if (userDao != null) {
//                    anonymous = userDao.select("anonymous@awrank.com");
//                }
            }
            user = anonymous;
        }
        return user;
    }

    /**
     * link user in thread
     *
     * @param user
     */
    public static void setCurrentUser(User user) {
        CURRENT_USER.set(user);
    }

    /**
     * unlink user of thread
     * необходимо вызывать в конце обработки запроса так как используется пул потоков
     */
    public static void removeCurrentUser() {
        CURRENT_USER.remove();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CurrentUserUtils.applicationContext = applicationContext;
    }
}
