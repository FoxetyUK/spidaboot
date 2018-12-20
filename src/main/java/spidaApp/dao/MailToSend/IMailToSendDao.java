package spidaApp.dao.MailToSend;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import spidaApp.domain.ConstructConnection;
import spidaApp.domain.PrepareMailToSend;

public interface IMailToSendDao {

	List<ConstructConnection> getConstructConnection(String thread);

	String getSaveReportThread(String reportId);

	String getSaveReportQuery(String reportId) throws SQLException;

	List<Map<String, Object>> getSaveConstructReport(String reportId) throws SQLException;

	List<PrepareMailToSend> getMailToSend();

	List<Map<String, Object>> getReport(String reportId);

	void deleteInvalidReport(String reportId);

}
