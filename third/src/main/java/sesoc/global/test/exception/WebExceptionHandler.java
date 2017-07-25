package sesoc.global.test.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {
	private static final Logger logger
	= LoggerFactory.getLogger(WebExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public String errorHandler(Exception e,Model model){
		model.addAttribute("errormsg",e.getMessage());
		logger.debug("error ===>", e.getMessage());
		return "/error";
	}
}
