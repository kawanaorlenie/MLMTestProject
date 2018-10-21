package org.mlm.dao;

import org.mlm.model.entity.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationDAO extends CrudRepository<Notification, Integer> {

}
