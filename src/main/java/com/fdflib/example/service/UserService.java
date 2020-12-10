package com.fdflib.example.service;

import com.fdflib.example.model.User;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.List;

public class UserService extends FdfCommonServices {

    public User saveUser(User user) {
        if(user != null) {
            return this.save(User.class, user).current;
        }
        return null;
    }

    public User getUserById(long id) {
        return getUserWithHistoryById(id).current;

    }

    /*
    Queries for all users with the first name passed
    Returns each with history as a FdfEntity<User>
    */
    public FdfEntity<User> getUsersByFirstNameWithHistory(String firstName) {
        List<FdfEntity<User>> userWithHistory = getEntitiesByValueForPassedField(User.class,
                "firstName", firstName);
        if(userWithHistory.size() > 0) {
            return userWithHistory.get(0);
        }
        return null;
    }

    /*
        Returns the same as the last method, but removes the historical records
        Returns simple User
     */
    public User getUserByFirstName(String firstname) {
        FdfEntity<User> userWithHistory = getUsersByFirstNameWithHistory(firstname);
        if(userWithHistory != null && userWithHistory.current != null) {
            return userWithHistory.current;
        }
        return null;
    }

    public FdfEntity<User> getUserWithHistoryById(long id) {
        FdfEntity<User> user = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            user = this.getEntityById(User.class, id);
        }

        return user;
    }
}
