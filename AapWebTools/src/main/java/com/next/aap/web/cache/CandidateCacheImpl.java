package com.next.aap.web.cache;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

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
	Map<Long, CandidateDto> candidateMapByPcId = new HashMap<>();
    Map<Long, CandidateDto> candidateMapByAcId = new HashMap<>();
	
	Map<String, Set<CandidateDto>> candidateMapByStateName = new HashMap<>();
    Map<Long, Set<CandidateDto>> candidateMapByElectionId = new HashMap<>();
	
	
	public void refreshCache(){
		try {
            allCandidates = aapService.getCandidates(800, 0);
            logger.info("Total Candidate From Database = " + allCandidates.size());
			Map<String, CandidateDto> localCandidateMap = new HashMap<String, CandidateDto>(allCandidates.size());
			Map<String, Set<CandidateDto>> localCandidateMapByStateName = new HashMap<>();
            Map<Long, Set<CandidateDto>> localCandidateMapByElectionId = new HashMap<>();
			Set<CandidateDto> candidateSetForState;
            Set<CandidateDto> candidateSetForElection;
            candidateMapByPcId.clear();
            candidateMapByAcId.clear();
			for(CandidateDto oneCandidate:allCandidates){
                if (oneCandidate.getCandidateType().equalsIgnoreCase("MLA")) {
                    logger.info("   oneCandidate = " + oneCandidate.getId() + " , " + oneCandidate.getName() + ", " + oneCandidate.getCandidateType() + ", " + oneCandidate.getElectionId());
                }

                if ("MP".equalsIgnoreCase(oneCandidate.getCandidateType())) {
                    candidateMapByPcId.put(oneCandidate.getParliamentConstituencyId(), oneCandidate);
                    localCandidateMap.put(createKey(oneCandidate.getUrlTextPart1().toLowerCase(), oneCandidate.getUrlTextPart2().toLowerCase()), oneCandidate);
                } else {
                    candidateMapByAcId.put(oneCandidate.getAssemblyConstituencyId(), oneCandidate);
                    localCandidateMap.put(createAcKey(oneCandidate.getUrlTextPart1().toLowerCase(), oneCandidate.getUrlTextPart2().toLowerCase()), oneCandidate);
                }
                candidateSetForState = getSortedSetOfCandidateOfState(localCandidateMapByStateName, oneCandidate.getStateName());
                candidateSetForElection = getSortedSetOfCandidateOfElection(localCandidateMapByElectionId, oneCandidate.getElectionId());
                if ("MP".equalsIgnoreCase(oneCandidate.getCandidateType())) {
                    candidateSetForState.add(oneCandidate);
                }
                if (oneCandidate.getCandidateType().equalsIgnoreCase("MLA")) {
                    logger.info("   Adding oneCandidate = " + oneCandidate.getId() + " , " + oneCandidate.getName() + ", " + oneCandidate.getCandidateType() + ", " + oneCandidate.getElectionId());
                }
                candidateSetForElection.add(oneCandidate);
                logger.info("   Total Candidate = " + candidateSetForElection.size() + " for elction Id " + oneCandidate.getElectionId());
				
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
			candidateMapByStateName = localCandidateMapByStateName;
            candidateMapByElectionId = localCandidateMapByElectionId;
            printCandidateMapByElectionId();
		} catch (AppException e) {
			logger.error("Unable to refresh Cache", e);
		} 
	}
	private Set<CandidateDto> getSortedSetOfCandidateOfState(Map<String, Set<CandidateDto>> localCandidateMapByStateName, String  stateName){
		stateName = stateName.toLowerCase();
		Set<CandidateDto> sortedSet = localCandidateMapByStateName.get(stateName);
		if(sortedSet == null){
			sortedSet = new TreeSet<>(new Comparator<CandidateDto>() {
				@Override
				public int compare(CandidateDto o1, CandidateDto o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			localCandidateMapByStateName.put(stateName, sortedSet);
		}
		return sortedSet;
		
	}
	
	private Set<CandidateDto> getSortedSetOfCandidateOfElection(Map<Long, Set<CandidateDto>> localCandidateMapByElectionId, Long  electionId){
        Set<CandidateDto> sortedSet = localCandidateMapByElectionId.get(electionId);
        if(sortedSet == null){
            logger.info("   Creating new Set for Election Id  = " + electionId);
            sortedSet = new TreeSet<>(new Comparator<CandidateDto>() {
                @Override
                public int compare(CandidateDto o1, CandidateDto o2) {
                    int compareResult = -1;
                    if (o1.getCandidateType().equalsIgnoreCase("MLA")) {
                        compareResult = o1.getAcName().compareTo(o2.getAcName());
                        if (compareResult == 0) {
                            return -1;
                        }
                    } else {
                        compareResult = o1.getPcName().compareTo(o2.getPcName());
                        if (compareResult == 0) {
                            return -1;
                        }
                    }
                    return compareResult;
                }
            });
            localCandidateMapByElectionId.put(electionId, sortedSet);
        }
        return sortedSet;
        
    }
	

	public CandidateDto getCandidate(String urlPart1, String urlPart2){
		String key = createKey(urlPart1, urlPart2);
		return candidateMap.get(key);
	}

    public CandidateDto getAcCandidate(String urlPart1, String urlPart2) {
        String key = createAcKey(urlPart1, urlPart2);
        return candidateMap.get(key);
    }
	public Set<CandidateDto> getCandidatesOfState(String stateName){
		return getSortedSetOfCandidateOfState(candidateMapByStateName, stateName);
	}

    private void printCandidateMapByElectionId() {
        for (Entry<Long, Set<CandidateDto>> oneEntry : candidateMapByElectionId.entrySet()) {
            logger.info("ElectionId : " + oneEntry.getKey());
            if (((Long) 2L).equals(oneEntry.getKey())) {
                for (CandidateDto oneCandidateDto : oneEntry.getValue()) {
                    logger.info("      Candidate : " + oneCandidateDto.getName() + " , " + oneCandidateDto.getAcName());
                }
            }
        }
    }

    public Set<CandidateDto> getCandidatesOfElection(Long electionId) {
        printCandidateMapByElectionId();
        return getSortedSetOfCandidateOfElection(candidateMapByElectionId, electionId);
    }
	public List<CandidateDto> getAllCandidates(){
		return allCandidates;
	}
	public CandidateDto getCandidateByPcId(Long pcId){
		return candidateMapByPcId.get(pcId);
	}
	Random random = new Random(System.currentTimeMillis());
	public CandidateDto getCandidateByPcId(Long livingPcId, Long votingPcId){
		if(livingPcId == 0 && votingPcId == 0){
			return allCandidates.get(random.nextInt(allCandidates.size()));
		}
		CandidateDto candidate = null;
		if(livingPcId != 0 && votingPcId != 0){
			int idToPick = random.nextInt(2);
			if(idToPick == 0){
				candidate = candidateMapByPcId.get(livingPcId);
			}
			if(idToPick == 1){
				candidate =  candidateMapByPcId.get(votingPcId);
			}
		}else{
			if(livingPcId != 0){
				candidate = candidateMapByPcId.get(livingPcId);
			}else{
				candidate = candidateMapByPcId.get(votingPcId);		
			}
			
		}
		if(candidate == null){
			candidate = allCandidates.get(random.nextInt(allCandidates.size()));
		}
		return candidate;
	}

    public CandidateDto getCandidateByAcId(Long acId) {
        return candidateMapByAcId.get(acId);
    }
    public CandidateDto getCandidateByAcId(Long livingAcId, Long votingAcId) {
        Set<CandidateDto> electionCandidates = candidateMapByElectionId.get(2L);
        if (livingAcId == 0 && votingAcId == 0) {
            return electionCandidates.toArray(new CandidateDto[electionCandidates.size()])[random.nextInt(electionCandidates.size())];
        }
        CandidateDto candidate = null;
        if (livingAcId != 0 && votingAcId != 0) {
            int idToPick = random.nextInt(2);
            if (idToPick == 0) {
                candidate = candidateMapByAcId.get(livingAcId);
            }
            if (idToPick == 1) {
                candidate = candidateMapByAcId.get(votingAcId);
            }
        } else {
            if (livingAcId != 0) {
                candidate = candidateMapByAcId.get(livingAcId);
            } else {
                candidate = candidateMapByAcId.get(votingAcId);
            }

        }
        if (candidate == null) {
            candidate = electionCandidates.toArray(new CandidateDto[electionCandidates.size()])[random.nextInt(electionCandidates.size())];
        }
        return candidate;
    }
	private String createKey(String urlPart1, String urlPart2){
		return urlPart1 +"_" +urlPart2;
	}

    private String createAcKey(String urlPart1, String urlPart2) {
        return urlPart1 + "_AC_" + urlPart2;
    }

}
