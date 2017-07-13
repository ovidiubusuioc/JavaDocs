package ro.teamnet.zth.api.database;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Ovidiu.Busuioc on 7/13/2017.
 */
public class DBManagerTest {

    @Test
    public void testgetConnectionMethod(){
        Connection con1 = DBManager.getConnection();
        assertNotEquals(con1,null);


    }
    @Test
    public void testcheckConnectionMethod(){
        Connection con2 = DBManager.getConnection();
        assertEquals(DBManager.checkConnection(con2),true);
    }
}
