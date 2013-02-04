package com.awrank.web.backend.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * User: a_polyakov
 */
@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        //TODO
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //TODO
    }
}
