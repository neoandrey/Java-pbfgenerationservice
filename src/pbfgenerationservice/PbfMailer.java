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

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class PbfMailer {
    
    
	private  String smtpServer;
	private  String password;
	private  String username;
	private  String port;
	private  boolean sslOnConnect;
	private  String from;
	private  String to;
	private  String cc;
        HtmlEmail mailer;
        String headerImage="etc\\interswitch_logo.png";   
    private static String headerBgdCol;
    private static String headerFontCol;
    private static String alternateRowCol;
    private static String borderCol;
    private static String fontCol;
      private static Object lock = new Object();
      static PbfMailer unique_instance;
    
     static void setHeaderBgdColour(String newHeaderBgdCol){
			 headerBgdCol = newHeaderBgdCol;
		 }
		 static void setHeaderFontColour(String newHeaderFontCol){
			 headerFontCol = newHeaderFontCol;
		 }
		 static void setAlternateRowColour(String newAlternateRowCol){
			alternateRowCol = newAlternateRowCol;
		 }
		 static void setBorderCol(String newBorderCol){
			 borderCol = newBorderCol;
		 }
		 static void setFontCol(String newFontCol){
			fontCol = newFontCol;
		 }
          private PbfMailer(){
              
          }
          
             public static PbfMailer getInstance(){

          if (unique_instance == null)

                      synchronized(lock){

          if (unique_instance == null){

                 unique_instance = new PbfMailer();
               }
           }

          return unique_instance;

      }
    
        
        
           static String getHeaderBgdColour(){
			  return headerBgdCol;
		 }
		 static String getHeaderFontColour(){
			  return headerFontCol;
		 }
		 static String getAlternateRowColour(){
			    return alternateRowCol;
		 }
		 static String getBorderCol(){
			  return borderCol;
		 }
		 static String getFontCol(){
			 return fontCol;
		 }
                 
	
          public void PbfMailerPrepare(){
                            try{
 
 				this.mailer = new HtmlEmail();
 				mailer.setHostName(PbfGenerationController.baseFrame.getSMTPServer());
 				mailer.setSmtpPort(Integer.parseInt(PbfGenerationController.baseFrame.getPort()));
 				mailer.setAuthenticator(new DefaultAuthenticator(  PbfGenerationController.baseFrame.getEmailUser(),  PbfGenerationController.baseFrame.getEmailPassword()));
 				mailer.setSSLOnConnect( PbfGenerationController.baseFrame.getSelectedSSLOption());
 				mailer.setFrom(PbfGenerationController.baseFrame.getFrom());
 				//ArrayList<String> ccList = new ArrayList<String>();
 				
 				String ccStr = PbfGenerationController.baseFrame.getCc();
 				String[] ccs = ccStr.split(";");
 				if(ccs.length>1){
 				for(int i=0; i< ccs.length; i++){
 					mailer.addCc(ccs[i].trim());
 				}
 				} else{
 					ccs = ccStr.split(",");
 	 				if(ccs.length>1){
 	 				for(int i=0; i< ccs.length; i++){
 	 					mailer.addCc(ccs[i].trim());
 	 				}
 				}else{
 					mailer.addCc(ccStr.trim());
 				}
 				}
 				
 				String toStr = PbfGenerationController.baseFrame.getTo();
 				String[] tos = toStr.split(";");
 				if(tos.length>1){
 				for(int i=0; i< tos.length; i++){
 					mailer.addTo(tos[i].trim());
 				}
 				} else{
 					tos = toStr.split(",");
 	 				if(tos.length>1){
 	 				for(int i=0; i< tos.length; i++){
 	 					mailer.addTo(tos[i].trim());
 	 				}
 				}else{
 					mailer.addTo(toStr.trim());
 				}
 				}
                            }catch(Exception e){
                            
                               PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, "Unable prepare PBF Mailer.");
 			       PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, PbfGenerationController.getErrorMessage(e));
                               if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Unable prepare PBF Mailer.");
            PbfGenerationController.pbfErrorList.append(e.getMessage());
            PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
            PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
                            }
          
          }
          
          public boolean sendMail(String subject, String body){
                    boolean isMailSent  = false;
                    try{
                        String cid = this.mailer.embed(new File(PbfGenerationController.baseFrame.getHeaderImage()), "Interswitch logo");
                        body+=("<div style=\"position:relative; left:100px;\"><table style=\"border: 2px solid; border-spacing: 0px; font-family: Calibri, Arial, Helvetica; border:1px;\">"
                               + "<tr><td><img src=\"cid:"+cid+"\" /></td></tr></table></div><br />");
                        this.mailer.setSubject(subject);
                        this.mailer.setHtmlMsg(body);
                        PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, body);
                        this.mailer.setTextMsg("Your email client does not support HTML emails.");
                        this.mailer.send();
                        PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Notification email has been successfully sent.");
                        isMailSent = true;
                    }catch (Exception e){
                      e.printStackTrace();
                      PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, "Unable to send notification email.");
                      PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, PbfGenerationController.getErrorMessage(e));
                       if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Unable to send notification email.");
                        PbfGenerationController.pbfErrorList.append(e.getMessage());
                        PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                        PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
                    } 

               return isMailSent;
          }
          
        
          void setSMTPServer(String server){
			 smtpServer = server;
		 }
		  void setPort(String newPort){
			 port = newPort;
		 }
		 
		  void setUsername(String newUsername){
			 username = newUsername;
		 }
		  void setPassword(String newPassword){
			 password = newPassword;
		 }
		  void setSslOnConnect(boolean newsslOnConnect){
			 sslOnConnect = newsslOnConnect;
		 }
		  void setFrom(String newFrom){
			 from = newFrom;
		 }
		  void setTo(String newTo){
			 to = newTo;
		 }
		  void setCc(String newCc){
			 cc = newCc;
		 }
                  void setHeaderImage(String image){
                      headerImage = image;
                  }
                  
                 String getSMTPServer(){
			 String smtp =smtpServer!=null?smtpServer:"localhost";
	     	 return smtp;
		 }
		  String getPort(){
			 String prt =port!=null?port:"25";
	     	 return prt;
		 }
		  String getUsername(){
			  return username;
		 }
		  String getPassword(){
			  return password;
		 }
		  boolean getSslOnConnect(){
		// String sslOnCnt =?true:false;
			    return sslOnConnect ;
		 }
		  String getFrom(){
			   return from;
		 }
		  String getTo(){
			  return to;
		 }
		  String getCc(){
			  return  cc;
		 }
                 String getHeaderImage(){
                       return headerImage.isEmpty()?"etc\\interswitch_logo.png":"";
                                              

                 }
    
}
