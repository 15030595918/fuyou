package com.beta.controller.unrolling;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.beta.service.unrolling.UnrollingManager;
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
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;

/** 
 * 说明：非立卷管理
 * 创建人：FH Q313596790
 * 创建时间：2018-07-09
 */
@Controller
@RequestMapping(value="/unrolling")
public class UnrollingController extends BaseController {
	
	String menuUrl = "unrolling/list.do"; //菜单地址(权限用)
	@Resource(name="unrollingService")
	private UnrollingManager unrollingService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Unrolling");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("UNROLLING_ID", this.get32UUID());	//主键
		unrollingService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Unrolling");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		unrollingService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Unrolling");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		unrollingService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Unrolling");
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
			pd.put("time", time.trim());
		}
		page.setPd(pd);
		String currentPage = pd.getString("currentPage");
		if(currentPage!=null){
			int curPage = Integer.valueOf(currentPage).intValue();
			page.setCurrentPage(curPage);
		}
		List<PageData>	varList = unrollingService.list(page);	//列出Unrolling列表
		mv.setViewName("beta/unrolling/unrolling_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("beta/unrolling/unrolling_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = unrollingService.findById(pd);	//根据ID读取
		mv.setViewName("beta/unrolling/unrolling_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Unrolling");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			unrollingService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Unrolling到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
//		titles.add("备注1");	//1
		titles.add("全宗号");	//2
		titles.add("室编档号");	//3
		titles.add("馆编档号");	//4
		titles.add("室编件号");	//5
		titles.add("馆编件号");	//6
		titles.add("归档年度");	//7
		titles.add("机构");	//8
		titles.add("保管期限");	//9
		titles.add("文号");	//10
		titles.add("题名");	//11
		titles.add("责任者");	//12
		titles.add("日期");	//13
		titles.add("页数");	//14
		titles.add("密级");	//15
		titles.add("保管单位名称");	//16
		titles.add("备注");	//17
		dataMap.put("titles", titles);
		List<PageData> varOList = unrollingService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
//			vpd.put("var1", varOList.get(i).get("UNROLLING_ID").toString());	//1
			vpd.put("var1", varOList.get(i).getString("GENERAL_ARCHIVE"));	    //2
			vpd.put("var2", varOList.get(i).getString("ROOM_NUM"));	    //3
			vpd.put("var3", varOList.get(i).getString("LIBRARY_NUM"));	    //4
			vpd.put("var4", varOList.get(i).getString("ROOM_CODE"));	    //5
			vpd.put("var5", varOList.get(i).getString("LIBRARY_CODE"));	    //6
			vpd.put("var6", varOList.get(i).get("UNROLLING_YEAR").toString());	//7
			vpd.put("var7", varOList.get(i).getString("UNROLLING_SECTION"));	    //8
			vpd.put("var8", varOList.get(i).getString("UNROLLING_TIME"));	    //9
			vpd.put("var9", varOList.get(i).getString("FILE_NUM"));	    //10
			vpd.put("var10", varOList.get(i).getString("FILE_NAME"));	    //11
			vpd.put("var11", varOList.get(i).getString("FILE_RESPONSIBLER"));	    //12
			vpd.put("var12", varOList.get(i).get("UNROLLING_DATE").toString());	//13
			vpd.put("var13", varOList.get(i).get("UNROLLING_PAGE").toString());	//14
			vpd.put("var14", varOList.get(i).getString("SECRET_LEVEL"));	    //15
			vpd.put("var15", varOList.get(i).getString("COMPANY_NAME"));	    //16
			vpd.put("var16", varOList.get(i).getString("FILE_DESCRIPTION"));	    //17
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("beta/unrolling/uploadexcel");
		return mv;
	}
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "unrollingfile.xls", "unrollingfile.xls");
	}
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
	) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "unrollingexcel");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 2);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			pd.put("FILE_ID", "");					//权限
//			pd.put("LAST_LOGIN", "");				//最后登录时间
//			pd.put("IP", "");						//IP
//			pd.put("STATUS", "0");					//状态
//			pd.put("SKIN", "default");				//默认皮肤
//			pd.put("ROLE_ID", "1");
//			List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
//			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());		//设置角色ID为随便第一个
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			for(int i=0;i<listPd.size();i++){
				if((listPd.get(i).getString("var0")==null||listPd.get(i).getString("var0")=="")&&
						(listPd.get(i).getString("var1")==null||listPd.get(i).getString("var1")=="")
						){
					break;
				}
				pd.put("GENERAL_ARCHIVE", listPd.get(i).getString("var0"));
				pd.put("ROOM_NUM", listPd.get(i).getString("var1"));
				pd.put("LIBRARY_NUM", listPd.get(i).getString("var2"));
//				String VOLUME_NAME = GetPinyin.getPingYin(listPd.get(i).getString("var3"));	//根据姓名汉字生成全拼
//				pd.put("VOLUME_NAME", VOLUME_NAME);
//				if(fileService.findByName(pd) != null){									//判断用户名是否重复
//					VOLUME_NAME = GetPinyin.getPingYin(listPd.get(i).getString("var3"))+Tools.getRandomNum();
//					pd.put("VOLUME_NAME", VOLUME_NAME);
//					continue;
////				}
//				pd.put("VOLUME_NAME", listPd.get(i).getString("var3"));
				pd.put("ROOM_CODE", listPd.get(i).getString("var3"));
				if(unrollingService.findByFName(pd) != null){
					continue;
				}
				pd.put("LIBRARY_CODE", listPd.get(i).getString("var4"));
				pd.put("UNROLLING_YEAR", listPd.get(i).getString("var5"));
				pd.put("UNROLLING_SECTION", listPd.get(i).getString("var6"));
				pd.put("UNROLLING_TIME", listPd.get(i).getString("var7"));
				pd.put("FILE_NUM", listPd.get(i).getString("var8"));
				pd.put("FILE_NAME", listPd.get(i).getString("var9"));
				pd.put("FILE_RESPONSIBLER", listPd.get(i).getString("var10"));
				pd.put("UNROLLING_DATE", listPd.get(i).getString("var11"));
				pd.put("UNROLLING_PAGE", listPd.get(i).getString("var12"));
				pd.put("SECRET_LEVEL", listPd.get(i).getString("var13"));
				pd.put("FILE_DESCRIPTION", listPd.get(i).getString("var15"));

//				pd.put("USER_ID", this.get32UUID());										//ID
//				pd.put("NAME", listPd.get(i).getString("var1"));							//姓名
//
//				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"));	//根据姓名汉字生成全拼
//				pd.put("USERNAME", USERNAME);
//				if(userService.findByUsername(pd) != null){									//判断用户名是否重复
//					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1"))+Tools.getRandomNum();
//					pd.put("USERNAME", USERNAME);
//				}
//				pd.put("BZ", listPd.get(i).getString("var4"));								//备注
//				if(Tools.checkEmail(listPd.get(i).getString("var3"))){						//邮箱格式不对就跳过
//					pd.put("EMAIL", listPd.get(i).getString("var3"));
//					if(userService.findByUE(pd) != null){									//邮箱已存在就跳过
//						continue;
//					}
//				}else{
//					continue;
//				}
//				pd.put("NUMBER", listPd.get(i).getString("var0"));							//编号已存在就跳过
//				pd.put("PHONE", listPd.get(i).getString("var2"));							//手机号
//
//				pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, "123").toString());	//默认密码123
//				if(userService.findByUN(pd) != null){
//					continue;
//				}
				unrollingService.save(pd);
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
