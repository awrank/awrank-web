package com.awrank.web.backend.controller.auth;

import com.awrank.web.model.domain.User;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/rest")
public class TestRestController {

	private List<User> users = new ArrayList<User>();

	public class TestResource implements Serializable {
		public String var1;

		@JsonIgnore
		public String var2;

		public TestResource() {
		}

		public TestResource(String var1, String var2) {
			this.var1 = var1;
			this.var2 = var2;
		}
	}


	@RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	public
	@ResponseBody
	List<TestResource> search(@RequestParam(value = "query", required = true) String query) {
		List<TestResource> result = new ArrayList<TestResource>();
		result.add(new TestResource("1", "hidden"));
		result.add(new TestResource("2", "hidden"));
		return result;
	}

	@RequestMapping(value = "/postjson", method = RequestMethod.POST,
			headers = "Accept=application/json")
	public
	@ResponseBody
	List<User> post(@RequestBody User user) {
		users.add(user);
		return this.users;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}", headers = "Accept=application/json", produces = "application/json")
	public
	@ResponseBody
	User getUser(@PathVariable String id) {
		User user = new User();
		user.setId(Long.valueOf(id));
		user.setFirstName("Andrew_" + id);
		user.setLastName("Stoyaltsev_" + id);
		user.setEmail("andrew.v.stoyaltsev@gmail.com");
		user.setBirthday(new DateTime(1985, 6, 15, 5, 30).toDate());
		return user;
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "/emps", headers = "Accept=application/json")
	public
	@ResponseBody
	List<User> EmployeeListinggetAllEmp() {
		List<Employee> employees = employeeDS.getAll();
		EmployeeListinglist = new EmployeeList(employees);
		return list;
	}*/

}

