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

import static ro.teamnet.zth.api.em.EntityUtils.getColumns;
import static ro.teamnet.zth.api.em.EntityUtils.getFieldsByAnnotations;
import static ro.teamnet.zth.api.em.EntityUtils.getTableName;
import static ro.teamnet.zth.api.em.QueryType.INSERT;
import static ro.teamnet.zth.api.em.QueryType.SELECT;

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
                T inst = (T) entityClass.newInstance();
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


        return null;
    }

    @Override
    public Long getNextIdVal(String tableName, String columnIdName) {
        Connection connection = DBManager.getConnection();
        Statement statement=connection.createStatement();
        String querry = "SELECT MAX("+columnIdName+")+1 FROM "+tableName;
        try {
            return  statement.executeQuery(querry).getLong(columnIdName);
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
    public<T> Object insert(T entity) throws NoSuchFieldException, SQLException {
        Connection connection = DBManager.getConnection();
        Statement statement=connection.createStatement();
        List<ColumnInfo> myColumnList = getColumns((Class) entity);
        String tableName = getTableName((Class) entity);
        Long x = 0;
        for (ColumnInfo i : myColumnList) {
            if(i.isId()){
                i.setValue(getNextIdVal(tableName,i.getColumnName()));
                x = getNextIdVal(tableName,i.getColumnName()).longValue();
            }
            else{
                Field field = ((Class) entity).getDeclaredField(i.getColumnName());
                field.setAccessible(true);
                i.setValue(field.getLong());
            }

        }
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.setTableName(tableName);
        queryBuilder.setQueryType(INSERT);
        queryBuilder.addQueryColumns(myColumnList);
        String querry = queryBuilder.createQuery();
        ResultSet resultSet = statement.executeQuery(querry);

        return findbyId(entity,x);
    }

    @Override
    public List findAll(Class entityClass) {
        Connection connection = DBManager.getConnection();
        Statement statement=connection.createStatement();
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
        while(resultSet.next()){
            T inst = new entityClass.newInstance();

        }

        return null;
    }
}
