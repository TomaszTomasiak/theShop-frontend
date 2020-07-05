package com.validator;

import org.springframework.stereotype.Component;
import java.util.Random;

public class Validator {

    private static Validator validator;

    private Validator() {
    }

    public static Validator getInstance() {
        if (validator == null) {
            validator = new Validator();
        }
        return validator;
    }

    private boolean orderPaid = false;

    public boolean isOrderPaid() {
        return orderPaid;
    }

    public void setOrderPaid(boolean orderPaid) {
        this.orderPaid = orderPaid;
    }

    public boolean validatePayment() {
    boolean result = orderPaid;
        Random generator = new Random();
        if(!isOrderPaid()) {
            setOrderPaid(result && generator.nextBoolean());
        }
        return isOrderPaid();
    }
}

