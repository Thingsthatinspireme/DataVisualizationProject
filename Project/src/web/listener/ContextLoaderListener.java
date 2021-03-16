package web.listener;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import web.dao.BoardDao;
import web.dao.MysqlMemberDao;

public class ContextLoaderListener implements ServletContextListener {
	
//Connection conn = null;
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			System.out.println("contextDestroyed");
//				if(conn != null) {
//					conn.close();
//				}
			// 우리가 DataSource 객체를 해제하지 않아도
			// tomcat 서버가 알아서 해제한다
		}catch(Exception e) {
			e.printStackTrace();
		}		

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			System.out.println("contextInitialized");
			ServletContext sc = sce.getServletContext();

			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup(
					"java:comp/env/jdbc/knittingdb");
//			Connection conn = ds.getConnection();
			BoardDao boardDao = new BoardDao();
			MysqlMemberDao memberDao = new MysqlMemberDao();
			boardDao.setDataSource(ds);
			memberDao.setDataSource(ds);
			
			
			sc.setAttribute("boardDao", boardDao);
			sc.setAttribute("memberDao", memberDao);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
