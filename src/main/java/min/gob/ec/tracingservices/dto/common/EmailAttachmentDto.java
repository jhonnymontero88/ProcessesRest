package min.gob.ec.tracingservices.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAttachmentDto {
    private byte[] attachment;
    private String fileName;
}
