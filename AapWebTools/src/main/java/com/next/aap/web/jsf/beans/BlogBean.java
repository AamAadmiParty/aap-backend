package com.next.aap.web.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.next.aap.core.service.AapService;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.dto.BlogDto;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

//@Scope("session")
@ViewScoped
@ManagedBean
//@URLMapping(id = "homeBean", beanName="homeBean", pattern = "/home", viewId = "/WEB-INF/jsf/home.xhtml")
@URLMappings(mappings={
		@URLMapping(id = "blogBean", beanName="bloglistBean", pattern = "/blog/#{ blogBean.blogId }", viewId = "/WEB-INF/jsf/aapnewstyle/blog.xhtml")
		})
@URLBeanName("blogBean")
public class BlogBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	BlogDto blog;
	
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCache aapDataCacheDbImpl;
	
	@ManagedProperty("#{aapService}")
	protected AapService aapService;

	@ManagedProperty("#{trendingBean}")
	protected TrendingBean trendingBean;
	
	@ManagedProperty("#{votingBean}")
	protected VotingBean votingBean;
	
	private Long blogId;
	
	private boolean blogFound = false;

	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	@URLAction(onPostback=false)
	public void init() throws Exception {
		trendingBean.init();
		votingBean.init();
		
		blog = aapService.getBlogById(blogId);
		if(blog == null){
			blogFound = false;
		}else{
			blogFound = true;
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

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public BlogDto getBlog() {
		return blog;
	}

	public void setBlog(BlogDto blog) {
		this.blog = blog;
	}

	public AapService getAapService() {
		return aapService;
	}

	public void setAapService(AapService aapService) {
		this.aapService = aapService;
	}

	public boolean isBlogFound() {
		return blogFound;
	}

	public void setBlogFound(boolean blogFound) {
		this.blogFound = blogFound;
	}
}
