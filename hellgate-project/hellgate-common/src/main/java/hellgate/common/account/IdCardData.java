package hellgate.common.account;

import lombok.Data;

/**
 * 身份证数据。
 */
@Data
public class IdCardData {

    /**
     * 身份证号码。
     */
    private String number;
    /**
     * 身份证姓名。
     */
    private String fullName;
}
