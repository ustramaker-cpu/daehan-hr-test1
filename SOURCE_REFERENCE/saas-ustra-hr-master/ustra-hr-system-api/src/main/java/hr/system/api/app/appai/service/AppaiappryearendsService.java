package hr.system.api.app.appai.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsitm.framework.core.type.CamelCaseMap;

import hr.system.api.app.appai.dto.AppaiapopncntDto;
import hr.system.api.app.appai.dto.AppaiapopnetcDto;
import hr.system.api.app.appai.dto.AppaiaporgwishDto;
import hr.system.api.app.appai.dto.AppaiappryearendsDto;
import hr.system.api.app.appai.mapper.AppaiappryearendsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppaiappryearendsService {


	private final AppaiappryearendsMapper appaiappryearendsMapper;

    /**
     * 자기신고서 조회_현 직무 만족도 grid용
     * @param AppaiappryearendsDto
     * @return Map<String, List<CamelCaseMap>>
     */
	public List<CamelCaseMap> getApopncnt1(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.findApopncnt1(aprydsDto);
	}
    /**
     * 자기신고서 조회_전보희망 grid용
     * @param AppaiappryearendsDto
     * @return Map<String, List<CamelCaseMap>>
     */
	public List<CamelCaseMap> getApopncnt2(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.findApopncnt2(aprydsDto);
	}
    /**
     * 자기신고서 조회_전보희망부서
     * @param AppaiappryearendsDto
     * @return Map<String, List<CamelCaseMap>>
     */
	public List<CamelCaseMap> getAporgwish(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.findAporgwish(aprydsDto);
	}
    /**
     * 자기신고서 조회_기타의견
     * @param AppaiappryearendsDto
     * @return Map<String, List<CamelCaseMap>>
     */
	public List<CamelCaseMap> getApopnetc(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.findApopnetc(aprydsDto);
	}

	 /**
     * 자기신고서 본인 기본정보 조회
     * @param AppaiappryearendsDto
     * @return CamelCaseMap
     */
	public CamelCaseMap getSelfInfo(AppaiappryearendsDto aprydsDto) {
		return appaiappryearendsMapper.findSelfInfo(aprydsDto);
	}

	 /**
     * 마감여부 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
	public List<CamelCaseMap> checkInd(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.checkInd(aprydsDto);
	}

	 /**
     * 전송여부 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
	public List<CamelCaseMap> checkApprser(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.checkApprserList(aprydsDto);
	}

	/**
     * 자기신고서 저장
     * @param AppaiappryearendsDto
     * @return int
     */
	public int saveAppaiappryearends(AppaiappryearendsDto aprydsDto) {
		int result = 0;
		List<AppaiapopncntDto> appncntDtoList = aprydsDto.getAppaiapopncntDtoList(); // 자기신고서
		List<AppaiaporgwishDto> appprgwishDtoList = aprydsDto.getAppaiaporgwishDtoList(); // 이동희망부서
		List<AppaiapopnetcDto> apponetcDtoList = aprydsDto.getAppaiaponetcDtoList(); // 기타의견

		if(CollectionUtils.isNotEmpty(appncntDtoList)){
			for(AppaiapopncntDto appncntDto : appncntDtoList) {
				result += appaiappryearendsMapper.insertApopncnt(appncntDto);
			}
		}

		if(CollectionUtils.isNotEmpty(appprgwishDtoList)){
			for(AppaiaporgwishDto appprgwishDto : appprgwishDtoList) {
				result += appaiappryearendsMapper.insertAporgwish(appprgwishDto);
			}
		}

		if(CollectionUtils.isNotEmpty(apponetcDtoList)){
			for(AppaiapopnetcDto apponetcDto : apponetcDtoList) {
				int length = apponetcDto.getOpnEtc().getBytes().length;
				if(length >= 4000) {
					throw new ServiceException("기타의견의 글자수가 4000bytes를 초과합니다. 현재 bytes : " + length + " 입니다");
				}else {
					result += appaiappryearendsMapper.insertApopnetc(apponetcDto);
				}
			}
		}

		return result;
	}
	/**
     * 부서정보 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
	public List<CamelCaseMap> getOrg(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.findOrg(aprydsDto);
	}
	/**
     * 직무정보 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
	public List<CamelCaseMap> getJobList(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.findJobList(aprydsDto);
	}
	/**
     * 자기신고서 전송
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
	public int modifySubmitStatus(AppaiappryearendsDto aprydsDto) {
		return appaiappryearendsMapper.updateSubmitStatus(aprydsDto);
	}

	/**
     * 직무만족도코드 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
	public List<CamelCaseMap> getCdap61(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.findCdap61(aprydsDto);

	}

	/**
     * 전보희망코드 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
	public List<CamelCaseMap> getCdap63(AppaiappryearendsDto aprydsDto){
		return appaiappryearendsMapper.findCdap63(aprydsDto);

	}


}
