package com.sxj.supervisor.service.impl.rfid.purchase;

import java.util.List;
import java.util.concurrent.Callable;

import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;

public class WindowRfidThread implements Callable<Integer>
{
    
    private List<WindowRfidEntity> batch;
    
    private IWindowRfidService windowRfidService;
    
    public WindowRfidThread(List<WindowRfidEntity> batch,
            IWindowRfidService service)
    {
        super();
        this.batch = batch;
        windowRfidService = service;
    }
    
    @Override
    public Integer call() throws Exception
    {
        try
        {
            return windowRfidService.batchAddWindowRfid(batch.toArray(new WindowRfidEntity[batch.size()]));
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}
