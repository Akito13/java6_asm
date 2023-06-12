package sof3021.ca4.nhom1.asm.qls.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class MailInfo {
    String to;
    String[] cc;
    String[] bcc;
    String subject;
    String body;

    public MailInfo(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}