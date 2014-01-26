package com.next.aap.web.jsf.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.next.aap.web.ItemList;
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
	Map<Long, Long> selectedAnswer = new HashMap<Long, Long>();
	
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCacheDbImpl aapDataCacheDbImpl;
	

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	public void init() throws Exception {
		UserDto loggedInUser = getLoggedInUser();//true,buildLoginUrl("/home"));
		System.out.println("loggedInUser = "+loggedInUser);
		if(loggedInUser == null){
			ItemList<PollQuestionDto> pollItemList = aapDataCacheDbImpl.getPollQuestionDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, 0,0,0,0);
			pollQuestions = pollItemList.getItems();
		}else{
			ItemList<PollQuestionDto> pollItemList = aapDataCacheDbImpl.getPollQuestionDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, loggedInUser.getAssemblyConstituencyLivingId(), loggedInUser.getAssemblyConstituencyVotingId(), loggedInUser.getParliamentConstituencyLivingId(), loggedInUser.getParliamentConstituencyVotingId());
			pollQuestions = pollItemList.getItems();
		}
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
			sendInfoMessageToJsfScreen("Your vote saved");
			//save vote for user
		}
	}
	
	public AapDataCacheDbImpl getAapDataCacheDbImpl() {
		return aapDataCacheDbImpl;
	}

	public void setAapDataCacheDbImpl(AapDataCacheDbImpl aapDataCacheDbImpl) {
		this.aapDataCacheDbImpl = aapDataCacheDbImpl;
	}

	public List<PollQuestionDto> getPollQuestions() {
		return pollQuestions;
	}

	public void setPollQuestions(List<PollQuestionDto> pollQuestions) {
		this.pollQuestions = pollQuestions;
	}

	public Map<Long, Long> getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(Map<Long, Long> selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
}
