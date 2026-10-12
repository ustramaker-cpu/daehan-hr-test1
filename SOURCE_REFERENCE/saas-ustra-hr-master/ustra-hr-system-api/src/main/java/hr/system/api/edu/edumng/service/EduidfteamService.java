package hr.system.api.edu.edumng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfmngDto;
import hr.system.api.edu.edumng.mapper.EduidfteamMapper;
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
public class EduidfteamService {

    private final EduidfteamMapper eduidfteamMapper;

    /**
     * 자기개발계획관리 조회
     * @param eduidfmngDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findEduidfteamList(EduidfmngDto eduidfmngDto){
        return eduidfteamMapper.findEduidfteamList(eduidfmngDto);
    }

    /**
     * 자기개발계획수립 계획 다운로드
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findEduidfExcelAllDownList(EduidfmngDto eduidfmngDto) {
        return eduidfteamMapper.findEduidfExcelAllDownList(eduidfmngDto);
    }

    /**
     * 자기개발계획관리의 상태를 수정한다. (전송)
     * @param eduidfmngList
     * @return int
     */
    public int updateStatusCode(List<EduidfmngDto> eduidfmngList) {
        int result = 0;
        /*
        1. 6개월 미만 근무자 체크 ( 신규입사, 파견, 휴직, 육아휴직)
        2. 교육과정년도별 총 교육 시간이 최소 30시간 or 15시간 이상인지 체크
         */
        if(CollectionUtils.isNotEmpty(eduidfmngList)) {
            for (EduidfmngDto eduidfmngDto : eduidfmngList) {
                String checkDay = eduidfteamMapper.checkSubmitEmpDay(eduidfmngDto).getDayData();
                String checkEduSum = eduidfteamMapper.checkEduplanSum(eduidfmngDto).getEduSumData();

                BigDecimal checkDayB = new BigDecimal(checkDay);
                BigDecimal checkEduSumB = new BigDecimal(checkEduSum);

                if( checkEduSumB.compareTo(checkDayB) == 1 || checkEduSumB.compareTo(checkDayB) == 0 ) {
                    // 총 교육 시간이 30시간 or 15시간(6개월 미만 근무자에 한해서) 이상이면 '전송' 상태로 업데이트
                    result += eduidfteamMapper.updateStatusCode(eduidfmngDto);
                } else if ( checkEduSumB.compareTo(checkDayB) == -1 ) {
                    // 총 교육 시간이 30시간 or 15시간(6개월 미만 근무자에 한해서) 미만이면 X
                    throw new ServiceException(
                            eduidfmngDto.getCourseYear()+"년 "+eduidfmngDto.getEmplNo()+"-"+eduidfmngDto.getEmplNameHan()+" 님의 총 교육시간이 부족하기 때문에 전송 불가합니다.");
                }

            }
        }

        return result;
    }

}