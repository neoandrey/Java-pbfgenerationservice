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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PbfFileWriter {
    static PbfFileWriter unique_instance;
    private static Object lock = new Object();
    public PbfFile currentFile;
    
    private PbfFileWriter(){



   }
    
    public static PbfFileWriter getInstance(PbfFile file){

//          if (unique_instance == null)
//
//                      synchronized(lock){
//
//          if (unique_instance == null){
//
//                 unique_instance = new PbfFileWriter(file);
//               }
//           }

          return new PbfFileWriter(file);

      }
    
    public String prepareHeader(){
       StringBuilder header = new StringBuilder();
       header.append(PbfGenerationController.PBF_HEADER_PREFIX)
             .append(PbfGenerationController.PBF_DATA_SEPARATOR)
             .append(currentFile.getCreationDateTime())
             .append(PbfGenerationController.PBF_DATA_SEPARATOR)
             .append(currentFile.getSequenceNumber())  
             .append(PbfGenerationController.PBF_DATA_SEPARATOR)
             .append(PbfGenerationController.PBF_VERSION_NUMBER);
       
       return header.toString();
    
    }
    
    public String prepareTrailer(){
    StringBuilder trailer = new StringBuilder();
       trailer.append(PbfGenerationController.PBF_TRAILER_PREFIX)
             .append(PbfGenerationController.PBF_DATA_SEPARATOR)
             .append(currentFile.getRecordCount());
       return trailer.toString();
    
    
    
    }
    
    public String formatPbfRecord(int seqNum, HashMap<String, String> record){
             StringBuilder pbfRecord = new StringBuilder();
        try{
   
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
        String  recDateStr = record.get("datetime_req").substring(0, record.get("datetime_req").lastIndexOf(".")).replace(" ","").replace("-","").replace(":","");
        DecimalFormat decForm = new DecimalFormat("###0.00");
        PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, " Raw Date: "+record.get("datetime_req"));
        PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Converted Date: "+recDateStr);
        pbfRecord.append(PbfGenerationController.PBF_RECORD_PREFIX)
                 .append(PbfGenerationController.PBF_DATA_SEPARATOR)
                 .append(String.valueOf(seqNum))
                 .append(PbfGenerationController.PBF_DATA_SEPARATOR)
                 .append(record.get("retrieval_reference_nr"))
                 .append(PbfGenerationController.PBF_DATA_SEPARATOR)
                 .append("/XM/").append(record.get("pan")).append("/").append(record.get("expiry_date"))
                 .append(PbfGenerationController.PBF_DATA_SEPARATOR)
                 .append("S")
                 .append(PbfGenerationController.PBF_DATA_SEPARATOR)
                 .append("0")
                 .append(PbfGenerationController.PBF_DATA_SEPARATOR)
                 .append(recDateStr.substring(0,12))
                 .append(PbfGenerationController.PBF_DATA_SEPARATOR)
                 .append(record.get("card_acceptor_id_code"))
                 .append(PbfGenerationController.PBF_DATA_SEPARATOR)
                 .append(record.get("terminal_id"))
                 .append(PbfGenerationController.PBF_DATA_SEPARATOR)
                 .append(decForm.format(Double.parseDouble(record.get("tran_amount_req")))) ;
                 
        }catch(Exception e){
           PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
           if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error reading  record.");
            PbfGenerationController.pbfErrorList.append(e.getMessage());
            PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
            PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
        
        }
         return pbfRecord.toString();
    }
    
    public void write(){
        try{
            FileWriter pbfFileWriter = new FileWriter(currentFile.getFile());
            PrintWriter pbfPrintWriter = new PrintWriter(pbfFileWriter);
            pbfPrintWriter.println(this.prepareHeader());
            for(int i=0; i<  PbfGenerationController.newPbfFile.getPbfRawData().size(); i++){
                  pbfPrintWriter.println(  formatPbfRecord((i+1),PbfGenerationController.newPbfFile.getPbfRawData().get(i)));
            }
            pbfPrintWriter.println(this.prepareTrailer());
            pbfFileWriter.close();
            pbfPrintWriter.close();

        }catch(Exception e ){
          PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
           if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error  exporting file.");
            PbfGenerationController.pbfErrorList.append(e.getMessage());
            PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
            PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");

        }
    
    
    }
    
    
    
     private PbfFileWriter(PbfFile  pbFile){
         this.currentFile =  pbFile;


   }
    
    
    
}
