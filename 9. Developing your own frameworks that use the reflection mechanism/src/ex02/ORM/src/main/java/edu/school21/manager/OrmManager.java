package edu.school21.manager;

import java.io.File;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

public class OrmManager {
    private final Connection connection;

    public OrmManager(Connection connection, String entitiesDirName)
            throws SQLException {
        this.connection = connection;

        File packageDir = new File(entitiesDirName);
        List<Class> entitiesClasses = new ArrayList<>();
        for (File file : Objects.requireNonNull(packageDir.listFiles())) {
            String className = file.getName().replace(".java", "");
            try {
                Class entityClass = ClassLoader.getSystemClassLoader().loadClass(
                        "edu.school21.entities." + className);
                entitiesClasses.add(entityClass);
            } catch (ClassNotFoundException e) {
                System.err.println("Unable to process class " + className);
                System.exit(1);
            }
        }

        for (Class class_ : entitiesClasses) {
            generateCreateTableQuery(class_);
        }
    }

    private void generateCreateTableQuery(Class class_) throws SQLException {
        OrmEntity entity = (OrmEntity) class_.getAnnotation(OrmEntity.class);
        OrmColumnId id = null;
        List<OrmColumn> columnsAnnotations = new ArrayList<>();
        List<Type> columnsTypes = new ArrayList<>();
        for (Field field : class_.getDeclaredFields()) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                id = field.getAnnotation(OrmColumnId.class);
            } else if (field.isAnnotationPresent(OrmColumn.class)) {
                columnsAnnotations.add(field.getAnnotation(OrmColumn.class));
                columnsTypes.add(field.getType());
            }
        }

        String statement = "drop table if exists " + entity.tableName() + ";\n";
        statement += "create table " + entity.tableName() + " (\n";
        if (id != null) {
            statement += "    id serial primary key,\n";
        }

        for (int i = 0; i < columnsAnnotations.size(); ++i) {
            statement += "    " + columnsAnnotations.get(i).name() + " ";
            Type fieldType = columnsTypes.get(i);
            if (fieldType.equals(Integer.class) || fieldType.equals(Long.class)) {
                statement += "bigint";
            } else if (fieldType.equals(Double.class)) {
                statement += "numeric";
            } else if (fieldType.equals(Boolean.class)) {
                statement += "boolean";
            } else if (fieldType.equals(String.class)) {
                statement += "varchar(" + columnsAnnotations.get(i).length() + ")";
            } else {
                System.err.println("Type " + fieldType + " not supported");
                System.exit(1);
            }

            if (i != columnsAnnotations.size() - 1) {
                statement += ",\n";
            } else {
                statement += "\n);\n";
            }
        }

        System.out.println(statement);
        PreparedStatement preparedStatement
                = connection.prepareStatement(statement);
        preparedStatement.execute();

    }

    public void save(Object entity) throws SQLException {
        Class class_ = entity.getClass();
        OrmEntity ormEntity = (OrmEntity) class_.getAnnotation(OrmEntity.class);
        String statement = "insert into " + ormEntity.tableName() + " (";

        List<OrmColumn> columnsAnnotations = new ArrayList<>();
        List<Object> columnsValues = new ArrayList<>();
        for (Field field : class_.getDeclaredFields()) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                field.setAccessible(true);
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                Object value = null;
                try {
                    value = field.get(entity);
                } catch (IllegalAccessException e) {
                    System.err.println(
                            "Unable to access field: " + field.getName());
                    System.exit(1);
                }

                if (value != null) {
                    columnsValues.add(value);
                    columnsAnnotations.add(ormColumn);
                }
            }
        }

        for (int i = 0; i < columnsAnnotations.size(); ++i) {
            statement += columnsAnnotations.get(i).name();
            if (i != columnsAnnotations.size() - 1) {
                statement += ", ";
            } else {
                statement += ")\n";
            }
        }

        statement += "values (";
        for (int i = 0; i < columnsValues.size(); ++i) {
            if (columnsValues.get(i).getClass().equals(String.class)) {
                statement += "'";
            }
            statement += columnsValues.get(i);
            if (columnsValues.get(i).getClass().equals(String.class)) {
                statement += "'";
            }
            if (i != columnsValues.size() - 1) {
                statement += ", ";
            }
        }
        statement += ");\n";

        PreparedStatement preparedStatement
                = connection.prepareStatement(statement);
        preparedStatement.execute();
        System.out.println(statement);
    }

    public void update(Object entity) throws SQLException {
        Class class_ = entity.getClass();
        OrmEntity ormEntity = (OrmEntity) class_.getAnnotation(OrmEntity.class);
        String statement = "update " + ormEntity.tableName() + "\nset ";

        List<OrmColumn> columnsAnnotations = new ArrayList<>();
        List<Object> columnsValues = new ArrayList<>();
        Long id = null;
        for (Field field : class_.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                Object value = null;
                try {
                    value = field.get(entity);
                } catch (IllegalAccessException e) {
                    System.err.println("Unable to access to field: "
                            + field.getName());
                    System.exit(1);
                }

                if (value != null) {
                    columnsValues.add(value);
                    columnsAnnotations.add(ormColumn);
                }
            } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                try {
                    id = (Long) field.get(entity);
                } catch (IllegalAccessException e) {
                    System.err.println("Unable to access to field: "
                            + field.getName());
                    System.exit(1);
                }
                if (id == null) {
                    System.err.println(
                            "Id of the object being updated is null");
                    System.exit(1);
                }
            }
        }

        for (int i = 0; i < columnsAnnotations.size(); ++i) {
            statement += columnsAnnotations.get(i).name() + " = ";
            if (columnsValues.get(i).getClass().equals(String.class)) {
                statement += "'";
            }
            statement += columnsValues.get(i);
            if (columnsValues.get(i).getClass().equals(String.class)) {
                statement += "'";
            }
            if (i != columnsAnnotations.size() - 1) {
                statement += ", ";
            } else {
                statement += "\n";
            }
        }

        statement += "where id = " + id + "\n";

        PreparedStatement preparedStatement
                = connection.prepareStatement(statement);
        preparedStatement.execute();
        System.out.println(statement);
    }

    public <T> T findById(Long id, Class<T> class_) throws SQLException {
        OrmEntity ormEntity = class_.getAnnotation(OrmEntity.class);
        String statement = "select * from " + ormEntity.tableName()
                + " where id = " + id + "\n";
        PreparedStatement preparedStatement
                = connection.prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        Constructor constructor;
        T object = null;
        try {
            constructor = class_.getConstructor(Long.class);
            object = (T) constructor.newInstance(id);
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            System.err.println("There is no public constructor with id");
            System.exit(1);
        }

        for (Field field : class_.getDeclaredFields()) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                field.setAccessible(true);
                String columnName = field.getAnnotation(OrmColumn.class).name();
                Type fieldType = field.getType();
                try {
                    if (fieldType.equals(Integer.class)) {
                        field.set(object, resultSet.getInt(columnName));
                    } else if (fieldType.equals(Long.class)) {
                        field.set(object, resultSet.getLong(columnName));
                    } else if (fieldType.equals(Boolean.class)) {
                        field.set(object, resultSet.getBoolean(columnName));
                    } else if (fieldType.equals(Double.class)) {
                        field.set(object, resultSet.getDouble(columnName));
                    } else if (fieldType.equals(String.class)) {
                        field.set(object, resultSet.getString(columnName));
                    } else {
                        System.err.println("Type " + fieldType + " not supported");
                        System.exit(1);
                    }
                } catch (Exception e) {
                    System.err.println("Unable to initialize object");
                    System.exit(1);
                }
            }
        }

        System.out.println(statement);
        return object;
    }
}
