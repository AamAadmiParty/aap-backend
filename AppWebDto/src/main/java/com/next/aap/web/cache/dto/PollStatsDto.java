package com.next.aap.web.cache.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PollStatsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<Long, Long> answerCounts = new HashMap<Long, Long>();

	public Long getAnswerCounts(Long answerId) {
		return answerCounts.get(answerId);
	}

	public void addAnswerCounts(Long answerId, Long answerCounts) {
		this.answerCounts.put(answerId, answerCounts);
	}
}
