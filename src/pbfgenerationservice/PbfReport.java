/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbfgenerationservice;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Mobolaji.Aina
 */
public class PbfReport {
       
	private  String headerBgdCol;
	private  String headerFontCol;
	private  String alternateRowCol;
	private  String borderCol;
	private  String fontCol;
        public   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        public PbfFile  newPbfFile;
	
        public PbfReport(){
        
        }
        
        
        
        public PbfReport(PbfFile  file){
            this.newPbfFile = file;
           // this.readConfigFile();
        
        }
    		public void  setFile(PbfFile  file){
                    this.newPbfFile = file;
                }
		  String getHeaderBgdColour(){
			  return headerBgdCol;
		 }
		  String getHeaderFontColour(){
			  return headerFontCol;
		 }
		  String getAlternateRowColour(){
			    return alternateRowCol;
		 }
		  String getBorderCol(){
			  return borderCol;
		 }
		  String getFontCol(){
			 return fontCol;
		 }
                   	  public Color getColour(String colour){
 		 
 		    if(colour != null){
 		    	String[] rgbValues =colour.split(",");
 		    	return new Color (Float.parseFloat(rgbValues[0]),Float.parseFloat(rgbValues[1]),Float.parseFloat(rgbValues[2]));
 		    }else{
 		    	return null;
 		    }
 		 
 	 }

		
		  void setHeaderBgdColour(String newHeaderBgdCol){
			 headerBgdCol = newHeaderBgdCol;
		 }
		  void setHeaderFontColour(String newHeaderFontCol){
			 headerFontCol = newHeaderFontCol;
		 }
		  void setAlternateRowColour(String newAlternateRowCol){
			alternateRowCol = newAlternateRowCol;
		 }
		  void setBorderCol(String newBorderCol){
			 borderCol = newBorderCol;
		 }
		  void setFontCol(String newFontCol){
			fontCol = newFontCol;
		 }
		
	
     public String maskPan(String clearPan){
         int n = clearPan.length() - 10;
        String stars =   new String(new char[n]).replace("\0", "*");
       return  clearPan.substring(0,6)+stars+clearPan.substring(clearPan.length()-5, clearPan.length()-1);
     
     }
	
      public String  getReportDetails(){
           StringBuilder emailMsg =  new StringBuilder();
           
 		  if(PbfGenerationController.baseFrame.getMaiEnaledOptionVal().equalsIgnoreCase("YES")){
                      PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Sending notification email... ");
 		  try{
				String  rgb=Integer.toHexString(getColour(this.getHeaderBgdColour()).getRGB());
				String headerBgColor ="#"+rgb.substring(2, rgb.length());
				rgb=Integer.toHexString(getColour(this.getHeaderFontColour()).getRGB());
				String headerFontColor ="#"+rgb.substring(2, rgb.length());
				rgb=Integer.toHexString(getColour(this.getAlternateRowColour()).getRGB());
				String alternateRowColor  ="#"+rgb.substring(2, rgb.length());
				rgb=Integer.toHexString(getColour(this.getBorderCol()).getRGB());
				String borderColor ="#"+rgb.substring(2, rgb.length());
				rgb=Integer.toHexString(getColour(this.getFontCol()).getRGB());
				String FontColor = "#"+rgb.substring(2, rgb.length());
				int size =this.newPbfFile.getRecordCount();
				//String todaysDate =dateFormat.format(new Date(System.currentTimeMillis()));
				//int k = 1;                               
                                //String subject= "PBF File Generation Report Executed at:"+todaysDate;
                                emailMsg.append("<table width=\"100%\" border=\"none\" valign=\"top\"><tr width=\"100%\"><td width=\"100%\"><tr><td>Hi, all.<br /></td></tr>");
                                emailMsg.append("<tr><td></td></tr>");
                                emailMsg.append("<tr><td>Trust this meets you well.<br /></td></tr>");
                                emailMsg.append("<tr><td></td></tr>");
                                emailMsg.append("<tr><td>The Verve PBF file for today has been generated to the: "+this.newPbfFile.getParentFolder() +" folder. The contents of the file are shown below:<br /></td></tr><tr><td></td></tr></table>");
				emailMsg.append("<p style=\"position:relative; left:100px;color:").append(FontColor).append(";\"> <table style=\"border: qpx solid ").append(borderColor).append("; border-spacing: 0px; font-family: Calibri, Arial, Helvetica; border:1px;\">");
				emailMsg.append("<caption style=\"font-size: 14px; margin: 1em auto; font-style: bold;text-decoration:underline;\">PBF File: "+this.newPbfFile.getName()+" Generation Report</caption>");
				emailMsg.append("<thead><tr style=\"background: ").append(headerBgColor).append("; color: ").append(headerFontColor).append("border: 0.5px solid ").append(borderColor).append(";\">" + "<th style=\"border-radius: 9px 0 0 0; border: 0.5px solid ").append(borderColor).append(";\">Sequence Number</th>" + "<th style=\"border: 0.5px solid ").append(borderColor).append(";\">Transaction Reference </th>" + "<th style=\"border: 0.5px solid ").append(borderColor).append(";\">Customer\\Product ID</th>" + "<th style=\"border: 0.5px solid ").append(borderColor).append(";\">Transaction Operation</th>" + "<th style=\"border: 0.5px solid ").append(borderColor).append(";\">Transaction Mode</th>" + "<th style=\"border: 0.5px solid ").append(borderColor).append(";\">Transaction Time</th>" + "<th style=\"border: 0.5px solid ").append(borderColor).append(";\">Merchant ID</th>" + "<th style=\"border: 0.5px solid ").append(borderColor).append(";\">Terminal ID</th>" + "<th style=\"border: 0.5px solid ").append(borderColor).append(";\">Transaction Amount</th>"+
					 "</thead><tbody>");
                                HashMap<String, String> record = new HashMap<> ();
                                            for(int i=0; i<size; i++){
                                                     record = this.newPbfFile.getPbfRawData().get(i);
                                                
                                                if(i==1){
					    emailMsg.append("<tr style=\"border: 0.5px solid ").append(borderColor).append(";\">")
                                                    .append("<td style=\"padding: 0em;border: 0.5px solid "+borderColor+";\">"+String.valueOf(i+1)+"</td>")
                                                    .append("<td style=\"padding: 0em; border: 0.5px solid "+borderColor+"; \">"+record.get("retrieval_reference_nr")+"</td>")
                                                    .append("<td style=\"padding: 0em; border: 0.5px solid "+borderColor+"; \">"+maskPan(record.get("pan"))+"</td>")
                                                    .append("<td style=\"padding: 0em; border: 0.5px solid "+borderColor+"; \">S</td>")
                                                    .append("<td style=\"padding: 0em; border: 0.5px solid "+borderColor+"; \">0</td>")
                                                    .append("<td style=\"padding: 0em; border: 0.5px solid "+borderColor+"; \">"+record.get("datetime_req")+"</td>")
                                                    .append("<td style=\"padding: 0em; border: 0.5px solid "+borderColor+"; \">"+record.get("card_acceptor_id_code")+"</td>")
                                                    .append("<td style=\"padding: 0em;\">"+record.get("terminal_id")+"</td>")
                                                    .append("<td style=\"padding: 0em;\">"+record.get("tran_amount_req")+"</td>")
                                                    .append("</tr>");
					}else if(i==(size)){
					    if(i%2==0){
                                                emailMsg.append("<tr  style=\"background: "+alternateRowColor+";border: 0.5px solid "+borderColor+";\">")
                                                        .append( "<td style=\"padding: 0em; border-radius: 0 0 0 9px; border: 0.5px solid "+borderColor+";\">"+String.valueOf(i+1)+"</td>")
                                                        .append( "<td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("retrieval_reference_nr")+"</td>")
                                                        .append("<td  style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+maskPan(record.get("pan"))+"</td>")
                                                        .append("<td style=\"padding: 0em;border: 0.5px solid "+borderColor+";\">S</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">0</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("datetime_req")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("card_acceptor_id_code")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("terminal_id")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("tran_amount_req")+"</td>")
                                                        .append("</tr>");
                                               } else {
                                                emailMsg.append("<tr style=\"border: 0.5px solid "+borderColor+";\">")
                                                      .append( "<td style=\"padding: 0em; border-radius: 0 0 0 9px; border: 0.5px solid "+borderColor+";\">"+String.valueOf(i+1)+"</td>")
                                                        .append( "<td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("retrieval_reference_nr")+"</td>")
                                                        .append("<td  style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+maskPan(record.get("pan"))+"</td>")
                                                        .append("<td style=\"padding: 0em;border: 0.5px solid "+borderColor+";\">S</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">0</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("datetime_req")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("card_acceptor_id_code")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("terminal_id")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("tran_amount_req")+"</td>")
                                                        .append("</tr>");
                                            }
					}else{
                                             if(i%2==0){
                                                emailMsg.append("<tr  style=\"background: "+alternateRowColor+";border: 0.5px solid "+borderColor+";\">")
                                                        .append( "<td style=\"padding: 0em; border-radius: 0 0 0 9px; border: 0.5px solid "+borderColor+";\">"+String.valueOf(i+1)+"</td>")
                                                        .append( "<td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("retrieval_reference_nr")+"</td>")
                                                        .append("<td  style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+maskPan(record.get("pan"))+"</td>")
                                                        .append("<td style=\"padding: 0em;border: 0.5px solid "+borderColor+";\">S</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">0</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("datetime_req")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("card_acceptor_id_code")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("terminal_id")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("tran_amount_req")+"</td>")
                                                        .append("</tr>");
                                               } else {
                                                emailMsg.append("<tr style=\"border: 0.5px solid "+borderColor+";\">")
                                                      .append( "<td style=\"padding: 0em; border-radius: 0 0 0 9px; border: 0.5px solid "+borderColor+";\">"+String.valueOf(i+1)+"</td>")
                                                        .append( "<td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("retrieval_reference_nr")+"</td>")
                                                        .append("<td  style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+maskPan(record.get("pan"))+"</td>")
                                                        .append("<td style=\"padding: 0em;border: 0.5px solid "+borderColor+";\">S</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">0</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("datetime_req")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("card_acceptor_id_code")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("terminal_id")+"</td>")
                                                        .append("<td td style=\"padding: 0em; border: 0.5px solid "+borderColor+";\">"+record.get("tran_amount_req")+"</td>")
                                                        .append("</tr>");
                                            }
					 }
                                 }
                          
			  emailMsg.append("</tbody></table></div>");
                         
// 			  email.setHtmlMsg(emailMsg.toString());
// 			
//                          PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, emailMsg.toString() );
//	 	
// 			  emailMsg.delete(0, emailMsg.length());
// 				email.setTextMsg("Your email client does not support HTML emails.");
// 				email.send();
// 				 PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Notification email has been successfully sent.");
//                                 PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,  "Notification email has been successfully sent.");
 			  }catch ( Exception e){
 			    e.printStackTrace();
 			    PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, "Unable to send notification email.");
 			    PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, PbfGenerationController.getErrorMessage(e));
                            if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Unable to send notification email.");
                            PbfGenerationController.pbfErrorList.append(e.getMessage());
                            PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                            PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
 			  } 
                  }
                   return emailMsg.toString();
 	   }
    
    
}
