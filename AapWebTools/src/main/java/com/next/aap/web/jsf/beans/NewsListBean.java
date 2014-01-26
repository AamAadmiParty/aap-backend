package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

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
		@URLMapping(id = "newsListBean", beanName="newslistBean", pattern = "/news", viewId = "/WEB-INF/jsf/aapnewstyle/newslist.xhtml")
		})
@URLBeanName("newsListBean")
public class NewsListBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	List<NewsDto> homeNewsItems;
	
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCacheDbImpl aapDataCacheDbImpl;
	
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
		System.out.println("loggedInUser = "+loggedInUser);
		if(loggedInUser == null){
			ItemList<NewsDto> newsItems = aapDataCacheDbImpl.getNewsDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, 0,0,0,0);
			homeNewsItems = newsItems.getItems();
		}else{
			ItemList<NewsDto> newsItems = aapDataCacheDbImpl.getNewsDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, loggedInUser.getAssemblyConstituencyLivingId(), loggedInUser.getAssemblyConstituencyVotingId(), loggedInUser.getParliamentConstituencyLivingId(), loggedInUser.getParliamentConstituencyVotingId());
			homeNewsItems = newsItems.getItems();
		}
	}
	
	public List<NewsDto> getHomeNewsItems() {
		return homeNewsItems;
	}

	public void setHomeNewsItems(List<NewsDto> homeNewsItems) {
		this.homeNewsItems = homeNewsItems;
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
}
