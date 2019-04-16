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

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class PbfLogger {
    
    
    String APP_LOG_FILE= "log/pbf_file_generation.log";
    private static  Logger logger;
    private static  SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd, MMM,yyyy");
    private static File pbfLogDirectory;
    public static  PbfLogger unique_instance;
    private static Object lock = new Object();
    public static   Handler fileHandler;
    
       private PbfLogger() {
            new PbfLogger(new File(APP_LOG_FILE));
       }

       public static PbfLogger getInstance(String className){

        if (unique_instance == null)

     synchronized(lock){

        if (unique_instance == null){
            unique_instance = new PbfLogger(className);
      
             }
         }

        return unique_instance;

    }
    private PbfLogger(File logFile) {
       APP_LOG_FILE= logFile.getAbsolutePath();
            try {
                logger = Logger.getLogger(PbfLogger.class.getName());
                    pbfLogDirectory = new File("log");
                    pbfLogDirectory.mkdir();
                    logger.setLevel(Level.INFO);
                    Formatter formatter = new Formatter() {
                            @Override     
                            public String format(LogRecord logRecord) {
                                StringBuilder b = new StringBuilder();
                                        b.append("|")
                                                .append(dateFormat.format(new Date(System.currentTimeMillis())));
                                        b.append(" | ");
                                        b.append(logger.getName());
                                        b.append(" | ");
                                        b.append(logRecord.getSourceClassName());
                                        b.append(" | ");
                                        b.append(logRecord.getSourceMethodName());
                                        if (logRecord.getLevel()!=Level.SEVERE && logRecord.getLevel()!=Level.WARNING )b.append(" | ");
                                        else b.append(" |");
                                        b.append(logRecord.getLevel());

                                        if (logger.getParent() ==null && logRecord.getLevel()!=Level.SEVERE && logRecord.getLevel()!=Level.WARNING  )b.append("\t|> ");
                                        else if (logger.getParent() !=null && logRecord.getLevel()!=Level.SEVERE && logRecord.getLevel()!=Level.WARNING  ){
                                            b.append("|> ");
                                        }
                                        else b.append("|> ");
                                        b.append(logRecord.getMessage());
                                        b.append(System.getProperty("line.separator"));
                                        return b.toString();			   }
                                };
   fileHandler  = new FileHandler(logFile.getAbsolutePath());
                                fileHandler.setFormatter(formatter);
                                logger.addHandler(fileHandler);
                                LogManager lm = LogManager.getLogManager();
                                lm.addLogger(logger);
                        }
            catch (Throwable e) {
                    this.log(Level.SEVERE, e.getMessage());
            }
    }

   private PbfLogger(String className) {
            try {
                    logger = Logger.getLogger(className);
                    pbfLogDirectory = new File("log");
                    pbfLogDirectory.mkdir();
                    logger.setLevel(Level.INFO);
                    Formatter formatter = new Formatter() {
                            @Override     public String format(LogRecord logRecord) {
                                    StringBuilder b = new StringBuilder();
                            b.append("|")
                                    .append(dateFormat.format(new Date(System.currentTimeMillis())));
                            b.append(" | ");
                            b.append(logger.getName());
                            b.append(" | ");
                            b.append(logRecord.getSourceClassName());
                            b.append(" | ");
                            b.append(logRecord.getSourceMethodName());
                            if (logRecord.getLevel()!=Level.SEVERE && logRecord.getLevel()!=Level.WARNING )b.append(" | ");
                            else b.append(" |");
                            b.append(logRecord.getLevel());

                            if (logger.getParent() ==null && logRecord.getLevel()!=Level.SEVERE && logRecord.getLevel()!=Level.WARNING  )b.append("\t|> ");
                            else if (logger.getParent() !=null && logRecord.getLevel()!=Level.SEVERE && logRecord.getLevel()!=Level.WARNING  ){
                                b.append("|> ");
                            }
                            else b.append("|> ");
                            b.append(logRecord.getMessage());
                            b.append(System.getProperty("line.separator"));
                            return b.toString();			   }
                    };

                    Handler fh = new FileHandler(new File(APP_LOG_FILE).getAbsolutePath(),true);
                    fh.setFormatter(formatter);
                    logger.addHandler(fh);
                    LogManager lm = LogManager.getLogManager();
                    lm.addLogger(logger);
            }
            catch (Throwable e) {
                    this.log(Level.SEVERE, e.getMessage());
            }
    }

        public   void log(Level level, String message) {
                logger.log(level, message);
        }

       public   void setLevel(Level level) {
           logger.setLevel(level);
        }

         public  Logger getLogger() {
           return logger;
        }

        public void setParent(Logger logger) {
                   PbfLogger.logger.setParent(logger);
        }
    
}
