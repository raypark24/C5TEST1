package sesoc.global.test;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.CookieGenerator;

import sesoc.global.test.dao.CustomerRepository;
import sesoc.global.test.vo.Customer;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerRepository repo;
	

	@RequestMapping(value="/join1", method=RequestMethod.POST)
	public String join(Customer customer){
		System.out.println("customer/join");
		repo.insertCustomer(customer);
		return "index";
	}
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login(){
		System.out.println("customer/ get-login");
		return "customer/loginForm";
	}
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(String custid,String password,Model model,
			HttpSession session,HttpServletResponse response){
		Customer customer = repo.findCustomer(custid,password);
		
		String message="";
		if(customer == null){
			message="로그인을 할 수 없습니다.";
			return "message";
		}
		session.setAttribute("custid", custid);
		session.setAttribute("name", customer.getName());
		message="로그인 되었습니다.";
		System.out.println(message);
		model.addAttribute("message",message);
		
		return "message";
	}
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insert(Customer customer, Model model) {
		System.out.println("customer / post-insert");
		int result = repo.insertCustomer(customer);
		String message="";
		if(result==0){
			message="회원가입을 할 수 없습니다.";
		}
		else{
			message="회원가입이 완료되었습니다.";
		}
		System.out.println(message);
		model.addAttribute("message",message);
		
		return "message";
		// redirect 시
		// return "redirect:home";
	}
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(Model model,HttpSession session){
		System.out.println("customer/ get-logout");
		session.invalidate();
		return "redirect:/";
	}
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(Model model,HttpSession session){
		return "redirect:/";
	}
}






