package hr.system.api.apr.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WctimedescDto extends BaseDto {

    /**
     * 결재할문서함에서 최종승인 후 출퇴근내역 테이블 insert/update 시 사용
     */

    // 출퇴근기록내역
    private String emplNo;
    private String workDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String nextDayInd;
    private String workHour;
    private String remark;
    private String chngInd;

}
