package com.duck.owlapi.util;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.SharedAccessBlobPermissions;
import com.microsoft.azure.storage.blob.SharedAccessBlobPolicy;

public class AzureStorage {
	
	private static final String storageConnString = 
			"DefaultEndpointsProtocol=https;" +
			"AccountName=duck0528;" +
			"AccountKey=j4oTcSqO/QRxpDV+vCRihf4l6LpHOV0pOBfOddjRz6Y03DpqZjgcSoTOkVfxemHEorvW0Tt5Vl+xL1RZMGYD9w==;EndpointSuffix=core.windows.net";
	
	private static final AzureStorage inst = new AzureStorage();
	
	private CloudStorageAccount storageAccount = null;
	private CloudBlobClient blobClient = null;
	
	public AzureStorage() {
		try {
			this.storageAccount = CloudStorageAccount.parse(storageConnString);
			this.blobClient = this.storageAccount.createCloudBlobClient();
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
	
	/**
	 * @param containerName: ����� ��� ����� �����̳� �̸�
	 * @param policyName: �����̳ʿ� ���Ӱ� ������ SAS ���� ��å �̸�
	 */
	public void createSharedAccessPolicy(String containerName, String policyName) {
		
		try {
			CloudBlobContainer container = this.blobClient.getContainerReference(containerName);
			if (container.exists() == false) {
				return;
			}
			
			// ���� �����̳��� �۹̼��� �����´�
			BlobContainerPermissions perms = container.downloadPermissions();
			
			// ���ο� Shared Access Policy�� �����Ѵ�
			SharedAccessBlobPolicy policy = new SharedAccessBlobPolicy();
			policy.setPermissions(EnumSet.of(
					SharedAccessBlobPermissions.WRITE,
					SharedAccessBlobPermissions.LIST,
					SharedAccessBlobPermissions.READ));
			GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
			cal.setTime(new Date());
			policy.setSharedAccessStartTime(cal.getTime());
			cal.add(GregorianCalendar.HOUR, 24);
			policy.setSharedAccessExpiryTime(cal.getTime());
			
			// ���ο� policy�� �����̳ʿ� �����Ѵ�
			perms.getSharedAccessPolicies().put(policyName, policy);
			container.uploadPermissions(perms);
		} catch (StorageException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param container: SAS �ļ� ��å�� ��� �����̳�
	 * SAS ���� ��å�� �ʱ�ȭ�Ѵ� (createSharedAccessPolicy�� ȣ���ϱ� ���� ���� ȣ��Ǿ�� �Ѵ�)
	 */
	public void clearSharedAccessPolicy(String containerName) {
		
		try {
			// �����Ǿ� �ִ� ��� policy�� �����Ѵ�
			CloudBlobContainer container = this.blobClient.getContainerReference(containerName);
			if (container.exists() == false) {
				return;
			}
			
			BlobContainerPermissions perms = container.downloadPermissions();
			perms.getSharedAccessPolicies().clear();
			container.uploadPermissions(perms);
		} catch (StorageException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param container: SAS Uri�� ������ �����̳�
	 * @param policyName: �̸� ������ �� SAS ���� ��å
	 * @return Azure Cloud Container SAS Uri
	 */
	public String getContainerSasUriWithPolicy(String containerName, String policyName) {
		
		try {
			CloudBlobContainer container = this.blobClient.getContainerReference(containerName);
			if (container.exists() == false) {
				return null;
			}
			
			String sasConnToken = container.generateSharedAccessSignature(null, policyName);
			return sasConnToken;
		} catch (StorageException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param blob: SAS Uri�� ������ Blob
	 * @param policyName: �̸� ������ �� SAS ���� ��å
	 * @return Azure Cloud Blob SAS Uri
	 */
	public String getBlockBlobSasUriWithPolicy(String containerName, String blobName, String policyName) {
		try {
			CloudBlobContainer container = this.blobClient.getContainerReference(containerName);
			if (container.exists() == false) {
				return null;
			}
			
			CloudBlob blob = container.getBlockBlobReference(blobName);
			String sasConnToken = blob.generateSharedAccessSignature(null, policyName);
			return sasConnToken;
		} catch (StorageException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
