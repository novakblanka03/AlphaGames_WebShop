package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.services.GameService;
import com.msglearning.javabackend.to.PurchaseTO;
import com.msglearning.javabackend.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PurchaseConverter {

    public static PurchaseTO toPurchaseTO(Purchase purchase){

        return new PurchaseTO(purchase.getId(), purchase.getGame().getName(),
                purchase.getUser().getFullName(), purchase.getPurchaseDate());
    }
}
