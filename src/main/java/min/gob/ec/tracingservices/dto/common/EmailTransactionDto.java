package min.gob.ec.tracingservices.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailTransactionDto {
    private String institution;
    private String system;
    private String from;
    private String to;
    private String subject;
    private String message;
    private String cco;
    private List<EmailAttachmentDto> attachmentList;
    private boolean includeTemplate;
}
