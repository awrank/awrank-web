package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.UserDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserEmailActivationService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPOJO;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link UserService} interface.
 *
 * @author Olga Korokhina
 */
@Service
//@PropertySource("/WEB-INF/properties/application.properties")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EntryPointService entryPointService;

    @Autowired
    private UserEmailActivationService userEmailActivationService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;


    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")//<global-method-security jsr250-annotations="enabled" /> in config
    //@PreAuthorize("isAnonymous()")// <global-method-security pre-post-annotations="enabled" /> in config
    @Transactional
    @Override
    public void register(UserRegistrationFormPOJO form, HttpServletRequest request) throws UserNotCreatedException, EntryPointNotCreatedException, UserActivationEmailNotSetException {

        //--------------------- create user ---------------------------

        User user = new User();

        user.setApiKey(form.getApiKey());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        //user.setLanguage(form.getLanguage());

        add(user);

        //---------------- create entrance point for him -----------------

        EntryPoint entryPoint = new EntryPoint();

        entryPoint.setUser(user);

        entryPoint.setUid(user.getEmail());
        entryPoint.setPassword(form.getPassword());
        entryPoint.setType(EntryPointType.EMAIL.EMAIL);//on registration we demand User to have email

        entryPointService.add(entryPoint);

        //---------------- sending verification email --------------------

        String key;
        try {

            key = SMTPAuthenticator.getHashed256(form.getEmail() + "." + form.getPassword() + "." + form.getUserLocalAddr() + "." + form.getUserLocalAddr());

        } catch (Exception e1) {

            e1.printStackTrace();
            throw new UserNotCreatedException();
        }

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("localAddr", form.getUserLocalAddr());
        params.put("remoteAddr", form.getUserRemoteAddr());
        params.put("testactivation_email", user.getEmail());
        params.put("testactivation_password", form.getPassword());

        userEmailActivationService.send(params);

//-------------- store to db information about verification email was sent -------------------------------------------

        UserEmailActivation userEmailActivation = new UserEmailActivation();

        userEmailActivation.setCode(key);
        userEmailActivation.setUser(user);
        userEmailActivation.setEmail(user.getEmail());
        userEmailActivation.setIpAddress(form.getUserRemoteAddr());//Check later if we need remote or local IP here

        userEmailActivationService.save(userEmailActivation);

//-------------- here we need save a role for user -------------	

        UserRole role = new UserRole();
        role.setUser(user);
        role.setRole(Role.USER);

        userRoleService.save(role);

//---------- we need some authorization for register user + he is logged in right after it -------
        //----- we need to do it here manually-----

		/*
    	GrantedAuthority[] grantedAuthorities = new GrantedAuthority[] { new GrantedAuthorityImpl("ROLE_USER") };

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());//, grantedAuthorities);

        // generate session if one doesn't exist
        request.getSession();
        
        token.setDetails(new WebAuthenticationDetails(request));
       
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        */

        //-------------------------------

    }


    /* (non-Javadoc)
     * @see com.awrank.web.model.service.UserService#add(com.awrank.web.model.domain.User)
     */
    @Override
    //@Transactional
    public void add(User user) throws UserNotCreatedException {

        try { //can be thrown javax.persistence.PersistenceException etc.

            //Session session = sessionFactory.getCurrentSession();
            //session.save(user);
            userDao.save(user);

            //session.flush();

        } catch (Exception e) {

            throw new UserNotCreatedException();

        }

    }

    /* (non-Javadoc)
     * @see com.awrank.web.model.service.UserService#delete(com.awrank.web.model.domain.User)
     */
    @Override
    @Transactional
    public void delete(User user) throws UserNotDeletedException {

        userDao.delete(user);

    }

    /* (non-Javadoc)
     * @see com.awrank.web.model.service.UserService#save(com.awrank.web.model.domain.User)
     */
    @Override
    public void save(User user) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.awrank.web.model.service.UserService#findById(java.lang.Integer)
     */
    @Override
    public List<User> findById(Long id) {
        // TODO Auto-generated method stub
        return new ArrayList<User>();
    }

    /* (non-Javadoc)
     * @see com.awrank.web.model.service.UserService#findByEmail(java.lang.String)
     */
    @Override
    public List<User> findByEmail(String email) {
        // TODO Auto-generated method stub
        return new ArrayList<User>();
    }

    @Override
    public List<User> findByAPIKey(String key) {
        // TODO Auto-generated method stub
        return new ArrayList<User>();
    }

}