package br.com.springboot.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

@Controller
public class MyContoller {

	private Cluster cluster;
	private Session session;
	private CassandraOperations template;
	
	@PostConstruct
	public void postConstruct() {
		cluster = Cluster.builder()
						 .addContactPoints("localhost")
						 .withPort(9042)
						 .withCredentials("username", "password")
						 .withoutJMXReporting()
						 .build();
		session = cluster.connect("keyspace");
		template = new CassandraTemplate(session);
	}
	
	@PreDestroy
	public void preDestroy() {
		session.close();
		cluster.close();
	}

	@RequestMapping(value = "/testando/{param}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<String> select(@PathVariable("param") String param) {

		String one = template.selectOne(QueryBuilder.select("column").from("table").allowFiltering().where(QueryBuilder.eq("column", param)), String.class);

		System.out.println(one);
		
		List<String> lista = template.select("select column from table limit 100", String.class);

		return lista;
	}

}
