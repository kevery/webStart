package com.kever.web.service;

import com.kever.web.help.DBHelper;
import com.kever.web.model.Customer;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class CustomerService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);



    public List<Customer> getCustomList(String keyword) {
        String sql = "select * from customer";

        return DBHelper.queryEntityList(Customer.class, sql);
    }


    public Customer getCustomer(long id) {
        String sql = "select * from customer where id=?";

        return DBHelper.queryEntity(Customer.class, sql, id);
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DBHelper.insertEntity(Customer.class, fieldMap);
    }


    public boolean updateCustom(long id, Map<String, Object> fieldMap) {
        return DBHelper.updateEntity(Customer.class, fieldMap, id);
    }

    public boolean deleteCustomer(long id) {
        return DBHelper.deleteEntity(Customer.class, id);
    }
}
