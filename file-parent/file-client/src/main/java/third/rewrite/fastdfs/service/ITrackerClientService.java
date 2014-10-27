package third.rewrite.fastdfs.service;

import third.rewrite.fastdfs.GroupState;
import third.rewrite.fastdfs.StorageClient;
import third.rewrite.fastdfs.StorageState;

public interface ITrackerClientService {

	StorageClient getStoreStorage();

	StorageClient getStoreStorage(String groupName);

	StorageClient getFetchStorage(String groupName, String filename);

	StorageClient getUpdateStorage(String groupName, String filename);

	GroupState[] listGroups();

	StorageState[] listStorages(String groupName);

	StorageState[] listStorages(String groupName, String storageIpAddr);

	void deleteStorage(String groupName, String storageIpAddr);

}
