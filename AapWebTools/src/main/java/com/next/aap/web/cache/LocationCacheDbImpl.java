package com.next.aap.web.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.AssemblyConstituencyDto;
import com.next.aap.web.dto.DistrictDto;
import com.next.aap.web.dto.ParliamentConstituencyDto;
import com.next.aap.web.dto.StateDto;

@Component
public class LocationCacheDbImpl {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AapService aapService;
	
	private boolean loadCacheOnStartup;
	
	private boolean cacheLoaded = false;;
	
	
	@Value("${load.cache.on.startup:true}")
	public void setDatabaseName(boolean loadCacheOnStartup) {
		this.loadCacheOnStartup = loadCacheOnStartup;
	}

	private List<StateDto> allStates;
	private Map<Long, List<DistrictDto>> stateToDistrictMap;
	private Map<Long, List<AssemblyConstituencyDto>> districtToAcMap;
	private Map<Long, List<AssemblyConstituencyDto>> stateToAcMap;
	private Map<Long, List<ParliamentConstituencyDto>> stateToPcMap;
	
	@PostConstruct
	public void init(){
		if(loadCacheOnStartup){
			refreshCache();
		}
	}
	private boolean alreadyRunning = false;
	/* (non-Javadoc)
	 * @see com.next.aap.server.cache.db.LocaltionCache#refreshCacheAsync()
	 */
	public String refreshCacheAsync(){
		
		synchronized (LocationCacheDbImpl.class) {
			if(alreadyRunning){
				return "Task Already Running";
			}
			Runnable task = new Runnable() {
				
				public void run() {
					refreshCache();
				}
			};
			new Thread(task).start();
		}
		return "Task Started";
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.cache.db.LocaltionCache#refreshCache()
	 */
	public void refreshCache(){
		logger.info("refreshing Location cache");
		List<StateDto> allDbStates;
		Map<Long, List<AssemblyConstituencyDto>> localDistrictToAcMap = new HashMap<>();
		Map<Long, List<DistrictDto>> localStateToDistrictMap = new HashMap<>();
		Map<Long, List<AssemblyConstituencyDto>> localStateToAcMap = new HashMap<>();
		Map<Long, List<ParliamentConstituencyDto>> localStateToPcMap = new HashMap<>();
		try {
			allDbStates = aapService.getAllStates();
			
			//Create District Cache
			List<DistrictDto> allDistricts;
			List<AssemblyConstituencyDto> allAssemblyConstituencies;
			List<AssemblyConstituencyDto> allStateAssemblyConstituencies = new ArrayList<AssemblyConstituencyDto>();
			List<ParliamentConstituencyDto> parliamentConstituencyDtos;
			for(StateDto oneStateDto:allDbStates){
				allStateAssemblyConstituencies.clear();
				allDistricts = aapService.getAllDistrictOfState(oneStateDto.getId());
				localStateToDistrictMap.put(oneStateDto.getId(), allDistricts);
				//Create Assembly COnstituency Cache
				for(DistrictDto oneDistrictDto:allDistricts){
					allAssemblyConstituencies = aapService.getAllAssemblyConstituenciesOfDistrict(oneDistrictDto.getId());
					allStateAssemblyConstituencies.addAll(allAssemblyConstituencies);
					localDistrictToAcMap.put(oneDistrictDto.getId(), allAssemblyConstituencies);
					
				}
				localStateToAcMap.put(oneStateDto.getId(), allStateAssemblyConstituencies);
				
				parliamentConstituencyDtos = aapService.getAllParliamentConstituenciesOfState(oneStateDto.getId());
				localStateToPcMap.put(oneStateDto.getId(), parliamentConstituencyDtos);
			}
			this.allStates = allDbStates;
			this.stateToDistrictMap = localStateToDistrictMap;
			this.districtToAcMap = localDistrictToAcMap;
			this.stateToAcMap = localStateToAcMap;
			this.stateToPcMap = localStateToPcMap;
		}catch (Exception e) {
			logger.error("Error occured while refreshing Location cache",e);
		}finally{
			logger.info("refreshing Location cache Ended");
		}
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.cache.db.LocaltionCache#getAllStates()
	 */
	public List<StateDto> getAllStates() {
		return allStates;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.cache.db.LocaltionCache#getAllDistrictOfState(java.lang.Long)
	 */
	public List<DistrictDto> getAllDistrictOfState(Long stateId) {
		return stateToDistrictMap.get(stateId);
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.cache.db.LocaltionCache#getAllAssemblyConstituenciesOfDistrict(java.lang.Long)
	 */
	public List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfDistrict(Long districtId) {
		return districtToAcMap.get(districtId);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.cache.db.LocaltionCache#getAllAssemblyConstituenciesOfState(java.lang.Long)
	 */
	public List<AssemblyConstituencyDto> getAllAssemblyConstituenciesOfState(Long stateId) {
		return stateToAcMap.get(stateId);
	}
	public List<ParliamentConstituencyDto> getAllParliamentConstituenciesOfState(Long stateId) {
		return stateToPcMap.get(stateId);
	}

}
