package yggc.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrContext {
	private static SolrClient server=null;
	static{
		server = new HttpSolrClient("http://192.168.23.3:8983/solr/zh");
	}
	
	public static SolrClient getServer(){
		return server;
	}
}
