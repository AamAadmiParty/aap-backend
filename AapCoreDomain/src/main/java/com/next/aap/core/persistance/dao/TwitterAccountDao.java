package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.TwitterAccount;

public interface TwitterAccountDao {

	public abstract TwitterAccount saveTwitterAccount(TwitterAccount twitterAccount);

	public abstract TwitterAccount getTwitterAccountById(Long id);

	public abstract TwitterAccount getTwitterAccountByUserId(Long userId);

	public abstract TwitterAccount getTwitterAccountByHandle(String userName);

	public abstract TwitterAccount getTwitterAccountByTwitterUserId(String twitterUserId);

	public abstract List<TwitterAccount> getTwitterAccountsAfterId(Long lastId, int pageSize);

	public abstract List<TwitterAccount> getAllTwitterAccountsForVoiceOfAapToPublishOnTimeLine();
	
	public abstract List<TwitterAccount> getStateTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long stateId);
	
	public abstract List<TwitterAccount> getDistrictTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long district);
	
	public abstract List<TwitterAccount> getAcTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long acId);
	
	public abstract List<TwitterAccount> getPcTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long pcId);
	
	public abstract List<TwitterAccount> getCountryTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long pcId);
	
	public abstract List<TwitterAccount> getCountryRegionTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long pcId);
	
	public abstract List<TwitterAccount> getCountryRegionAreaTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long pcId);

    public abstract List<TwitterAccount> getAllTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long afterId, int total);

    public abstract List<TwitterAccount> getStateTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long stateId, Long afterId, int total);

    public abstract List<TwitterAccount> getDistrictTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long district, Long afterId, int total);

    public abstract List<TwitterAccount> getAcTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long acId, Long afterId, int total);

    public abstract List<TwitterAccount> getPcTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long pcId, Long afterId, int total);

    public abstract List<TwitterAccount> getCountryTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long pcId, Long afterId, int total);

    public abstract List<TwitterAccount> getCountryRegionTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long pcId, Long afterId, int total);

    public abstract List<TwitterAccount> getCountryRegionAreaTwitterAccountsForVoiceOfAapToPublishOnTimeLine(Long pcId, Long afterId, int total);
}