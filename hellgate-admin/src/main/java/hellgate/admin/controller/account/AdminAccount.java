package hellgate.admin.controller.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hellgate.common.util.Roles;
import hellgate.common.domain.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;

@Setter
@Getter
@ToString(callSuper = true)
@SQLDelete(sql = "update admin_account set deleted = true where id = ?")
@Entity
public class AdminAccount extends BaseAuditableEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    private boolean disabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // todo role entity and role_group entity
        return AuthorityUtils.createAuthorityList(Roles.ADMIN);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
