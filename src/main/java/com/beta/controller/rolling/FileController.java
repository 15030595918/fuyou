package com.beta.controller.rolling;

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

import com.beta.service.rolling.FileManager;
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
 * 说明：文件管理
 * 创建人：FH Q313596790
 * 创建时间：2018-07-04
 */
@Controller
@RequestMapping(value="/file")
public class FileController extends BaseController {
	
	String menuUrl = "file/list.do"; //菜单地址(权限用)
	@Resource(name="fileService")
	private FileManager fileService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增File");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("FILE_ID", this.get32UUID());	//主键
		fileService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除File");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		fileService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改File");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		fileService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表File");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
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
		String currentPage = pd.getString("currentPage");
		if(currentPage!=null ) {
			int curPage = Integer.parseInt(currentPage);

			page.setCurrentPage(curPage);
		}
		List<PageData>	varList = fileService.list(page);	//列出File列表
		mv.setViewName("beta/file/file_list");
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
		mv.setViewName("beta/file/file_edit");
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
		pd = fileService.findById(pd);	//根据ID读取
		mv.setViewName("beta/file/file_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除File");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			fileService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出File到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
//		titles.add("备注1");	//1
		titles.add("全宗号");	//2
		titles.add("目录号");	//3
		titles.add("案卷号");	//4
		titles.add("档号");	//5
		titles.add("顺序号");	//6
		titles.add("题名");	//7
		titles.add("文号");	//8
		titles.add("责任者");	//9
		titles.add("页号");	//10
		titles.add("页数");	//11
		titles.add("日期");	//12
		titles.add("归档年度");	//13
		titles.add("保管期限");	//14
		titles.add("密级");	//15
		titles.add("保管单位名称");	//16
		titles.add("备注");	//17
//		titles.add("案卷号");	//18
		dataMap.put("titles", titles);
		page.setPd(pd);
		List<PageData> varOList = fileService.listAll(page);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
//			vpd.put("var1", varOList.get(i).get("FILE_ID").toString());	//1
			vpd.put("var1", varOList.get(i).getString("GENERAL_ARCHIVE"));	    //2
			vpd.put("var2", varOList.get(i).getString("CATALOG_NUMBER"));	    //3
			vpd.put("var3", varOList.get(i).getString("VOLUME_SN"));	    //4
			vpd.put("var4", varOList.get(i).getString("VOLUME_NUM"));	    //5
			vpd.put("var5", varOList.get(i).get("FILE_SN").toString());	//6
			vpd.put("var6", varOList.get(i).getString("FILE_NAME"));	    //7
			vpd.put("var7", varOList.get(i).getString("FILE_NUM"));	    //8
			vpd.put("var8", varOList.get(i).getString("FILE_RESPONSIBLER"));	    //9
			vpd.put("var9", varOList.get(i).getString("START_PAGE"));	    //10
			vpd.put("var10", varOList.get(i).get("FILE_PAGE").toString());	//11
			vpd.put("var11", varOList.get(i).getString("FILE_DATE"));	    //12
			vpd.put("var12", varOList.get(i).get("FILE_YEAR").toString());	    //13
			vpd.put("var13", varOList.get(i).getString("STORAGE_TIME"));	    //14
			vpd.put("var14", varOList.get(i).getString("SECRET_LEVEL"));	    //15
			vpd.put("var15", varOList.get(i).getString("COMPANY_NAME"));	    //16
			vpd.put("var16", varOList.get(i).getString("FILE_DESCRIPTION"));	    //17
//			vpd.put("var17", varOList.get(i).get("VOLUME_ID").toString());	//18
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
		mv.setViewName("beta/file/uploadexcel");
		return mv;
	}
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "file.xls", "file.xls");
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
			String fileName =  FileUpload.fileUp(file, filePath, "fileexcel");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			pd.put("FILE_ID", "");
			/**
			 * var0 :编号
			 * var1 :姓名
			 * var2 :手机
			 * var3 :邮箱
			 * var4 :备注
			 */
			for(int i=0;i<listPd.size();i++){
				pd.put("GENERAL_ARCHIVE", listPd.get(i).getString("var0"));
				pd.put("CATALOG_NUMBER", listPd.get(i).getString("var1"));
				pd.put("VOLUME_SN", listPd.get(i).getString("var2"));
//				String VOLUME_NAME = GetPinyin.getPingYin(listPd.get(i).getString("var3"));	//根据姓名汉字生成全拼
//				pd.put("VOLUME_NAME", VOLUME_NAME);
//				if(fileService.findByName(pd) != null){									//判断用户名是否重复
//					VOLUME_NAME = GetPinyin.getPingYin(listPd.get(i).getString("var3"))+Tools.getRandomNum();
//					pd.put("VOLUME_NAME", VOLUME_NAME);
//					continue;
////				}
//				pd.put("VOLUME_NAME", listPd.get(i).getString("var3"));
				pd.put("VOLUME_NUM", listPd.get(i).getString("var3"));
				pd.put("FILE_SN", listPd.get(i).getString("var4"));
				if(fileService.findByFName(pd) != null){
					continue;
				}
				pd.put("FILE_NAME", listPd.get(i).getString("var5"));
				pd.put("FILE_NUM", listPd.get(i).getString("var6"));
				pd.put("FILE_RESPONSIBLER", listPd.get(i).getString("var7"));
				pd.put("START_PAGE", listPd.get(i).getString("var8"));
				pd.put("FILE_PAGE", listPd.get(i).getString("var9"));
				pd.put("FILE_DATE", listPd.get(i).getString("var10"));
				pd.put("FILE_YEAR", listPd.get(i).getString("var11"));
				pd.put("STORAGE_TIME", listPd.get(i).getString("var12"));
				pd.put("SECRET_LEVEL", listPd.get(i).getString("var13"));
				pd.put("FILE_DESCRIPTION", listPd.get(i).getString("var15"));

				fileService.save(pd);
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/findByNum")
	public ModelAndView findByNum()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String url = "";
		List<PageData> files = fileService.findByNum(pd);
		System.out.println(files.size());
		if (files.size() == 1){
			url = "/uploadFiles/uploadFile/"+	pd.getString("VOLUME_NUM") + ".pdf";
		}else if (files.size() > 1){
			url = "/uploadFiles/uploadFile/"+	pd.getString("VOLUME_NUM") + "-" + pd.getString("FILE_SN") + ".pdf";
		}
		mv.setViewName("redirect:/pdfjs/web/viewer.html?file=" + url);
		return mv;
	}


	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
