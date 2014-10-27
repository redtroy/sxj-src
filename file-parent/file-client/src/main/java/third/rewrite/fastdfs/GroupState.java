package third.rewrite.fastdfs;


/**
 * fastdfs中group的状态信息
 * 
 * @author yuqih
 * 
 */
public class GroupState {

	String groupName; // name of this group
	long totalMB; // total disk storage in MB
	long freeMB; // free disk space in MB
	long trunkFreeMB; // trunk free space in MB
	int storageCount; // storage server count
	int storagePort; // storage server port
	int storageHttpPort; // storage server HTTP port
	int activeCount; // active storage server count
	int currentWriteServer; // current storage server index to upload
									// file
	int storePathCount; // store base path count of each storage server
	int subdirCountPerPath; // sub dir count per store path
	int currentTrunkFileId; // current trunk file id

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the totalMB
	 */
	public long getTotalMB() {
		return totalMB;
	}

	/**
	 * @param totalMB
	 *            the totalMB to set
	 */
	public void setTotalMB(long totalMB) {
		this.totalMB = totalMB;
	}

	/**
	 * @return the freeMB
	 */
	public long getFreeMB() {
		return freeMB;
	}

	/**
	 * @param freeMB
	 *            the freeMB to set
	 */
	public void setFreeMB(long freeMB) {
		this.freeMB = freeMB;
	}

	/**
	 * @return the trunkFreeMB
	 */
	public long getTrunkFreeMB() {
		return trunkFreeMB;
	}

	/**
	 * @param trunkFreeMB
	 *            the trunkFreeMB to set
	 */
	public void setTrunkFreeMB(long trunkFreeMB) {
		this.trunkFreeMB = trunkFreeMB;
	}

	/**
	 * @return the storageCount
	 */
	public int getStorageCount() {
		return storageCount;
	}

	/**
	 * @param storageCount
	 *            the storageCount to set
	 */
	public void setStorageCount(int storageCount) {
		this.storageCount = storageCount;
	}

	/**
	 * @return the storagePort
	 */
	public int getStoragePort() {
		return storagePort;
	}

	/**
	 * @param storagePort
	 *            the storagePort to set
	 */
	public void setStoragePort(int storagePort) {
		this.storagePort = storagePort;
	}

	/**
	 * @return the storageHttpPort
	 */
	public int getStorageHttpPort() {
		return storageHttpPort;
	}

	/**
	 * @param storageHttpPort
	 *            the storageHttpPort to set
	 */
	public void setStorageHttpPort(int storageHttpPort) {
		this.storageHttpPort = storageHttpPort;
	}

	/**
	 * @return the activeCount
	 */
	public int getActiveCount() {
		return activeCount;
	}

	/**
	 * @param activeCount
	 *            the activeCount to set
	 */
	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}

	/**
	 * @return the currentWriteServer
	 */
	public int getCurrentWriteServer() {
		return currentWriteServer;
	}

	/**
	 * @param currentWriteServer
	 *            the currentWriteServer to set
	 */
	public void setCurrentWriteServer(int currentWriteServer) {
		this.currentWriteServer = currentWriteServer;
	}

	/**
	 * @return the storePathCount
	 */
	public int getStorePathCount() {
		return storePathCount;
	}

	/**
	 * @param storePathCount
	 *            the storePathCount to set
	 */
	public void setStorePathCount(int storePathCount) {
		this.storePathCount = storePathCount;
	}

	/**
	 * @return the subdirCountPerPath
	 */
	public int getSubdirCountPerPath() {
		return subdirCountPerPath;
	}

	/**
	 * @param subdirCountPerPath
	 *            the subdirCountPerPath to set
	 */
	public void setSubdirCountPerPath(int subdirCountPerPath) {
		this.subdirCountPerPath = subdirCountPerPath;
	}

	/**
	 * @return the currentTrunkFileId
	 */
	public int getCurrentTrunkFileId() {
		return currentTrunkFileId;
	}

	/**
	 * @param currentTrunkFileId
	 *            the currentTrunkFileId to set
	 */
	public void setCurrentTrunkFileId(int currentTrunkFileId) {
		this.currentTrunkFileId = currentTrunkFileId;
	}

}
