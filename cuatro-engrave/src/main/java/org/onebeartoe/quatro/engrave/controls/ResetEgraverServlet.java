
package org.onebeartoe.quatro.engrave.controls;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.onebeartoe.quatro.engrave.ApplicationProfile;
import org.onebeartoe.quatro.engrave.NejeEngraver;
import static org.onebeartoe.quatro.engrave.controls.StartEngraverServlet.APPLICTION_PROFILE_CONTEXT_KEY;

/**
 * @author Roberto Marquez
 */
@WebServlet(urlPatterns = {"/controls/reset/*"})
public class ResetEgraverServlet extends HttpServlet
{
    protected Logger logger;
    
    private ApplicationProfile applicationProfile;
    
    public ResetEgraverServlet()
    {
        logger = Logger.getLogger(getClass().getName());
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException            
    {
        String pi = request.getPathInfo();
//        String animationName = pi.substring(1); // remove the slash character

        logger.log(Level.INFO, "sending initial data to user..");
        StringBuilder sb = new StringBuilder("request recieved");

        try 
        {
            applicationProfile.getEngraver().reset();
        } 
        catch (Exception ex) 
        {
            String message = "Error: " + ex.getMessage();
            sb.append("<br/><br/>" + message);
            logger.log(Level.SEVERE, message, ex);
        }

        String message = "request processed";
        logger.log(Level.INFO, message);

        String r = "<br/><br/>" + message;

        sb.append(r);

        OutputStream os = response.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        
        pw.print( sb.toString() );
        pw.flush();
        pw.close();
    }
    
    @Override
    public void init() throws ServletException
    {
        super.init();
        
        logger = Logger.getLogger(getClass().getName());
        
        ServletContext servletContext = getServletContext();
        
        applicationProfile = (ApplicationProfile) servletContext.getAttribute(APPLICTION_PROFILE_CONTEXT_KEY);
    }   
}