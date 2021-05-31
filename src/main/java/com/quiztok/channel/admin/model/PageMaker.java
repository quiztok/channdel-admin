package com.quiztok.channel.admin.model;

import com.quiztok.channel.admin.model.Criteria;

public class PageMaker {

    private int totalCount; // 데이터 전체 개수
    private int displayPageNum = 10; // 게시판 화면에서 한번에 보여질 페이지 번호의 개수 ( 1,2,3,4,5,6,7,9,10)

    private int startPage; // 현재 화면에서 보이는 startPage 번호
    private int endPage; // 현재 화면에 보이는
    private boolean prev; // 페이징 이전 버튼 활성화 여부
    private boolean next; // 페이징 다음 버튼 활성화 여부
    private int pageStartNumber;
    private String searchName;


    private Criteria cri;

    public int getTotalCount(){
        return totalCount;
    }

    public void  setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcData();
        //return this.totalCount;
    }



    private void calcData( ) {
        int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
        int pageNum = (int) totalCount;
        this.pageStartNumber = pageNum - (cri.getPage()- 1) *  displayPageNum;
        System.out.println ("#############################");
        System.out.println ("현 페이지 :"+ cri.getPage());
        System.out.println ("시작 넘버 :"+  pageStartNumber  );
        System.out.println ("#############################");
        endPage = ( int ) ( Math.ceil( cri.getPage()/ (double) displayPageNum ) * displayPageNum );
        startPage = (endPage - displayPageNum) +1;


        if(endPage > tempEndPage) {
            endPage = tempEndPage;
        }
        prev = startPage == 1 ? false : true;
        next = endPage * cri.getPerPageNum() >= totalCount ? false : true;

    }
    public String getSearchName (){
        return  searchName;
    }

    public void setSearchName(String searchName){
        this.searchName = searchName;
    }

    public int getPageStartNumber(){
        return pageStartNumber;
    }
    public void setPageStartNumber (int pageStartNumber){
        this.pageStartNumber = pageStartNumber;
    }
    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }
    public int getEndPage(){
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isPrev(){
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public boolean isNext(){
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getDisplayPageNum() {
        return displayPageNum;
    }
    public void setDisplayPageNum(int displayPageNum){
        this.displayPageNum = displayPageNum;
    }

    public Criteria getCri(){
        return cri;
    }

    public void setCri(Criteria cri){
        this.cri = cri;
    }

    @Override
    public String toString(){
        return "PageMaker [totalCount="+ totalCount +", startPage="+ startPage +", pageStartNumber="+ pageStartNumber
                + ", searchName="+searchName + ", endPage = " + endPage +", prev="+ prev +", next="+ next
                +", displayPageNum="+ displayPageNum + ", cri="+ cri +"]";
    }



}
