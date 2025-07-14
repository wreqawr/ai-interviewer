package cn.minglg.interview.exception;

/**
 * @author kfzx-minglg
 * 账户状态异常
 */
public class AccountAbnormalException extends RuntimeException {
    public AccountAbnormalException(String message) {
        super(message);
    }
}
