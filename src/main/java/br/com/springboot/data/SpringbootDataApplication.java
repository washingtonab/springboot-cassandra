package br.com.springboot.data;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { CassandraDataAutoConfiguration.class })
public class SpringbootDataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Cluster cluster = Cluster.builder()
				 .addContactPoints("localhost")
				 .withPort(9042)
				 .withCredentials("username", "password")
				 .withoutJMXReporting()
				 .build();
		Session session = cluster.connect("keyspace");
		CassandraOperations template = new CassandraTemplate(session);

		String one = template.selectOne(QueryBuilder.select("column").from("table").allowFiltering().where(QueryBuilder.eq("column", "123")), String.class);

		System.out.println(one);

		List<String> lista = template.select("select column from table limit 100", String.class);

		System.out.println(lista.size());
	}

}
