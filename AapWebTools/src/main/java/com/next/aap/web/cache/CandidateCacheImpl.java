package com.next.aap.web.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.core.exception.AppException;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.CandidateDto;

@Component
public class CandidateCacheImpl {

	@Autowired
	private AapService aapService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostConstruct
	public void init(){
		refreshCache();
	}
	
	Map<String, CandidateDto> candidateMap = new HashMap<>();
	List<CandidateDto> allCandidates = new ArrayList<>();
	
	public void refreshCache(){
		try {
			allCandidates = aapService.getCandidates(600, 0);
			Map<String, CandidateDto> localCandidateMap = new HashMap<String, CandidateDto>(allCandidates.size());
			for(CandidateDto oneCandidate:allCandidates){
				localCandidateMap.put(createKey(oneCandidate.getUrlTextPart1().toLowerCase(), oneCandidate.getUrlTextPart2().toLowerCase()), oneCandidate);
				if(StringUtil.isEmpty(oneCandidate.getCandidateFbPageId())){
					oneCandidate.setCandidateFbPageId("AamAadmiParty");
				}
				if(StringUtil.isEmpty(oneCandidate.getTwitterId())){
					oneCandidate.setTwitterId("AamAadmiParty");
				}
				if(StringUtil.isEmpty(oneCandidate.getImageUrl32())){
					oneCandidate.setImageUrl32("https://s3.amazonaws.com/myaap/test/candidate/profile/icon-person-32.png");
				}
				if(StringUtil.isEmpty(oneCandidate.getImageUrl64())){
					oneCandidate.setImageUrl64("https://s3.amazonaws.com/myaap/test/candidate/profile/icon-person-64.png");
				}
			}
			candidateMap = localCandidateMap;
		} catch (AppException e) {
			logger.error("Unable to refresh Cache", e);
		} 
	}
	public CandidateDto getCandidate(String urlPart1, String urlPart2){
		String key = createKey(urlPart1, urlPart2);
		return candidateMap.get(key);
	}
	public List<CandidateDto> getAllCandidates(){
		return allCandidates;
	}
	private String createKey(String urlPart1, String urlPart2){
		return urlPart1 +"_" +urlPart2;
	}

}
