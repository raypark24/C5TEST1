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
import sesoc.global.test.vo.Board;

@Repository
public class BoardRepository {
	@Autowired
	SqlSession sqlSession;

	
	public List<Board> findAll(String searchtype,String searchword,int startRecord,int countPerPage2){
		int countPerPage = countPerPage2;
		List<Board> boardList;
		
		RowBounds rb = new RowBounds(startRecord, countPerPage);
		
		BoardDAO daoB = sqlSession.getMapper(BoardDAO.class);
		Map<String,String> search = new HashMap<String,String>();
		
		search.put("searchtype", searchtype);
		search.put("searchword", searchword);
		
		boardList = daoB.select(search,rb);
		System.out.println("결과물 : "+boardList);
		return boardList;
	}
	public Board findOne(int boardnum){
		Board board = null;
		BoardDAO daoB = sqlSession.getMapper(BoardDAO.class);
		board = daoB.selectOne(boardnum);
		return board;
	}
	public int insert(Board board){
		int result = 0;
		
		BoardDAO daoB = sqlSession.getMapper(BoardDAO.class);
		result = daoB.insert(board);
		
		return result;
	}
	public int update(Board board){
		int result = 0;
		
		BoardDAO daoB = sqlSession.getMapper(BoardDAO.class);
		result = daoB.update(board);
		
		return result;
	}
	public int updatehits(int boardnum){
		int result = 0;
		
		BoardDAO daoB = sqlSession.getMapper(BoardDAO.class);
		result = daoB.updatehits(boardnum);
		
		return result;
	}
	
	public int delete(int boardnum){
		int result = 0;
		
		BoardDAO daoB = sqlSession.getMapper(BoardDAO.class);
		result = daoB.delete(boardnum);
		
		return result;
	}
	public int getCount(String searchtype,String searchword){
		int result = 0;
		Map<String,String> search = new HashMap<String,String>();
		search.put("searchtype", searchtype);
		search.put("searchword", searchword);
		
		BoardDAO daoB = sqlSession.getMapper(BoardDAO.class);
		result = daoB.getBoardCount(search);
		
		return result;
	}
	public List<Board> rankList(int count){
		List<Board> rankList = new ArrayList<Board>();
		BoardDAO daoB = sqlSession.getMapper(BoardDAO.class);
		rankList = daoB.rankList(count);
		return rankList;
	}
}
