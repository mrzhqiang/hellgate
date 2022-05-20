package hellgate.admin.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Streams;
import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
@Getter
@ToString(callSuper = true)
@Entity
public class AdminUser extends AuditableEntity implements UserDetails {

    @Column(updatable = false, unique = true, nullable = false)
    private String username;
    @JsonIgnore
    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    private Instant accountExpired;
    private Instant locked;
    private Instant credentialsExpired;
    private boolean disabled;

    @ManyToMany
    @ToString.Exclude
    private List<AdminGroup> groups;
    @ManyToMany
    @ToString.Exclude
    private List<AdminRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (ObjectUtils.isEmpty(groups) && ObjectUtils.isEmpty(roles)) {
            return  AuthorityUtils.NO_AUTHORITIES;
        }

        Stream<AdminRole> roleStream = Stream.empty();
        if (!ObjectUtils.isEmpty(groups)) {
            roleStream = Streams.concat(roleStream, groups.stream()
                    .map(AdminGroup::getRoles)
                    .flatMap(Collection::stream));
        }
        if (!ObjectUtils.isEmpty(roles)) {
            roleStream = Streams.concat(roleStream, roles.stream());
        }
        return roleStream.map(AdminRole::getName)
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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
        return accountExpired == null || Instant.now().isAfter(accountExpired);
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked == null || Instant.now().isAfter(locked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpired == null || Instant.now().isAfter(credentialsExpired);
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
