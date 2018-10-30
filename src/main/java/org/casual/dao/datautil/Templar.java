package org.casual.dao.datautil;

import org.casual.util.ResultMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/10/29
 */
public class Templar {

    private Templar(){}

    public static ResultMessage update(String sql, Object... args){
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql)){
            inject(statement, args);
            statement.executeUpdate();
            return ResultMessage.SUCCESS;
        } catch (SQLException e){
            e.printStackTrace();
            return ResultMessage.FAILURE;
        }
    }

    public static Object getOne(String sql, Class type, Object... args){
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql)){
            inject(statement, args);
            try (ResultSet set = statement.executeQuery()){
                List list = fetchValue(set, type);
                if (list.isEmpty()){
                    return null;
                } else {
                    return list.get(0);
                }
            }
        } catch (SQLException e){
            return null;
        } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException|InstantiationException e){
            e.printStackTrace();
            return null;
        }
    }

    public static List getList(String sql, Class type, Object... args){
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql)){
            inject(statement, args);
            try (ResultSet set = statement.executeQuery()){
                return fetchValue(set, type);
            }
        } catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException|InstantiationException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private static void inject(PreparedStatement statement, Object... args) throws SQLException{
        for (int i=1; i<=args.length; i++) {
            Object arg = args[i-1];
            if (arg.getClass() == Integer.class){
                statement.setInt(i, (Integer) arg);
            } else if (arg.getClass() == String.class){
                statement.setString(i, (String)arg);
            } else if (arg.getClass() == Double.class){
                statement.setDouble(i, (Double)arg);
            } else if (arg.getClass() == Boolean.class){
                statement.setBoolean(i, (Boolean)arg);
            } else if (arg.getClass() == Long.class){
                statement.setLong(i, (Long)arg);
            } else if (arg.getClass() == LocalDate.class){
                statement.setDate(i, Date.valueOf((LocalDate)arg));
            } else if (arg.getClass() == LocalDateTime.class){
                statement.setTimestamp(i, Timestamp.valueOf((LocalDateTime)arg));
            } else {//unknown data type
                throw new SQLException();
            }
        }
    }

    private static List fetchValue(ResultSet set, Class type) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException{
        List<Method> setters = new ArrayList<>(type.getMethods().length);
        for (Method method : type.getMethods()) {
            setters.add(method);
        }
        setters.removeIf(method -> !method.getName().startsWith("set") || method.getParameterTypes().length!=1);
        List<Object> result = new ArrayList<>();

        while (set.next()){
            Object temp = type.getConstructor().newInstance();

            for (Method setter : setters) {//set values
                String valueLabel = setter.getName().substring(3);
                Class fieldType = setter.getParameterTypes()[0];

                if (fieldType == Integer.class || fieldType == int.class){
                    setter.invoke(temp, set.getInt(valueLabel));
                } else if (fieldType == String.class){
                    setter.invoke(temp, set.getString(valueLabel));
                } else if (fieldType == Double.class || fieldType == double.class){
                    setter.invoke(temp, set.getDouble(valueLabel));
                } else if (fieldType == Boolean.class || fieldType == boolean.class){
                    setter.invoke(temp, set.getBoolean(valueLabel));
                } else if (fieldType == Long.class || fieldType == long.class){
                    setter.invoke(temp, set.getLong(valueLabel));
                } else if (fieldType == LocalDate.class){
                    setter.invoke(temp, set.getDate(valueLabel).toLocalDate());
                } else if (fieldType == LocalDateTime.class){
                    setter.invoke(temp, set.getTimestamp(valueLabel).toLocalDateTime());
                } else {
                    throw new SQLException();
                }
            }
            result.add(temp);
        }
        return result;
    }
}
