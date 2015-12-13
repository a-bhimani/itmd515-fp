package edu.iit.sat.itmd4515.abhimani.fp.web.cdi;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
@Dependent
public class BeanEmailer{
    @Resource(lookup = "mail/iit_events_d515")
    private Session mailSession;

    @SuppressWarnings("empty-statement")
    public void sendMail(String toAddress, String subject, String message){
	try{
	    MimeMessage msg=new MimeMessage(mailSession);
	    msg.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
	    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
	    msg.setSubject(subject);
	    msg.setText(message);
	    msg.setSentDate(new Date());
	    Transport.send(msg);
	}catch(Exception ex){
	    Logger.getLogger(BeanEmailer.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}
