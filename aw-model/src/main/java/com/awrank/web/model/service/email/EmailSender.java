package com.awrank.web.model.service.email;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Interface provides a method for email sending based on templates.
 *
 * @author Olga Korokhina
 */
@Service
public interface EmailSender {

    /**
     * Sends email using given {@code template} and {@code params} parameters.
     * @param template todo
     * @param params todo
     * @throws Exception todo: no personal exception?
     */
    public void send(String template, Map<String, Object> params) throws Exception;

}
