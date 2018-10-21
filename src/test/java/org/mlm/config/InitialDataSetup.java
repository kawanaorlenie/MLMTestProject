package org.mlm.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mlm.model.entity.User;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class InitialDataSetup {

	@PersistenceContext
	private EntityManager entityManager;

	private TransactionTemplate transactionTemplate;

	private User user00;

	public InitialDataSetup(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void initialize() {

		this.transactionTemplate.execute(new TransactionCallback<Void>() {
			public Void doInTransaction(TransactionStatus status) {
				if (dataIsAlreadyPresent()) {
					return null;
				}

				InitialDataSetup.this.user00 = new User();
				InitialDataSetup.this.user00.setUserName("user00");

				return null;
			}

			private boolean dataIsAlreadyPresent() {
				return InitialDataSetup.this.entityManager
						.createQuery("select count(a.id) from Account a",
								Long.class).getSingleResult().longValue() > 0;
			}
		});
	}
}
