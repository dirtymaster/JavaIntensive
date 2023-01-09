package edu.school21.reflection.UI;

import java.lang.reflect.*;
import java.util.*;

public class UI {
    private static final Set<String> classesNames = new HashSet<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static Class currentClass;
    private static Field[] fields;
    private static final List<Object> argumentsOfConstructor = new ArrayList<>();
    private static Class<?>[] parametersTypes;
    private static final List<Object> parameters = new ArrayList<>();
    private static Object currentObject;

    public static void main(String[] args) {
        initClasses();

        outputClassesNames();
        System.out.println("---------------------");

        currentClass = requestClass();

        outputFields();
        System.out.println("---------------------");

        outputMethods();
        System.out.println("---------------------");

        createObject();
        System.out.println("---------------------");

        changeObject();
        System.out.println("---------------------");

        callMethod();
    }

    private static void initClasses() {
        classesNames.add("User");
        classesNames.add("Car");
    }

    private static Class requestClass() {
        System.out.println("Enter class name:");
        try {
            return Class.forName(
                    "edu.school21.classes." + scanner.nextLine());
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid class name");
            return requestClass();
        }
    }

    private static void outputClassesNames() {
        System.out.println("Classes:");
        for (String className : classesNames) {
            System.out.println("   -   " + className);
        }
    }

    private static void outputFields() {
        System.out.println("fields:");
        fields = currentClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldType = field.getType().getSimpleName();
            System.out.println("       " + fieldType
                    + " " + field.getName());
        }
    }

    private static void outputMethods() {
        System.out.println("methods:");
        Method[] methods = currentClass.getDeclaredMethods();
        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();
            System.out.print("      " + method.getReturnType().getSimpleName()
                    + " " + method.getName() + "(");
            for (int i = 0; i < parameters.length; ++i) {
                System.out.print(parameters[i].getType().getSimpleName());
                if (i != parameters.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(")");
        }
    }

    private static void createObject() {
        System.out.println("Let's create an object.");

        for (Field field : fields) {
            System.out.println(field.getName() + ":");
            scanArgumentOfConstructor(field);
        }

        initParametersTypes();

        try {
            Constructor<?> constructor
                    = currentClass.getConstructor(parametersTypes);

            currentObject = constructor.newInstance(argumentsOfConstructor.toArray());
        } catch (InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            System.err.println(
                    "There is no public constructor with these parameters");
            System.exit(1);
        }

        System.out.print("Object created: ");
        outputObjectParameters();
    }

    private static void scanArgumentOfConstructor(Field field) {
        Type type = field.getType();
        try {
            if (type.equals(int.class) || type.equals(Integer.class)) {
                argumentsOfConstructor.add(scanner.nextInt());
            } else if (type.equals(boolean.class)
                    || type.equals(Boolean.class)) {
                argumentsOfConstructor.add(scanner.nextBoolean());
            } else if (type.equals(double.class) || type.equals(Double.class)) {
                argumentsOfConstructor.add(scanner.nextDouble());
            } else if (type.equals(String.class)) {
                argumentsOfConstructor.add(scanner.nextLine());
            } else {
                System.err.println("The type '" + field.getType().getSimpleName()
                        + "' is not supported");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("The value does not match the type");
            scanArgumentOfConstructor(field);
            System.exit(1);
        }
    }

    private static void initParametersTypes() {
        parametersTypes = new Class<?>[argumentsOfConstructor.size()];
        for (int i = 0; i < parametersTypes.length; ++i) {
            parametersTypes[i] = argumentsOfConstructor.get(i).getClass();
            if (parametersTypes[i].equals(Integer.class)) {
                parametersTypes[i] = int.class;
            } else if (parametersTypes[i].equals(Boolean.class)) {
                parametersTypes[i] = boolean.class;
            } else if (parametersTypes[i].equals(Double.class)) {
                parametersTypes[i] = double.class;
            }
        }
    }

    private static void outputObjectParameters() {
        System.out.println(currentObject);
    }

    public static void changeObject() {
        System.out.println("Enter name of the field for changing:");

        scanner.nextLine();
        String fieldName = scanner.nextLine();

        boolean fieldFound = false;
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                fieldFound = true;
                Type type = field.getType();
                try {
                    if (type.equals(int.class) || type.equals(Integer.class)) {
                        System.out.println("Enter int value:");
                        field.set(currentObject, scanner.nextInt());
                    } else if (type.equals(boolean.class)
                            || type.equals(Boolean.class)) {
                        System.out.println("Enter boolean value:");
                        field.set(currentObject, scanner.nextBoolean());
                    } else if (type.equals(double.class)
                            || type.equals(Double.class)) {
                        System.out.println("Enter double value:");
                        field.set(currentObject, scanner.nextDouble());
                    } else if (type.equals(String.class)) {
                        System.out.println("Enter String value:");
                        field.set(currentObject, scanner.nextLine());
                    }
                } catch (Exception e) {
                    System.err.println(
                            "It is not possible to change " +
                                    "the value of the field");
                    System.exit(1);
                }
            }
        }
        if (!fieldFound) {
            System.err.println("There is no field with name " + fieldName);
        } else {
            try {
                for (int i = 0; i < argumentsOfConstructor.size(); ++i) {
                    argumentsOfConstructor.set(i, fields[i].get(currentObject));
                }
            } catch (Exception ignored) {
            }

            System.out.print(
                    "Object updated: ");
            outputObjectParameters();
        }
    }

    private static void callMethod() {
        System.out.println("Enter name of the method for call");
        String line = scanner.next();
        // для создания метода нужно его название и тип параметра
        String methodName = line.substring(0, line.indexOf('('));
        char[] charArray = line.toCharArray();
        List<String> parametersTypesNames = new ArrayList<>();
        int startIndex = line.indexOf('(') + 1;
        for (int i = startIndex; i < charArray.length; ++i) {
            if (charArray[i] == ',' || charArray[i] == ')') {
                String typeName = line.substring(startIndex, i);
                parametersTypesNames.add(typeName);
                startIndex = i + 1;
            }
        }

        parameters.clear();
        for (String parametersTypeName : parametersTypesNames) {
            Class class_ = null;
            try {
                switch (parametersTypeName) {
                    case "int":
                    case "Integer":
                        System.out.println("Enter int value:");
                        parameters.add(scanner.nextInt());
                        break;
                    case "boolean":
                    case "Boolean":
                        System.out.println("Enter boolean value:");
                        parameters.add(scanner.nextBoolean());
                        break;
                    case "double":
                    case "Double":
                        System.out.println("Enter double value:");
                        parameters.add(scanner.nextDouble());
                        break;
                    case "String":
                        System.out.println("Enter String value:");
                        parameters.add(scanner.nextLine());
                        break;
                    default:
                        System.err.println("The type '" + class_.getSimpleName()
                                + "' is not supported");
                        System.exit(1);
                }
            } catch (Exception e) {
                System.err.println("Invalid parameter");
                System.exit(1);
            }
        }

        parametersTypes = new Class<?>[parameters.size()];
        for (int i = 0; i < parameters.size(); ++i) {
            Class class_ = parameters.get(i).getClass();
            if (class_.equals(Integer.class)) {
                parametersTypes[i] = int.class;
            } else if (class_.equals(Boolean.class)) {
                parametersTypes[i] = boolean.class;
            } else if (class_.equals(Double.class)) {
                parametersTypes[i] = double.class;
            } else {
                parametersTypes[i] = class_;
            }
        }
        Object result = null;
        try {
            Method method = currentClass.getMethod(methodName, parametersTypes);
            result = method.invoke(currentObject, parameters.toArray());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("There is no such method");
            System.exit(1);
        }

        if (result != null) {
            System.out.println("Method returned:");
            System.out.println(result);
        }
    }
}
