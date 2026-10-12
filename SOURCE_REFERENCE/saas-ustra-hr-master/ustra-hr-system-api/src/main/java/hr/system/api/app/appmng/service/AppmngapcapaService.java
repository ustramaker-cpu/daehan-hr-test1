package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapcapaDto;
import hr.system.api.app.appmng.mapper.AppmngapcapaMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppmngapcapaService {

    private final AppmngapcapaMapper appmngapcapaMapper;

    public List<CamelCaseMap> findAppmngapcapaList(AppmngapcapaDto appmngapcapaDto){
        return appmngapcapaMapper.findAppmngapcapaList(appmngapcapaDto);
    }

    public int insertupdatedeleteApcapa(AppmngapcapaDto appmngappgcapaDto, List<AppmngapcapaDto> appmngapcapaDtoList )  {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(appmngapcapaDtoList)) {
            for(AppmngapcapaDto innerappmngapcapaDto : appmngapcapaDtoList) {
                if(innerappmngapcapaDto.getCapaKind() != null)
                    innerappmngapcapaDto.setCapaKind(org.springframework.web.util.HtmlUtils.htmlUnescape(innerappmngapcapaDto.getCapaKind()));
                if(innerappmngapcapaDto.getCapaName() != null)
                    innerappmngapcapaDto.setCapaName(org.springframework.web.util.HtmlUtils.htmlUnescape(innerappmngapcapaDto.getCapaName()));
                if(innerappmngapcapaDto.getCapaDef() != null)
                    innerappmngapcapaDto.setCapaDef(org.springframework.web.util.HtmlUtils.htmlUnescape(innerappmngapcapaDto.getCapaDef()));

                switch(innerappmngapcapaDto.getRowStatus()) {
                    case DataConst.INSERT : // 등록
                        int capaSeqNo = appmngapcapaMapper.checkYearSeqNo(innerappmngapcapaDto);
                        innerappmngapcapaDto.setCapaSeqNo(String.valueOf(capaSeqNo));
                    case DataConst.UPDATE : // 수정
                        result += appmngapcapaMapper.saveAppgcapa(innerappmngapcapaDto);
                        break;
                    case DataConst.DELETE : // 삭제
                        result += appmngapcapaMapper.deleteAppgcapa(innerappmngapcapaDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerappmngapcapaDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int copyAppgcapa(AppmngapcapaDto appmngapcapaDto) {
        int result = appmngapcapaMapper.deleteYearAppgcapa(appmngapcapaDto);
        result += appmngapcapaMapper.insertselectAppgcapa(appmngapcapaDto);

        return result;
    }

    public int saveappmngapcapaupload(AppmngapcapaDto appmngappgcapaDto , List<AppmngapcapaDto> appmngapcapaDtoList) {
        int result = 0;
        int cnt = 0;
        log.info("appmngappgcapaDtoList====>"+appmngapcapaDtoList);
        if (CollectionUtils.isNotEmpty(appmngapcapaDtoList)) {
            for (AppmngapcapaDto innerappmngapcapaDto : appmngapcapaDtoList) {
                log.info("innerappmngapcapaDto.getRowStatus()====>"+innerappmngapcapaDto.getRowStatus());
                switch(innerappmngapcapaDto.getRowStatus()) {
                    case DataConst.INSERT :
                        result += appmngapcapaMapper.insertAppgcapa(innerappmngapcapaDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerappmngapcapaDto.getRowStatus());
                        //throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }
}
