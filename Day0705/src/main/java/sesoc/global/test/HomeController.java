package sesoc.global.test;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sesoc.global.test.dao.CustomerRepository;
import sesoc.global.test.vo.Customer;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	CustomerRepository repo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("home - /");
		return "index";
		// redirect 시
		// return "redirect:home";
		
	}
	@RequestMapping(value = "join", method = RequestMethod.GET)
	public String join(Locale locale, Model model) {
		System.out.println("home - join");
		
		return "customer/joinForm";
		// redirect 시
		// return "redirect:home";
	}

	
	
	
	@RequestMapping(value="/input", method=RequestMethod.POST)
	public @ResponseBody Friend input(Friend friend){
		logger.info("데이터수집" + friend);
		
		
		return friend;
	}
	
	@RequestMapping(value="input2", method=RequestMethod.POST)
	public String input2(@ModelAttribute("friend") Friend friend,@ModelAttribute("birth") String birth){
		logger.info("데이터수집" + friend);
		
		return "home";
	}
}
	
	//@Controller - 클래스 위에
	//@Service - 클래스 위에
	//@Repository - 클래스 위에
	//@Component - 클래스 위에
	//클래스 위에 붙으면 Bean이 됨. -> ApplicationContext가 관리하는 객체
	
	// 이상 위의 태그가 아니고 bean으로 관리하고 싶을 시 bean 태크로 관리.(root-context에서)
	
