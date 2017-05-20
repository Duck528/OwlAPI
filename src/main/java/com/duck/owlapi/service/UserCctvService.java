package com.duck.owlapi.service;

import java.time.LocalDateTime;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duck.owlapi.dao.UserCctvDao;
import com.duck.owlapi.util.RandGenerator;
import com.duck.owlapi.vo.UserCctv;

@Service("userCctvService")
public class UserCctvService {
	
	@Resource(name="userCctvDao")
	private UserCctvDao ucDao;
	
	public int addOne(Map<String, Object> cctvMap) {
		
		try {
			String description = (String)cctvMap.get("description");
			String location = (String)cctvMap.get("location");
			Integer userId = (Integer)cctvMap.get("userId");
			
			if (description != null && location != null && userId != null) {
				UserCctv uc = new UserCctv();
				uc.setAuthCode(RandGenerator.numLenFour());
				uc.setDescription(description);
				uc.setLocation(location);
				uc.setRegisterDate(LocalDateTime.now().toString());
				uc.setUserId(userId);
				
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
}
