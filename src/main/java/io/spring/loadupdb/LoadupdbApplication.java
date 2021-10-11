package io.spring.loadupdb;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class LoadupdbApplication {
//test
	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(LoadupdbApplication.class, args);
	}


	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				insertTaskExecutions();
			}
		};
	}
	//INSERT INTO `task`.`TASK_EXECUTION` (END_TIME) VALUES ({ts '2021-10-07 09:08:11'});
	//INSERT INTO "master"."dbo"."TASK_EXECUTION" (START_TIME) VALUES ({ts '2021-10-08 16:29:13'});

	private void insertTaskExecutions() {
		JdbcTemplate template = new JdbcTemplate(this.dataSource);
		String curlIds = "";
		for (int i = 1 ; i < 2500 ; i++) {
			curlIds += i + ",";
			System.out.println(i + " ==> ");
//			template.execute("INSERT INTO \"master\".\"dbo\".\"TASK_EXECUTION\" (TASK_EXECUTION_ID, START_TIME, END_TIME, EXIT_CODE, EXIT_MESSAGE) VALUES ("
//					+ i + ", {ts '2021-10-08 16:29:13'}, {ts '2021-10-08 16:29:13'}, 0, " +  "'WOMBAT')" );
			template.execute("INSERT INTO task.TASK_EXECUTION (TASK_EXECUTION_ID, START_TIME, END_TIME, EXIT_CODE, EXIT_MESSAGE) VALUES ("
					+ i + ", {ts '2021-10-08 16:29:13'}, {ts '2021-10-08 16:29:13'}, 0, " +  "'WOMBAT')" );

		}
		curlIds = curlIds.substring(0, curlIds.lastIndexOf(','));
		System.out.println("curl 'http://localhost:9393/tasks/executions/" + curlIds + "?action=CLEANUP,REMOVE_DATA' -i -X DELETE");
	}


}
