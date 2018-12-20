package spidaApp.service.MailToSend;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import spidaApp.dao.MailToSend.IMailToSendDao;
import spidaApp.domain.ConstructConnection;
import spidaApp.domain.PrepareMailToSend;


public class MailSendingService implements IMailSendingService{
	private final IMailToSendDao mailToSendDao;
	
	public MailSendingService(IMailToSendDao mailToSendDao){
		this.mailToSendDao = mailToSendDao;
	}
	@Override
	public List<PrepareMailToSend> getMailToSend(){
		return mailToSendDao.getMailToSend();
	}
	
	@Override
	public List<Map<String,Object>> getSaveConstructReport(String id){
		try{
			return mailToSendDao.getSaveConstructReport(id);
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<ConstructConnection> getConnection(String thread) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Map<String,Object>> getReport(String reportId){
		return mailToSendDao.getReport(reportId);
	}
	
	@Override
	public void deleteInvalidReport(String reportId){
		mailToSendDao.deleteInvalidReport(reportId);
	}
}
