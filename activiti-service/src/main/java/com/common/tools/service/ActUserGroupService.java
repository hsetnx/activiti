package com.common.tools.service;

import com.common.tools.common.utils.PubMethod;
import com.common.tools.dto.ActGroupInfoReqDto;
import com.common.tools.dto.ActUserInfoReqDto;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.util.IoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: jingyan
 * @Time: 2017/4/17 17:16
 * @Describe:
 */
@Service
public class ActUserGroupService {

    private static Logger logger = LoggerFactory.getLogger(ActUserGroupService.class);
    @Autowired
    protected IdentityService identityService;


    /**
     * Created with: jingyan.
     * Date: 2016/8/22  15:31
     * Description: 新增用户
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean createUser(ActUserInfoReqDto actUserInfoReqDto) {
        logger.info("新增用户---入参" + actUserInfoReqDto.toString());
        //ID校验
        if (this.identityService.createUserQuery().userId(actUserInfoReqDto.getUserId()).count() != 0) {
            logger.info("新增用户---用户已存在" + actUserInfoReqDto.getUserId());
            return false;
        }
        //用户信息
        User user = this.identityService.newUser(actUserInfoReqDto.getUserId());
        user.setFirstName(actUserInfoReqDto.getFirstName());
        user.setLastName(actUserInfoReqDto.getLastName());
        user.setPassword(actUserInfoReqDto.getPassword());
        user.setEmail(actUserInfoReqDto.getEmail());
        this.identityService.saveUser(user);
        //组信息（角色）
        if (!PubMethod.isEmpty(actUserInfoReqDto.getGroups())) {
            for (String groupId : actUserInfoReqDto.getGroups()) {
                this.identityService.createMembership(actUserInfoReqDto.getUserId(), groupId);
            }
        }
        //头像
        if (!PubMethod.isEmpty(actUserInfoReqDto.getImageResource())) {
            byte[] pictureBytes = IoUtil.readInputStream(this.getClass().getClassLoader().getResourceAsStream(actUserInfoReqDto.getImageResource()), null);
            Picture picture = new Picture(pictureBytes, "image/jpeg");
            this.identityService.setUserPicture(actUserInfoReqDto.getUserId(), picture);
        }
        //扩展信息
        if (!PubMethod.isEmpty(actUserInfoReqDto.getUserAttachInfo())) {
            Iterator iter = actUserInfoReqDto.getUserAttachInfo().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
                this.identityService.setUserInfo(actUserInfoReqDto.getUserId(), entry.getKey(), (String) entry.getValue());
            }
        }
        if (this.identityService.createUserQuery().userId(actUserInfoReqDto.getUserId()).count() == 0) {
            logger.info("新增用户---新增失败...");
            return false;
        }
        return true;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/8/22  15:30
     * Description: 新增用户组
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean createGroup(ActGroupInfoReqDto actGroupInfoReqDto) {
        if (this.identityService.createGroupQuery().groupId(actGroupInfoReqDto.getGroupId()).count() != 0) {
            logger.info("新增用户组---用户组已存在" + actGroupInfoReqDto.getGroupId());
            return false;
        }
        Group newGroup = identityService.newGroup(actGroupInfoReqDto.getGroupId());
        newGroup.setName(actGroupInfoReqDto.getGroupName());
        newGroup.setType(actGroupInfoReqDto.getType());
        this.identityService.saveGroup(newGroup);
        if (this.identityService.createGroupQuery().groupId(actGroupInfoReqDto.getGroupId()).count() == 0) {
            logger.info("新增用户组---新增失败...");
            return false;
        }
        return true;
    }

    /**
     * Created with: jingyan.
     * Date: 2016/8/22  16:04
     * Description: 查询所有用户组
     */
    public List<Group> getAllGroup() {
        List<Group> groups = this.identityService.createGroupQuery().orderByGroupId().list();
        return groups;
    }
}
