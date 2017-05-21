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
	 * @return 컨테이너가 생성되면 생성된 컨테이너의 이름을 반환한다. 만약 실패하였다면 null을 반환한다
	 * 사용자가 회원가입을 하면 각 계정마다 컨테이너를 생성한다
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
	 * @param containerName: 사용자 디비에 저장된 컨테이너 이름
	 * @param policyName: 컨테이너에 새롭게 저장할 SAS 생성 정책 이름
	 */
	public void createSharedAccessPolicy(String containerName, String policyName) {
		
		try {
			CloudBlobContainer container = this.blobClient.getContainerReference(containerName);
			if (container.exists() == false) {
				return;
			}
			
			// 현재 컨테이너의 퍼미션을 가져온다
			BlobContainerPermissions perms = container.downloadPermissions();
			
			// 새로운 Shared Access Policy를 생성한다
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
			
			// 새로운 policy를 컨테이너에 저장한다
			perms.getSharedAccessPolicies().put(policyName, policy);
			container.uploadPermissions(perms);
		} catch (StorageException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param container: SAS 셍성 정책을 비울 컨테이너
	 * SAS 생성 정책을 초기화한다 (createSharedAccessPolicy를 호출하기 전에 먼저 호출되어야 한다)
	 */
	public void clearSharedAccessPolicy(String containerName) {
		
		try {
			// 설정되어 있는 모든 policy를 삭제한다
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
	 * @param container: SAS Uri를 생성할 컨테이너
	 * @param policyName: 미리 정의해 둔 SAS 생성 정책
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
	 * @param blob: SAS Uri를 생성할 Blob
	 * @param policyName: 미리 정의해 둔 SAS 생성 정책
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
