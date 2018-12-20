package spidaApp.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
@Author FoxetyUK
@Purpose Необходим для создания постоянного коннекта к базе, откуда тянутся запросы
*/

public class DataSourceFactoryConfiguration{

    private static String sourceUrl = "url";
    private static String sourceUser = "user";
    private static String sourcePassword = "password";
    private static String sourceDriver = "driver";
    
    public static DataSource createDataSource(){
      ComboPooledDataSource source = new ComboPooledDataSource();
      
      try{
        source.sedDriverClass(sourceDriver);
      }catch(PropertyVetoException e){
        return null;      
      }
      
      source.setJdbcUrl(sourceUrl);
      source.setUser(sourceUser);
      source.setPassword(sourcePassword);
      
      
      source.setMaxPoolSize(30);
      source.setMinPoolSize(30);
      source.setInitialPoolSize(30);
      source.setMaxIdleTime(30);
      source.setTestConnectionOnCheckin(true);
      source.setIdleConnectionTestPeriod(1);
      source.setPreferredTestQuery("select 1 from dual");

      return source;
      
    }
}
