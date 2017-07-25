package sesoc.global.test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import sesoc.global.test.vo.Notice;

public interface NoticeDAO {
	public List<Notice> select(Map<String,String> search, RowBounds rb);
	public Notice selectOne(int noticenum);
	public int insert(Notice notice);
	public int update(Notice notice);
	public int delete(int noticenum);
	public int getNoticeCount(Map<String,String> search);
	public int hitCount(int noticenum);
	public int updatehits(int noticenum);
	public List<Notice> rankList(int count);
}
