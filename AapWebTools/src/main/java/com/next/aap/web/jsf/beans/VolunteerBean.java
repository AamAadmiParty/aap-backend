package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.next.aap.core.exception.AppException;
import com.next.aap.core.service.AapService;
import com.next.aap.web.dto.InterestDto;
import com.next.aap.web.dto.InterestGroupDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.VolunteerDto;
import com.next.aap.web.jsf.beans.model.InterestGroupDtoModel;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;


@Component
//@Scope("session")
@ViewScoped
//@URLMapping(id = "volunteerBean", beanName = "volunteerBean", pattern = "/profile", viewId = "/WEB-INF/jsf/volunteer.xhtml")
@URLBeanName("volunteerBean")
public class VolunteerBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;

	private VolunteerDto selectedVolunteer;

	private List<InterestGroupDtoModel> interestGroupDtoModels;
	
	private Map<Long, Boolean> selectedInterestMap = new HashMap<Long, Boolean>();

	@Autowired
	private AapService aapService;

	// @URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	//@URLAction(onPostback = false)
	//@PostConstruct
	public void init(UserDto user) throws Exception {
		List<InterestGroupDto> interestGroups = aapService.getAllVolunterInterests();
		selectedInterestMap.clear();
		interestGroupDtoModels = new ArrayList<>();
		if(interestGroups != null && !interestGroups.isEmpty()){
			for(InterestGroupDto oneInterestGroupDto:interestGroups){
				interestGroupDtoModels.add(new InterestGroupDtoModel(oneInterestGroupDto));
			}
		}
		if(user != null){
			selectedVolunteer = aapService.getVolunteerDataForUser(user.getId());
			List<InterestDto> userInterests = aapService.getuserInterests(user.getId());
			if(userInterests != null && userInterests.size() > 0){
				for(InterestDto oneInterestDto:userInterests){
					selectedInterestMap.put(oneInterestDto.getId(), true);
				}
			}
		}
		if(selectedVolunteer == null){
			selectedVolunteer = new VolunteerDto();	
		}
		
	}

	public List<InterestGroupDtoModel> getInterestGroupDtoModels() {
		return interestGroupDtoModels;
	}

	public void setInterestGroupDtoModels(List<InterestGroupDtoModel> interestGroupDtoModels) {
		this.interestGroupDtoModels = interestGroupDtoModels;
	}

	public Map<Long, Boolean> getSelectedInterestMap() {
		return selectedInterestMap;
	}

	public void setSelectedInterestMap(Map<Long, Boolean> selectedInterestMap) {
		this.selectedInterestMap = selectedInterestMap;
	}

	public VolunteerDto getSelectedVolunteer() {
		return selectedVolunteer;
	}

	public void setSelectedVolunteer(VolunteerDto selectedVolunteer) {
		this.selectedVolunteer = selectedVolunteer;
	}
	
	public List<Long> getSelectedInterestIds(){
		System.out.println("Total Selected : " + selectedInterestMap.size());
		List<Long> selectedInterests = new ArrayList<>();
		for(Entry<Long, Boolean> oneInterest:selectedInterestMap.entrySet()){
			System.out.println("OneInterest : " + oneInterest.getKey() +" , "+oneInterest.getValue());
			if(oneInterest.getValue()){
				selectedInterests.add(oneInterest.getKey());
			}
		}
		return selectedInterests;
	}
	
}
