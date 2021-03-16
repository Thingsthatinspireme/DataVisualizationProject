package web.dao;

import web.vo.Member;

public interface MemberDao {
	public Member exist(String email, String password) throws Exception;

}
