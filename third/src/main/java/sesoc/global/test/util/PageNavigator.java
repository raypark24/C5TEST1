package sesoc.global.test.util;

public class PageNavigator {
	//멤버
	private int countPerPage = 5; // 한 페이지 당 글 갯수
	private int pagePerGroup = 3;	// 그룹당 보여줄 페이지 수
	private int currentPage;	// 현재 페이지.(외부에서 전달)
	private int totalRecordCount; // 전체 글 개수.(외부에서 전달)
	private int totalPageCount;	// 총 페이지 수  (글 갯수 / 페이지당 글 갯수)
	private int startPageGroup;	// 현재 그룹의 첫 페이지 계산 
	private int currentGroup;	// 현재 페이지 그룹
	private int endPageGroup;	// 현재 그룹의 마지막 페이지 계산
	private int startRecord;	// 전체 레코드 중 현재 페이지의 첫 글이 몇번째인지 포인팅.
	// srow, erow 계산하지 않고 , mybatis의 기능을 사용할 예정.
	//생성자
	public PageNavigator(int currentPage, int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
		//전체 글 갯수가 152개라면 몇 페이지?
		totalPageCount = (totalRecordCount+countPerPage-1)/countPerPage;
		//현재 페이지 요청시 게산
		if(currentPage < 1) currentPage = 1;
		if(currentPage > totalPageCount) currentPage = totalPageCount;
		
		this.currentPage = currentPage;
		
		// 현재 그룹
		currentGroup = ( currentPage-1 ) / pagePerGroup;
		
		//현재 그룹의 첫 페이지
		startPageGroup = currentGroup * pagePerGroup +1;
		startPageGroup = startPageGroup < 1 ? 1 : startPageGroup;
		//현재 그룹의 마지막페이지
		endPageGroup = startPageGroup + pagePerGroup -1; // 
		endPageGroup = endPageGroup < totalPageCount ? endPageGroup : totalPageCount;
		
		// 전체 레코드 중 현재 페이지의 첫글의 위치
		startRecord = (currentPage - 1) * countPerPage;
		
	}
	
	// setter , getter
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getStartPageGroup() {
		return startPageGroup;
	}
	public void setStartPageGroup(int startPageGroup) {
		this.startPageGroup = startPageGroup;
	}
	public int getCurrentGroup() {
		return currentGroup;
	}
	public void setCurrentGroup(int currentGroup) {
		this.currentGroup = currentGroup;
	}
	public int getEndPageGroup() {
		return endPageGroup;
	}
	public void setEndPageGroup(int endPageGroup) {
		this.endPageGroup = endPageGroup;
	}
	public int getStartRecord() {
		return startRecord;
	}
	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}
	
	
	public int getCountPerPage() {
		return countPerPage;
	}

	public int getPagePerGroup() {
		return pagePerGroup;
	}
	

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	public void setPagePerGroup(int pagePerGroup) {
		this.pagePerGroup = pagePerGroup;
	}

	// toString()
	@Override
	public String toString() {
		return "PageNavigator [countPerPage=" + countPerPage + ", pagePerGroup=" + pagePerGroup
				+ ", currentPage=" + currentPage + ", totalRecordCount=" + totalRecordCount + ", totalPageCount="
				+ totalPageCount + ", startPageGroup=" + startPageGroup + ", currentGroup=" + currentGroup
				+ ", endPageGroup=" + endPageGroup + ", startRecord=" + startRecord + "]";
	}
}
