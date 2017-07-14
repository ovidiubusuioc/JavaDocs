package ro.teamnet.zth.api.em;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Ovidiu.Busuioc on 7/13/2017.
 */
public interface EntityManager {
    <T> T findbyId(Class<T> entityClass, Long id)  throws SQLException;
    Long getNextIdVal(String tableName, String columnIdName);
    <T>Object insert(T entity) throws NoSuchFieldException, SQLException, IllegalAccessException;
    <T>List<T> findAll(Class<T> entityClass);
    <T> T update(T entity) throws SQLException;
    void delete(Object entity) throws SQLException, IllegalAccessException;
    <T> List<T> findByParams(Class<T> entityClass, Map<String, Object> params) throws SQLException;


}
