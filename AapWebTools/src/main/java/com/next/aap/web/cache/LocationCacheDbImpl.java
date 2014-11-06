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
import com.next.aap.web.dto.CountryDto;
import com.next.aap.web.dto.CountryRegionAreaDto;
import com.next.aap.web.dto.CountryRegionDto;
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
	private Map<Long,StateDto> allStatesMapById;
	private Map<Long, List<DistrictDto>> stateToDistrictMap;
	private Map<Long,DistrictDto> allDistrictMapById;
	private Map<Long,AssemblyConstituencyDto> allAssemblyConstituencyMapById;
	private Map<Long, List<AssemblyConstituencyDto>> districtToAcMap;
	private Map<Long, List<AssemblyConstituencyDto>> stateToAcMap;
	private Map<Long, List<ParliamentConstituencyDto>> stateToPcMap;
	private Map<Long,ParliamentConstituencyDto> allPcMapById;	
	private List<CountryDto> allCountries;
	private Map<Long, List<CountryRegionDto>> allCountryRegions;
	private Map<Long, List<CountryRegionAreaDto>> allCountryRegionAreas;
	
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
				
				@Override
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
		Map<Long, StateDto> allDStatesMapById;
		Map<Long, List<AssemblyConstituencyDto>> localDistrictToAcMap = new HashMap<>();
		Map<Long, DistrictDto> allDbDistrictMapById = new HashMap<>();
		Map<Long, List<DistrictDto>> localStateToDistrictMap = new HashMap<>();
		Map<Long, List<AssemblyConstituencyDto>> localStateToAcMap = new HashMap<>();
		Map<Long, AssemblyConstituencyDto> allDbAssemblyConstituencyMapById = new HashMap<>();
		Map<Long, List<ParliamentConstituencyDto>> localStateToPcMap = new HashMap<>();
		Map<Long, ParliamentConstituencyDto> allDbPcMapById = new HashMap<>();
		
		allDStatesMapById = new HashMap<>();
		
		List<CountryDto> allCountriesLocal;
		Map<Long, List<CountryRegionDto>> allCountryRegionsLocal = new HashMap<>();
		Map<Long, List<CountryRegionAreaDto>> allCountryRegionAreasLocal = new HashMap<>();

		try {
			allDbStates = aapService.getAllStates();
			
			//Create District Cache
			List<DistrictDto> allDistricts;
			List<AssemblyConstituencyDto> allAssemblyConstituencies;
			List<AssemblyConstituencyDto> allStateAssemblyConstituencies = new ArrayList<AssemblyConstituencyDto>();
			List<ParliamentConstituencyDto> parliamentConstituencyDtos;
			for(StateDto oneStateDto:allDbStates){
                if (oneStateDto.getId().equals(12L)) {
                    logger.warn("Loading Delhi State");
                }
				allDStatesMapById.put(oneStateDto.getId(), oneStateDto);
                allStateAssemblyConstituencies = aapService.getAllAssemblyConstituenciesOfState(oneStateDto.getId());
				allDistricts = aapService.getAllDistrictOfState(oneStateDto.getId());
				localStateToDistrictMap.put(oneStateDto.getId(), allDistricts);
				//Create Assembly COnstituency Cache
				for(DistrictDto oneDistrictDto:allDistricts){
					allDbDistrictMapById.put(oneDistrictDto.getId(), oneDistrictDto);
                    allAssemblyConstituencies = aapService.getAllAssemblyConstituenciesOfDistrict(oneDistrictDto.getId());
					localDistrictToAcMap.put(oneDistrictDto.getId(), allAssemblyConstituencies);
					for(AssemblyConstituencyDto oneAc:allAssemblyConstituencies){
						allDbAssemblyConstituencyMapById.put(oneAc.getId(), oneAc);
                        if (oneStateDto.getId().equals(12L)) {
                            logger.warn("     oneAc - {}", oneAc);
                        }
					}
					
				}
                if (oneStateDto.getId().equals(12L)) {
                    logger.warn(" *** allStateAssemblyConstituencies - {}", allStateAssemblyConstituencies);
                }
				localStateToAcMap.put(oneStateDto.getId(), allStateAssemblyConstituencies);
				
				parliamentConstituencyDtos = aapService.getAllParliamentConstituenciesOfState(oneStateDto.getId());
				for(ParliamentConstituencyDto onePc:parliamentConstituencyDtos){
					allDbPcMapById.put(onePc.getId(), onePc);
				}
				localStateToPcMap.put(oneStateDto.getId(), parliamentConstituencyDtos);
			}
			allCountriesLocal = aapService.getNriCountries();
			
			List<CountryRegionDto> allCountryRegionsList = aapService.getAllCountryRegions();
			List<CountryRegionDto> countryCountryRegionList;
			for(CountryRegionDto oneCountryRegionDto : allCountryRegionsList){
				countryCountryRegionList = allCountryRegionsLocal.get(oneCountryRegionDto.getCountryId());
				if(countryCountryRegionList == null){
					countryCountryRegionList = new ArrayList<>();
					allCountryRegionsLocal.put(oneCountryRegionDto.getCountryId(), countryCountryRegionList);
				}
				countryCountryRegionList.add(oneCountryRegionDto);
			}
			
			List<CountryRegionAreaDto> allCountryRegionAreaList = aapService.getAllCountryRegionAreas();
			List<CountryRegionAreaDto> countryCountryRegionAreaList;
			for(CountryRegionAreaDto oneCountryRegionArea:allCountryRegionAreaList){
				countryCountryRegionAreaList = allCountryRegionAreasLocal.get(oneCountryRegionArea.getCountryRegionId());
				if(countryCountryRegionAreaList == null){
					countryCountryRegionAreaList = new ArrayList<>();
					allCountryRegionAreasLocal.put(oneCountryRegionArea.getCountryRegionId(), countryCountryRegionAreaList);
				}
				countryCountryRegionAreaList.add(oneCountryRegionArea);
			}
			
			this.allStates = allDbStates;
			this.allStatesMapById = allDStatesMapById;
			this.stateToDistrictMap = localStateToDistrictMap;
			this.districtToAcMap = localDistrictToAcMap;
			this.stateToAcMap = localStateToAcMap;
			this.stateToPcMap = localStateToPcMap;
			this.allCountries = allCountriesLocal;
			this.allCountryRegions = allCountryRegionsLocal;
			this.allCountryRegionAreas = allCountryRegionAreasLocal;
			this.allPcMapById = allDbPcMapById;
			this.allDistrictMapById = allDbDistrictMapById;
			this.allAssemblyConstituencyMapById = allDbAssemblyConstituencyMapById;
			
			logger.info("total acs = "+ allAssemblyConstituencyMapById.size());
			logger.info("total pcs = "+ allPcMapById.size());
			logger.info("total Districts = "+ allDistrictMapById.size());
			logger.info("total States = "+ allStatesMapById.size());
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

	public StateDto getStateById(Long stateId) {
		return allStatesMapById.get(stateId);
	}
	public ParliamentConstituencyDto getParliamentConstituenciesById(Long pcId) {
		return allPcMapById.get(pcId);
	}
	public DistrictDto getDistrictById(Long districtId) {
		return allDistrictMapById.get(districtId);
	}
	public AssemblyConstituencyDto getAssemblyConstituencyById(Long acId) {
		return allAssemblyConstituencyMapById.get(acId);
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
        System.out.println("Getting All AC of State " + stateId);
        System.out.println("All State Ids are " + stateToAcMap.keySet());
        System.out.println("All ACs are " + stateToAcMap.get(stateId));
		return stateToAcMap.get(stateId);
	}
	public List<ParliamentConstituencyDto> getAllParliamentConstituenciesOfState(Long stateId) {
		return stateToPcMap.get(stateId);
	}
	
	public List<CountryDto> getAllNriCountries(){
		return allCountries;
	}

	public List<CountryRegionDto> getCountryRegionsOfCountry(Long countryId){
		return allCountryRegions.get(countryId);
	}
	public List<CountryRegionAreaDto> getCountryRegionAreasOfCountryRegion(Long countryRegionId){
		return allCountryRegionAreas.get(countryRegionId);
	}

}
