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

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PbfFile {
    
    private String fileName =null;
    private String filePath =null;
    private String parentFolder = null;
    private File   pbfRawFile = null;
    private long   age = 0;
    private long   size=0;
    private String format =null;
    static PbfDataManager dataMan = new PbfDataManager();
    SimpleDateFormat pbfDateFormat = new SimpleDateFormat("YYYYMMDDHHmmss");
    private static final String PBF_FILENAME_PREFIX = "PBF";
    int fileSequenceNumber = 0;
    String creationDate;
    String creationDateTime;
    ArrayList<HashMap> pbfData = new ArrayList<HashMap>();

    public PbfFile(int fileSequenceNumber){
        this.setCreationDate(PbfGenerationController.baseFrame.getStartDateField());
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            DateFormat dateFormat2 = new SimpleDateFormat("HHmmss");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);  
            this.setSequenceNumber(fileSequenceNumber);
            dateFormat = new SimpleDateFormat("yyyyMMdd");
            String fileName = PBF_FILENAME_PREFIX+PbfGenerationController.baseFrame.getPbfDate()+String.valueOf(this.getSequenceNumber())+".dat";
              try {
              String headerDate  =dateFormat.format(dateFormat.parse(PbfGenerationController.baseFrame.getPbfDate()))+dateFormat2.format(System.currentTimeMillis()).substring(0, 4);
              setCreationDateTime(headerDate);
                }catch(Exception e){
                   PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
                    if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error creating file.");
                            PbfGenerationController.pbfErrorList.append(e.getMessage());
                            PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                            PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
                 
               }
            
        PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Creating PBF File: "+ fileName+" in the "+PbfGenerationController.baseFrame.getOutputFolder()+" folder");
          if(isPbfFile(fileName)){
               try{
                   this.pbfRawFile = new File(PbfGenerationController.baseFrame.getOutputFolder()+"\\"+fileName);      
                   setFileName(pbfRawFile.getAbsolutePath());     
                   if(pbfRawFile.exists())pbfRawFile.delete();
		   if(this.pbfRawFile.createNewFile()){
                       PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,  fileName+" has been successfully created.");
			setFilePath();
			setFileName();
			setParentFolder();
			setSize();
			setAge();
			setFormat();			
		}else{
                   PbfGenerationController.pbfLogger.log(java.util.logging.Level.WARNING, fileName+" could not be created");
                   }
                   
               }catch(Exception e){
                   PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
                    if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error creating file.");
                            PbfGenerationController.pbfErrorList.append(e.getMessage());
                            PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                            PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
                 
               }
                
          }
	}
	public PbfFile(File pbfFile1){
               String fileName = pbfRawFile.getName();
               
               if(isPbfFile(fileName)){  
                   this.setCreationDate(fileName.substring(3,11));
                   this.setSequenceNumber(Integer.parseInt(fileName.substring(11,fileName.length()-4)));
                    this.pbfRawFile = null;
                  this.pbfRawFile =  pbfFile1; 
		setFileName(pbfRawFile.getAbsolutePath());
		if(this.pbfRawFile.exists()){
			setFilePath();
			setFileName();
			setParentFolder();
			setSize();
			setAge();
			setFormat();			
		}
               }
	}
        public void setCreationDateTime(String date){
         this.creationDateTime  = date;
        }
	public String  getCreationDateTime(){
           return this.creationDateTime ;
        }
        public boolean isPbfFile(String fileName){
            boolean isCompliant =false;
            PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "The file date is: "+fileName.substring(3,11)); 
            int pbfYear =Integer.parseInt(fileName.substring(3,7));
             PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "The file year is: "+fileName.substring(3,7)); 
            int pbfFileSequenceNo =Integer.parseInt(fileName.substring(11,fileName.length()-4)); 
            PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Sequence Number is: "+pbfFileSequenceNo); 
            fileName = fileName.toLowerCase();
            if(fileName.startsWith("pbf")){
               if( fileName.endsWith("dat") ){
                   if(pbfYear >=1970 && pbfYear <=3000 ){
                       if(pbfFileSequenceNo>= 8888 ){
                       isCompliant = true;
                       }else{
                             PbfGenerationController.pbfLogger.log(java.util.logging.Level.WARNING, "The sequence number of the file is invalid");    
                     }
                   
                   }else{
                       PbfGenerationController.pbfLogger.log(java.util.logging.Level.WARNING, "The creation date of the file is invalid");  
                     }  
            } else{
                    PbfGenerationController.pbfLogger.log(java.util.logging.Level.WARNING, "File extension does not end with \'DAT\'.");                
            } 
            }else{
                
                 PbfGenerationController.pbfLogger.log(java.util.logging.Level.WARNING, "File name does not start with \'PBF\'."); 
            
            }
             return isCompliant;      
        }
               
            
      int getSequenceNumber(){
        return  this.fileSequenceNumber;
     
     }
     String  getCreationDate(){
         return this.creationDate;
     
     }        
     ArrayList<HashMap> getPbfRawData(){
       return this.pbfData;
     }
      
     int getRecordCount(){
        return this.pbfData.size();
     }
     
     
     void setSequenceNumber(int seqNum){
      this.fileSequenceNumber = seqNum;
     
     }
     void setCreationDate(String cDate){
       this.creationDate = cDate;
     
     }
    void setPbfRawData(ArrayList<HashMap> data){
        
        this.pbfData = data;
    }
     
    private void setFileName(String fileName){
    	try {
    		File tempFile = new File(fileName);
                PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Creating PBF file: "+tempFile.getAbsolutePath());
                tempFile.createNewFile();
    		if(tempFile.exists())this.pbfRawFile =tempFile;
		}
	catch (Exception e)
		{
			 PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
               if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error Fetching data for PBF file");
                              if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error Fetching data for PBF file.");
                            PbfGenerationController.pbfErrorList.append(e.getMessage());
                            PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                            PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
                 
                
		}
    }
    private void setFilePath(){
    	this.filePath = this.pbfRawFile.getAbsolutePath();  	
    }
    private void setFileName(){
    	this.fileName  = this.pbfRawFile.getName();
    }
    private void setParentFolder(){
    	this.parentFolder=this.pbfRawFile.getParentFile().getAbsolutePath();
    }
    private void setSize(){
    	this.size= this.pbfRawFile.length();
    }
    private void setAge(){
    	long currentTime = System.currentTimeMillis();
    	long sourceFileAge = this.pbfRawFile.lastModified();
    	long timeDiff = currentTime  - sourceFileAge;
		long  numOfDays = timeDiff/(24*60*60*1000);
    	this.age=numOfDays ;
    }
    private void setFormat(){
    	
    		this.format ="PBF";
    	}
    public File getFile(){
		return this.pbfRawFile;
	}
    public String getName(){
    	return this.fileName;    	
    } 
    public String getPath(){
        return this.filePath;
    }
    public String getParentFolder(){
    	return this.parentFolder;
    }
    public long getAge(){
    	return this.age;	
    } 
    public String getFormat(){
    	return this.format;
    }
    public long getSize(){
    	return this.size;    	
    }
    public boolean delete(){
    	boolean deleted;
    	deleted = getFile().delete();
    	return deleted;
    	
    }
    
}
