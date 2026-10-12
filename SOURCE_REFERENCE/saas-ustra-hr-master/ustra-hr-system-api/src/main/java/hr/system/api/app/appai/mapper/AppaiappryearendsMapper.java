package hr.system.api.app.appai.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gsitm.framework.core.type.CamelCaseMap;

import hr.system.api.app.appai.dto.AppaiapopncntDto;
import hr.system.api.app.appai.dto.AppaiapopnetcDto;
import hr.system.api.app.appai.dto.AppaiaporgwishDto;
import hr.system.api.app.appai.dto.AppaiappryearendsDto;

@Mapper
public interface AppaiappryearendsMapper {

	public List<CamelCaseMap> findCdap61(AppaiappryearendsDto aprydsDto);       // 자기신고서_현 직무 만족도 항목에 매핑되는 응답 list 조회
	public List<CamelCaseMap> findCdap63(AppaiappryearendsDto aprydsDto);       // 자기신고서_전보희망 항목에 매핑되는 응답 list 조회

	public List<CamelCaseMap> findOrg(AppaiappryearendsDto aprydsDto);          // 부서list 조회
	public List<CamelCaseMap> findJobList(AppaiappryearendsDto aprydsDto);      // 직무list 조회
	public CamelCaseMap findSelfInfo(AppaiappryearendsDto aprydsDto);           // 본인정보 조회

	public List<CamelCaseMap> findApopncnt1(AppaiappryearendsDto aprydsDto);    // 자기신고서_현 직무 만족도 grid용 조회
	public List<CamelCaseMap> findApopncnt2(AppaiappryearendsDto aprydsDto);    // 자기신고서_전보희망 grid용 조회
	public List<CamelCaseMap> findAporgwish(AppaiappryearendsDto aprydsDto);    // 자기신고서_전보희망부서 조회
	public List<CamelCaseMap> findApopnetc(AppaiappryearendsDto aprydsDto);     // 자기신고서_기타의견 조회
	public List<CamelCaseMap> checkInd(AppaiappryearendsDto aprydsDto);         // 자기신고서 마감여부 조회
	public List<CamelCaseMap> checkApprserList(AppaiappryearendsDto aprydsDto); // 자기신고서 전송여부 조회

	public int insertApopncnt(AppaiapopncntDto appncntDto);                     // 자기신고서_현 직무 만족도, 자기신고서_전보희망 insert
	public int insertAporgwish(AppaiaporgwishDto appprgwishDto);                // 자기신고서_전보희망부서 insert
	public int insertApopnetc(AppaiapopnetcDto apponetcDto);                    // 자기신고서_기타의견 insert

	public int updateSubmitStatus(AppaiappryearendsDto aprydsDto);              // 자기신고서 전송 update

	/* 자기신고서 항목 grid로 변경하면서 사용x */
	//public List<CamelCaseMap> findJobPleasureItem(AppaiappryearendsDto aprydsDto);
	//public List<CamelCaseMap> findWishMoveItem(AppaiappryearendsDto aprydsDto);
	//public List<CamelCaseMap> findApopncnt(AppaiappryearendsDto aprydsDto);
	//public int deleteApopncnt(AppaiapopncntDto appncntDto);      // 자기신고서_현 직무 만족도, 자기신고서_전보희망 delete
	//public int deleteAporgwish(AppaiaporgwishDto appprgwishDto); // 자기신고서_전보희망부서 delete
	//public int deleteApopnetc(AppaiapopnetcDto apponetcDto);     // 자자기신고서_기타의견 delete

}
