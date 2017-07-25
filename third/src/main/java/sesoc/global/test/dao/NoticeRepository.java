package sesoc.global.test.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sesoc.global.test.util.PageNavigator;
import sesoc.global.test.vo.Notice;

@Repository
public class NoticeRepository {
	@Autowired
	SqlSession sqlSession;

	
	public List<Notice> findAll(String searchtype,String searchword,int startRecord,int countPerPage2){
		int countPerPage = countPerPage2;
		List<Notice> noticeList;
		
		RowBounds rb = new RowBounds(startRecord, countPerPage);
		
		NoticeDAO daoN = sqlSession.getMapper(NoticeDAO.class);
		Map<String,String> search = new HashMap<String,String>();
		
		search.put("searchtype", searchtype);
		search.put("searchword", searchword);
		
		noticeList = daoN.select(search,rb);
		System.out.println("결과물 : "+ noticeList);
		return noticeList;
	}
	public Notice findOne(int noticenum){
		Notice notice = null;
		NoticeDAO daoN = sqlSession.getMapper(NoticeDAO.class);
		notice = daoN.selectOne(noticenum);
		return notice;
	}
	public int insert(Notice notice){
		int result = 0;
		
		NoticeDAO daoN = sqlSession.getMapper(NoticeDAO.class);
		result = daoN.insert(notice);
		
		return result;
	}
	public int update(Notice notice){
		int result = 0;
		
		NoticeDAO daoN = sqlSession.getMapper(NoticeDAO.class);
		result = daoN.update(notice);
		
		return result;
	}
	public int updatehits(int noticenum){
		int result = 0;
		
		NoticeDAO daoN = sqlSession.getMapper(NoticeDAO.class);
		result = daoN.updatehits(noticenum);
		
		return result;
	}
	
	public int delete(int noticenum){
		int result = 0;
		
		NoticeDAO daoN = sqlSession.getMapper(NoticeDAO.class);
		result = daoN.delete(noticenum);
		
		return result;
	}
	public int getCount(String searchtype,String searchword){
		int result = 0;
		Map<String,String> search = new HashMap<String,String>();
		search.put("searchtype", searchtype);
		search.put("searchword", searchword);
		
		NoticeDAO daoN = sqlSession.getMapper(NoticeDAO.class);
		result = daoN.getNoticeCount(search);
		
		return result;
	}
	public List<Notice> rankList(int count){
		List<Notice> rankList = new ArrayList<Notice>();
		NoticeDAO daoN = sqlSession.getMapper(NoticeDAO.class);
		rankList = daoN.rankList(count);
		return rankList;
	}

}
