package org.mlm.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchException;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.mlm.dao.SearchEventsDAO;
import org.mlm.model.dto.SearchForm;
import org.mlm.model.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchEventsDAOimpl implements SearchEventsDAO {
	//TODO: wyszukiwanie po nazwach kategorii

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) { 
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Transactional 
	public List<Event> selectEventsByFilter(SearchForm keyword) {

		Session session = getSessionFactory().openSession();

		FullTextSession fullTextSession = Search.getFullTextSession(session);
		Transaction tx = fullTextSession.beginTransaction();
		
		fullTextSession.purgeAll(Event.class);
		tx.commit();
		tx = fullTextSession.beginTransaction();

		ScrollableResults results = fullTextSession.createCriteria(Event.class)
				.scroll(ScrollMode.FORWARD_ONLY);
		while (results.next()) {
			Event e = (Event) results.get(0);
			/*System.out.println(e.getDescryption());*/
			fullTextSession.index(e);
		}
		tx.commit();

		/*System.out.println("after indexing");*/
		
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Event.class).get();

		/*org.apache.lucene.search.Query categoryQuery = queryBuilder
				.bool()
				.must(queryBuilder.keyword().onField("category.categoryId")
						.matching(CategoryId).createQuery()).createQuery();*/

		String[] keywords = keyword.getKeyword().split("\\s+");

		
		org.apache.lucene.search.BooleanQuery query = new org.apache.lucene.search.BooleanQuery();
		/*query.add(categoryQuery,
				org.apache.lucene.search.BooleanClause.Occur.MUST);*/

		
		for (String s : keywords) {

			try {
				org.apache.lucene.search.Query luceneQuery = queryBuilder
						.bool()
						.must(queryBuilder
								.keyword()
								.onFields("Descryption", "Header", "UserName",
										"Country", "Street", "City")
								.matching(s).createQuery()).createQuery();
				query.add(luceneQuery,
						org.apache.lucene.search.BooleanClause.Occur.MUST);
			} catch (SearchException e) {
				System.out.println("analyzer uznal to za badziewny keyword: "
						+ s);
			}
		}
		/*System.out.println("after query build");*/
		
		org.hibernate.Query fullTextQuery = fullTextSession
				.createFullTextQuery(query, Event.class);
		/*System.out.println("after full text query build");*/
		
		List<Event> QueryResult = new ArrayList<Event>();
		QueryResult = fullTextQuery.list();

		/*System.out.println("after result");*/
		
		fullTextSession.close();

		if (QueryResult.isEmpty())
			return new ArrayList<Event>();
		else
			return QueryResult;
	}

}
