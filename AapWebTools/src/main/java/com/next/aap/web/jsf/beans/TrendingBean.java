package com.next.aap.web.jsf.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.next.aap.web.ItemList;
import com.next.aap.web.cache.AapDataCache;
import com.next.aap.web.cache.AapDataCacheDbImpl;
import com.next.aap.web.dto.NewsDto;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;

//@Scope("session")
@ApplicationScoped
@ManagedBean
@URLBeanName("trendingBean")
public class TrendingBean extends BaseJsfBean {

	private static final long serialVersionUID = 1L;
	
	List<NewsDto> trendingNewsItems;
	@ManagedProperty("#{aapDataCacheDbImpl}")
	protected AapDataCache aapDataCacheDbImpl;

	private boolean initialized = false;
	//@URLActions(actions = { @URLAction(mappingId = "userProfileBean") })
	//@URLAction(onPostback=false)
    @PostConstruct
	public void init() {
        try {
            if (!initialized) {
                ItemList<NewsDto> newsItems = aapDataCacheDbImpl.getNewsDtos(AapDataCacheDbImpl.DEFAULT_LANGUAGE, 0, 0, 0, 0);
                trendingNewsItems = newsItems.getItems();
                initialized = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	public AapDataCache getAapDataCacheDbImpl() {
		return aapDataCacheDbImpl;
	}

	public void setAapDataCacheDbImpl(AapDataCache aapDataCacheDbImpl) {
		this.aapDataCacheDbImpl = aapDataCacheDbImpl;
	}

	public List<NewsDto> getTrendingNewsItems() {
		return trendingNewsItems;
	}

	public void setTrendingNewsItems(List<NewsDto> trendingNewsItems) {
		this.trendingNewsItems = trendingNewsItems;
	}

	
}
