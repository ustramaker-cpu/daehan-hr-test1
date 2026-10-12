package hr.system.api.edu.edumil.dto;

import hr.system.api.apr.dto.AprvdocmstrDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class EdursltDto extends AprvdocmstrDto {
    private String docNo;
    private String createEmplNo;
    private String emplNo;
    private String reqDate;
    private String trpDocNo;
    private String trpSeqNo;
    private String cnclRsn;

    // 조회조건
    private String fromDate;
    private String toDate;

    // 근무내역삭제에 이용
    private String eduDateFrom;
    private String eduDateTo;
    private String courseName;
    private String eduDays;     // 교육일수
    private String wcwrkInd;    // 근태반영여부(ED27 Etc1Value)
    private String halfdayCode; // 근태구분(WC12)
    private String days;        // 휴가일수(WC12 Etc1Value)
    private String eduMethodCode;

    private String coCode;
    private String seq;
    private String refDocNo;
    private String refSeqNo;
    private String subject;
    private String remark;
    private String eduContents;
    private String eduAmt;
    private String eduSetle;
    private String eduRemark;
    private String trnsAmt;
    private String trnsSetle;
    private String trnsRemark;
    private String mealAmt;
    private String mealSetle;
    private String mealRemark;

    private String atchFileId;      // 첨부파일ID
    private String[] filePath;          // 첨부경로
    private List<MultipartFile> files;  // 첨부한 파일
}
