package com.next.aap.web.jsf.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.next.aap.core.service.AapService;
import com.next.aap.web.ItemList;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.cache.AapDataCacheDbImpl;
import com.next.aap.web.dto.PollQuestionDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;

//@Scope("session")
@ViewScoped
@ManagedBean
@URLBeanName("votingBean")
public class VotingBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	List<PollQuestionDto> pollQuestions;
	Map<Long, String> selectedAnswer = new HashMap<Long, String>();
	
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCache aapDataCacheDbImpl;
	
	@ManagedProperty("#{aapService}")
	protected AapService aapService;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser();//true,buildLoginUrl("/home"));
		System.out.println("loggedInUser = "+loggedInUser);
		long livingAcId = 0;
		long votingAcId = 0;
		long livingPcId = 0;
		long votingPcId = 0;
		if(loggedInUser != null){
			if(loggedInUser.getAssemblyConstituencyLivingId() != null){
				livingAcId = loggedInUser.getAssemblyConstituencyLivingId();
			}
			if(loggedInUser.getAssemblyConstituencyVotingId() != null){
				votingAcId = loggedInUser.getAssemblyConstituencyVotingId();
			}
			if(loggedInUser.getParliamentConstituencyLivingId() != null){
				livingPcId = loggedInUser.getParliamentConstituencyLivingId();
			}
			if(loggedInUser.getParliamentConstituencyVotingId() != null){
				votingPcId = loggedInUser.getParliamentConstituencyVotingId();
			}
		}
		ItemList<PollQuestionDto> pollItemList = aapDataCacheDbImpl.getPollQuestionDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId);
		pollQuestions = pollItemList.getItems();
	}
	public void saveVote(Long questionId){
		System.out.println("questionId="+questionId);
		System.out.println("selected answer="+selectedAnswer.get(questionId));
		System.out.println("selected answer="+selectedAnswer);
		
		if(selectedAnswer.get(questionId) == null){
			System.out.println("Please select an answer");
			sendErrorMessageToJsfScreen("Please select an answer");
		}
		if(isValidInput()){
			UserDto user = getLoggedInUser();
			System.out.println("user.getId()="+user.getId().getClass());
			System.out.println("questionId="+questionId.getClass());
			System.out.println("selectedAnswer.get(questionId)="+selectedAnswer.get(questionId).getClass());
			String result = aapService.savePollVote(user.getId(), questionId, Long.parseLong(selectedAnswer.get(questionId)));
			sendInfoMessageToJsfScreen(result);
			//save vote for user
		}
	}
	
	public AapDataCache getAapDataCacheDbImpl() {
		return aapDataCacheDbImpl;
	}

	public void setAapDataCacheDbImpl(AapDataCache aapDataCacheDbImpl) {
		this.aapDataCacheDbImpl = aapDataCacheDbImpl;
	}

	public List<PollQuestionDto> getPollQuestions() {
		return pollQuestions;
	}

	public void setPollQuestions(List<PollQuestionDto> pollQuestions) {
		this.pollQuestions = pollQuestions;
	}

	public Map<Long, String> getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(Map<Long, String> selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
	public AapService getAapService() {
		return aapService;
	}
	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}
}
