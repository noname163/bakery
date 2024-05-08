package com.home.bakery.data.objects.interfaces;

import java.time.LocalDate;

import com.home.bakery.data.constans.BillStatus;

public interface BillInterface {
    Long getId();
    String getCustomerName();
    Long getMoney();
    BillStatus getStatus();
    LocalDate getDeliveryDate();
    String getAddress();
    String getDriverName();
}

