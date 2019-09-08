package parameterManager;

import entity.Address;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import servises.parameterManager.ParameterManager;

public class ParameterManagerTest {
    private static ParameterManager pm;

    @BeforeClass
    public static void init() {
        pm = new ParameterManager();
    }

    @AfterClass
    public static void clear() {
        pm = null;
    }

    @Test
    public void isAllEmptyTest() {
        String s = null;
        String s2 = null;
        String s3 = null;
        boolean isAllEmpty = pm.isAllEmpty(s, s2, s3);
        Assert.assertTrue(isAllEmpty);
    }

    @Test
    public void isAllEmptyTest2() {
        String s = null;
        String s2 = null;
        String s3 = "not null";
        boolean isAllEmpty = pm.isAllEmpty(s, s2, s3);
        Assert.assertFalse(isAllEmpty);
    }

    @Test
    public void isEmptyTest() {
        String s = null;
        String s2 = null;
        String s3 = "not null";
        boolean isEmpty = pm.isEmpty(s, s2, s3);
        Assert.assertTrue(isEmpty);
    }

    @Test
    public void isEmailCorrectTest() {
        String email = "test@ukr.net";
        boolean isCorrect = pm.isEmailCorrect(email);
        Assert.assertTrue(isCorrect);
    }

    @Test
    public void isEmailCorrectTest2() {
        String email = "test@ukr";
        boolean isCorrect = pm.isEmailCorrect(email);
        Assert.assertFalse(isCorrect);
    }

    @Test
    public void isPasswordCorrect() {
        String password = "pass23";
        boolean isCorrect = pm.isPasswordCorrect(password);
        Assert.assertTrue(isCorrect);
    }

    @Test
    public void isPasswordCorrect2() {
        String password = "pass";
        boolean isCorrect = pm.isPasswordCorrect(password);
        Assert.assertFalse(isCorrect);
    }

    @Test
    public void isAddressCorrect() {
        Address address = new Address("Kiev", "Glinky", "12", "8");
        boolean isCorrect = pm.isAddressCorrect(address);
        Assert.assertTrue(isCorrect);
    }

    @Test
    public void isAddressCorrect2() {
        Address address = new Address("Tigeran73", "Glinky", "12", "8");
        boolean isCorrect = pm.isAddressCorrect(address);
        Assert.assertFalse(isCorrect);
    }

    @Test
    public void isNameAndSurnameCorrectTest() {
        String name = "Timofey";
        String surname = "Gavrilenko";
        boolean isCorrect = pm.isNameAndSurnameCorrect(name, surname);
        Assert.assertTrue(isCorrect);
    }

    @Test
    public void isNameAndSurnameCorrectTest2() {
        String name = "Timofey33";
        String surname = "Gavrilenko";
        boolean isCorrect = pm.isNameAndSurnameCorrect(name, surname);
        Assert.assertFalse(isCorrect);
    }

    @Test
    public void isThemeCorrectTest2() {
        String theme = " ";
        boolean isCorrect = pm.isThemeCorrect(theme);
        Assert.assertFalse(isCorrect);
    }

    @Test
    public void isNumberTest() {
        String number = "77";
        boolean isCorrect = pm.isNumberCorrect(number);
        Assert.assertTrue(isCorrect);
    }

    @Test
    public void isNumberTest2() {
        String number = "77a";
        boolean isCorrect = pm.isNumberCorrect(number);
        Assert.assertFalse(isCorrect);
    }

}
