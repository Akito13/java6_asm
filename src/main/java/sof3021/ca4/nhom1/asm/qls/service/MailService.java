package sof3021.ca4.nhom1.asm.qls.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sof3021.ca4.nhom1.asm.qls.model.MailInfo;

@Service
public class MailService implements Mailable{

    @Autowired
    private JavaMailSender sender;

    @Override
    public void send(MailInfo mail) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setTo(InternetAddress.parse(mail.getTo()));
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(),true);
        String[] cc = mail.getCc();
        if(cc != null && cc.length >0) {
            helper.setCc(cc);
        }
        String[] bcc = mail.getBcc();
        if(bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }
        sender.send(message);
    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
        this.send(new MailInfo(to, subject, body));
    }
}
