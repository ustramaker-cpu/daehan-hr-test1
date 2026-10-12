package hr.system.api.apr.common;

public class AprvType {
    public static final String TEMP_SAVE = "1";       // 임시저장
    public static final String APPROVAL_WAIT = "2";   // 결재대기
    public static final String APPROVAL_REQ = "3";    // 결재요청
    public static final String DISMISSAL = "4";       // 기각
    public static final String APPROVAL = "5";        // 결재완료
    public static final String DELETE = "D";          // 삭제
    public static final String APPROVAL_CNCL = "6";   // 회수
    public static final String RE_DRAFT = "7";        // 재기안

    public static final String DRAFTER = "S";          // 기안자
    public static final String APPROVER = "A";         // 결재자
    public static final String CHECKER = "C";          // 확인자
    public static final String VIEWER = "V";           // 수신자

    private AprvType(){}
}
