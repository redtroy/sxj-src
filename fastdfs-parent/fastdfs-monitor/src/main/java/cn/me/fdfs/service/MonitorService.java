package cn.me.fdfs.service;

import java.io.IOException;
import java.util.List;

import cn.me.fdfs.vo.Fdfs_file;
import cn.me.fdfs.vo.Group;
import cn.me.fdfs.vo.GroupDay;
import cn.me.fdfs.vo.Line;
import cn.me.fdfs.vo.Storage;
import cn.me.fdfs.vo.StorageHour;

public interface MonitorService
{
    
    List<Group> listGroupInfo() throws Exception;
    
    List<Group> listGroups() throws IOException;
    
    List<Storage> listStorage(String groupName) throws IOException;
    
    List<Storage> listStorageTop(String ipaddr) throws IOException;
    
    List<Line> listStorageLines(String groupName) throws IOException;
    
    List<Line> getNetTrafficLines(String ip, String start, String end);
    
    Line getListStoragesInfo(String ip, String startTime, String endTime)
            throws IOException;
    
    StorageHour getStorageByIp(String ip) throws IOException;
    
    List<Group> getAllGroups() throws IOException;
    
    List<Line> getListFileCountStorage(String ip, String startTime,
            String endTime) throws IOException;
    
    void saveFile(Fdfs_file f) throws IOException;
    
    List<GroupDay> getGroupsByName(String groupName) throws IOException;
    
}
