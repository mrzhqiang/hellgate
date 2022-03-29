package hellgate.common.session;

import lombok.Data;

import java.io.Serializable;

/**
 * 会话详情。
 * <p>
 * 这个类来自 spring-session-sample-boot-findbyusername/src/main/java/sample/session/SessionDetails.java
 */
@Data
public class SessionDetails implements Serializable {

    private static final long serialVersionUID = 8850489178248613501L;

    private String ip;
    private String location;
    private String accessType;
}
