package org.mlm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.mlm.converters.UserToUserJTableConverter;
import org.mlm.dao.ActivationDAO;
import org.mlm.dao.UserDAO;
import org.mlm.model.dto.ChangePasswordModel;
import org.mlm.model.entity.ActivationModel;
import org.mlm.model.entity.Avatar;
import org.mlm.model.entity.Notification;
import org.mlm.model.entity.Profile;
import org.mlm.model.entity.Role;
import org.mlm.model.entity.User;
import org.mlm.model.json.UserJTable;
import org.mlm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ActivationDAO activationDAO;
	@Autowired
	private ShaPasswordEncoder shaPasswordEncoder;
	@Autowired
	private UserToUserJTableConverter userToUserJTableConverter;

	@Transactional
	public boolean createUser(User userModel) {
		if (userDAO.findByUserName(userModel.getUserName()) == null) {

			userModel.setPassword(shaPasswordEncoder.encodePassword(
					userModel.getPassword(), null));
			Profile profile = new Profile();
			profile.setAvatar(new Avatar());
			userModel.setProfile(profile);
			userDAO.save(userModel);

			String uuid = UUID.randomUUID().toString();
			activationDAO.save(new ActivationModel(uuid, userModel
					.getUserName()));
			// mailService.sendActivationMail(signUp, uuid);
			return true;

		} else
			return false;
	}

	@Transactional
	public boolean createUsers(List<User> userList) {
		for (User userModel : userList) {
			userModel.setPassword(shaPasswordEncoder.encodePassword(
					userModel.getPassword(), null));
			Profile profile = new Profile();
			profile.setAvatar(new Avatar());
			userModel.setProfile(profile);
			userDAO.save(userModel);

			String uuid = UUID.randomUUID().toString();
			activationDAO.save(new ActivationModel(uuid, userModel
					.getUserName()));

		}
		// userDAO.save(userList);
		return true;
	}

	/**
	 * Construct UserDetails instance required by spring security
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 *      loadUserByUsername(java.lang.String)
	 */
	@Transactional
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		User user = userDAO.findByUserName(userName);
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Collection<Role> roles = user.getRoles();
		for (Role x : roles) {
			authorities.add(new SimpleGrantedAuthority(x.getAuthority()));
		}
		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				user.getUserName(), user.getPassword(), authorities);
		return userDetails;
	}

	@Transactional
	public boolean activateUser(String userName, String uuid) {

		ActivationModel activationModel = activationDAO.findOne(userName);

		if (activationModel == null || !activationModel.getUuid().equals(uuid))
			return false;
		else {
			User user = userDAO.findByUserName(userName);
			user.setEnabled(true);
			userDAO.save(user);
			return true;
		}

	}

	@Transactional
	public List<User> getAllUsers() {
		return (List<User>) userDAO.findAll();
	}

	@Transactional
	public List<User> getAllUsersWithRoles() {
		List<User> users = (List<User>) userDAO.getAllWithRoles();
		return users;
	}

	@Transactional
	public void changePassword(String userName,
			ChangePasswordModel changePasswordModel) {
		changePasswordModel.setOldPassword(shaPasswordEncoder.encodePassword(
				changePasswordModel.getOldPassword(), null));
		changePasswordModel.setNewPassword(shaPasswordEncoder.encodePassword(
				changePasswordModel.getNewPassword(), null));
		changePasswordModel
				.setConfirmPassword(shaPasswordEncoder.encodePassword(
						changePasswordModel.getConfirmPassword(), null));
		User user = userDAO.findByUserName(userName);
		user.setPassword(changePasswordModel.getNewPassword());
		userDAO.save(user);
	}

	public boolean exists(String userName) {
		return userDAO.findByUserName(userName) != null;
	}

	@Transactional
	public User updateUser(User userModel) {
		userModel.setPassword(shaPasswordEncoder.encodePassword(
				userModel.getPassword(), null));
		return userDAO.save(userModel);
	}

	@Transactional
	public void deleteUser(User userModel) {
		userDAO.delete(userModel);
	}

	@Transactional
	public void deleteAll() {
		userDAO.deleteAll();
	}

	@Transactional
	public byte[] getAvatar(String userName) {
		return userDAO.findByUserName(userName).getProfile().getAvatar()
				.getImage();
	}

	@Transactional
	public User findUser(String name) {
		return userDAO.findByUserName(name);
	}

	@Transactional
	public void saveUserProfile(String name, Profile userProfile) {
		User user = userDAO.findByUserName(name);
		userProfile.setId(user.getProfile().getId());
		userProfile.setAvatar(user.getProfile().getAvatar());
		user.setProfile(userProfile);

		userDAO.save(user);
	}

	@Transactional
	public void saveUserAvatar(String name, MultipartFile file)
			throws IOException {
		Avatar avatar = new Avatar();
		avatar.setImage(file.getBytes());
		User user = userDAO.findByUserName(name);
		user.getProfile().setAvatar(avatar);
		userDAO.save(user);

	}

	@Transactional
	public Profile getProfile(String name) {
		User userModel = userDAO.findByUserName(name);
		Profile profileModel = userModel.getProfile();
		profileModel.getAvatar();
		return profileModel;
	}

	@Transactional
	public User getUserWithNotifications(String userName) {
		User res = userDAO.findByUserName(userName);
		Hibernate.initialize(res.getNotifications());
		return res;
	}

	@Transactional
	public void dropRelatedNotifications(User user, String link) {

		Iterator<Notification> i = user.getNotifications().iterator();
		while (i.hasNext()) {
			Notification notification = i.next();
			if (notification.getLink().equals(link)) {
				i.remove();
			}
		}
		userDAO.save(user);

	}

	@Transactional
	public Profile getProfile(int id) {
		User userModel = userDAO.findOne(id);
		Profile profileModel = null;
		if (userModel != null) {
			profileModel = userModel.getProfile();
			profileModel.getAvatar();
		}
		return profileModel;
	}

	@Transactional
	public void lowerReliability(User user, ReliabilityLoweringType t) {
		double newReliability = user.getReliability() - t.getQuantity();
		user.setReliability(newReliability);
		userDAO.save(user);
	}

	@Transactional
	public List<UserJTable> getAllJTableUsers() {
		logger.debug("invoke getAllJTableUsers");
		List<UserJTable> jTableUsers = new ArrayList<UserJTable>();
		List<User> users = userDAO.getAllWithRoles();
		for (User user : users) {
			jTableUsers.add(userToUserJTableConverter.convert(user));
		}
		return jTableUsers;
	}
}
