package sesoc.global.test.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import sesoc.global.test.vo.Board;



public interface BoardDAO {
	// CRUD
	public int insert(Board board);
	public int update(Board board);
	public int updatehits(int boardnum);
	public int delete(int boardnum);
	public List<Board> select(Map<String, String> search, RowBounds rb);
	public Board selectOne(int boardnum);
	public int getBoardCount(Map<String, String> search); // 전체 글 개수
	public List<Board> rankList(int count);
}
