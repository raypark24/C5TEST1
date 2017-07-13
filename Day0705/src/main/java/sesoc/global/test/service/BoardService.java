package sesoc.global.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sesoc.global.test.dao.BoardRepository;
import sesoc.global.test.vo.Board;

@Service
public class BoardService {
	
	@Autowired
	BoardRepository repo;
	
	public List<Board> boardList(String searchtype, String searchword,int startRecord,int countPerPage){
		List<Board> list = null;
		
		list = repo.findAll(searchtype, searchword, startRecord, countPerPage);
		
		return list;
	}
}
