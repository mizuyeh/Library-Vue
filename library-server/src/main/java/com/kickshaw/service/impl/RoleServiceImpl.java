package com.kickshaw.service.impl;

import com.kickshaw.entity.Resource;
import com.kickshaw.entity.Role;
import com.kickshaw.mapper.ResourceMapper;
import com.kickshaw.mapper.RoleMapper;
import com.kickshaw.service.ResourceService;
import com.kickshaw.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kickshaw
 * @since 2021-03-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResourceService resourceService;

    @Override
    public List<Role> listRoles(Role role) {
        List<Role> roles = roleMapper.listRoles(role);
        for (int i = 0; i < roles.size(); i++) {
            List<Integer> resourceIdsList = roles.get(i).getResourceIdsList();
            List<String> resourceNames = new ArrayList<>();
            for (Integer id : resourceIdsList) {
                Resource resource = resourceService.getById(id);
                resourceNames.add(resource.getResName());
            }
            roles.get(i).setResourceNames(resourceNames);
            if(!resourceNames.isEmpty()) {
                roles.get(i).setResourceNamesStr(String.join("--", resourceNames));
            }
        }
        return  roles;
    }
}
