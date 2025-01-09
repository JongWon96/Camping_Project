package dto;

public class InquiryVo {
    public static final int PENDING = 0; // 대기 중
    public static final int RESOLVED = 1; // 해결됨


    // 객체 생성을 방지하기 위한 private 생성자
    private InquiryVo() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

