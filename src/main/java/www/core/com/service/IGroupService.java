package www.core.com.service;


import java.io.IOException;
import java.util.List;

import www.core.com.Enum.StatusEnum;
import www.core.com.pojo.Group;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;

/**
 * @author BlindingDark
 *
 */

public interface IGroupService {	
		
	public Message<Group> exitGroup(Integer groupid, Integer userid);
	/**
	 * 创建group
	 * 成功 message.status = true;
	 * 失败 message.status = false;
	 * @param group
	 * @return
	 * extlabel 是扩展标签
	 * @throws IOException 
	 */
	public Message<Group> addNewGroup(Group group,String admin,String staticPath,String... extLabel) throws IOException;
	
	//删除group
	public Message<Group> deleteGroup(Group group);
	
	
	
	
	
	/**
	 * 如group存在,message.status = true;
	 * 如group不存在,message.status = false;
	 * @param group
	 * @return Message
	 */
	public Group groupExistence(Group group);
	
	/**
	 * 根据id查询group
	 * @param groupid
	 * @return
	 */
	public Group getGroupById(Integer groupid);
	
	
	/**
	 * 设置圈子属性
	 * 更新成功 message.status = true;
	 * 更新失败 message.status = false;
	 * @param group 要更新的内容
	 * @return Message
	 */
	public Message<Group> updateGroupInfo(Group group);
	
	
	
	/**
	 * 加入圈子
	 * group.foucsPersonNum++
	 * Group-person这一联系中增加新的联系
	 * 加入成功设置message.status = true;
	 * 加入失败设置message.status = false;
	 * 
	 * @param group 要加入的圈子
	 * @param user 要加入的用户
	 * @return Message
	 */
	public Message<Group> joinGroup(Group group,User user);
	
	//离开圈子
	public Message<Group> quitGroup(Group group,User user);
	
	/**
	 * 得到热门group列表
	 * 即按照group.score排序
	 * @param numofTop 要得到的条数
	 * @return Message.list 为热门的group列表
	 */
	public Message<Group> getHotGroup(int numofTop);
	
	/**
	 * 触发计算 Group score
	 * 公式暂定score = Group.foucsPersonNum * 5 + Group.topics
	 * 计算成功完成之后 设置message.status = true;
	 * 该方法在，
	 * 用户加入joinGroup，用户离开quitGroup，用户在圈子里发帖，用户删帖
	 * 方法中触发
	 * @param group 要更新的圈子
	 * @return Message
	 */
	public Message<Group> updateHotGroup(Group group);
	
	/**
	 * 圈子详细 传入groupid
	 * @param group
	 * @return
	 */
	public Message<Group> detailGroup(Group group);
	/**
	 * 加圈子接口
	 * @param groupid 加圈子id
	 * @param userid 加入人的用户id 
	 * @return
	 */
	public Message<Group> addGroup(Integer groupId,Integer userid);	
	/**
	 * 
	 * @param groupId 提出人的圈子id
	 * @param userid 创建人
	 * @param outUserId 提出人用户id
	 * @return
	 */
	public Message<Group> outGroup(Integer groupId,Integer userid,Integer outUserId);	
	/**
	 * 行锁数据
	 * @param id
	 * @return
	 */
	public Group groupExistenceLock(Integer id) ;
	
	/**
	 * 审核创建圈子申请
	 * @param user 登陆用户
	 * @param groupId 圈子id
	 * @param statusEnum 操作
	 * @return
	 */
	public Message reviewGroup(User user,Integer groupId, StatusEnum statusEnum);
	
	/**
	 * 修改用户公告
	 * @param document
	 * @param id
	 * @param userId
	 * @return
	 */
	public Message updateDocument(String document,Integer id,Integer userId);
	public Message<Label> getCreate(Integer id, Integer pageNum,
			Integer count);
	public Message<Label> getfocus(Integer id, Integer pageNum,
			Integer count);
	
	
	/**
	 * 得到推荐圈子
	 * @param user
	 * @return
	 */
	public Message<Group> getRecommendGroup(User user, Boolean isLogin);
	/**
	 *获取圈子分页
	 *@param pageNow
	 *@param count
	 *@return
	 */
	public Message<Group> getlistGroup( Integer pageNow,
			Integer count);
	public Message<Group> searchByname(String name);
}
