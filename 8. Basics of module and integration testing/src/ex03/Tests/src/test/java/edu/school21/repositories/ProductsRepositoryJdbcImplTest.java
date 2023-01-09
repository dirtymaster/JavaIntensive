package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private Connection connection;
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS
            = new ArrayList<>(Arrays.asList(
            new Product(1L, "энергос", 50),
            new Product(2L, "шоколадка", 40),
            new Product(3L, "печеньки", 38),
            new Product(4L, "чипсы", 60),
            new Product(5L, "изюм", 28)));
    final Product EXPECTED_FIND_BY_ID_PRODUCT
            = new Product(1L, "энергос", 50);
    final Product EXPECTED_UPDATED_PRODUCT
            = new Product(4L, "чипсы", 70);
    final Product EXPECTED_SAVED_PRODUCT
            = new Product(6L, "сосиски", 99);

    @BeforeEach
    public void init() {
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder
                = new EmbeddedDatabaseBuilder();
        embeddedDatabaseBuilder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScripts("schema.sql", "data.sql");
        EmbeddedDatabase embeddedDatabase = embeddedDatabaseBuilder.build();
        try {
            connection = embeddedDatabase.getConnection();
        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @Test
    public void productsListShouldBeEqual() {
        ProductsRepositoryJdbcImpl productsRepositoryJdbc
                = new ProductsRepositoryJdbcImpl(connection);
        try {
            Assertions.assertEquals(productsRepositoryJdbc.findAll(),
                    EXPECTED_FIND_ALL_PRODUCTS);
        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @Test
    public void optionalShouldBeEmpty() {
        ProductsRepositoryJdbcImpl productsRepositoryJdbc
                = new ProductsRepositoryJdbcImpl(connection);
        try {
            Assertions.assertEquals(productsRepositoryJdbc.findById(100500L),
                    Optional.empty());
        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @Test
    public void productShouldBeEqual() {
        ProductsRepositoryJdbcImpl productsRepositoryJdbc
                = new ProductsRepositoryJdbcImpl(connection);
        try {
            Assertions.assertEquals(
                    productsRepositoryJdbc.findById(1L).get(),
                    EXPECTED_FIND_BY_ID_PRODUCT);
        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @Test
    public void updatedProductShouldBeEqual() {
        ProductsRepositoryJdbcImpl productsRepositoryJdbc
                = new ProductsRepositoryJdbcImpl(connection);
        try {
            Product product = productsRepositoryJdbc.findById(4L).get();
            product.setPrice(70);
            productsRepositoryJdbc.update(product);
            product = productsRepositoryJdbc.findById(4L).get();
            Assertions.assertEquals(product, EXPECTED_UPDATED_PRODUCT);

            product.setName(null);
            EXPECTED_UPDATED_PRODUCT.setName(null);
            productsRepositoryJdbc.update(product);
            product = productsRepositoryJdbc.findById(4L).get();
            Assertions.assertEquals(product, EXPECTED_UPDATED_PRODUCT);
        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @Test
    public void savedProductShouldBeEqual() {
        ProductsRepositoryJdbcImpl productsRepositoryJdbc
                = new ProductsRepositoryJdbcImpl(connection);
        try {
            Product product = new Product(null, "сосиски", 99);
            productsRepositoryJdbc.save(product);
            product = productsRepositoryJdbc.findById(6L).get();
            Assertions.assertEquals(product, EXPECTED_SAVED_PRODUCT);

            product = new Product(100500L, null, 993);
            productsRepositoryJdbc.save(product);
            product = new Product(7L, null, 993);
            EXPECTED_SAVED_PRODUCT.setId(7L);
            EXPECTED_SAVED_PRODUCT.setName(null);
            EXPECTED_SAVED_PRODUCT.setPrice(993);
            Assertions.assertEquals(product, EXPECTED_SAVED_PRODUCT);
        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @Test
    public void productShouldBeDeleted() {
        ProductsRepositoryJdbcImpl productsRepositoryJdbc
                = new ProductsRepositoryJdbcImpl(connection);
        try {
            productsRepositoryJdbc.delete(5L);
            EXPECTED_FIND_ALL_PRODUCTS.remove(4);
            Assertions.assertEquals(productsRepositoryJdbc.findAll(),
                    EXPECTED_FIND_ALL_PRODUCTS);
        } catch (SQLException e) {
            Assertions.fail();
        }
    }
}
