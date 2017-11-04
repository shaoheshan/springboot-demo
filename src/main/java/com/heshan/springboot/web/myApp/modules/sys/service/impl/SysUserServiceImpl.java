package com.heshan.springboot.web.myApp.modules.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.myApp.modules.sys.dao.SysUserRoleDao;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserRoleEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.heshan.springboot.web.myApp.modules.sys.dao.SysRoleDao;
import com.heshan.springboot.web.myApp.modules.sys.dao.SysUserDao;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysUserRoleService;
import com.heshan.springboot.web.platform.common.utils.DateUtils;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import com.heshan.springboot.web.platform.modules.sys.dao.SysConfigDao;
/**
 * 系统用户
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysConfigDao sysConfigDao;
	
    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return sysUserDao.queryByUserName(username);
    }

    @Override
    public SysUserEntity queryObject(Long userId) {
        return sysUserDao.queryObject(userId);
    }

    @Override
    public List<SysUserEntity> queryList(Map<String, Object> map) {
        return sysUserDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysUserDao.queryTotal(map);
    }

    @Override
    @Transactional
    public PageResult save(SysUserEntity user) {
        // sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        //新增只能是普通用户
		user.setIsadmin(0);
		//启用
		user.setIsenable(1);
		user.setStatus(1);
		user.setCreateTime(DateUtils.getUnixTimeMis());
		
		Long i  = sysUserDao.isUserName(user);
		if(i > 0) {
			return PageResult.error("用户名已存在");
		}
        sysUserDao.save(user);
		return PageResult.ok();
    }

    @Override
    @Transactional
    public void update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        sysUserDao.update(user);

        // 保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] userId) {
    	//删除用户
        sysUserDao.deleteBatch(userId);
        //删除用户角色关系
        for (int i = 0; i < userId.length; i++) {
        	HashMap<String, Object> hashMap = new HashMap<>();
        	hashMap.put("userId",userId[i]);
        	//根据用户id查询用户角色关系  , 有数据进行删除 , 没数据则不操作
        	List<SysUserRoleEntity> queryList = sysUserRoleDao.queryList(hashMap);
        	  if(queryList!=null) {
        		  sysUserDao.deleteBatchRoUs(userId);
              }
        }
      
    }

    @Override
    public int updatePassword(Long userId, String password, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPassword", newPassword);
        return sysUserDao.updatePassword(map);
    }

 /*   @Override
    public void initializtionUser(DictHospitalEntity HospitalEntity, String password) {
        String hospitalName = HospitalEntity.getHospitalName();
        String orgId = HospitalEntity.getOrgId();

        SysUserEntity sysUser = new SysUserEntity();

        // 医院名称
        sysUser.setHospitalName(hospitalName);
        // 医院唯一标识
        sysUser.setOrgId(orgId);
        // 用户名
        sysUser.sysUsers("admin");
        // 昵称
        sysUser.setName("超级管理员");
        // 密码 admin
        if (StringUtils.isEmpty(password)) {
            password = "admin";
        }
        // 加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(password, salt).toHex());
        sysUser.setSalt(salt);

        // 1 正常
        sysUser.setStatus(1);
        // 创建时间
        sysUser.setCreateTime(DateUtils.getUnixTimeMis());
        // 0:普通员工 1:超级管理员'
        sysUser.setIsadmin(1);
        // '0,启用 1.禁用'
        sysUser.setIsenable(1);
        
        String queryByKey = "BARCODESTES";
        String q= sysConfigDao.queryByKey(queryByKey);
        if(StringUtils.isEmpty(q)) {
        	//配置管理 , 初始化数据
            SysConfigEntity config = new SysConfigEntity();
            config.setKey("BARCODESTES");
            config.setRemark("条码设置");
            sysConfigDao.save(config);
        }
        Long i= sysUserDao.isUserName(sysUser);
        // i > 0 说明数据库存在 admin 
        if(i>0) {
        sysUserDao.update(sysUser);
        }else {
        //初始化超级管理员用户
        sysUserDao.save(sysUser);
        }
    }*/

	@Override
	public void updatePass(SysUserEntity userEntity) {
		sysUserDao.update(userEntity);
	}

	@Override
	public void isNoUser(SysUserEntity user) {
	        sysUserDao.isNoUser(user);
	}
	
	  @Override
	    @Transactional
	    public void updateUser(SysUserEntity user) {
	        sysUserDao.update(user);
	    }

	@Override
	public void saveOrUser(Long userId, List<Long> roleIdList) {
		// 保存用户与角色关系
        sysUserRoleService.saveOrUpdate(userId, roleIdList);
	}

	@Override
	public List<SysRoleEntity> OrUserList(Long userId) {
		//根据用户id  查询角色id  list
		List<Long> roleId= sysUserRoleDao.queryRoleIdList(userId);
		if(!roleId.isEmpty()) {
			//根据角色id  list 查询当前用户所拥有的角色 
			List<SysRoleEntity> list = sysRoleDao.queryRoleList(roleId);
			return list;
		}
		return null;
	}

}
