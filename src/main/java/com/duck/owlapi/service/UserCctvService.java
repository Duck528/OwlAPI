package com.duck.owlapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duck.owlapi.dao.UserCctvDao;
import com.duck.owlapi.util.RandGenerator;
import com.duck.owlapi.vo.User;
import com.duck.owlapi.vo.UserCctv;

@Service("userCctvService")
public class UserCctvService {
	
	@Resource(name="userCctvDao")
	private UserCctvDao ucDao;
	
	@Resource(name="userService")
	private UserService userService;
	
	/**
	 * @param cctvMap: description, location, userEmail ������ ���� ��
	 * @return ��� ���Ե� ���ڵ��� ��
	 * 
	 * �ΰ������� Ư�� �� �� �̻��� CCTV�� ���� �� ���� �Ѵ�
	 */
	public int addOne(Map<String, Object> cctvMap) {
		
		try {
			String description = (String)cctvMap.get("description");
			String location = (String)cctvMap.get("location");
			String userEmail = (String)cctvMap.get("userEmail");
			
			if (description != null && location != null && userEmail != null) {
				User u = this.userService.getOneByEmail(userEmail);
				
				UserCctv uc = new UserCctv();
				uc.setAuthCode(RandGenerator.numLenFour());
				uc.setDescription(description);
				uc.setLocation(location);
				uc.setRegisterDate(LocalDateTime.now().toString());
				uc.setUserId(u.getId());
				
				int nAdded = this.ucDao.insert(uc);
				return nAdded;
			} else {
				return -1;
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<UserCctv> getListByUserEmail(String userEmail) {
		User u = this.userService.getOneByEmail(userEmail);
		if (u != null) {
			return this.ucDao.selectListByUserId(u.getId());
		} else {
			return null;
		}
	}
	
	public UserCctv getOne(int cctvId) {
		return this.ucDao.selectOne(cctvId);
	}
	
	public int deleteOne(int cctvId) {
		return this.ucDao.deleteOne(cctvId);
	}
}
