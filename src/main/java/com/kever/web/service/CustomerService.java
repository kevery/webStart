package com.kever.web.service;

import com.kever.web.help.DBHelper;
import com.kever.web.model.Customer;
import com.kever.web.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);


    public List<Customer> getCustomList(String keyword) {
        String sql = "select * from customer";

        return DBHelper.queryEntityList(Customer.class, sql);
    }


    public Customer getCustomer(long id) {
        // TODO: 2017/7/9 0009
        return null;
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        // TODO: 2017/7/9 0009
        return false;
    }


    public boolean updateCustom(long id, Map<String, Object> fieldMap) {
        // TODO: 2017/7/9 0009
        return false;
    }

    public boolean deleteCustomer(long id) {
        // TODO: 2017/7/9 0009
        return false;
    }
}
