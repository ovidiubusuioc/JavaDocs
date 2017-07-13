package ro.teamnet.zth.api.em;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ovidiu.Busuioc on 7/13/2017.
 */
public interface EntityManager {
    <T> T findbyId(Class<T> entityClass, Long id)  throws SQLException;
    Long getNextIdVal(String tableName, String columnIdName);
    Object insert(T entity);
    <T>List<T> findAll(Class<T> entityClass);

}
