package com.awrank.web.model.service.sharing;

import java.util.Properties;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Olga Korokhina
 *
 * Interface for "Share To" services - including mailing
 */
@Service
public interface ShareToService {
	 
	  public void share(Object session, Object message)  throws Exception;
	  
	  public Object getAuthenticatedSession(Properties properties, String username, String password);

}
