
package org.onebeartoe.quatro.engrave;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 
 * This class send messages to the physical Neje laser engraver via the command line 
 * application from the EzGraver project:
 * 
 *      https://github.com/camrein/EzGraver/tree/master/EzGraverCli
 * 
 */
public class NejeEngraver implements Serializable
{
    private String cliExecutable = "--cli-executable-is-not-set-";
//    private String cliExecutable = "/opt/ez-graver/EzGraverCli/EzGraverCli";
//    private String cliExecutable = "/home/roberto/Versioning/group/github/camrein/EzGraver-unix_cli_fixes/EzGraverCli/EzGraverCli";
    

    private final String serialPort = "/dev/ttyUSB0";

    private transient Logger logger;

    public String getCliExecutable()
    {
        return cliExecutable;
    }

    public void setCliExecutable(String cliExecutable)
    {
        this.cliExecutable = cliExecutable;
    }
    
    
    
    public Logger getLogger()
    {
        return logger;
    }

    public void setLogger(Logger logger)
    {
        this.logger = logger;
    }

    public NejeEngraver()
    {
        logger = Logger.getLogger( getClass().getName() );
    }
    
    public void reset() throws IOException
    {        
        char command = 'r';
        
        sendEngraverCommand(command);
    }
    
    /**
     * 
     * The final form of the command could be something like the following:
     * 
     *      EzGraverCli/EzGraverCli s /dev/ttyUSB0 14
     * 
     * The 14 is the burn time and /dev/ttyUSB is the label for the engravers serial port
     * 
     * @param commandChar
     * @param additionalArguments
     * @throws IOException 
     */
    private void sendEngraverCommand(char commandChar, String ... additionalArguments) throws IOException
    {
        List<String> command = new ArrayList();
        command.add(cliExecutable);
        command.add( String.valueOf(commandChar) );
        command.add(serialPort);
        
        String debugList = "";
        for(String s : command)
        {
            
            debugList += s + " ";
        }
        
        for(String arg : additionalArguments)
        {
            command.add(arg);
            
            debugList += arg;
        }
        
        logger.info("command list: >" + debugList + "<");        
        
        logger.info("staring process for the engraver commmand");
        ProcessBuilder builder = new ProcessBuilder(command);

        builder.start();

        logger.info("process started");
    }
    
    public void startEngraving(String burnTime) throws IOException
    {
        char command = 's';
        
        sendEngraverCommand(command, burnTime);
    }    

    public void uploadImage(File imageUpload) throws IOException
    {
        char command = 'u';
        
        String imagePath = imageUpload.getAbsolutePath();
        
        sendEngraverCommand(command, imagePath);
    }  

    public void pause() throws IOException
    {
        char command = 'p';
        
        sendEngraverCommand(command);
    }
}
