/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbfgenerationservice;

/**
 *
 * @author Mobolaji.Aina
 */
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
 import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PbfGenerationController {

    /**
     * @param args the command line arguments
     */
    static final String ERROR_MARGIN = "\t\t\t\t\t\t\t\t\t\t\t";
    static  PbfLogger pbfLogger;
    public String pbfFileHeader;
    public String pbfFileTrailer;
    public static final String PBF_HEADER_PREFIX="H;PBF";
    public static final String PBF_TRAILER_PREFIX="T";
    public static final String PBF_RECORD_PREFIX="R";
    public static final String PBF_DATA_SEPARATOR=";";
    public static final String PBF_VERSION_NUMBER="10.00.00";
    public static String fileSequenceNumber;
    public static String fileCreationDate;
    public static PbfFile newPbfFile = null;
    public static boolean isStarted;
    private  static  FileWriter fileWriter =  null;
    private static PrintWriter printWriter =  null;
    public static   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    public static Timer timer;
    public static Timer timer2;
    private static PbfGenerationController unique_instance;
    private static Object lock;
    public static long runningThreadID =0; 
    public static String email_username;
    public static String email_password;
    public static String database;
    public static String userName;
    public static String server;
    public static String password;
    public static String startDate;
    public static String endDate;
    public static String yesterday;
    public static String outputFolder;
    public static String pbfFileDate;
    public static String defaultDate;
    public static String specific_file_date;
    public static String pbfQueryFile;
    public static String emailHeaderImage;
    public static String PREFERRED_LAF = "";
    public static String BASIC_LAF = "";
    public static PbfServiceConfigurator baseFrame ;
    public static PbfMailer pbfMailer;
    public static PbfFileWriter pbfWriter;
    public static PbfDataManager pbfDataManager;
    public static String schedule;
    public static String mailEnabled;
    public static String mailOnError;
    public static String scheduleOption;
    public static PbfReport report  = new PbfReport();
    public static StringBuilder pbfErrorList = new StringBuilder();
    public static String startTimeHours;
    public static String startTimeMins;
    public static String startTimeSecs;

   static HashMap<String, String>  getSystemLAFs(){

	  	HashMap<String, String> systemLAFs = new HashMap<String, String>();
	  	UIManager.LookAndFeelInfo[] installedLafs = UIManager.getInstalledLookAndFeels();
	  	for (UIManager.LookAndFeelInfo lafInfo : installedLafs) {
	  	    systemLAFs.put(lafInfo.getName(),lafInfo.getClassName());
	  	 }
	  	return systemLAFs;
 }
    
    private static  void readConfigFile(){
            try{
                PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Reading configuration file");
		String configFile = new String("etc/pbf_settings.conf");
		Scanner configScanner = new Scanner(new File(configFile));
		String configParamStr = "";
		while(configScanner.hasNextLine()){
		   configParamStr = configScanner.nextLine().trim();
		   String[] configParams = configParamStr.split(":>");
		   if("schedule".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1)
		           schedule= configParams[1];
                            baseFrame.setScheduleText(schedule);
		   }else  if("schedule_option".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
                           scheduleOption =configParams[1];
                           baseFrame.setScheduleOption(scheduleOption);
 
                       }
		   }  else if("smtp_server".equalsIgnoreCase(configParams[0])){
                          if(configParams.length>1){
			   pbfMailer.setSMTPServer(configParams[1]);
                           baseFrame.setSMTPField(configParams[1]);
                          }
		   }else  if("port".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
                            configParams[1] = configParams[1]==null?"":configParams[1];
                           pbfMailer.setPort(configParams[1]);
                           baseFrame.setPortField(configParams[1]);
                        }
		   }else  if("email_username".equalsIgnoreCase(configParams[0])){
                         if(configParams.length>1){  
			   email_username=  configParams[1];
                           pbfMailer.setUsername(email_username);
                           baseFrame.setEmailUserNameField(email_username);
                          }
		   }else  if("email_password".equalsIgnoreCase(configParams[0])){
                          if(configParams.length>1){  
			   email_password=  PbfDataCipher.decipher(configParams[1]);
                           email_password =email_password.isEmpty()?"":email_password;
                           pbfMailer.setPassword(email_password);
                           baseFrame.setEmailPasswordField(email_password);
                          }
		   }else  if("sslOnConnect".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
                            boolean isSSL =configParams[1].equalsIgnoreCase("yes")?true:false;
                            pbfMailer.setSslOnConnect(isSSL);
                            baseFrame.setSSlOnConnectOption(configParams[1]); 
                       }
		   }else  if("from".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
                            pbfMailer.setFrom(configParams[1]);
                            baseFrame.setFromTextField(configParams[1]);
                        }
		   }else  if("to".equalsIgnoreCase(configParams[0])){
                                              
                                if(configParams.length>1){  
                                        pbfMailer.setTo(configParams[1]);
                                        baseFrame.setToTextField(configParams[1]);
                                }
		   }else  if("cc".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
			    pbfMailer.setCc(configParams[1]);
                            baseFrame.setCcTextField(configParams[1]);
                        }
		   }else  if("header_background".equalsIgnoreCase(configParams[0])){
                      if(configParams.length>1){  
                            pbfMailer.setHeaderBgdColour(configParams[1]);
                            baseFrame.setheaderBgColor(configParams[1]);
                            report.setHeaderBgdColour(configParams[1]);
                      }
		   }else  if("header_font".equalsIgnoreCase(configParams[0])){
                      if(configParams.length>1){  
                            pbfMailer.setHeaderFontColour(configParams[1]);
                            baseFrame.setHeaderFontColor(configParams[1]);
                            report.setHeaderFontColour(configParams[1]);
                      }
		   }else  if("alternate_row".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
                       pbfMailer.setAlternateRowColour(configParams[1]);
			    baseFrame.setAlternateRowColor( configParams[1]);
                            report.setAlternateRowColour(configParams[1]);
                       }
		   }else  if("border".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
                           pbfMailer.setBorderCol(configParams[1]);
                            baseFrame.setBorderColor(configParams[1]);
                            report.setBorderCol(configParams[1]);
                        }
		   }else  if("font".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
			  pbfMailer.setFontCol(configParams[1]);
                          baseFrame.setFontFieldColor(configParams[1]);
                          report.setFontCol(configParams[1]);
                       }
		   }else if("database".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
		             database  = PbfDataCipher.decipher( configParams[1]);
                             pbfDataManager.database  = database;
                             baseFrame.setDatabaseField(database);
                        }
		   }else if("username".equalsIgnoreCase(configParams[0])){
                      if(configParams.length>1){  
		             userName  =PbfDataCipher.decipher( configParams[1]);
                             pbfDataManager.user  =userName;
                             baseFrame.setDatabaseUserField(userName);
                      }
		   }else if("server".equalsIgnoreCase(configParams[0])){
                      if(configParams.length>1){  
		             server  = PbfDataCipher.decipher(configParams[1]);
                             pbfDataManager.server  =server;
                             baseFrame.setDatabaseServerField(server);
                      }
		   }else if("password".equalsIgnoreCase(configParams[0])){
                      if(configParams.length>1){  
		             password  =  PbfDataCipher.decipher( configParams[1]);
                             pbfDataManager.password  = password;
                             baseFrame.setDatabasePassword(password);
                      }
		   }else if("start_date".equalsIgnoreCase(configParams[0])){
                      if(configParams.length>1){  
		             startDate  = configParams[1];
                             baseFrame.setStartDateField(startDate);
                      }
		   }else if("end_date".equalsIgnoreCase(configParams[0])){
                      if(configParams.length>1){  
		              endDate  = configParams[1];
                              baseFrame.setEndDateField(endDate);
                      }
		   }else if("yesterday".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
		             yesterday  = configParams[1];
                             if(1==Integer.parseInt(configParams[1])) baseFrame.setYesterdayOption();
                             else baseFrame.setDateRangeFileDate();
                       }
		    }else if("output_folder".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
		             outputFolder  = configParams[1];
                             baseFrame.setOutputField(configParams[1]);
                        }
		    }else if("file_sequence_num".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
		            fileSequenceNumber  = configParams[1];
                            baseFrame.setFileSequenceNumberField(configParams[1]);
                        }
		    }else if("pbf_date".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
		           pbfFileDate= configParams[1];
                           baseFrame.setPbfDateField(pbfFileDate);
                           
                        }
		    }else if("default_date".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
		           defaultDate= configParams[1];
                          if(1==Integer.parseInt(configParams[1])) { 
                               
                               baseFrame.setDefaultFileDate();
                                 baseFrame.setDefaultDateOption();
                          }
                        }

		    }else if("query_file".equalsIgnoreCase(configParams[0])){
                        if(configParams.length>1){  
		   	 pbfQueryFile= configParams[1];
                         baseFrame.setPbfQueryField(pbfQueryFile);
                        }
		    }else if("email_header_image".equalsIgnoreCase(configParams[0])){
                         if(configParams.length>1){  
		   	 emailHeaderImage= configParams[1];
                         baseFrame.setEmailHeaderImage(emailHeaderImage);
                         
                         }
		    }else if("is_mail_enabled".equalsIgnoreCase(configParams[0])){
                         if(configParams.length>1){  
		   	 mailEnabled= configParams[1];
                         baseFrame.setEmailEnabled(mailEnabled);
                         }
		    }else if("send_mail_on_error".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
		   	 mailOnError= configParams[1];
                         baseFrame.setMailOnError(mailOnError);
                       }
		    }else if("specific_file_date".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
		   	   specific_file_date= configParams[1];
                           if(1==Integer.parseInt(specific_file_date)) baseFrame.setDateRangeFileDate();
                       }
		    }else if("start_time_hour".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
		   	   startTimeHours= configParams[1];
                           if(Integer.parseInt(startTimeHours)>=0 && Integer.parseInt(startTimeHours)<=23 ){
                           baseFrame.setStartHourOption(startTimeHours);
                           }
                       }
		    }else if("start_time_minute".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
		   	   startTimeMins= configParams[1];
                             if(Integer.parseInt(startTimeMins)>=0 && Integer.parseInt(startTimeMins)<=59 ){
                                baseFrame.setStartMinuteOption(startTimeMins);
                             }
                       }
                       
		    }else if("start_time_second".equalsIgnoreCase(configParams[0])){
                       if(configParams.length>1){  
		   	   startTimeSecs= configParams[1];
                              if(Integer.parseInt(startTimeSecs)>=0 && Integer.parseInt(startTimeSecs)<=59 ){
                           baseFrame.setStartSecondOption(startTimeSecs);
                              }
                       }
                       
		    }
                   
                    PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Settings successfully loaded.");
                  
		}
                 baseFrame.setSchedule( schedule, scheduleOption);
		 configScanner.close();
		}catch(Exception e){
			 PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
                         PbfGenerationController.pbfErrorList.append(e.getMessage());
                         PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                         PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
                         
		}

	}
	
	public  static void saveConfigFile(){
		
		 try{
                        PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, " Saving settings...");
			 File outputFile =  new File("etc/pbf_settings.conf");
			 if(outputFile.exists()) outputFile.delete();
                            outputFile.createNewFile();
                            fileWriter =  new FileWriter(outputFile);
                            printWriter =  new PrintWriter(fileWriter);
                            String parameterDivider =":>";
                            printWriter.println("schedule"+parameterDivider+baseFrame.getSchedule());	
                            printWriter.println("schedule_option"+parameterDivider+baseFrame.getScheduleOption());	
                            printWriter.println("smtp_server"+parameterDivider+baseFrame.getSMTPServer());
                            printWriter.println("port"+parameterDivider+baseFrame.getPort());
                            printWriter.println("email_username"+parameterDivider+baseFrame.getEmailUser());
                            printWriter.println("email_password"+parameterDivider+PbfDataCipher.scramble(baseFrame.getEmailPassword()));
                            printWriter.println("sslOnConnect"+parameterDivider+baseFrame.getSelectedSSLOptionValue());
                            printWriter.println("from"+parameterDivider+baseFrame.getFromField());
                            printWriter.println("to"+parameterDivider+baseFrame.getTo());
                            printWriter.println("cc"+parameterDivider+baseFrame.getCc());
                            printWriter.println("header_background"+parameterDivider+pbfMailer.getHeaderBgdColour());
                            printWriter.println("header_font"+parameterDivider+pbfMailer.getHeaderFontColour());
                            printWriter.println("alternate_row"+parameterDivider+pbfMailer.getAlternateRowColour());
                            printWriter.println("border"+parameterDivider+pbfMailer.getBorderCol());
                            printWriter.println("font"+parameterDivider+pbfMailer.getFontCol()); 
                            printWriter.println("database"+parameterDivider+PbfDataCipher.scramble(baseFrame.getDatabaseField()));
                            printWriter.println("username"+parameterDivider+PbfDataCipher.scramble(baseFrame.getDatabaseUserField()));
                            printWriter.println("server"+parameterDivider+PbfDataCipher.scramble(baseFrame.getDatabaseServerField()));
                            printWriter.println("password"+parameterDivider+PbfDataCipher.scramble(baseFrame.getDatabasePassword()));
                            printWriter.println("start_date"+parameterDivider+baseFrame.getStartDateField()); 
                            printWriter.println("end_date"+parameterDivider+baseFrame.getEndDateField()); 
                            printWriter.println("yesterday"+parameterDivider+ baseFrame.getYesterdayOptionVal()); 
                            printWriter.println("output_folder"+parameterDivider+baseFrame.getOutputFolder());
                            printWriter.println("file_sequence_num"+parameterDivider+baseFrame.getFileSequenceNumberField());
                            printWriter.println("pbf_date"+parameterDivider+baseFrame.getPbfDateField());
                            printWriter.println("default_date"+parameterDivider+baseFrame.getDefaultFileDateOptionVal());
                            printWriter.println("specific_file_date"+parameterDivider+baseFrame.getDateRangeOptionVal());
                            printWriter.println("query_file"+parameterDivider+baseFrame.getPbfQueryField()); 
                            printWriter.println("email_header_image"+parameterDivider+baseFrame.getHeaderImage());
                            printWriter.println("is_mail_enabled"+parameterDivider+baseFrame.getMaiEnaledOptionVal());
                            printWriter.println("send_mail_on_error"+parameterDivider+baseFrame.getMailOnErrorOption());
                            printWriter.println("start_time_hour"+parameterDivider+baseFrame.getStartHourOption());
                            printWriter.println("start_time_minute"+parameterDivider+baseFrame.getStartMinuteOption());
                            printWriter.println("start_time_second"+parameterDivider+baseFrame.getStartSecondOption());
                            printWriter.close();
                            fileWriter.close();
                            System.gc();
                            baseFrame.showInformationMessage("Settings successfully saved", "Settings Updated");
                            PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, " Settings saved successfully...");

          }catch(Exception e){
	 	    	 PbfGenerationController.pbfLogger.log(java.util.logging.Level.WARNING, "Error saving setting file: ");
	 	    	 PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
                         PbfGenerationController.pbfErrorList.append(e.getMessage());
                         PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                          PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
		  }
		
		
	}
	
      public static PbfGenerationController getInstance(){

          if (unique_instance == null){
  lock = new Object();
                      synchronized(lock){

          if (unique_instance == null){

                 unique_instance = new PbfGenerationController();
               }
           }
          }
          return unique_instance;

      }

	  public static void close(){
		  pbfLogger.log(java.util.logging.Level.INFO, "================================================");
		  pbfLogger.log(java.util.logging.Level.INFO, "Stopping PBF File Generation Service ... ");
		  pbfLogger.log(java.util.logging.Level.INFO, "================================================");
                for(Handler h: pbfLogger.getLogger().getHandlers())
                    {
                        h.close();   
                    }
                  System.exit(0);
		  }
    private PbfGenerationController(){
        
                 pbfLogger.log(Level.INFO, "========================================");
    		 pbfLogger.log(Level.INFO, "Starting PBF File Generation Service....");        
    		 pbfLogger.log(Level.INFO, "========================================");
                 pbfMailer =  PbfMailer.getInstance();
                 pbfDataManager = new PbfDataManager();
                 timer = new Timer();          
    }
    public static void runService(){
              try{
                 pbfDataManager.init();

                 PbfFile tempPBFFile =new PbfFile(Integer.parseInt(baseFrame.getFileSequenceNumberField()));
                if(baseFrame.getYesterdayOption()){
                         baseFrame.setYesterdayOption();
                                 }
                 startDate =baseFrame.getStartDateField();
                 endDate = baseFrame.getEndDateField();
                 tempPBFFile.setPbfRawData(pbfDataManager.getPbfData(startDate,endDate));
                 newPbfFile = null;
                 newPbfFile  = tempPBFFile;
                 pbfWriter  = PbfFileWriter.getInstance(tempPBFFile);
                 pbfWriter.write();
                 report.setFile(tempPBFFile);
                 String reportData = report.getReportDetails();
                 pbfMailer.PbfMailerPrepare();
                 
                  if(baseFrame.getMaiEnaledOptionVal().equalsIgnoreCase("yes")){
                        pbfMailer.sendMail("Verve PBF Report for "+dateFormat.format(new Date(System.currentTimeMillis())),reportData );
                  }
              }catch(Exception e){
              PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
               if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error starting service");
                    PbfGenerationController.pbfErrorList.append(e.getMessage());
                    PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                    PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
              }
              if( PbfGenerationController.pbfErrorList.length()>0 && baseFrame.getMaiEnaledOptionVal().equalsIgnoreCase("yes")){
                  pbfMailer.PbfMailerPrepare();
                 String body = "<table width=\"100%\" border=\"none\"><tr width=\"100%\"><td width=\"100%\"> Hi, all.</td></tr><tr><td></td></tr><tr><td>Trust this meets you well.</td></tr><tr><td></td></tr><tr><td>The following errors were reported during the last run of the PBF service:</td></tr></tr><tr><td>"+PbfGenerationController.pbfErrorList.toString()+"</td></tr><tr><td></td></tr><tr><td>Kindly investigate the errors or report to your support team.</td></tr><tr><td></td></tr><tr><td>Thank you.</td></tr>";
                 String header = "</td></tr>PBF Generation Report for "+dateFormat.format(new Date(System.currentTimeMillis()))+" Error Log</td></tr></table>";
                 pbfMailer.sendMail(header, body);
              }
              PbfGenerationController.pbfErrorList.delete(0,PbfGenerationController.pbfErrorList.length());
    }
    public  static String implode(String separator, ArrayList<String> data) {

	    StringBuilder builder = new StringBuilder();

	    for (int i = 0; i < (data.size() - 1); i++) {

	            builder.append(data.get(i));
	            builder.append(separator);

	    }
	    builder.append(data.get((data.size() - 1)));
	    return builder.toString();
	}

        public static String getErrorMessage(Exception e){
         StringBuilder message= new StringBuilder();
         message.append(e.getMessage()).append("\n"+ERROR_MARGIN);
         StackTraceElement[] errorStack = e.getStackTrace();
         for (StackTraceElement errorStack1:errorStack){
            message.append(errorStack1.toString()).append("\n"+ERROR_MARGIN);
         }
         String errorMessage = message.toString();
         message.delete(0, message.length());
         return errorMessage;
    }

       public static String getThrownMessage(Throwable e){
         StringBuilder message= new StringBuilder();
         StackTraceElement[] errorStack = e.getStackTrace();
         for (StackTraceElement errorStack1 : errorStack) {
             message.append(errorStack1.toString()).append("\n");
         }
         String errorMessage = message.toString();
         message.delete(0, message.length());
         return errorMessage;
    }
       public static long getStartTimeDelay(){
             long delay =0;
           try{
           
             int startTimeHour = Integer.parseInt(baseFrame.getStartHourOption());
             int startTimeMins = Integer.parseInt(baseFrame.getStartMinuteOption());
             int startTimeSecs = Integer.parseInt(baseFrame.getStartSecondOption());
             
             Calendar cal = Calendar.getInstance();
             Calendar cal2 = Calendar.getInstance();
             Date currentTime = cal.getTime();
             Date startTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis())+" "+baseFrame.getStartHourOption()+":"+baseFrame.getStartMinuteOption()+":"+baseFrame.getStartSecondOption());
            String currentTimeStr =  new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(currentTime);
             String hourStr =  currentTimeStr.substring(currentTimeStr.indexOf(" ")).trim();
             String[] timeComps = hourStr.split(":");
          
             PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Current Time "+ new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(currentTime)); 
             int currentTimeHour = Integer.parseInt(timeComps[0]);  //cal.get(Calendar.HOUR); 
             int currentTimeMins = Integer.parseInt(timeComps[1]); //  cal.get(Calendar.MINUTE); 
             int currentTimeSecs = Integer.parseInt(timeComps[2]); //cal.get(Calendar.SECOND);
             cal2.setTime(startTime);
             cal2.add(Calendar.DATE, 1);
             Date newTime = cal2.getTime();
             
             if(startTimeHour>currentTimeHour){
              delay =startTime.getTime() - currentTime.getTime();
               PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Start Time "+ new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(startTime));
             }
             else if(startTimeHour<currentTimeHour){
                  delay =newTime.getTime() - currentTime.getTime();   
                   PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Start Time "+ new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(newTime));
             }else if(startTimeHour==currentTimeHour){
                 if(startTimeMins<currentTimeMins ){
                      delay =newTime.getTime() - currentTime.getTime();
                       PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Start Time "+ new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(newTime));
                  }else if(startTimeMins==currentTimeMins){
                         if(startTimeSecs<currentTimeSecs ){
                          delay =newTime.getTime() - currentTime.getTime();
                           PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Start Time "+ new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(newTime));
                  }else{
                         delay =startTime.getTime() - currentTime.getTime();
                          PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Start Time "+ new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(startTime));
                         }
                  
                  }else{
                        delay =startTime.getTime() - currentTime.getTime();
                         PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Start Time "+ new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(startTime));
                    }
                     
             }
             }catch(Exception e){
              PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
               if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error starting service");
                    PbfGenerationController.pbfErrorList.append(e.getMessage());
                    PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                    PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
              }
             PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Estimated Delay:  "+ String.valueOf(delay));
             return delay;
       
       }
       public static void startService(){
         try{
    				long delay = getStartTimeDelay();
    				long interval =  baseFrame.getAppSchedule(schedule, scheduleOption);
                                
    				isStarted =true;
                                baseFrame.disableStartBttn();
    				timer.scheduleAtFixedRate(new TimerTask() {
    					public void run() {
    				      //  new PbfGenerationController();	
                                       
                                        runService();
    				}
    		
    				}, delay, interval);
    			} catch (Exception e) {
    				
    				PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
                                if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error starting service");
                    PbfGenerationController.pbfErrorList.append(e.getMessage());
                    PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                    PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
    		
    			}
       
       }
       
       public static String getProcessOutput(Process proc){

      	StringBuilder output = new StringBuilder();
      	StringBuilder error = new StringBuilder();
    	String outputStr ="";
    	try{
		  
  	BufferedReader stdInput = new BufferedReader(new 
  	     InputStreamReader(proc.getInputStream()));

  	BufferedReader stdError = new BufferedReader(new 
  	     InputStreamReader(proc.getErrorStream()));

  	String s = null;
  	while ((s = stdInput.readLine()) != null) {
  	    output.append(s);
  	}

  	while ((s = stdError.readLine()) != null) {
  	    error.append(s);
  	}
       
  	 } catch (Exception e) {
			
			e.printStackTrace();
	
		}
    	outputStr = !error.toString().isEmpty()?error.toString():output.toString();
        // PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Process output: "+outputStr);
    	return outputStr;
    	
    }
       
       public static boolean isServiceRunning(){
         boolean serviceStarted = false;
         
          try{
	    	Runtime rt = Runtime.getRuntime();
	    	String commands = "";
	    	commands = "sc query \"PbfFileService\"";
                Process proc = rt.exec(commands);
	    	proc = rt.exec(commands);
	    	 serviceStarted =  getProcessOutput(proc).contains("RUNNING");
		} catch (Exception e) {
                        PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
                        if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error checking service status");
                        PbfGenerationController.pbfErrorList.append(e.getMessage());
                        PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                        PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
		
		}
         return serviceStarted;
       }
       public static void stopService(){
            if(isStarted){
               pbfLogger.log(java.util.logging.Level.INFO, "================================================");
               pbfLogger.log(java.util.logging.Level.INFO, "Stopping PBF File Generation Service ... ");
               pbfLogger.log(java.util.logging.Level.INFO, "================================================");
               isStarted =false;
               baseFrame.enableStartBttn();
                           for(Handler h: pbfLogger.getLogger().getHandlers())
                           {
                               h.close();   
                           }
                timer.cancel();
              timer.purge();
            }
       }
       
       public static  void showPBFDialog(){
        HashMap<String, String> systemLAFs= getSystemLAFs();
        PREFERRED_LAF = (systemLAFs.containsKey("Windows Classic"))?systemLAFs.get("Windows"):systemLAFs.get("Nimbus");
        BASIC_LAF = systemLAFs.get("Windows Classic");
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                
                 UIManager.setLookAndFeel(BASIC_LAF);
                          }
        } catch (ClassNotFoundException ex) {
        	PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(ex));
        } catch (InstantiationException ex) {
        	PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(ex));
        } catch (IllegalAccessException ex) {
        	PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(ex));
        } catch (UnsupportedLookAndFeelException ex) {
        	PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(ex));
        }
        //</editor-fold>

        /* Create and display the form */
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	            	
	            	baseFrame =PbfServiceConfigurator.getInstance();
                        baseFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE); 
                        baseFrame.setVisible(true);
	            }
	        });
       }
      
     
        public static void main(String[] args) {
                     pbfLogger  =  PbfLogger.getInstance("PbfFileGenService");
                     getInstance();
                      baseFrame =  PbfServiceConfigurator.getInstance();
                    readConfigFile();
                   
                    if(args.length==0 || args[0].equalsIgnoreCase( "-D")){
                        
                        showPBFDialog();
                  timer2 =  new Timer();
                  timer2.scheduleAtFixedRate(new TimerTask() {
    			public void run() {
                                    if(  (baseFrame.isVisible())  && (isServiceRunning() || isStarted) ) {
                                    baseFrame.disableStartBttn();
                                    } else{
                                    baseFrame.enableStartBttn();
                                    }
    				}
    		
    				}, 0, 2000);
                                
                    }else if(args[0].equalsIgnoreCase( "-S")){
                        startService();
                    }							 

        }
         
            	
    }
    
    

