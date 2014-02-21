package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.next.aap.web.ItemList;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.cache.AapDataCacheDbImpl;
import com.next.aap.web.dto.VideoDto;
import com.next.aap.web.dto.UserDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

//@Scope("session")
@ViewScoped
@ManagedBean
//@URLMapping(id = "homeBean", beanName="homeBean", pattern = "/home", viewId = "/WEB-INF/jsf/home.xhtml")
@URLMappings(mappings={
		@URLMapping(id = "videoListBean", beanName="videolistBean", pattern = "/video", viewId = "/WEB-INF/jsf/aapnewstyle/videolist.xhtml")
		})
@URLBeanName("videoListBean")
public class VideoListBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	List<VideoDto> videoItems;
	
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCache aapDataCacheDbImpl;
	
	@ManagedProperty("#{trendingBean}")
	protected TrendingBean trendingBean;
	
	@ManagedProperty("#{votingBean}")
	protected VotingBean votingBean;
	


	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		trendingBean.init();
		votingBean.init();
		UserDto loggedInUser = getLoggedInUser();//true,buildLoginUrl("/home"));
		
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
		
		ItemList<VideoDto> videoItemList = aapDataCacheDbImpl.getVideoDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId);
		videoItems = videoItemList.getItems();
	}
	
	public AapDataCache getAapDataCacheDbImpl() {
		return aapDataCacheDbImpl;
	}

	public void setAapDataCacheDbImpl(AapDataCache aapDataCacheDbImpl) {
		this.aapDataCacheDbImpl = aapDataCacheDbImpl;
	}

	public TrendingBean getTrendingBean() {
		return trendingBean;
	}
	public void setTrendingBean(TrendingBean trendingBean) {
		this.trendingBean = trendingBean;
	}
	public VotingBean getVotingBean() {
		return votingBean;
	}
	public void setVotingBean(VotingBean votingBean) {
		this.votingBean = votingBean;
	}

	public List<VideoDto> getVideoItems() {
		return videoItems;
	}

	public void setVideoItems(List<VideoDto> videoItems) {
		this.videoItems = videoItems;
	}
}
