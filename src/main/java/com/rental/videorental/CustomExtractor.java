package com.rental.videorental;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomExtractor implements ResultSetExtractor<List<Customer>> {
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
}

