package com.next.aap.web.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.next.aap.core.service.AapService;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.dto.VideoDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

//@Scope("session")
@ViewScoped
@ManagedBean
//@URLMapping(id = "homeBean", beanName="homeBean", pattern = "/home", viewId = "/WEB-INF/jsf/home.xhtml")
@URLMappings(mappings={
 @URLMapping(id = "videoBean", beanName = "videolistBean", pattern = "/videooietnerner/#{ videoBean.videoId }", viewId = "/WEB-INF/jsf/aapnewstyle/video.xhtml")
		})
@URLBeanName("videoBean")
public class VideoBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	VideoDto video;
	
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCache aapDataCacheDbImpl;
	
	@ManagedProperty("#{aapService}")
	protected AapService aapService;

	@ManagedProperty("#{trendingBean}")
	protected TrendingBean trendingBean;
	
	@ManagedProperty("#{votingBean}")
	protected VotingBean votingBean;
	
	private Long videoId;
	
	private boolean videoFound = false;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		trendingBean.init();
		votingBean.init();
		video = aapService.getVideoById(videoId);
		if(video == null){
			videoFound = false;
		}else{
			videoFound = true;
		}
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

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public VideoDto getVideo() {
		return video;
	}

	public void setVideo(VideoDto video) {
		this.video = video;
	}

	public AapService getAapService() {
		return aapService;
	}

	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}

	public boolean isVideoFound() {
		return videoFound;
	}

	public void setVideoFound(boolean videoFound) {
		this.videoFound = videoFound;
	}
}
