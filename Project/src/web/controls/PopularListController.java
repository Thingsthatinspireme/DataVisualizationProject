package web.controls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import web.dao.BoardDao;
import web.vo.Board;

public class PopularListController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
		BoardDao boardDao = (BoardDao)model.get("boardDao");
		List<Board> popular = boardDao.selectPopularList();
		model.put("popularInfo", popular);
		List<Board> notice = boardDao.selectNotice();
		model.put("noticeInfo", notice);
//		Board board = (Board)model.get("popularInfo");
//		model.put("popularInfo", boardDao.selectPopularList());
		
	    HttpSession session = (HttpSession) model.get("session");
	    session.setAttribute("popularInfo",popular);
	    session.setAttribute("noticeInfo", notice);
	    
	    String id = (String) model.get("id");
//	    System.out.println(id);
	    if(id != "") {
//	    	System.out.println(1);
	    	return "/memberInfo.jsp"; 
	    }else {
	    	return "/index.jsp";
	    }

	}

}
