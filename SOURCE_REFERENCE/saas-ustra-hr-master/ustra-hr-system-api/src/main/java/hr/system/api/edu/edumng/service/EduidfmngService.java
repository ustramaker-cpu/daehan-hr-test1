package hr.system.api.edu.edumng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfmngDto;
import hr.system.api.edu.edumng.mapper.EduidfmngMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EduidfmngService {

    private final EduidfmngMapper eduidfmngMapper;

    public List<CamelCaseMap> findEduidfmngList(EduidfmngDto eduidfmngDto){
        return eduidfmngMapper.findEduidfmngList(eduidfmngDto);
    }

    /**
     * 부서별 자기개발계획(담당자) 저장/추가/삭제
     * @param List<EduidfmngDto> - 등록, 수정, 삭제할 정보가 담긴 eduidfmngList
     * @return
     */
    public int saveEduidfmngList(List<EduidfmngDto> eduidfmngList) {

        int result = 0;

        if(CollectionUtils.isNotEmpty(eduidfmngList)) {
            for(EduidfmngDto eduidfmngDto : eduidfmngList) {
                switch(eduidfmngDto.getRowStatus()) {
                    case DataConst.INSERT :

                        result += eduidfmngMapper.insertEduidfmng(eduidfmngDto);
                        break;
                    case DataConst.UPDATE :
                        break;
                    case DataConst.DELETE :

                        result += eduidfmngMapper.deleteEduidfmng(eduidfmngDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", eduidfmngDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 부서별 자기개발계획(담당자) 대상자를 생성한다.
     * @param eduidfmngDto - 등록, 수정, 삭제할 정보가 담긴 EduidfmngDto
     * @return
     */
    public int saveNewtarget(EduidfmngDto eduidfmngDto) {
        int result = 0;

        /// 추후에 validation check 필요한 부분 추가하기!!!

        eduidfmngMapper.deletetargetEduidfmng(eduidfmngDto);
        result = eduidfmngMapper.maketargetEduidfmng(eduidfmngDto);

        return result;
    }

    /**
     * 부서별 자기개발계획(담당자)의 상태를 수정한다. (확인 OR 전송취소, 확인취소(대기))
     * @param eduidfmngList
     * @return int
     */
    public int updateStatusCode(List<EduidfmngDto> eduidfmngList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(eduidfmngList)) {
            for (EduidfmngDto eduidfmngDto : eduidfmngList) {
                if( "SO".equals(status) ) {  // 전송
                    String checkDay = eduidfmngMapper.checkSubmitEmpDay(eduidfmngDto).getDayData();
                    String checkEduSum = eduidfmngMapper.checkEduplanSum(eduidfmngDto).getEduSumData();

                    BigDecimal checkDayB = new BigDecimal(checkDay);
                    BigDecimal checkEduSumB = new BigDecimal(checkEduSum);

                    if( checkEduSumB.compareTo(checkDayB) == 1 || checkEduSumB.compareTo(checkDayB) == 0 ) {
                        // 총 교육 시간이 30시간 or 15시간(6개월 미만 근무자에 한해서) 이상이면 '전송' 상태로 업데이트
                        result += eduidfmngMapper.updateStatusSubmit(eduidfmngDto);
                    } else if ( checkEduSumB.compareTo(checkDayB) == -1 ) {
                        // 총 교육 시간이 30시간 or 15시간(6개월 미만 근무자에 한해서) 미만이면 X
                        throw new ServiceException(
                                eduidfmngDto.getCourseYear()+"년 "+eduidfmngDto.getEmplNo()+"-"+eduidfmngDto.getEmplNameHan()+" 님의 총 교육시간이 부족하기 때문에 전송 불가합니다.");
                    }

                } else if ( "CO".equals(status) ) {  // 확인
                    result += eduidfmngMapper.updateStatusConfirm(eduidfmngDto);
                } else if ( "CA".equals(status) ) {  // 전송취소, 확인 취소
                    result += eduidfmngMapper.updateStatusCancel(eduidfmngDto);
                }
            }
        }
        return result;
    }

}