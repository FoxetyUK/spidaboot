package spidaApp.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import spidaApp.domain.PrepareMailToSend;
import spidaApp.service.MailToSend.IMailSendingService;

@Service
@Configurable
public class MailerConfiguration extends Thread{
    @Autowired
    public static IMailSendingService mailToSend;
    
    private static final String EMPTY_RESULT = "Данные в таблице отсувствуют";
    private static final String UNEXPECTED_ERROR = "Произошла ошибка при попытке обработки отчёта. Данный отчёт будет помещен в карантин. Номер отчёта :";
    
    public MailerConfiguration (IMailSendingService mailToSend){
      this.mailToSend = mailToSend;
    }
    
    private static boolean active = true;
    
    @Override
    public void run(){
      while(active){
        List<PrepareMailToSend> listMailToSend = mailToSend.getMailToSend();
        String mailResponce = null;
        for(PrepareMailToSend sending : listMailToSend){
          List<Map<String,Object>> report = null;
          try{
            report = new ArrayList<Map<String,Object>>(mailToSend.getReport(sending.getId()));
            throw new SQLException("SQL_ERROR");
          }catch (Exception e){
            e.getMessage();
          }
          try{
            if(report.size > 0){
              mailResponce = sending.getName();
              Set<String> header = report.get(0).keySet();
              mailResponce += "<table><thead>";
              for(String head : header){
                mailResponce += "<th>" + head + "</th>";
              }
              mailResponce += "</thead><tbody>";
              Object[] rows = null;
              for(int i = 0; i < report.size(); i++){
                mailResponce += "<tr>";
                rows = report.get(i).values().toArray();
                for(int j = 0; j < rows.length; j++){
                  mailResponce += "<td>" + rows[j] + "</td>";
                }
                mailResponce += "<tbody><table>";
                
                System.out.println(mailResponce);
              }else{
                System.err.println(EMPTY_RESULT);
              }catch(NullPointerException e){
                e.printStackTrace();
                System.err.println(UNEXPECTED_ERROR + sending.getId());
                mailToSend.deleteInvalidReport(sending.getId());
              }
            }
          }
        }
      }
    }
}
