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


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;
import java.io.*;
import java.sql.ResultSetMetaData;
import java.util.logging.Level;

public class PbfDataManager {
    
 static  Statement stmt = null;
 static  ResultSet results = null;
 static  String server = "";
 static  String database = "";
 static  String user = "";
 static  String password = "";
 static  PreparedStatement prepStmt = null;
 static  Connection conn = null;
 static  int resultCount = 0;
 static  ArrayList<ArrayList<Object>> resultList;
 static  Map<Integer, Object> resultIndexMap = new HashMap<Integer, Object>();
 static  Map<String, String> resultDataTypeMap = new HashMap<String, String>();
 static  Map<String, Object> resultColumnMap = new HashMap<String, Object>();
 static  String allQueriesFileStr ;
 static  File allQueriesFile ;
 static  String queryNameDelimiter;
 static  String queryDelimiter;
 static  Map<String, String> queryMap;
final static String PROPERTY_FILE= "etc\\pbf.properties";
final static String QUERY_FILE =   "etc\\get_pbf_records.sql";
static ArrayList<HashMap> resultData = new ArrayList<HashMap>();
    
    public PbfDataManager(){
        // init() ;
    
    }
    
    
     static void getConnection(String server, String databaseName, String user, String password) {

        try {
            server =server.contains(":")?server:server+":1433";
            conn = DriverManager.getConnection("jdbc:sqlserver://" + server + ";databaseName=" + databaseName + ";userName=" + user + ";password=" + password+";allowMultiQueries=true");
            stmt = (Statement) conn.createStatement();
        } catch (Exception e) {
             PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
              if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Unable to connect to server");
                        PbfGenerationController.pbfErrorList.append(e.getMessage());
                        PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                        PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
        }
        
    }
     
        public  static String maskPan(String clearPan){
         int n = clearPan.length() - 10;
        String stars =   new String(new char[n]).replace("\0", "*");
       return  clearPan.substring(0,6)+stars+clearPan.substring(clearPan.length()-5, clearPan.length()-1);
     
     }
     public static boolean execute(String query) {
        boolean isSuccessful = false;
        try {
            resultData.clear();
            PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO,"Executing query: "+query);
            prepStmt = conn.prepareStatement(query);
            results = prepStmt.executeQuery();
            isSuccessful = true;
            resultCount =0;
                 ResultSetMetaData meta = results.getMetaData();  // for a valid resultset object after executing query
                int colCount = meta.getColumnCount();
               // PbfGenerationController.baseFrame.showPlainMessage("Column Count: "+colCount, "Number of Columns");
                String[] tabColumns = new String[(colCount+1)];
                  
                for (int i = 1; i <=colCount; i++ ) {
                     tabColumns[i] = meta.getColumnName(i);
                    // PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Returned column name: "+ tabColumns[i]);
                }
                       
                     
                while (results.next()) {
                          ++resultCount;
                          HashMap<String,String> tempResultList = new HashMap<String, String>();
                        for (int i = 1; i <= colCount; i++) {                         
                             tempResultList.put(tabColumns[i], String.valueOf(results.getObject(i)));
                      if(!tabColumns[i].equalsIgnoreCase("pan"))  PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Column name: "+tabColumns[i]+"; Value: "+String.valueOf(results.getObject(i)));
                      else  PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, "Column name: "+tabColumns[i]+"; Value: "+maskPan(String.valueOf(results.getObject(i))));
                        }
                    resultData.add(tempResultList);
                      
                }
           PbfGenerationController.pbfLogger.log(java.util.logging.Level.INFO, String.valueOf(resultCount)+" rows affected.");
        } catch (Exception e) {
            
               PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
               if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error executing SQL query");
                if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error executing SQL query");
                        PbfGenerationController.pbfErrorList.append(e.getMessage());
                        PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                        PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
                 
        }
        return isSuccessful;

    }
     
     public  String getServer (){
         return this.server;
     
     }
          public  String getDatabase (){
         return this.database;
     
     }
          public String getUser(){
              return this.user;
          }
     
     
        public static ArrayList<HashMap> getPbfData(String startDate, String endDate ) {
        Object[] resultObjects =null;
        ArrayList<String> tableColumns = new ArrayList<String>();
        tableColumns.add("retrieval_reference_nr");
        tableColumns.add("CASE WHEN dbo.usf_decrypt_pan(pan,pan_encrypted) !=\'0\' THEN dbo.usf_decrypt_pan(pan,pan_encrypted)  ELSE PAN END pan");
        tableColumns.add("datetime_req");
        tableColumns.add("c.card_acceptor_id_code");
        tableColumns.add("c.terminal_id");
        tableColumns.add("c.terminal_id");
        tableColumns.add("expiry_date");
        tableColumns.add("dbo.formatAmount(tran_amount_req, tran_currency_code) tran_amount_req");  
        String queryFields = PbfGenerationController.implode(",",tableColumns);
        String  query ="SELECT  " + queryFields + " FROM  post_tran t WITH (NOLOCK, INDEX(ix_post_tran_7))  JOIN "+
                    " (SELECT [date] recon_business_date FROM [get_dates_in_range](\'"+startDate+"\',\'"+endDate+"\'))r ON "+
                    " r.recon_business_date = CONVERT(DATE,t.datetime_req) AND tran_postilion_originated = 0  AND message_type IN (\'0200\',\'0220\') AND tran_completed = 1 AND tran_reversed = 0  and rsp_code_rsp = '00' AND tran_type ='00' JOIN POST_TRAN_CUST c  WITH (NOLOCK, INDEX(pk_post_tran_cust))  ON "+
                    " t.post_tran_cust_id = c.post_tran_cust_id  AND LEFT(terminal_id,1) = \'2\' JOIN tbl_reward_outOfBand ob WITH (NOLOCK) ON c.terminal_id =ob.terminal_id AND ob.is_kimono_term = \'N\' OPTION(RECOMPILE,MAXDOP 6)";
        try {

            if (execute(query)) {
                  return resultData;

            }else{
              if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage("PBF data could not be fetched", "Error Fetching Data");
            
            }
        } catch (Exception e) {
               PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
                if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage(PbfGenerationController.getErrorMessage(e), "Error executing SQL query");
                        PbfGenerationController.pbfErrorList.append(e.getMessage());
                        PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                        PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
        }
        return resultData;
    }
 public static ArrayList<HashMap> getPbfData(File sqlFile ) {
        
        Object[] resultObjects =null;

        try {
                Scanner queryScanner = new Scanner(sqlFile);
                StringBuilder queryFieldBuilder =  new StringBuilder();

                while (queryScanner.hasNextLine()){
                       queryFieldBuilder.append(queryScanner.nextLine());
               }
 
        String query = queryFieldBuilder.toString();
        queryFieldBuilder.delete(0, queryFieldBuilder.length());
      
            if (execute(query)) {
                
                ResultSetMetaData meta = results.getMetaData();  // for a valid resultset object after executing query
                int colCount = meta.getColumnCount();
                String[] tableColumns = new String[colCount];
                for (int i = 0; i < colCount; i++ ) {
                     
                     tableColumns[i] = meta.getColumnName(i);
                     if(tableColumns[i].equalsIgnoreCase("retrieval_reference_nr")  ||tableColumns[i].equalsIgnoreCase("pan")
                          ||tableColumns[i].equalsIgnoreCase("datetime_req") ||tableColumns[i].equalsIgnoreCase("card_acceptor_id_code")
                          ||tableColumns[i].equalsIgnoreCase("terminal_id")         ||tableColumns[i].equalsIgnoreCase("tran_amount_req")
                          ||tableColumns[i].equalsIgnoreCase("expiry_date")
                        )
                     {
                         tableColumns[i] =tableColumns[i].toLowerCase();
                     }else{
                        tableColumns[i] ="";
                     }
                } 
                    if(tableColumns.length == 7){
//                    HashMap<String,String> tempResultList = new HashMap<String, String>();
//                    while (results.next()) {
//                    tempResultList.clear();       
//                    for (int i = 1; i <= colCount; i++) {
//                        resultObjects[i] = results.getObject(i);
//                        tempResultList.put(  tableColumns[i], String.valueOf(resultObjects[i]));
//                    }
                return    resultData;
                    }
                }else{
                if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage("The results returned from query file do not contain the required fields:\n1. retrieval_reference_nr,\n2. pan,\n3. datetime_req\n4. card_acceptor_id_code\n5. terminal_id\n6. tran_amount_req\n7. expiry_date", "Invalid Columns Returned");
                                         PbfGenerationController.pbfErrorList.append("\nThe results returned from query file do not contain the required columns:\n1. retrieval_reference_nr,\n2. pan,\n3. datetime_req\n4. card_acceptor_id_code\n5. terminal_id\n6. tran_amount_req\n7. expiry_date");
                        PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
            
            }
            
        } catch (Exception e) {
               PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
                if(PbfGenerationController.baseFrame.isVisible())PbfGenerationController.baseFrame.showErrorMessage("PBF data could not be fetched from query file", "Error Fetching Data from query file");
                        PbfGenerationController.pbfErrorList.append(e.getMessage());
                        PbfGenerationController.pbfErrorList.append("<tr><td>"+PbfGenerationController.getErrorMessage(e)+"</td></tr>");
                        PbfGenerationController.pbfErrorList.append("<tr><td></td></tr>");
        }
        return resultData;
    }
 
    public static void closeConnection() {

        if (results != null) {
            try {
                results.close();
            } catch (Exception e) {
                PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
            }
            results = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                      PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
            }
            stmt = null;

        }
        if (prepStmt != null) {
            try {
                prepStmt.close();
            } catch (Exception e) {
		  PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
            } // ignore
            prepStmt = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
              PbfGenerationController.pbfLogger.log(java.util.logging.Level.SEVERE, PbfGenerationController.getErrorMessage(e));
            } // ignore
            conn = null;
        }
            }

  @SuppressWarnings("empty-statement")
    public static void init() {
        getConnection(server, database, user, password);
    }

}
