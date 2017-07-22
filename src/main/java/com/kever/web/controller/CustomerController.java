package com.kever.web.controller;


import com.kever.web.annotation.Action;
import com.kever.web.annotation.Controller;
import com.kever.web.annotation.Inject;
import com.kever.web.bean.Param;
import com.kever.web.bean.View;
import com.kever.web.model.Customer;
import com.kever.web.service.CustomerService;

import java.util.List;

@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Action("get:/customer_show")
    public View index(Param param) {
        List<Customer> customList = customerService.getCustomList("");
        View view = new View();
        view.setPath("customer.jsp");
        view.addModel("customList", customList);
        return view;
    }
}
