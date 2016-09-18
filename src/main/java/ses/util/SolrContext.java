package ses.util;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/*
 *@Title:SolrContext
 *@Description:solr工具类
 *@author QuJie
 *@date 2016-9-7下午6:32:28
 */
public class SolrContext {
	private static SolrClient server=null;
	static{
		PropertiesUtil config = new PropertiesUtil("config.properties");
		server = new HttpSolrClient(config.getString("solrUrl"));
//		server = new HttpSolrClient("http://192.168.23.3:8983/solr/zh");
	}
	
	public static SolrClient getServer(){
		return server;
	}
}
