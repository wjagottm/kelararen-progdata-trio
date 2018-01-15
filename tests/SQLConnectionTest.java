import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class SQLConnectionTest {

    private Object fakeAccount = 1215425;

    @Test
    //Test if getAccountInformation returns a ResultSet with the expected information.
    void getAccountInformationTestResultSet() {
        //Arange
        SQLConnection connection =  new SQLConnection();
        Object account = connection.getFirstAccountId();
        ResultSet expected = connection.getFirstAccountInformation();
        Object expectedId = null;
        Object expectedName = null;
        Object returnId = null;
        Object returnName = null;

        try {
            while (expected.next()){
                expectedId = expected.getString("SubscriberId");
                expectedName = expected.getString("Name");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        //Act
        ResultSet returnValue = connection.getAccountInformation(account);

        try {
            while (returnValue.next()){
                returnId = returnValue.getString("SubscriberId");
                returnName = returnValue.getString("Name");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        //Assert
        Assertions.assertEquals(expectedId, returnId);
        Assertions.assertEquals(expectedName, returnName);
    }

    @Test
        //Test if getAccountInformation returns an empty ResultSet.
    void getAccountInformationTestReturnEmptyResultSet() {
        //Arange
        SQLConnection connection =  new SQLConnection();
        Object account = connection.getFirstAccountId();
        ResultSet expected = connection.getAccountInformation(fakeAccount);
        Object expectedId = null;
        Object expectedName = null;
        Object returnId = null;
        Object returnName = null;

        try {
            while (expected.next()){
                expectedId = expected.getString("SubscriberId");
                expectedName = expected.getString("Name");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        //Act
        ResultSet returnValue = connection.getAccountInformation(account);

        try {
            while (returnValue.next()){
                returnId = returnValue.getString("SubscriberId");
                returnName = returnValue.getString("Name");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        //Assert
        Assertions.assertNotEquals(returnId, expectedId);
        Assertions.assertNotEquals(returnName, expectedName);
    }

}