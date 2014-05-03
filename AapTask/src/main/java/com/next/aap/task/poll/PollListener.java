package com.next.aap.task.poll;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.next.aap.cache.CacheKeyService;
import com.next.aap.cache.CacheService;
import com.next.aap.core.service.AapService;
import com.next.aap.task.util.AwsQueueListener;
import com.next.aap.web.cache.dto.PollStatsDto;
import com.next.aap.web.dto.PollAnswerDto;

@Component
public class PollListener extends AwsQueueListener{
	@Autowired
	public PollListener(@Value("${poll_vote_queue_name}") String queueName,@Value("${aws_access_key}") String accessKey
			, @Value("${aws_access_secret}") String accessSecret,@Value("${donation_queue_region}") String donationQueueRegion) {
		super(donationQueueRegion, queueName, accessKey, accessSecret, false);
	}
	@Autowired
	private AapService aapService;
	@Autowired
	private CacheService cacheService;

	@Override
	public void onQueueMessage(String jsonMessage) {
		try{
			logger.info("onQueueMessage "+ jsonMessage);
			JSONObject jsonObject = new JSONObject(jsonMessage);
			Long userId = jsonObject.getLong("userId");
			Long pollQuestionId = jsonObject.getLong("pollQuestionId");
			Long answerId = jsonObject.getLong("answerId");
			
			aapService.savePollVote(userId, pollQuestionId, answerId, false);
			
			List<PollAnswerDto> pollAnswers = aapService.getPollAnswers(pollQuestionId);
			PollStatsDto pollStatsDto = new PollStatsDto();
			for(PollAnswerDto onePollAnswer : pollAnswers){
				pollStatsDto.addAnswerCounts(onePollAnswer.getId(), onePollAnswer.getTotalVotes());
			}
			
			String key = CacheKeyService.createPollVoteKey(pollQuestionId);
			cacheService.saveData(key, pollStatsDto);
			
			String userPollKey = CacheKeyService.createUserPollVoteKey(userId, pollQuestionId);
			cacheService.saveData(userPollKey, String.valueOf(answerId));

		}catch(Exception ex){
			logger.error("Unable to import Donation"+ jsonMessage,ex);
		}
		
		
		
	}
	
}
