package org.d2.cse.dao;

import org.d2.cse.dto.CObject;

import org.d2.cse.dto.Query;

/**
 *
 * @author cSearch
 */
public interface Database {

    public boolean existQueryInDB(Query query);

    public CObject getClusters(Query query);

    public Query getQueryId(Query query);

    public long getQueryID(Query queryObj);

    public int generateQueryId();

    public void insertClusterResults(CObject cobject);

    public CObject insertResults(CObject cobject);

    public Query insertQuery(Query query);

    public String now();
    //  public void insertQueryinDB(String query);
}
