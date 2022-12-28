package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final Connection connection;

    public ProductsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from PRODUCT");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Product> productList = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            long price = resultSet.getLong(3);
            productList.add(new Product(id, name, price));
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from product where id = " + id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(new Product(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getLong(3)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "update product set id = " + product.getId()
                        + ", name = " + (product.getName() == null ?
                        null : "'" + product.getName() + "'") +
                        ", price = " + product.getPrice()
                        + " where id = " + product.getId());
        preparedStatement.execute();
    }

    @Override
    public void save(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into product values " +
                        "((select max(id) + 1 from product), " +
                        (product.getName() == null ?
                                null : "'" + product.getName() + "'")
                        + ", " + product.getPrice() + ")");
        preparedStatement.execute();
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from product where id = " + id);
        preparedStatement.execute();
    }
}
