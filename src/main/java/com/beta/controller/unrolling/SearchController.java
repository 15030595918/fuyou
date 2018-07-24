package com.beta.controller.unrolling;

import com.beta.service.rolling.FileManager;
import com.beta.service.unrolling.UnrollingManager;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.util.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 说明：文件管理
 * 创建人：FH Q313596790
 * 创建时间：2018-07-04
 */
@Controller
@RequestMapping(value="/search")
public class SearchController extends BaseController {

	String menuUrl = "search/list.do"; //菜单地址(权限用)
	@Resource(name="fileService")
	private FileManager fileService;
	@Resource(name="unrollingService")
	private UnrollingManager unrollingService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;


	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表File");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String time = pd.getString("time");				//关键词检索条件
		if(null != time && !"".equals(time)){
			pd.put("time",time.trim());
		}
        String lastStart = pd.getString("lastStart");				//关键词检索条件
        if(null != lastStart && !"".equals(lastStart)){
            pd.put("lastStart", lastStart.trim());
        }
        String lastEnd = pd.getString("lastEnd");				//关键词检索条件
        if(null != lastEnd && !"".equals(lastEnd)){
            pd.put("lastEnd", lastEnd.trim());
        }
		page.setPd(pd);
		List<PageData>	varList = fileService.listS(page);	//列出File列表
//		List<PageData>	varListT = unrollingService.listS(page);	//列出unFile列表
//		varList.addAll(varListT);
		mv.setViewName("beta/search/search_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}



	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
