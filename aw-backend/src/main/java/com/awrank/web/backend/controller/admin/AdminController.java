package com.awrank.web.backend.controller.admin;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import com.awrank.web.model.service.DiaryService;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.email.EmailSenderSendGridImpl;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

/**
 * todo: description
 *
 * @author Olga Korokhina
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends AbstractController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
	@Qualifier("diaryServiceImpl")
	private DiaryService diaryService;

	@Autowired
	/*todo: access modifier?*/ EmailSenderSendGridImpl sendGridEmailSender;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public
	@ResponseBody()
	String printWelcome(ModelMap model, Principal principal) {
		String name = principal.getName();
		List<GrantedAuthority> authorities =
				(List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) principal).getAuthorities();

		model.addAttribute("username", name);
		model.addAttribute("authorities", authorities);

		return "hello, admin!";
	}

	@RequestMapping(value = "/userlist", method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody()
	List<User> getAll(ModelMap model) {
		List<User> allUsers = userService.getAll();
		model.addAttribute("result", allUsers);
		return allUsers;
	}

	@RequestMapping(value = "/userListDT", method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody()
	Map<String, Object> getAllUsers(ModelMap model) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<User> allUsers = userService.getAllUsers();
		response.put("result", "ok");
		response.put("sEcho", 1); // param from request?
		response.put("iTotalRecords", allUsers.size());
		response.put("iTotalDisplayRecords", 2);

		Object[][] aData = new Object[allUsers.size()][];
		for (int i = 0; i < allUsers.size(); i++) {
			aData[i] = allUsers.get(i).toArray();
		}
		response.put("aaData", aData);
		return response;
	}

	/**
	 * List of user with pagination
	 *
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/userlistpage", method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody()
	Page<User> getAllWithPage(ModelMap model, @PageableDefaults(pageNumber = 0, value = 30) Pageable pageable) {
		Page<User> allUsers = userService.getPage(pageable);
		model.addAttribute("result", allUsers);
		return allUsers;
	}

	/**
	 * Blocking one user by email in form.getEmail()
	 *
	 * @param form
	 * @param principal
	 * @return
	 */
	@RequestMapping(
			value = "/blockuserbyemail",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	User blockUserByEmail(@ModelAttribute UserRegistrationFormPojo form, Principal principal) {
		if (principal == null) {
			return null;
		}

		User user = userService.findOneByEmail(form.getEmail());
		if (user != null) {
			userService.blockUser(user, principal);
		}

		return user;
	}

	/**
	 * Blocking one user by id
	 *
	 * @param form
	 * @param principal
	 * @return
	 */
	@RequestMapping(
			value = "/blockuserbyid",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	User blockUserByID(@ModelAttribute UserRegistrationFormPojo form, Principal principal) {
		if (principal == null) {
			return null;
		}

		User user = userService.findOne(form.getId());
		if (user != null) {
			userService.blockUser(user, principal);
		}

		return user;
	}

	/**
	 * Blocking one user by email in form.getEmail()
	 *
	 * @param form
	 * @param principal
	 * @return
	 */
	@RequestMapping(
			value = "/unblockuserbyemail",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	User unblockUserByEmail(@ModelAttribute UserRegistrationFormPojo form, Principal principal) {
		if (principal == null) {
			return null;
		}

		User user = userService.findOneByEmail(form.getEmail());
		if (user != null) {
			userService.unblockUser(user, principal);
		}

		return user;
	}

	/**
	 * Blocking one user by id
	 *
	 * @param form
	 * @param principal
	 * @return
	 */
	@RequestMapping(
			value = "/unblockuserbyid",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	User unblockUserByID(@ModelAttribute UserRegistrationFormPojo form, Principal principal) {
		if (principal == null) {
			return null;
		}

		User user = userService.findOne(form.getId());
		if (user != null) {
			userService.unblockUser(user, principal);
		}

		return user;
	}

	/**
	 * Finding one user by email in form.getEmail()
	 *
	 * @param form
	 * @param request
	 * @return
	 */
	@RequestMapping(
			value = "/user",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	User getUser(@ModelAttribute UserRegistrationFormPojo form, HttpServletRequest request) {
		return userService.findOneByEmail(form.getEmail());
	}

	/**
	 * Get users ever logged in from given IP
	 *
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(
			value = "/ip",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	List<User> getUserByIP(@ModelAttribute UserRegistrationFormPojo form, ModelMap model) {
		// here fetch from entry_history and get list of users ever came from given IP
		ArrayList<User> list = new ArrayList<User>();
		String ip = String.valueOf(form.getIp());
		List<EntryHistory> entryHistoryList = entryHistoryService.findByIP(ip);

		for (EntryHistory entry : entryHistoryList) {
			//Hibernate.initialize(entry.getUser());
			Long id = ((DatedAbstractAuditable) entry.getUser()).getId();
			if (id != null) {
				User user = userService.findOne(id);
				list.add(user);
			}
		}

		model.addAttribute("result", list);
		return list;
	}

	/**
	 * Get all entry points for user with given email
	 *
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(
			value = "/userentryhistory",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	List<EntryHistory> getEntryHistoryByUser(@ModelAttribute UserRegistrationFormPojo form, ModelMap model) {

		String email = String.valueOf(form.getEmail());
		User user = userService.findOneByEmail(email);
		List<EntryHistory> entryHistoryList = entryHistoryService.findAllByUser(user);

		model.addAttribute("result", entryHistoryList);
		return entryHistoryList;
	}

	@RequestMapping(value = "/allEntryHistoryDT", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody()
	public Map<String, Object> getAllEntryHistory(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", "ok");
		response.put("sEcho", request.getParameter("sEcho"));

		int iDisplayStart = Integer.parseInt(request.getParameter("iDisplayStart"));
		int iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));

		//Sort sort = new Sort(Sort.Direction.ASC, "");
		Pageable pageable = new PageRequest(iDisplayStart, iDisplayLength);

		Page<EntryHistory> page = null;
		String token = (request.getParameter("sSearch"));
		String criteria = request.getParameter("sSearchCriteria");

		if (StringUtils.hasLength(token) /*&& StringUtils.hasLength(criteria)*/) {
			/*if (criteria.equals("btnUser")) {
				page = entryHistoryService.pFindByUser(pageable);
			} else if (criteria.equals("btnIPAddress")) {
				page = entryHistoryService.pFindByIP(token, pageable);
			} else if (criteria.equals("btnSessionID")) {
				page = entryHistoryService.pFindBySessionId(token, pageable);
			}
			*/
			page = entryHistoryService.pFindBySessionId(token, pageable);
		}
		if (page == null) {
			page = entryHistoryService.pFindAll(pageable);
		}

		response.put("iTotalRecords", page.getTotalElements());
		response.put("iTotalDisplayRecords", page.getNumberOfElements());

		Iterator<EntryHistory> i = page.iterator();
		List<Object[]> aaData = new ArrayList<Object[]>();
		while(i.hasNext()) {
			aaData.add(i.next().toArray());
		}
		response.put("aaData", aaData.toArray());

		return response;
	}

	/**
	 * Get all access IPs for user with given email
	 *
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(
			value = "/useriphistory",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	List<String> getIPByUser(@ModelAttribute UserRegistrationFormPojo form, ModelMap model) {

		String email = String.valueOf(form.getEmail());
		User user = userService.findOneByEmail(email);
		List<String> ipList = entryHistoryService.findAllIPByUser(user);

		model.addAttribute("result", ipList);
		return ipList;
	}
}
