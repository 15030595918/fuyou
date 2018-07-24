package com.beta.service.rolling.impl;

import java.util.List;
import javax.annotation.Resource;

import com.beta.service.rolling.FileManager;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


/** 
 * 说明： 文件管理
 * 创建人：FH Q313596790
 * 创建时间：2018-07-04
 * @version
 */
@Service("fileService")
public class FileService implements FileManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("FileMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("FileMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("FileMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FileMapper.datalistPage", page);
	}

	/**列表V
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listV(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FileMapper.datalistV", page);
	}
	/**列表F
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listF(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FileMapper.datalistF", page);
	}
	/**列表S
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listS(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FileMapper.datalistPageSearch", page);
	}
	/**列表(全部)
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FileMapper.listAll", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FileMapper.findById", pd);
	}
	/**通过id获得name
	 */
	public PageData findNameById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FileMapper.findNameById",pd);
	}

	/**
	 * 通过名字获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByNum(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FileMapper.findByNum",pd);
	}

	/**通过FILE_NAME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByFName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FileMapper.findByName", pd);
	}
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("FileMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

