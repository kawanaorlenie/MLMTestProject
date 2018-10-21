package org.mlm.service;


import java.io.IOException;
import java.util.List;

import org.mlm.model.dto.ChangePasswordModel;
import org.mlm.model.entity.Profile;
import org.mlm.model.entity.User;
import org.mlm.model.json.UserJTable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	
	enum ReliabilityLoweringType
	{
		REMOVE_PARTICIPANT(0.05),
		CANCEL_EVENT(0.1);
		
		private double quantity;
		private ReliabilityLoweringType(double quantity) {
			this.quantity = quantity;
		}
		
		public double getQuantity()
		{
			return this.quantity;
		}
		
	}
	
	boolean createUser(User userModel);
	boolean activateUser(String userName, String uuid);
	List<User> getAllUsers();
	void changePassword(String userName, ChangePasswordModel changePasswordModel);
	boolean exists(String userName);
	List<User> getAllUsersWithRoles();
	User updateUser(User userModel);
	void deleteUser(User userModel);
	byte[] getAvatar(String userName);
	User findUser(String name);
	void saveUserProfile(String name, Profile userProfile);
	void saveUserAvatar(String name, MultipartFile file) throws IOException;
	Profile getProfile(String name);
	void deleteAll();
	boolean createUsers(List<User> userList);
	User getUserWithNotifications (String userName);
	void dropRelatedNotifications(User user, String link);
	Profile getProfile(int id);
	void lowerReliability(User user, ReliabilityLoweringType t);
	List<UserJTable> getAllJTableUsers();
}
	