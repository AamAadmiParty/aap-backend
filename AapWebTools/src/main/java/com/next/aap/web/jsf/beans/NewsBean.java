package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.next.aap.core.service.AapService;
import com.next.aap.web.ItemList;
import com.next.aap.web.cache.AapDataCacheDbImpl;
import com.next.aap.web.dto.NewsDto;
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
		@URLMapping(id = "newsBean", beanName="newslistBean", pattern = "/news/#{ newsBean.newsId }", viewId = "/WEB-INF/jsf/aapnewstyle/news.xhtml")
		})
@URLBeanName("newsBean")
public class NewsBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	NewsDto news;
	
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCacheDbImpl aapDataCacheDbImpl;
	
	@ManagedProperty("#{aapService}")
	protected AapService aapService;

	@ManagedProperty("#{trendingBean}")
	protected TrendingBean trendingBean;
	
	@ManagedProperty("#{votingBean}")
	protected VotingBean votingBean;
	
	private Long newsId;
	
	private boolean newsFound = false;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		trendingBean.init();
		votingBean.init();
		
		news = aapService.getNewsById(newsId);
		if(news == null){
			newsFound = false;
		}else{
			newsFound = true;
		}
	}
	
	public AapDataCacheDbImpl getAapDataCacheDbImpl() {
		return aapDataCacheDbImpl;
	}

	public void setAapDataCacheDbImpl(AapDataCacheDbImpl aapDataCacheDbImpl) {
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

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public NewsDto getNews() {
		return news;
	}

	public void setNews(NewsDto news) {
		this.news = news;
	}

	public AapService getAapService() {
		return aapService;
	}

	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}

	public boolean isNewsFound() {
		return newsFound;
	}

	public void setNewsFound(boolean newsFound) {
		this.newsFound = newsFound;
	}
}
