package knk.erp.api.shlee.board.entity;

public enum SearchType {
    TAG("태그검색"),
    TITLE("제목검색"),
    WRITER("작성자검색"),
    NORMAL("기본보기");

    private String value;

    SearchType(String value){
        this.value=value;
    }

    public String getValue(){
        return this.value;
    }
}
