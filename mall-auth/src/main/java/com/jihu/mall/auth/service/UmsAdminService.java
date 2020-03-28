package com.jihu.mall.auth.service;

import com.github.pagehelper.PageHelper;
import com.jihu.mall.auth.utils.JwtTokenUtil;
import com.jihu.mall.auth.mapper.UmsAdminMapper;
import com.jihu.mall.auth.mapper.UmsAdminRoleRelationMapper;
import com.jihu.mall.ums.example.UmsAdminExample;
import com.jihu.mall.ums.example.UmsAdminRoleRelationExample;
import com.jihu.mall.ums.model.UmsAdmin;
import com.jihu.mall.ums.model.UmsAdminRoleRelation;
import com.jihu.mall.ums.model.UmsPermission;
import com.jihu.mall.ums.model.UmsRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("umsAdminService")
public class UmsAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public UmsAdmin getAdminByUsername(String username){
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> admins = adminMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(admins)){
            return admins.get(0);
        }
        return null;
    }

    public List<UmsPermission> getPermissionList(Long adminId){
        return adminRoleRelationMapper.getPermissionList(adminId);
    }


    public String login(String username, String password) {

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            String token = jwtTokenUtil.generateToken(userDetails);
            return token;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return null;
    }

    public UmsAdmin register(UmsAdmin umsAdmin) {
        umsAdmin.setCreateTime(new Date());
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        List<UmsAdmin> admins = adminMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(admins)){
            return null;
        }
        //将密码加密
        String pasword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(pasword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    public List<UmsAdmin> list(String keyword, Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        UmsAdminExample example = new UmsAdminExample();
        if(!StringUtils.isEmpty(keyword)){
            example.createCriteria().andUsernameLike("%"+keyword+"%");
            example.or().andNickNameLike("%"+keyword+"%");
        }
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(example);
        return umsAdmins;
    }

    public int update(long id, UmsAdmin admin) {
        admin.setId(id);
        UmsAdmin temp = adminMapper.selectByPrimaryKey(id);
        if(Optional.ofNullable(temp).isPresent()){
            if(StringUtils.isEmpty(admin.getPassword())
                    || admin.getPassword().equals(temp.getPassword())){
            }else{
                    admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
            return adminMapper.updateByPrimaryKey(admin);
        }
        //用户不存在
        return 0;
    }

    public int delete(Long id) {
        return adminMapper.deleteByPrimaryKey(id);
    }

    public List<UmsRole> getRoleList(Long adminId) {
        List<UmsRole> roles = adminRoleRelationMapper.getRoleList(adminId);
        if(!CollectionUtils.isEmpty(roles)){
            return roles;
        }
        return null;
    }

    @Transactional
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来关系
        UmsAdminRoleRelationExample example = new UmsAdminRoleRelationExample();
        example.createCriteria().andAdminIdEqualTo(adminId);
        adminRoleRelationMapper.deleteByExample(example);

        //建立新关系
        if(!CollectionUtils.isEmpty(roleIds)){
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            roleIds.forEach(roleId -> {
                UmsAdminRoleRelation adminRoleRelation = new UmsAdminRoleRelation();
                adminRoleRelation.setAdminId(adminId);
                adminRoleRelation.setRoleId(roleId);
                list.add(adminRoleRelation);
            });
            adminRoleRelationMapper.insertList(list);
        }
        return count;
    }

    @Transactional
    public int updateStatus(Long id, Integer status) {
        UmsAdmin umsAdmin = adminMapper.selectByPrimaryKey(id);
        if(Optional.ofNullable(umsAdmin).isPresent()){
            if(!StringUtils.isEmpty(status)){
                umsAdmin.setStatus(status);
                return adminMapper.updateByPrimaryKey(umsAdmin);
            }
        }
        return 0;
    }
}
