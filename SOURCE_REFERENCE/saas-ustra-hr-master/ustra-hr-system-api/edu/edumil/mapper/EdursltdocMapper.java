package hr.system.api.edu.edumil.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumil.dto.EdursltDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EdursltdocMapper {
    List<CamelCaseMap> findEdursltList(EdursltDto edursltDto);           // 문서함에서 들어간 경우 조회
    List<CamelCaseMap> findEdureqList(EdursltDto edursltDto);             // (팝업) 교육 신청내역 조회
    List<CamelCaseMap> searchDocapprtrav100List(EdursltDto edursltDto); // 문서 조회
    List<CamelCaseMap> findApprtravAttachFile(EdursltDto edursltDto); // 첨부파일
    String getDocNo(EdursltDto edursltDto);                               // 문서번호 생성
    String EdursltSeqNo(EdursltDto edursltDto); // seq
    int insertEdurslt(EdursltDto edursltDto);                            // 교육결과보고서 저장
}
