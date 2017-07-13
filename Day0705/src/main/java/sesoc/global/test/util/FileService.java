package sesoc.global.test.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileService {
	public static String saveFile(MultipartFile upload, String uploadPath){
		
			File path = new File(uploadPath);
			
			if(!path.isDirectory()){ // 저장 디렉토리가 없으면 디렉토리 생성함.
				path.mkdirs();
			}
			String originalFileName = upload.getOriginalFilename();
			String uuid = UUID.randomUUID().toString();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String uuid_time = sdf.format(new Date());
			
			String fileName = "";	// 확장자명을 뺀 파일명
			String ext="";			// 파일명을 뺀 확장자
			String savedFileName=""; // HDD에 저장되는 이름
			
			int lastIndex = originalFileName.lastIndexOf(".");
			fileName = originalFileName.substring(0,lastIndex);
			
			//확장자가 없으면 -1리턴
			if(lastIndex==-1) ext="";
			else ext = originalFileName.substring(lastIndex+1);
			
			// HDD에 저장할 savedFileName 작성
			savedFileName = fileName + "_" + uuid + "." + ext;
			
			System.out.println("파일 명 : " + savedFileName);
			//savedFileName = fileName + "_" + uuid + "." + ext;
			
			// 파일 객체 생성 : 경로 + 파일명
			File serverFile = null;
			
			serverFile = new File(uploadPath + "/" + savedFileName);
			
			// 저장만 함 
			try {
				upload.transferTo(serverFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return savedFileName;
	}
	/**
	 * HDD에 저장된 파일을 삭제함
	 * @param fullPath = 경로 + 파일명
	 * @return
	 */
	public static boolean deleteFile(String fullPath){
		boolean result = false;
		
		File delFile = new File(fullPath);
		if(delFile.isFile()){
			delFile.delete();
			result = true;
		}
		return result;
	}
}
