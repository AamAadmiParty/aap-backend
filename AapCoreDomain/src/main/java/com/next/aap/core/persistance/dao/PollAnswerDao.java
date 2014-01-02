package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.PollAnswer;

public interface PollAnswerDao {

	public abstract PollAnswer savePollAnswer(PollAnswer pollAnswer);

	public abstract PollAnswer getPollAnswerById(Long id);

	public abstract List<PollAnswer> getPollAnswersByQuestionId(Long userId);

	public abstract List<PollAnswer> getPollAnswersAfterId(Long lastId, int pageSize);

}