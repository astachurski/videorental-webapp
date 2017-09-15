package com.rental.videorental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RentalController {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String mainPage() {
        return "<b>Hello - VideoRental Company</b>";
    }


    @RequestMapping("/getClientList")  //with anonymous class implementign generic interface
    public String getClientList() {
        List<Customer> result = null;

        result = jdbcTemplate.query("select * from movierental.customer", new ResultSetExtractor<List<Customer>>() {
            @Nullable
            @Override
            public List<Customer> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Customer> result = new ArrayList<>();
                while (resultSet.next()) {
                    Customer customer = new Customer();
                    customer.setAddress(resultSet.getString("address"));
                    customer.setFname(resultSet.getString("fname"));
                    customer.setLname(resultSet.getString("lname"));
                    result.add(customer);
                }
                return result;
            }
        });

        StringBuilder sb = new StringBuilder();


        sb.append("<b>Clients list</b></br>");
        if (result.size() > 0)
            for (Customer customer : result) {
                sb.append(customer.getFname() + " " + customer.getLname());
                sb.append("</br>");
            }

        return sb.toString();

    }

    @RequestMapping("/getClientList2") //with named instance implementign generic interface
    public String getClientList2() {
        List<Customer> result = null;

        ResultSetExtractor<List<Customer>> myExtractor = new CustomExtractor();
        result = jdbcTemplate.query("select * from movierental.customer", myExtractor);
        StringBuilder sb = new StringBuilder();

        sb.append("<b>Clients list</b></br>");
        if (result.size() > 0)
            for (Customer customer : result) {
                sb.append(customer.getFname() + " " + customer.getLname());
                sb.append("</br>");
            }

        return sb.toString();

    }

    //todo: show status information (handle possible exceptions)
    @RequestMapping("/addCustomer")
    public void addClient(@RequestParam(value = "id") Integer id,
                          @RequestParam(value = "address") String address,
                          @RequestParam(value = "fname") String fname,
                          @RequestParam(value = "lname") String lname,
                          @RequestParam(value = "phone") String phone) {

/*
        Customer newCustomer = new Customer();
        newCustomer.setId(id);
        newCustomer.setFname(fname);
        newCustomer.setLname(lname);
        newCustomer.setAddress(address);
*/

        String query = String.format("INSERT INTO movierental.customer(customerid, address, fname, lname, phone) VALUES (%d, '%s', '%s', '%s', '%s')",
                id, address, fname, lname, phone);


        jdbcTemplate.query(query, new ResultSetExtractor<Object>() {
            @Nullable
            @Override
            public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                return null;
            }
        });


    }

}