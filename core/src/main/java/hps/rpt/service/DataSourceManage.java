package hps.rpt.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSourceManage {

	private static DataSource dataSource;
	private static Connection connection;
	@Autowired
	private BeanFactory beanFactory;

	@PostConstruct
	public void init() {
		DataSourceManage.dataSource = (DataSource) beanFactory.getBean("dataSource");
	}



	public static Connection getConnection() {
		if (connection == null) {
			// dataSourceManage.dataSource = (DataSource)
			// beanFactory.getBean("dataSource");
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}

}
