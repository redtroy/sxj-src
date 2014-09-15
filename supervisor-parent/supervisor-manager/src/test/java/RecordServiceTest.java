import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.service.record.IRecordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class RecordServiceTest
{
    @Autowired
    private IRecordService recordService;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    public void testAddRecord()
    {
        RecordEntity record = new RecordEntity();
        recordService.addRecord(record);
    }
    
    public void testModifyRecord()
    {
        fail("Not yet implemented");
    }
    
    public void testDeleteRecord()
    {
        fail("Not yet implemented");
    }
    
    @Test
    public void testGetRecord()
    {
        RecordEntity record = recordService.getRecord("B1mGKTM8m12qu11M6A30Bz7cfTyOjhUh");
        Assert.assertNotNull(record);
    }
    
    public void testQueryRecord()
    {
        fail("Not yet implemented");
    }
    
    public void testBindingContract()
    {
        fail("Not yet implemented");
    }
    
    public void testGetRecordByNo()
    {
        fail("Not yet implemented");
    }
    
    public void testModifyState()
    {
        fail("Not yet implemented");
    }
    
    public void setRecordService(IRecordService recordService)
    {
        this.recordService = recordService;
    }
    
}
