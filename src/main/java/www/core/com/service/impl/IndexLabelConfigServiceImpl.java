package www.core.com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.dao.BaseDAO;
import www.core.com.pojo.IndexLabelConfig;
import www.core.com.pojo.IndexTopicConfig;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.service.IIndexLabelConfigService;
import www.core.com.service.ILabelService;



@Transactional
@Service("indexLabelConfigService")
public class IndexLabelConfigServiceImpl implements IIndexLabelConfigService{

	@Resource(name = "hibernateDAO")
	BaseDAO<IndexLabelConfig> hibernateDAO;
	
	@Autowired
	ILabelService labelService;
	
	
	@Override
	public Message<IndexLabelConfig> setLabelRange(Integer labelId, Integer range) {
		
		Message<IndexLabelConfig> indexLabelMsg = new Message<IndexLabelConfig>();
		
		
		Label oldLabel = labelService.labelExistencebyId(labelId);
		if (oldLabel == null) {
			indexLabelMsg.setMsg("标签不存在");
			indexLabelMsg.setStatus(false);
			return indexLabelMsg;
		}
		
		
		
		IndexLabelConfig indexLabelConfig = getIndexLabelConfigByLabelId(labelId);
		
		
		indexLabelConfig.setLabel(oldLabel);
		indexLabelConfig.setSort(range);
		
		hibernateDAO.saveOrUpdate(indexLabelConfig);
		
		indexLabelMsg.setMsg("设置成功");
		indexLabelMsg.setStatus(true);
		
		return indexLabelMsg;
	}
	
	IndexLabelConfig getIndexLabelConfigByLabelId(Integer labelId){
		IndexLabelConfig result = hibernateDAO.findByColumn(IndexLabelConfig.class, "labelId", labelId);
		if (result == null) {
			result = new IndexLabelConfig();
		}
		return result;
	}

	@Override
	public List getLabelRangeList(Integer start, Integer row) {
		Message<IndexLabelConfig> indexMsg = new Message<>();
		List labels = hibernateDAO.getListByPageByObject(
				"from IndexLabelConfig ilc order by ilc.sort", null, start, row);
		return labels;
	}

}
