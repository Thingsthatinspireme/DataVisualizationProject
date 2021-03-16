package web.controls;

import java.util.Map;


import javax.servlet.http.HttpSession;

import web.controls.Component;
import web.dao.MemberDao;
import web.dao.MysqlMemberDao;
import web.vo.Member;



public class LoginController implements Controller {


	@Override
	public String execute(Map<String, Object> model) throws Exception {
	
			MysqlMemberDao memberDao = (MysqlMemberDao)model.get("memberDao");
			Member loginInfo = (Member)model.get("loginInfo");
			
			Member member = memberDao.exist(loginInfo.getId(),
											loginInfo.getPwd());
			
			if(member != null) {
//				String id = member.getId();
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("member", member);
				model.put("member",member);
				
				Member user = (Member) session.getAttribute("member");
				session.setAttribute("id", user.getId());
					
				System.out.println(session.getAttribute("id"));
//				System.out.println((String)model.get("id"));
//				model.put("session",session);
				return "redirect:memberInfo.jsp";
				
			}else {
				return "/index.jsp";
			}
		}
	}

	

