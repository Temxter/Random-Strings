package dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EntityDaoTest {

    private EntityDao entityDao;

    @Before
    public void init() {
        entityDao = new EntityDao();
    }

    @Test
    public void sumIntegersTest() {
        Long result = entityDao.getSumIntegers();
        Assert.assertNotNull("Integers sum not calculated.", result);
        System.out.println(result);
    }

    @Test
    public void medianDoublesTest() {
        Double result = entityDao.getMedianDoubles();
        Assert.assertNotNull("Doubles medians not calculated.", result);
        System.out.println(result);
    }
}
