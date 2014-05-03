package com.next.aap.core.util;

import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.next.aap.core.service.AapService;

@Component
public class PollQuestionAnswerUpdater {

	LinkedBlockingQueue<Vote> voteQueue = new LinkedBlockingQueue<>(); 

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AapService aapService;
	
	@PostConstruct
	public void init(){
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				alwaysRunning();
				
			}
		};
		new Thread(runnable).start();
		System.out.println("alwaysRunning started");
	}
	public void alwaysRunning(){
		System.out.println("Starting alwaysRunning");
		try{
			Vote vote;
			while(true){
				vote = voteQueue.poll();
				if(vote != null){
					logger.info("Got Next Vote "+vote);
					updatePollAnswerStats(vote.getUserId(), vote.getPollQuestionId(), vote.getPollAnswerId(), vote.getPollExisitngAnswerId());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			//Doesnt matter what happens keep it running
		}
	}
	
	public void updatePollAnswerStatsAsync(Long userId, Long pollQuestionId, Long pollAnswerId,Long pollExisitngAnswerId){
		voteQueue.add(new Vote(userId, pollQuestionId, pollAnswerId, pollExisitngAnswerId));
	}
	public void updatePollAnswerStats(Long userId, Long pollQuestionId, Long pollAnswerId,Long pollExisitngAnswerId){
		aapService.updatePollVoteAnswerTotalCount(pollAnswerId, pollExisitngAnswerId);
		logger.info("Updated Count");

	}
	
	private class Vote{
		private Long userId;
		private Long pollQuestionId;
		private Long pollAnswerId;
		private Long pollExisitngAnswerId;
		
		public Vote(Long userId, Long pollQuestionId, Long pollAnswerId, Long pollExisitngAnswerId) {
			super();
			this.userId = userId;
			this.pollQuestionId = pollQuestionId;
			this.pollAnswerId = pollAnswerId;
			this.pollExisitngAnswerId = pollExisitngAnswerId;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public Long getPollQuestionId() {
			return pollQuestionId;
		}
		public void setPollQuestionId(Long pollQuestionId) {
			this.pollQuestionId = pollQuestionId;
		}
		public Long getPollAnswerId() {
			return pollAnswerId;
		}
		public void setPollAnswerId(Long pollAnswerId) {
			this.pollAnswerId = pollAnswerId;
		}
		public Long getPollExisitngAnswerId() {
			return pollExisitngAnswerId;
		}
		public void setPollExisitngAnswerId(Long pollExisitngAnswerId) {
			this.pollExisitngAnswerId = pollExisitngAnswerId;
		}
		@Override
		public String toString() {
			return "Vote [userId=" + userId + ", pollQuestionId=" + pollQuestionId + ", pollAnswerId=" + pollAnswerId + ", pollExisitngAnswerId="
					+ pollExisitngAnswerId + "]";
		}
		
	}

	public AapService getAapService() {
		return aapService;
	}
	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}
}
