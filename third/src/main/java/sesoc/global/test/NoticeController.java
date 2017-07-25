package sesoc.global.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sesoc.global.test.dao.NoticeRepository;
import sesoc.global.test.util.FileService;
import sesoc.global.test.util.PageNavigator;
import sesoc.global.test.vo.Notice;

@Controller
public class NoticeController {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(NoticeController.class);
	@Autowired
	NoticeRepository repo;
	
	final String uploadPath = "/noticefile"; // 파일 경로
	
	/**
	 * 공지사항 목록
	 */
	@RequestMapping(value="noticeList", method=RequestMethod.GET)
	public String noticeList(
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="searchtype", defaultValue="title") String searchtype,
			@RequestParam(value="searchword", defaultValue="") String searchword,
			Model model
			){
		logger.info("searchword : "+searchword);
		int totalRecordCount = repo.getCount(searchtype, searchword);
		System.out.println("전체 글 갯수 : "+ totalRecordCount);
		
		PageNavigator navi = new PageNavigator(currentPage, totalRecordCount);
		List<Notice> noticeList = repo.findAll(searchtype,searchword,navi.getStartRecord(),navi.getCountPerPage());
		Map<String,String> map = new HashMap<String,String>();
		map.put("searchtype", searchtype);
		map.put("searchword", searchword);
		
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("searchtype", searchtype);
		model.addAttribute("searchword", searchword);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("navi",navi);
		model.addAttribute("map",map);
		
		List<Notice> rankList = repo.rankList(3);
		model.addAttribute("rankList",rankList);
		
		return "notice/noticeList";
	}
	@RequestMapping(value="noticeDetail", method=RequestMethod.GET)
	public String NoticeDetail(Model model,
			@RequestParam("noticenum") int noticenum,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="searchtype", defaultValue="title") String searchtype,
			@RequestParam(value="searchword", defaultValue="") String searchword
			){
		Notice notice = repo.findOne(noticenum);
		System.out.println(notice);
		repo.updatehits(noticenum);
		String fullPath = uploadPath + "/" + notice.getSavedfile();
		
		File f = new File(fullPath);
		String mimeType = new MimetypesFileTypeMap().getContentType(f);
		System.out.println("mimeType= " + mimeType);
		
		
		System.out.println(notice);
		System.out.println("========"+notice.getOriginalfile());
		int lastIndex=0;
		String originalFile = "";
		if(notice.getOriginalfile()!=null){
			originalFile = notice.getOriginalfile();
		}
		lastIndex = originalFile.lastIndexOf(".");
		String ext="";
		//확장자가 없으면 -1리턴
		if(lastIndex==-1) ext="";
		else ext = notice.getOriginalfile().substring(lastIndex+1);
		
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
		model.addAttribute("notice",notice);
		return "notice/noticeDetail";
	}
	
	
	@RequestMapping(value="noticeWriteForm", method=RequestMethod.GET)
	public String noticeWriteForm(Model model,HttpSession session){
		System.out.println(session.getAttribute("custid"));
		return "notice/noticeWrite";
	}
	
	
	@RequestMapping(value="noticeWrite", method=RequestMethod.POST)
	public String noticeWrite(Notice notice,HttpSession session,
			MultipartFile upload){
		String custid = (String)session.getAttribute("custid");
		notice.setCustid(custid);
		
		if(!upload.isEmpty()){
			String originalFileName = upload.getOriginalFilename();
			String savedFileName = FileService.saveFile(upload, uploadPath);
			notice.setOriginalfile(originalFileName);
			notice.setSavedfile(savedFileName);
		}
	
		System.out.println("--"+notice);
		int result = repo.insert(notice);
		
		return "redirect:noticeList";
	}
	@RequestMapping(value="noticeUpdateForm", method=RequestMethod.GET)
	public String NoticeUpdateForm(Model model,@RequestParam("noticenum") int noticenum,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="searchtype", defaultValue="title") String searchtype,
			@RequestParam(value="searchword", defaultValue="") String searchword
			){
		Notice notice = repo.findOne(noticenum);
		model.addAttribute("notice",notice);
		
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("searchtype", searchtype);
		model.addAttribute("searchword", searchword);
		return "notice/noticeUpdate";
	}
	@RequestMapping(value="noticeUpdate", method=RequestMethod.POST)
	public String noticeUpdate(Notice notice,MultipartFile upload, RedirectAttributes rttr){
		Notice savednotice = repo.findOne(notice.getnoticenum());
/*		System.out.println(upload.getOriginalFilename());
		System.out.println(savednotice.getOriginalfile());
		System.out.println(uploadPath + "/" + savednotice.getSavedfile());*/
		System.out.println(!upload.isEmpty());
		boolean a = (upload.getOriginalFilename() != savednotice.getOriginalfile());
		System.out.println(a);
		if(upload.isEmpty()){
			System.out.println(notice);
			repo.update(notice);
		}
		if(!upload.isEmpty()
				&& (upload.getOriginalFilename() != savednotice.getOriginalfile())){
				String originalFileName = upload.getOriginalFilename();
				String savedFileName = FileService.saveFile(upload, uploadPath);
				notice.setOriginalfile(originalFileName);
				notice.setSavedfile(savedFileName);
				System.out.println(notice);
				repo.update(notice);
				System.out.println("-----");
				String fullPath = uploadPath + "/" + savednotice.getSavedfile();
				FileService.deleteFile(fullPath);
				System.out.println("-----");
		}
		rttr.addAttribute("noticenum",notice.getnoticenum());
		return "redirect:noticeDetail";
	}
	@RequestMapping(value="noticeDelete", method=RequestMethod.GET)
	public String noticeDelete(@RequestParam("noticenum") int noticenum){
		Notice deletenotice = repo.findOne(noticenum);
		String fullPath = uploadPath + "/" + deletenotice.getSavedfile();
		FileService.deleteFile(fullPath);
		int result = repo.delete(noticenum);
		return "redirect:noticeList";
	}
	
	@RequestMapping("/noticedownload")
	public String download(@RequestParam("noticenum") int noticenum, HttpServletResponse response){
		Notice notice = repo.findOne(noticenum);
		
		String originalFile = notice.getOriginalfile();
		String savedFile = notice.getSavedfile();
		
		// 이미지 등과 같은 것을 브라우저에서 열리는 것을 막고 다운로드 받도록 조정할 수 있다.
		try {
			response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(originalFile,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		String fullPath = uploadPath + "/" + notice.getSavedfile();
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