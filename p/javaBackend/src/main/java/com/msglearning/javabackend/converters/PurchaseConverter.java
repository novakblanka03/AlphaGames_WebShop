package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.to.PurchaseTO;
import com.msglearning.javabackend.to.UserTO;

public class PurchaseConverter {
    public static UserTO toUserTO(User user) {
        return UserTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(user.getGender())
                .admin(user.isAdmin())
                .build();
    }

    public static User toUser(UserTO userTO) {
        return User.builder()
                .id(userTO.getId())
                .firstName(userTO.getFirstName())
                .lastName(userTO.getLastName())
                .email(userTO.getEmail())
                .gender(userTO.getGender())
                .admin(userTO.isAdmin())
                .build();
    }
}
