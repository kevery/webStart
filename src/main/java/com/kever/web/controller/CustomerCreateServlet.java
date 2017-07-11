package com.kever.web.controller;

import com.kever.web.help.DBHelper;
import com.kever.web.model.Customer;
import com.kever.web.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CustomerCreateServlet")
public class CustomerCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerService customerService = new CustomerService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CustomerService customerService = new CustomerService();
            Customer customer = customerService.getCustomer(1);
            int a = 1 / 0;
            request.getRequestDispatcher("/WEB-INF/view/customer_create.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
