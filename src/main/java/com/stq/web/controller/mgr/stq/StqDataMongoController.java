package com.stq.web.controller.mgr.stq;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.stq.entity.Log;
import com.stq.entity.User;
import com.stq.service.account.AccountService;
import com.stq.service.log.LogService;
import com.stq.service.stq.SummaryService;
import com.stq.web.controller.mgr.BaseController;

/**
 * 查询Mongo数据指标的controller
 *
 */
@Controller("stqDataMongoController")
@RequestMapping(value="/manage/stq/dataMongo")
public class StqDataMongoController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(StqDataMongoController.class);
	
	private static final String PAGE_SIZE = "15";
	
	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd" ); 
	Calendar calendar = new GregorianCalendar(); 

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("createDate", "时间");
	}
	
	public static Map<String, String> getSortTypes() {
		return sortTypes;
	}

	public static void setSortTypes(Map<String, String> sortTypes) {
		StqDataMongoController.sortTypes = sortTypes;
	}

	@Override
	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class,"createDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class,"upDate",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private SummaryService summaryService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto")String sortType, Model model,
			ServletRequest request) throws Exception{
		logger.debug("概括");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = summaryService.getCurrentUserId();
		User user = accountService.getUser(userId);
		model.addAttribute("user", user);
		model.addAttribute("dateFrom", summaryService.thirtyDayAgoFrom());
		model.addAttribute("dateTo", summaryService.nowDate());
		logService.log(user.getName(), user.getName() + "：访问Mongo数据指标页面", Log.STQ_TYPE_DATA_MONGO);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "/stq/datamongo/index";
	}
	/**
	 * 异步查询
	 * @param id 用户id
	 */
	@RequestMapping(value = "findIndex", method = RequestMethod.GET )
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> findIndex(
			@RequestParam(value = "search_EQ_storeName")String search_EQ_storeName,
			@RequestParam(value = "search_EQ_dateFrom")String search_EQ_dateFrom,
			@RequestParam(value = "search_EQ_dateTo")String search_EQ_dateTo,
			@RequestParam(value = "search_EQ_serverZoneId", required = false)String[] search_EQ_serverZoneId,
			@RequestParam(value = "search_EQ_pfId" , required = false)String[] search_EQ_pfId,
			ServletRequest request,
			Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("c_30_newuser", springHttpClient.getMethodStr("http://private-9394a-g22.apiary-mock.com/30/newuser"));
		//map.put("c_30_activeuser",springHttpClient.getMethodStr("http://private-9394a-g22.apiary-mock.com/30/activeuser"));	
		//System.out.println(springHttpClient.getMethodStr("http://private-9394a-g22.apiary-mock.com/timeframe/newuser"));
		//map.put("c_timeframe_newuser", springHttpClient.getMethodStr("http://private-9394a-g22.apiary-mock.com/timeframe/newuser"));	
		return map;
	}
	/**
	 * 服务器获取时间
	 */
	@RequestMapping(value="/getDate")
	@ResponseBody
	public Map<String, String> getDate(){
		Map<String,String> dateMap = new HashMap<String, String>();
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd" ); 
		Calendar calendar = new GregorianCalendar(); 
		String nowDate = sdf.format(new Date());
		
	    calendar.setTime(new Date()); 
	    calendar.add(Calendar.DATE,-1);
	    String yesterday = sdf.format(calendar.getTime());
	    
	    calendar.setTime(new Date()); 
	    calendar.add(Calendar.DATE,-7);
	    String sevenDayAgo = sdf.format(calendar.getTime()); 
	    
	    calendar.setTime(new Date()); 
	    calendar.add(Calendar.DATE,-30);
	    String thirtyDayAgo = sdf.format(calendar.getTime()); 
		
	    dateMap.put("nowDate",nowDate);
	    dateMap.put("yesterday",yesterday);
	    dateMap.put("sevenDayAgo",sevenDayAgo);
	    dateMap.put("thirtyDayAgo",thirtyDayAgo);
		return dateMap;
	}
}