package com.fdflib.example.service;

import com.fdflib.example.model.Role;
import com.fdflib.example.model.User;
import com.fdflib.example.model.UserRole;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.ArrayList;
import java.util.List;

public class UserRoleService extends FdfCommonServices {
    public UserRole saveUserRole(UserRole ur) {
        if (ur != null) {
            return this.save(UserRole.class, ur).current;
        }
        return null;
    }

    /**
     * Gets all of the Roles attached to a user
     * @param userId
     * @return roles
     */
    public List<Role> getRolesByUser(long userId) {
        RoleService rs = new RoleService();

        List<FdfEntity<UserRole>> userRolesByUser
                = getEntitiesByValueForPassedField(UserRole.class, "userId", Long.toString(userId));
        List<Role> roles = new ArrayList<>();

        for(FdfEntity<UserRole> userRoleWithHistory: userRolesByUser){
            if(userRoleWithHistory.current != null){
                roles.add(rs.getRoleById(userRoleWithHistory.current.roleId));
            }
        }
        return roles;

    }
}
