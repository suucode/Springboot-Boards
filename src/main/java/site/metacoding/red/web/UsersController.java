package site.metacoding.red.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;

@RequiredArgsConstructor //DI
@Controller
public class UsersController {
	
	private final HttpSession session;
	private final UsersDao usersDao; //composition
	
	@GetMapping("/logout")
	public String logout() { //코드로 로그아웃하는것은 세션값을 날린다는것..!
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/login") //로그인만 select지만 post -> select는 원래 get
	public String login(LoginDto loginDto) {
		Users usersPS = usersDao.login(loginDto);
		if(usersPS != null) {//인증이 된 경우 -> 세션이 필요
			session.setAttribute("principal", usersPS);//principal = 인증된유저
			return "redirect:/";
		}else {//인증 안된경우
			return "redirect:/loginForm";
		}
	}
	
	@PostMapping("/join")
	public String join(JoinDto joinDto) {
		usersDao.insert(joinDto);
		return "redirect:/loginForm";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "users/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";
	}
}
