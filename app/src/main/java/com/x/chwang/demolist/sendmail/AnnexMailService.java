package com.x.chwang.demolist.sendmail;


import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * Created by wangchaohu on 2017/2/17.
 *
 * fun：发送邮件的主类
 */

public class AnnexMailService {


    public static void sendEmail(String host, String address, String from, String password, String to, String port, String subject, String content){

           try {

               Multipart multiPart;
               String finalString = "";

               Properties props = new Properties();
               props.put("mail.smtp.starttls.enable", "true");
               props.put("mail.smtp.host", host);
               props.put("mail.smtp.user", address);
               props.put("mail.smtp.password", password);
               props.put("mail.smtp.port", port);
               props.put("mail.smtp.auth", "true");

               Session session = Session.getDefaultInstance(props, null);
               DataHandler handler = new DataHandler(new ByteArrayDataSource(finalString.getBytes(), "text/plain"));
               MimeMessage message = new MimeMessage(session);
               message.setFrom(new InternetAddress(from));
               message.setDataHandler(handler);


               multiPart = new MimeMultipart();
               InternetAddress toAddress;
               toAddress = new InternetAddress(to);
               message.addRecipient(Message.RecipientType.TO, toAddress);

               message.setSubject(subject);
               message.setContent(multiPart);
               message.setText(content);

               MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
               mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
               mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
               mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
               mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
               mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
               CommandMap.setDefaultCommandMap(mc);

               Transport transport = session.getTransport("smtp");
               transport.connect(host, address, password);
               transport.sendMessage(message, message.getAllRecipients());
               transport.close();
           }catch (Exception e){
               e.printStackTrace();
           }

        }
    }
