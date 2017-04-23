package ma.aiaexpert.nat.translate.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
