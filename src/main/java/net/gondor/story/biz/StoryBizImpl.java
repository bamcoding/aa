package net.gondor.story.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.gondor.constants.Session;
import net.gondor.story.dao.StoryDao;
import net.gondor.story.dao.StoryDaoImpl;
import net.gondor.story.vo.StoryVO;
import net.gondor.user.dao.UserDao;
import net.gondor.user.dao.UserDaoImpl;
import net.gondor.user.vo.UserVO;

public class StoryBizImpl implements StoryBiz {

	StoryDao dao;
	UserDao userdao;

	public StoryBizImpl() {
		super();
		dao = new StoryDaoImpl();
		userdao = new UserDaoImpl();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<StoryVO> getStories() {
		return dao.getStories();
	}

	@Override
	public StoryVO getStory(String id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute(Session.SESSION_INFO);
		userdao.updatePoint(user.getId(), -1);
		
		user = userdao.getUserBy(user);
		session.setAttribute(Session.SESSION_INFO, user);
		
		dao.updateHitCount(id);
		return dao.getStory(id);
	}

	@Override
	public void deleteStory(String id) {
		// TODO Auto-generated method stub
		dao.deleteStory(id);
	}

	@Override
	public boolean writeStory(StoryVO article, HttpServletRequest request) {
		boolean isTrue = dao.writeStory(article) > 0;
		if (isTrue) {
			HttpSession session = request.getSession();
			UserVO user = (UserVO) session.getAttribute(Session.SESSION_INFO);
			userdao.updatePoint(user.getId(), 10);
			user = userdao.getUserBy(user);
			session.setAttribute(Session.SESSION_INFO, user);
		}
		return isTrue;
	}

	@Override
/*	public int updateLikeCount(String id) {
		int count=0;
		if(dao.updateLikeCount(id) > 0){
			count = dao.getLikeCount(id);
		}
		return count;
	}*/
	public boolean updateLikeCount(String id) {
		/*int count=0;
		if(dao.updateLikeCount(id) > 0){
			count = dao.getLikeCount(id);
		}*/
		return dao.updateLikeCount(id) > 0;
	}
}
