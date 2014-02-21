package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.next.aap.web.ItemList;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.cache.AapDataCacheDbImpl;
import com.next.aap.web.dto.BlogDto;
import com.next.aap.web.dto.UserDto;
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
		@URLMapping(id = "blogListBean", beanName="bloglistBean", pattern = "/blog", viewId = "/WEB-INF/jsf/aapnewstyle/bloglist.xhtml")
		})
@URLBeanName("blogListBean")
public class BlogListBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	List<BlogDto> blogItems;
	
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
		
		ItemList<BlogDto> blogItemList = aapDataCacheDbImpl.getBlogDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, livingAcId, votingAcId, livingPcId, votingPcId);
		blogItems = blogItemList.getItems();
		
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

	public List<BlogDto> getBlogItems() {
		return blogItems;
	}

	public void setBlogItems(List<BlogDto> blogItems) {
		this.blogItems = blogItems;
	}
}
