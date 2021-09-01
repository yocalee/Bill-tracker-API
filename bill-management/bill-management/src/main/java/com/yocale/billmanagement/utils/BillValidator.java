package com.yocale.billmanagement.utils;

import com.yocale.billmanagement.entities.Bill;
import com.yocale.billmanagement.entities.User;
import org.springframework.security.access.AccessDeniedException;

public class BillValidator {
    public static void validateUser(User user, Bill bill) throws AccessDeniedException {
        if (!user.isAdmin() && isNotCreatorOfBill(bill, user)) {
            throw new AccessDeniedException("You cannot delete other users' bills.");
        }
    }

    private static boolean isNotCreatorOfBill(Bill bill, User creator) {
        return !creator.getBills().contains(bill);
    }
}
