package sesoc.global.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.CookieGenerator;

import sesoc.global.test.dao.BoardRepository;
import sesoc.global.test.dao.CustomerRepository;
import sesoc.global.test.service.BoardService;
import sesoc.global.test.util.FileService;
import sesoc.global.test.util.PageNavigator;
import sesoc.global.test.vo.Board;
import sesoc.global.test.vo.Customer;



@Controller
public class BoarderController {
	// 테스트!!
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(BoarderController.class);
	@Autowired
	BoardRepository repo;
	
	final String uploadPath = "/boardfile"; // 파일이 저장되는 하드디스크 공간.
	/**
	 * 글 목록 요청
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/boardList", method=RequestMethod.GET)
	public String BoardList(
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="searchtype", defaultValue="title") String searchtype,
			@RequestParam(value="searchword", defaultValue="") String searchword,
			Model model){
		logger.info("searchword : " + searchword);
		int totalRecordCount = repo.getCount(searchtype,searchword);
		System.out.println("전체 글 갯수 : "+ totalRecordCount);
		
		PageNavigator navi = new PageNavigator(currentPage, totalRecordCount);
		List<Board> boardList = repo.findAll(searchtype,searchword,navi.getStartRecord(),navi.getCountPerPage());
		Map<String,String> map = new HashMap<String,String>();
		map.put("searchtype", searchtype);
		map.put("searchword", searchword);
		
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("searchtype", searchtype);
		model.addAttribute("searchword", searchword);
		model.addAttribute("boardList",boardList);
		model.addAttribute("navi",navi);
		model.addAttribute("map",map);
		
		List<Board> rankList = repo.rankList(3);
		model.addAttribute("rankList",rankList);
		
		return "board/boardList";
	}
	@RequestMapping(value="boardDetail", method=RequestMethod.GET)
	public String BoardDetail(Model model,
			@RequestParam("boardnum") int boardnum,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="searchtype", defaultValue="title") String searchtype,
			@RequestParam(value="searchword", defaultValue="") String searchword
			){
		Board board = repo.findOne(boardnum);
		repo.updatehits(boardnum);
		String fullPath = uploadPath + "/" + board.getSavedfile();
		
		File f = new File(fullPath);
		String mimeType = new MimetypesFileTypeMap().getContentType(f);
		System.out.println("mimeType= " + mimeType);
		
		
		System.out.println(board);
		System.out.println("========"+board.getOriginalfile());
		int lastIndex=0;
		String originalFile = "";
		if(board.getOriginalfile()!=null){
			originalFile = board.getOriginalfile();
		}
		lastIndex = originalFile.lastIndexOf(".");
		String ext="";
		//확장자가 없으면 -1리턴
		if(lastIndex==-1) ext="";
		else ext = board.getOriginalfile().substring(lastIndex+1);
		
		if(mimeType.contains("image") || (ext.equals("png"))){
			model.addAttribute("mime","image");
		}
		
		
		
		int totalRecordCount = repo.getCount(searchtype, searchword);
		PageNavigator navi = new PageNavigator(currentPage, totalRecordCount);
		
		System.out.println("currentPage :"+ currentPage);
		System.out.println("searchword :"+ searchword);
		System.out.println("searchtype :"+ searchtype);
		
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("searchtype", searchtype);
		model.addAttribute("searchword", searchword);
		model.addAttribute("board",board);
		return "board/boardDetail";
	}
	
	
	@RequestMapping(value="boardWriteForm", method=RequestMethod.GET)
	public String BoardWriteForm(Model model,HttpSession session){
		System.out.println(session.getAttribute("custid"));
		return "board/boardWrite";
	}
	
	
	@RequestMapping(value="boardWrite", method=RequestMethod.POST)
	public String BoardWrite(Board board,HttpSession session,
			MultipartFile upload){
		String custid = (String)session.getAttribute("custid");
		board.setCustid(custid);
		
		if(!upload.isEmpty()){
			String originalFileName = upload.getOriginalFilename();
			String savedFileName = FileService.saveFile(upload, uploadPath);
			board.setOriginalfile(originalFileName);
			board.setSavedfile(savedFileName);
		}
	
		System.out.println(board);
		int result = repo.insert(board);
		
		return "redirect:boardList";
	}
	@RequestMapping(value="boardUpdateForm", method=RequestMethod.GET)
	public String BoardUpdateForm(Model model,@RequestParam("boardnum") int boardnum,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="searchtype", defaultValue="title") String searchtype,
			@RequestParam(value="searchword", defaultValue="") String searchword
			){
		Board board = repo.findOne(boardnum);
		model.addAttribute("board",board);
		
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("searchtype", searchtype);
		model.addAttribute("searchword", searchword);
		return "board/boardUpdate";
	}
	@RequestMapping(value="boardUpdate", method=RequestMethod.POST)
	public String BoardUpdate(Board board,MultipartFile upload, RedirectAttributes rttr){
		Board savedBoard = repo.findOne(board.getBoardnum());
/*		System.out.println(upload.getOriginalFilename());
		System.out.println(savedBoard.getOriginalfile());
		System.out.println(uploadPath + "/" + savedBoard.getSavedfile());*/
		System.out.println(!upload.isEmpty());
		boolean a = (upload.getOriginalFilename() != savedBoard.getOriginalfile());
		System.out.println(a);
		if(upload.isEmpty()){
			System.out.println(board);
			repo.update(board);
		}
		if(!upload.isEmpty()
				&& (upload.getOriginalFilename() != savedBoard.getOriginalfile())){
				String originalFileName = upload.getOriginalFilename();
				String savedFileName = FileService.saveFile(upload, uploadPath);
				board.setOriginalfile(originalFileName);
				board.setSavedfile(savedFileName);
				System.out.println(board);
				repo.update(board);
				System.out.println("-----");
				String fullPath = uploadPath + "/" + savedBoard.getSavedfile();
				FileService.deleteFile(fullPath);
				System.out.println("-----");
		}
		rttr.addAttribute("boardnum",board.getBoardnum());
		return "redirect:boardDetail";
	}
	@RequestMapping(value="boardDelete", method=RequestMethod.GET)
	public String BoardDelete(@RequestParam("boardnum") int boardnum){
		Board deleteBoard = repo.findOne(boardnum);
		String fullPath = uploadPath + "/" + deleteBoard.getSavedfile();
		FileService.deleteFile(fullPath);
		int result = repo.delete(boardnum);
		return "redirect:boardList";
	}
	
	@RequestMapping("/download")
	public String download(@RequestParam("boardnum") int boardnum, HttpServletResponse response){
		Board board = repo.findOne(boardnum);
		
		String originalFile = board.getOriginalfile();
		String savedFile = board.getSavedfile();
		
		// 이미지 등과 같은 것을 브라우저에서 열리는 것을 막고 다운로드 받도록 조정할 수 있다.
		try {
			response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(originalFile,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		String fullPath = uploadPath + "/" + board.getSavedfile();
		FileInputStream fis = null;
		ServletOutputStream sos = null;
		
		try {
			fis = new FileInputStream(fullPath);
			sos = response.getOutputStream();
			
			FileCopyUtils.copy(fis, sos);
			fis.close();
			sos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}


