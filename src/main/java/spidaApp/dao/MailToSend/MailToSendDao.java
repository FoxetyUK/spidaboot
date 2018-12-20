package spidaApp.dao.MailToSend;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import spidaApp.dao.common.AbstractDao;
import spidaApp.domain.ConstructConnection;
import spidaApp.domain.PrepareMailToSend;

public class MailToSendDao extends AbstractDao implements IMailToSendDao{
  @Autowired
  public MailToSendDao(DataSource dataSource){
    super(dataSource);
  }
  
  @Override
	public List<PrepareMailToSend> getMailToSend(){
		return jdbcTemplate.query("select id"
									 + ", name"
									 + ", email "
								  + "from testmailsend r "
							    +"	where and id in(select id from testtable) ", new BeanPropertyRowMapper<PrepareMailToSend>(PrepareMailToSend.class));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConstructConnection> getConstructConnection(String thread){
		
		return  jdbcTemplate.query("select * from table(GET_CONSTR_SYS_CONFIG_PKG.get_connection(:thread))", map("thread", thread), new BeanPropertyRowMapper<ConstructConnection>(ConstructConnection.class));
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getSaveReportThread(String reportId){
		return jdbcTemplate.queryForObject("select id_connect from testreport"
														+ "	where id = :reportId", map("reportId", reportId), String.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getSaveReportQuery (String reportId) throws SQLException{
		
		String query = jdbcTemplate.queryForObject("select TEST_GET_CONSTR_REP.get_query(:reportId) from dual"
																 , map("reportId", reportId), String.class);
		
		return jdbcTemplate.queryForObject(query, Collections.EMPTY_MAP, String.class);
	}
	
	@Override
	public List<Map<String, Object>> getSaveConstructReport(String reportId) throws SQLException{
		
		String thread = getSaveReportThread(reportId);
		
		List<ConstructConnection> connection = getConstructConnection(thread);
		
		String sql = null;
		try {
			sql = getSaveReportQuery(reportId);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		DriverManagerDataSource source = new DriverManagerDataSource();
		
		source.setDriverClassName(connection.get(0).getDriverForConnect());
		source.setUrl(connection.get(0).getUrlForConnect());
		source.setUsername(connection.get(0).getUserName());
		try {
			source.setPassword(AESCrypt.decrypt(connection.get(0).getUserPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JdbcTemplate sourceTemplate = new JdbcTemplate(source);
		return sourceTemplate.query(
                sql,
                new ConstrTableRowMapper());
		
	}
	
	@Override
	public List<Map<String,Object>> getReport(String reportId){
		List<Map<String,Object>> report;
		try{
			report = getSaveConstructReport(reportId);
			return report;
		}catch (SQLException e){
			e.printStackTrace();
			jdbcTemplate.update("delete from testtable where id = " + reportId, Collections.EMPTY_MAP);
			return null;
		}
	}
	
	@Override
	public void deleteInvalidReport(String reportId){
		jdbcTemplate.update("delete from testtable where id = " + reportId, Collections.EMPTY_MAP);
	}
	
	private static final class ConstrTableRowMapper implements ResultSetExtractor<List<Map<String, Object>>>{
		@Override
		public List<Map<String, Object>> extractData(ResultSet resultSet) throws SQLException{
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (resultSet.next()) {
			    Map<String, Object> columns = new LinkedHashMap<String, Object>();

			    for (int i = 1; i <= columnCount; i++) {
			        columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
			    }

			    rows.add(columns);
			}
			return rows;
		}
	}
}
