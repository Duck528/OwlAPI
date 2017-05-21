package com.duck.owlapi.util;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;

public class AzureStorage {
	
	private static final String storageConnString = 
			"DefaultEndpointsProtocol=https;" +
			"AccountName=duck0528;" +
			"AccountKey=j4oTcSqO/QRxpDV+vCRihf4l6LpHOV0pOBfOddjRz6Y03DpqZjgcSoTOkVfxemHEorvW0Tt5Vl+xL1RZMGYD9w==;EndpointSuffix=core.windows.net";
	
	private static final AzureStorage inst = new AzureStorage();
	
	private CloudStorageAccount storageAccount = null;
	
	public AzureStorage() {
		try {
			this.storageAccount = CloudStorageAccount.parse(storageConnString);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static AzureStorage getInst() {
		return inst;
	}
	
	/**
	 * @return �����̳ʰ� �����Ǹ� ������ �����̳��� �̸��� ��ȯ�Ѵ�. ���� �����Ͽ��ٸ� null�� ��ȯ�Ѵ�
	 * 
	 * ����ڰ� ȸ�������� �ϸ� �� �������� �����̳ʸ� �����Ѵ�
	 */
	public String generateContainer() {
		
		try {
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			String containerName = null;
			while (true) {
				containerName = RandGenerator.charsWithLen(15);
				CloudBlobContainer blobContainer = blobClient.getContainerReference(containerName);
				boolean isCreated = blobContainer.createIfNotExists();
				if (isCreated == true) {
					break;
				}
			}
			return containerName;
		} catch (StorageException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}
