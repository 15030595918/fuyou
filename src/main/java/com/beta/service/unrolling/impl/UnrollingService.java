package com.beta.service.unrolling.impl;

import java.util.List;
import javax.annotation.Resource;

import com.beta.service.unrolling.UnrollingManager;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


/** 
 * 说明： 非立卷管理
 * 创建人：FH Q313596790
 * 创建时间：2018-07-09
 * @version
 */
@Service("unrollingService")
public class UnrollingService implements UnrollingManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UnrollingMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("UnrollingMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("UnrollingMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UnrollingMapper.datalistPage", page);
	}
	/**列表U
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listU(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UnrollingMapper.listUnrolling", page);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listS(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UnrollingMapper.datalistS", page);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UnrollingMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UnrollingMapper.findById", pd);
	}

	/**通过FILE_NAME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByFName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UnrollingMapper.findByName", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UnrollingMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

