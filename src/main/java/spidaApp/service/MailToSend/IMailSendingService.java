package spidaApp.service.MailToSend;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import spidaApp.domain.ConstructConnection;
import spidaApp.domain.PrepareMailToSend;

public interface IMailSendingService {
	
	List<PrepareMailToSend> getMailToSend();

	List<Map<String, Object>> getSaveConstructReport(String id);

	List<ConstructConnection> getConnection(String thread);

	List<Map<String, Object>> getReport(String reportId);

	void deleteInvalidReport(String reportId);
}
