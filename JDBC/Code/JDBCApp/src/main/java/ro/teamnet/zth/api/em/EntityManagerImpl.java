package ro.teamnet.zth.api.em;


import javafx.scene.shape.QuadCurve;
import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.database.DBManager;

import javax.sound.midi.Transmitter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ro.teamnet.zth.api.em.EntityUtils.getColumns;
import static ro.teamnet.zth.api.em.EntityUtils.getFieldsByAnnotations;
import static ro.teamnet.zth.api.em.EntityUtils.getTableName;
import static ro.teamnet.zth.api.em.QueryType.*;

/**
 * Created by Ovidiu.Busuioc on 7/13/2017.
 */
public class EntityManagerImpl implements EntityManager {


    @Override
    public <T> T findbyId(Class<T> entityClass, Long id) throws SQLException {
        Connection connection = DBManager.getConnection();
        Statement statement=connection.createStatement();
        List<ColumnInfo> myColumnList = getColumns(entityClass);
        String tableName = getTableName(entityClass);
        List<Field> myFieldList = getFieldsByAnnotations(entityClass, Id.class);
        Condition condition = new Condition();
        for(ColumnInfo s: myColumnList){
            if(s.isId())
                condition.setColumnName(s.getDbColumnName());
        }
        condition.setValue(id);
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.setTableName(tableName);
        queryBuilder.setQueryType(SELECT);
        queryBuilder.addCondition(condition);
        queryBuilder.addQueryColumns(myColumnList);
        String querry = queryBuilder.createQuery();
        try {
            ResultSet resultSet = statement.executeQuery(querry);
            if(resultSet.next()){
                T inst = entityClass.newInstance();
                for(ColumnInfo index : myColumnList){
                    Field declaredField = inst.getClass().getDeclaredField(index.getColumnName());
                    declaredField.setAccessible(true);
                    declaredField.set(inst, resultSet.getObject(index.getColumnName()));
                }
                return inst;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        connection.close();
        return null;
    }

    @Override
    public Long getNextIdVal(String tableName, String columnIdName) {
        Connection connection = DBManager.getConnection();
        Statement statement= null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String querry = "SELECT MAX("+columnIdName+")+1 FROM "+tableName;
        try {
            ResultSet resultSet = statement.executeQuery(querry);
            if(resultSet.next()){
                return  resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public <T> Object insert(T entity) throws NoSuchFieldException, SQLException, IllegalAccessException {
        Connection connection = DBManager.getConnection();
        Statement statement=connection.createStatement();
        List<ColumnInfo> myColumnList = getColumns((Class) entity);
        String tableName = getTableName((Class) entity);
        Long x = (long) 0;
        for (ColumnInfo i : myColumnList) {
            if(i.isId()){
                i.setValue(getNextIdVal(tableName,i.getColumnName()));
                x = getNextIdVal(tableName,i.getColumnName()).longValue();
            }
            else{
                Field field = ((Class) entity).getDeclaredField(i.getColumnName());
                field.setAccessible(true);
                i.setValue(field.get(entity));
            }

        }
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.setTableName(tableName);
        queryBuilder.setQueryType(INSERT);
        queryBuilder.addQueryColumns(myColumnList);
        String querry = queryBuilder.createQuery();
        if(statement.execute(querry)){
            return findbyId((Class<Object>) entity,x);
        }
        connection.close();
        return null;

    }

    @Override
    public <T> List<T> findAll(Class <T> entityClass) {
        Connection connection = DBManager.getConnection();
        Statement statement= null;
        try {
            statement = connection.createStatement();
            List<ColumnInfo> myColumnList = getColumns(entityClass);
            String tableName = getTableName(entityClass);
            List<Field> myFieldList = getFieldsByAnnotations(entityClass, Id.class);
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setTableName(tableName);
            queryBuilder.setQueryType(SELECT);
            queryBuilder.addQueryColumns(myColumnList);
            String querry = queryBuilder.createQuery();
            ResultSet resultSet = statement.executeQuery(querry);
            ArrayList<T> arrayList = new ArrayList<T>();
            int index = 0;
            while(resultSet.next()){
                T inst = entityClass.newInstance();
                for(ColumnInfo i : myColumnList){
                    Field f = inst.getClass().getDeclaredField(i.getColumnName());
                    f.setAccessible(true);
                    f.set(inst,resultSet.getObject(i.getColumnName()));
                }
                arrayList.add(index,inst);
                index++;

            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T update(T entity) throws SQLException {
        Connection connection = DBManager.getConnection();
        List<ColumnInfo> myColumnList = getColumns((Class) entity);
        String tableName = getTableName((Class) entity);
        long id = (long) 0;
        String columnName = "";
        for(ColumnInfo i: myColumnList){
            Field field = null;
            try {
                field = i.getClass().getDeclaredField(i.getColumnName());
                field.setAccessible(true);
                i.setValue(field.get(entity));
                if(i.isId()){
                    id = (long) i.getValue();
                    columnName = i.getDbColumnName();
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        Condition condition = new Condition();
        condition.setValue(id);
        condition.setColumnName(columnName);
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.setQueryType(UPDATE);
        queryBuilder.setTableName(tableName);
        queryBuilder.addQueryColumns(myColumnList);
        queryBuilder.addCondition(condition);
        String querry = queryBuilder.createQuery();
        Statement statement=connection.createStatement();
        ResultSet resultSet = statement.executeQuery(querry);
        connection.close();
       // connection.commit();
        return entity;
    }

    @Override
    public void delete(Object entity) throws SQLException, IllegalAccessException {
        Connection connection = DBManager.getConnection();
        List<ColumnInfo> myColumnList = getColumns((Class) entity);
        String tableName = getTableName((Class) entity);
        long id= (long) 0;
        String columnName = "";
        for(ColumnInfo i : myColumnList){
            Field field = null;
            try {
                field = i.getClass().getDeclaredField(i.getColumnName());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            field.setAccessible(true);
            i.setValue(field.get(entity));
            if(i.isId()){
                id = (Long) i.getValue();
                columnName = i.getColumnName();
            }
        }
        Condition condition = new Condition();
        condition.setColumnName(columnName);
        condition.setValue(id);
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.addCondition(condition);
        queryBuilder.addQueryColumns(myColumnList);
        queryBuilder.setTableName(tableName);
        queryBuilder.setQueryType(DELETE);
        String querry = queryBuilder.createQuery();
        try {
            Statement statement = connection.createStatement();
            statement.execute(querry);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.commit();
        connection.close();
    }

    @Override
    public <T> List<T> findByParams(Class<T> entityClass, Map<String, Object> params) throws SQLException {
        Connection connection = DBManager.getConnection();
        List<ColumnInfo> myColumnList = getColumns(entityClass);
        String tableName = getTableName(entityClass);
        List<T> myList = new ArrayList<T>();
        for(ColumnInfo i : myColumnList){
            Field field = null;
            try {
                field = i.getClass().getDeclaredField(i.getColumnName());
                field.setAccessible(true);
                i.setValue(field.get(entityClass.newInstance()));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
        Condition condition = new Condition();

        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.setQueryType(SELECT);
        queryBuilder.setTableName(tableName);
        queryBuilder.addCondition(condition);
        Statement statement = connection.createStatement();
        String querry = queryBuilder.createQuery();
        ResultSet resultSet = statement.executeQuery(querry);
        while (resultSet.next()){
            try {
                T inst = entityClass.newInstance();
                for(ColumnInfo columnInfo: myColumnList){
                    Field field = inst.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    field.set(inst,resultSet.getObject(columnInfo.getColumnName()));
                }
                myList.add(inst);
                connection.close();
                return myList;


            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        connection.close();
        return null;
    }
}
