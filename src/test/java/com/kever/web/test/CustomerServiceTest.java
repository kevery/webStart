package com.kever.web.test;

import com.kever.web.model.Customer;
import com.kever.web.service.CustomerService;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        this.customerService = new CustomerService();
    }

    public void init() {
        // TODO: 2017/7/9 0009 初始化数据库
    }

    @Test
    public void getCustomListTest() throws Exception {
        List<Customer> customList = customerService.getCustomList(null);
        Assert.assertEquals(2, customList.size());
    }

    @Test
    public void getCustomTest() throws Exception {
        long id = 1;
        Customer customList = customerService.getCustomer(id);
        Assert.assertEquals("kever", customList.getName());
    }

    @Test
    public void createCustomerTest() throws Exception {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("name", "customer3");
        fieldMap.put("contact", "root");
        fieldMap.put("telephone", "158748775");

        Assert.assertTrue(customerService.createCustomer(fieldMap));
    }

    @Test
    public void updateCustomTest() throws Exception {
        long id = 1;
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("contact", "ruby");

        Assert.assertTrue(customerService.updateCustom(id, fieldMap));
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        long id = 1;
        Assert.assertTrue(customerService.deleteCustomer(id));
    }

}
