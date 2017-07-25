package sesoc.global.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 세션 정보를 얻어옴
		HttpSession session = request.getSession();
		String custid = (String)session.getAttribute("custid");
		
//		로그인 하지 않은 경우
		if(custid == null){
			// 로그인화면으로 리다이렉트
			String ctx = request.getContextPath();
			response.sendRedirect("login");
			return false;
		}
		
		return super.preHandle(request, response, handler);
	}
}
