package com.next.aap.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.AppPermission;
import com.next.aap.web.dto.PollAnswerDto;
import com.next.aap.web.dto.LoginAccountDto;
import com.next.aap.web.dto.PollQuestionDto;
import com.next.aap.web.dto.UserDto;
import com.next.aap.web.dto.UserRolePermissionDto;
import com.next.aap.web.util.ClientPermissionUtil;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBean
//@Scope("session")
@ViewScoped
@URLMapping(id = "pollAdminBean", beanName="pollAdminBean", pattern = "/admin/poll", viewId = "/WEB-INF/jsf/admin_poll.xhtml")
@URLBeanName("pollAdminBean")
public class PollAdminBean extends BaseMultiPermissionAdminJsfBean {

	private static final long serialVersionUID = 1L;

	private PollQuestionDto selectedPollQuestion;;
	
	private boolean showList = true;
	private List<PollAnswerDto> answerList;
	private PollAnswerDto selectedAnswer;
	private boolean newAnswer = false;
	private boolean showAnswerList = true;
	
	private List<PollQuestionDto> pollQuestionList;
	public PollAdminBean(){
		super("/admin/poll", AppPermission.CREATE_POLL,AppPermission.UPDATE_POLL, AppPermission.DELETE_POLL, AppPermission.APPROVE_POLL);
		selectedAnswer = new PollAnswerDto();
	}
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		if(!checkUserAccess()){
			return;
		}
		refreshPollQuestionList();
	}
	private void refreshPollQuestionList(){
		pollQuestionList = aapService.getPollQuestion(menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
		answerList = new ArrayList<>();
	}

	public LoginAccountDto getLoginAccounts() {
		return getLoggedInAccountsFromSesion();
	}
	public void cancelTweet(){
		showAnswerList = true;
	}
	public void createPollQuestionAnswer(){
		selectedAnswer = new PollAnswerDto();
		newAnswer = true;
		showAnswerList = false;
	}
	public void addPollQuestionAnswer(){
		if(newAnswer){
			answerList.add(selectedAnswer);
		}
		showAnswerList = true;
	}

	public UserDto getUser() {
		return getLoggedInUser();
	}
	public PollQuestionDto getSelectedPollQuestion() {
		return selectedPollQuestion;
	}
	public void setSelectedPollQuestion(PollQuestionDto selectedPollQuestion) {
		this.selectedPollQuestion = selectedPollQuestion;
		showList = false;
		answerList = aapService.getPollAnswers(selectedPollQuestion.getId());
	}
	public boolean isSaveDraft(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isAllowed(AppPermission.CREATE_POLL, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()) ||
				ClientPermissionUtil.isAllowed(AppPermission.UPDATE_POLL, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isSaveAndPublish(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return (ClientPermissionUtil.isAllowed(AppPermission.CREATE_POLL, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()) ||
				ClientPermissionUtil.isAllowed(AppPermission.UPDATE_POLL, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType())) &&
				ClientPermissionUtil.isAllowed(AppPermission.APPROVE_POLL, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isPublish(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return ClientPermissionUtil.isAllowed(AppPermission.APPROVE_POLL, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType());
	}
	public boolean isEditAllowed(){
		UserRolePermissionDto userRolePermissionDto = getUserRolePermissionInSesion();
		return (ClientPermissionUtil.isAllowed(AppPermission.CREATE_POLL, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()) ||
				ClientPermissionUtil.isAllowed(AppPermission.UPDATE_POLL, userRolePermissionDto, menuBean.getAdminSelectedLocationId(), menuBean.getLocationType()));
	}
	public void saveAndPublishPost() {
		savePost();
		publishPost();
	}
	public void publishPost(){
		try{
			if(selectedPollQuestion == null){
				sendErrorMessageToJsfScreen("No pollQuestion selected to publish");
			}
			if(selectedPollQuestion.getId() == null || selectedPollQuestion.getId() <= 0){
				sendErrorMessageToJsfScreen("Please save the PollQuestion first");
			}
			if(!isPublish()){
				sendErrorMessageToJsfScreen("You do not have permission to publish a pollQuestion");
			}
			if(isValidInput()){
				selectedPollQuestion = aapService.publishPollQuestion(selectedPollQuestion.getId());
				sendInfoMessageToJsfScreen("PollQuestion Published Succesfully");
				refreshPollQuestionList();
			}
			
		}catch(Exception ex){
			sendErrorMessageToJsfScreen("Unable to save Post",ex);
		}
	}
	public void savePost(){
		try{
			if(StringUtil.isEmpty(selectedPollQuestion.getContent())){
				sendErrorMessageToJsfScreen("Please enter Poll Question Content");
			}

			if(isValidInput()){
				selectedPollQuestion = aapService.savePollQuestion(selectedPollQuestion, answerList, menuBean.getLocationType(), menuBean.getAdminSelectedLocationId());
				sendInfoMessageToJsfScreen("PollQuestion saved succesfully");
				refreshPollQuestionList();
				showList = true;
			}
				
		}catch(Exception ex){
			sendErrorMessageToJsfScreen("Unable to save Post",ex);
		}
		
	}
	public void createPollQuestion(){
		selectedPollQuestion = new PollQuestionDto();
		showList = false;
		answerList = new ArrayList<>();
	}
	public void cancel(){
		createPollQuestion();
		showList = true;
	}
	public void deleteTweet(){
		answerList.remove(selectedAnswer);
	}
	
	public boolean isShowList() {
		return showList;
	}
	public void setShowList(boolean showList) {
		this.showList = showList;
	}
	public List<PollQuestionDto> getPollQuestionList() {
		return pollQuestionList;
	}
	public void setPollQuestionList(List<PollQuestionDto> pollQuestionList) {
		this.pollQuestionList = pollQuestionList;
	}
	public List<PollAnswerDto> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<PollAnswerDto> answerList) {
		this.answerList = answerList;
	}
	public PollAnswerDto getSelectedAnswer() {
		return selectedAnswer;
	}
	public void setSelectedAnswer(PollAnswerDto selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
		newAnswer = false;
		showAnswerList = false;
	}
	public boolean isNewAnswer() {
		return newAnswer;
	}
	public void setNewAnswer(boolean newAnswer) {
		this.newAnswer = newAnswer;
	}
	public boolean isShowAnswerList() {
		return showAnswerList;
	}
	public void setShowAnswerList(boolean showAnswerList) {
		this.showAnswerList = showAnswerList;
	}


}
