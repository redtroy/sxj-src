package com.sxj.supervisor.manage.controller.rfid.purchase;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.purchase.DeliveryStateEnum;
import com.sxj.supervisor.enu.rfid.purchase.ImportStateEnum;
import com.sxj.supervisor.enu.rfid.purchase.PayStateEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.service.rfid.base.IRfidSupplierService;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.supervisor.service.rfid.purchase.IPurchaseRfidService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.util.common.FileUtil;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/purchase")
public class PurchaseRfidController extends BaseController
{
    @Autowired
    private IPurchaseRfidService purchaseRfidService;
    
    @Autowired
    private IRfidApplicationService appService;
    
    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private IRfidSupplierService supplierService;
    
    /**
     * 门窗RFID
     */
    @Autowired
    private IWindowRfidService windowRfidService;
    
    /**
     * 物流RFID
     */
    @Autowired
    private ILogisticsRfidService logisticsRfidService;
    
    /**
     * 查询列表
     * 
     * @param query
     * @param model
     * @return
     * @throws WebException
     */
    @RequestMapping("query")
    public String queryPurchaseRfid(PurchaseRfidQuery query, ModelMap model)
            throws WebException
    {
        try
        {
            query.setPagable(true);
            List<RfidPurchaseEntity> list = purchaseRfidService.queryPurchase(query);
            DeliveryStateEnum[] delivery = DeliveryStateEnum.values();
            ImportStateEnum[] importState = ImportStateEnum.values();
            PayStateEnum[] payState = PayStateEnum.values();
            RfidTypeEnum[] type = RfidTypeEnum.values();
            model.put("delivery", delivery);
            model.put("importState", importState);
            model.put("payState", payState);
            model.put("type", type);
            model.put("query", query);
            model.put("list", list);
            return "manage/rfid/purchase/purchase-list";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询采购单错误", e, this.getClass());
            throw new WebException("查询采购单错误");
        }
    }
    
    /**
     * 删除
     * 
     * @param id
     * @param model
     * @return
     * @throws WebException
     */
    @RequestMapping("delete")
    public @ResponseBody Map<String, String> delete(String id, ModelMap model)
            throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            purchaseRfidService.deletePurchase(id);
            map.put("isOK", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("删除采购单错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 确认付款
     * 
     * @param id
     * @param model
     * @return
     * @throws WebException
     */
    @RequestMapping("confirmPay")
    public @ResponseBody Map<String, String> confirmPay(String id,
            ModelMap model) throws WebException
    {
        try
        {
            purchaseRfidService.updatePayState(id, PayStateEnum.PAYED);
            Map<String, String> map = new HashMap<String, String>();
            map.put("isOK", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("确认付款错误", e, this.getClass());
            throw new WebException("确认付款错误");
        }
    }
    
    /**
     * 确认收货
     * 
     * @param id
     * @param model
     * @return
     * @throws WebException
     */
    @RequestMapping("confirmReceipt")
    public @ResponseBody Map<String, String> confirmReceipt(String id,
            ModelMap model) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            purchaseRfidService.confirmReceipt(id);
            map.put("isOK", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("确认收货错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 确认发货
     * 
     * @param id
     * @param model
     * @return
     * @throws WebException
     */
    @RequestMapping("confirmDelivery")
    public @ResponseBody Map<String, String> confirmDelivery(String id,
            ModelMap model) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            purchaseRfidService.confirmDelivery(id);
            map.put("isOK", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("确认发货错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 申请单详情
     * 
     * @param id
     * @param applyNo
     * @param model
     * @return
     * @throws WebException
     */
    @RequestMapping("purchaseInfo")
    public String getPurchase(String id, String applyNo, ModelMap model)
            throws WebException
    {
        try
        {
            RfidApplicationEntity app = appService.getApplication(applyNo);
            model.put("app", app);
            if (app != null)
            {
                MemberEntity member = memberService.memberInfo(app.getMemberNo());
                model.put("member", member);
            }
            model.put("id", id);
            return "manage/rfid/purchase/purchase-info";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询采购单错误", e, this.getClass());
            throw new WebException("查询采购单错误");
        }
    }
    
    /**
     * 修改采购单
     * 
     * @param purchase
     * @param model
     * @return
     * @throws WebException
     */
    @RequestMapping("modify")
    public @ResponseBody Map<String, String> modify(
            RfidPurchaseEntity purchase, ModelMap model) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            purchaseRfidService.updatePurchase(purchase);
            map.put("isOK", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("修改采购单错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 跳转添加
     * 
     * @param id
     * @param model
     * @return
     * @throws WebException
     */
    @RequestMapping("toAdd")
    public String toAdd(String id, ModelMap model) throws WebException
    {
        try
        {
            RfidApplicationEntity app = appService.getApplicationInfo(id);
            model.put("app", app);
            return "manage/rfid/purchase/purchase-add";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询采购单错误", e, this.getClass());
            throw new WebException("查询采购单错误");
        }
    }
    
    /**
     * 添加
     * 
     * @param purchase
     * @param model
     * @return
     * @throws WebException
     */
    @RequestMapping("add")
    public @ResponseBody Map<String, String> add(RfidPurchaseEntity purchase,
            String applyId, String hasNumber, ModelMap model)
            throws WebException
    {
        try
        {
            purchase.setPurchaseDate(new Date());
            purchase.setImportState(ImportStateEnum.NOT_IMPORTED);
            purchase.setPayState(PayStateEnum.UN_PAYED);
            purchase.setReceiptState(DeliveryStateEnum.UN_FILLED);
            purchaseRfidService.addPurchase(purchase, applyId, hasNumber);
            Map<String, String> map = new HashMap<String, String>();
            map.put("isOK", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("新增采购单错误", e, this.getClass());
            throw new WebException("新增采购单错误");
        }
    }
    
    @RequestMapping("getSupplierPrice")
    public @ResponseBody Map<String, Object> getSupplierPrice(String supplierNo)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            RfidSupplierEntity supplier = supplierService.getSupplierByNo(supplierNo);
            if (supplier != null)
            {
                Double doorPrice = supplier.getDoorsPrice();
                Double batchPrice = supplier.getBatchPrice();
                map.put("doorPrice", doorPrice);
                map.put("batchPrice", batchPrice);
            }
            else
            {
                map.put("error", "供应商不存在");
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("获取价格错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("getMaxCount")
    public @ResponseBody Map<String, Object> getMaxCount(String applyNo,
            Long oldCount) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            RfidApplicationEntity apply = appService.getApplication(applyNo);
            if (apply == null)
            {
                throw new WebException("对应的申请单不存在");
            }
            long max = apply.getCount() - (apply.getHasNumber() - oldCount);
            map.put("maxCount", max);
        }
        catch (Exception e)
        {
            SxjLogger.error("获取价格错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("importRfid")
    public @ResponseBody Map<String, String> importRfid(String purchaseId,
            ModelMap model) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            purchaseRfidService.importRfid(purchaseId);
            map.put("isOK", "ok");
            
        }
        catch (Exception e)
        {
            SxjLogger.error("导入RFID错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 导出RFID CSV文件
     * 
     * @param purchaseId
     * @param model
     * @return
     * @throws WebException
     * @throws IOException 
     */
    @RequestMapping("exportRfid")
    public void exportRfid(String purchaseNo, Integer type, ModelMap model,
            HttpServletResponse response, HttpServletRequest request)
            throws WebException, IOException
    {
        Map<String, String> map = new HashMap<String, String>();
        InputStream in = null;
        ServletOutputStream out = null;
        try
        {
            String agent = request.getHeader("User-Agent");
            boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
            File file = writeCsv(purchaseNo, type);
            String fileName = file.getName();
            if (isMSIE)
            {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            else
            {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.reset();
            
            response.addHeader("Content-Disposition", "attachment;filename="
                    + fileName);
            //response.setContentType("APPLICATION/OCTET-STREAM");
            //response.setContentType("application/zip");
            response.setContentType("application/x-download");
            System.err.println(file.getName());
            response.addHeader("Content-Length", "" + file.length());
            response.setContentLength((int) file.length());
            in = new FileInputStream(file); //获取文件的流  
            int len = 0;
            byte buf[] = new byte[1024];//缓存作用  
            
            out = response.getOutputStream();//输出流  
            while ((len = in.read(buf)) > 0) //切忌这后面不能加 分号 ”;“  
            {
                out.write(buf, 0, len);//向客户端输出，实际是把数据存放在response中，然后web服务器再去response中读取  
            }
            out.flush();
            response.flushBuffer();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    SxjLogger.error("下载文件错误", e, this.getClass());
                    map.put("error", e.getMessage());
                }
            }
            
            if (out != null)
            {
                try
                {
                    
                    out.close();
                    
                }
                catch (IOException e)
                {
                    SxjLogger.error("下载CSV文件错误", e, this.getClass());
                    map.put("error", e.getMessage());
                }
            }
        }
    }
    
    /**
     * 生成本地文件
     * @param purchaseNo
     * @param type
     * @return
     */
    private File writeCsv(String purchaseNo, Integer type)
    {
        try
        {
            String filePath = "csv/私享家RFID-" + purchaseNo;
            dropFolderOrFile(new File("csv"));//删除文件
            File directory = new File(filePath);
            directory.mkdirs();//创建目录
            if (type == 0)
            {
                // 门窗RFID
                WindowRfidQuery winQuery = new WindowRfidQuery();
                winQuery.setPagable(true);
                winQuery.setShowCount(50);//每页20000条
                winQuery.setPurchaseNo(purchaseNo);
                List<WindowRfidEntity> window1 = windowRfidService.queryWindowRfid(winQuery);
                for (int i = 0; i < winQuery.getTotalPage(); i++)
                {
                    List<WindowRfidEntity> window = windowRfidService.queryWindowRfid(winQuery);
                    OutputStreamWriter fwriter = new OutputStreamWriter(
                            new FileOutputStream(new File(filePath + "/"
                                    + "私享家RFID-{" + purchaseNo + "}-" + type
                                    + "-" + winQuery.getCurrentPage() + ".csv")),
                            "UTF-8");
                    ICsvBeanWriter writer = new CsvBeanWriter(fwriter,
                            CsvPreference.STANDARD_PREFERENCE);
                    String headers[] = { "rfidNo" };
                    writer.writeHeader(headers);
                    for (WindowRfidEntity windowRfidEntity : window)
                    {
                        writer.write(windowRfidEntity, headers);
                    }
                    writer.close();
                    winQuery.setCurrentPage(winQuery.getCurrentPage() + 1);
                }
                
            }
            else
            {
                LogisticsRfidQuery lQuery = new LogisticsRfidQuery();
                lQuery.setPagable(true);
                lQuery.setShowCount(50);//每页20000条
                lQuery.setPurchaseNo(purchaseNo);
                List<LogisticsRfidEntity> logisticsList1 = logisticsRfidService.queryLogistics(lQuery);
                for (int i = 0; i < lQuery.getTotalPage(); i++)
                {
                    List<LogisticsRfidEntity> logisticsList = logisticsRfidService.queryLogistics(lQuery);
                    OutputStreamWriter fwriter = new OutputStreamWriter(
                            new FileOutputStream(new File(filePath + "/"
                                    + "私享家RFID-{" + purchaseNo + "}-" + type
                                    + "-" + lQuery.getCurrentPage() + ".csv")),
                            "UTF-8");
                    ICsvBeanWriter writer = new CsvBeanWriter(fwriter,
                            CsvPreference.STANDARD_PREFERENCE);
                    String headers[] = { "rfidNo" };
                    writer.writeHeader(headers);
                    for (LogisticsRfidEntity logisticsRfidEntity : logisticsList)
                    {
                        writer.write(logisticsRfidEntity, headers);
                    }
                    writer.close();
                    lQuery.setCurrentPage(lQuery.getCurrentPage() + 1);
                }
            }
            //打zip包
            zipFile(filePath);
            return new File(filePath + ".zip");
        }
        catch (Exception e)
        {
            SxjLogger.error("写入CSV文件错误", e, this.getClass());
        }
        return null;
        
    }
    
    /**
     * 删除文件夹
     * @param folder
     */
    private void dropFolderOrFile(File file)
    {
        if (file.isDirectory())
        {
            File[] fs = file.listFiles();
            for (File f : fs)
            {
                dropFolderOrFile(f);
            }
        }
        file.delete();
    }
    
    /**
     * 将指定文件夹打包成zip
     * @param folder
     * @throws IOException 
     */
    private void zipFile(String folder) throws IOException
    {
        File zipFile = new File(folder + ".zip");
        if (zipFile.exists())
        {
            zipFile.delete();
        }
        ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(
                zipFile));
        File dir = new File(folder);
        File[] fs = dir.listFiles();
        byte[] buf = null;
        if (fs != null)
        {
            for (File f : fs)
            {
                zipout.putNextEntry(new ZipEntry(f.getName()));
                FileInputStream fileInputStream = new FileInputStream(f);
                buf = new byte[2048];
                BufferedInputStream origin = new BufferedInputStream(
                        fileInputStream, 2048);
                int len;
                while ((len = origin.read(buf, 0, 2048)) != -1)
                {
                    zipout.write(buf, 0, len);
                }
                zipout.flush();
                origin.close();
                fileInputStream.close();
            }
        }
        zipout.flush();
        zipout.close();
    }
    
    @RequestMapping("importFile")
    public @ResponseBody Map<String, String> importFile(
            HttpServletRequest request, HttpServletResponse response, String id)
            throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            DefaultMultipartHttpServletRequest re = (DefaultMultipartHttpServletRequest) request;
            response.setContentType("text/html;charset=utf-8");
            MultipartFile file = re.getFile("fileName");
            File zipFile = new File(file.getOriginalFilename());
            file.transferTo(zipFile);
            ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
            for (Enumeration entries = zip.entries(); entries.hasMoreElements();)
            {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                System.err.println(zipEntryName);
                String prefix = zipEntryName.substring(zipEntryName.lastIndexOf(".") + 1);
                if (!prefix.equals("csv"))
                {
                    throw new WebException("文件格式错误");
                }
                String[] zipNameArr = zipEntryName.split("-");
                String type = zipNameArr[2];
                InputStreamReader freader = new InputStreamReader(in, "UTF-8");
                CsvBeanReader reader = new CsvBeanReader(freader,
                        CsvPreference.STANDARD_PREFERENCE);
                String[] headers = reader.getHeader(false);
                if (headers.length < 2)
                {
                    throw new WebException("文件未写入GID");
                }
                if (type.equals("0"))
                {
                    //门窗
                    WindowRfidEntity bean = null;
                    List<WindowRfidEntity> windowList = new ArrayList<WindowRfidEntity>();
                    while ((bean = reader.read(WindowRfidEntity.class, headers)) != null)
                    {
                        WindowRfidEntity win = windowRfidService.getWindowRfidByNo(bean.getRfidNo());
                        if (win == null)
                        {
                            break;
                        }
                        windowList.add(bean);
                    }
                    if (windowList != null && windowList.size() > 0)
                    {
                        windowRfidService.updateGid(windowList, id);
                    }
                    else
                    {
                        throw new WebException("文件内容错误");
                    }
                    
                }
                else if (type.equals("1") || type.equals("2"))
                {
                    //物流
                    LogisticsRfidEntity bean = null;
                    List<LogisticsRfidEntity> logisticsList = new ArrayList<LogisticsRfidEntity>();
                    while ((bean = reader.read(LogisticsRfidEntity.class,
                            headers)) != null)
                    {
                        LogisticsRfidEntity logistics = logisticsRfidService.getLogisticsByNo(bean.getRfidNo());
                        if (logistics == null)
                        {
                            break;
                        }
                        logisticsList.add(bean);
                    }
                    if (logisticsList != null && logisticsList.size() > 0)
                    {
                        logisticsRfidService.updateGid(logisticsList, id);
                    }
                    else
                    {
                        throw new WebException("文件内容错误");
                    }
                    
                }
                else
                {
                    throw new WebException("文件内容错误");
                }
                in.close();
                reader.close();
            }
            zip.close();
            FileUtil.delete(file.getOriginalFilename());
            map.put("isOk", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("导入GID错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
}
