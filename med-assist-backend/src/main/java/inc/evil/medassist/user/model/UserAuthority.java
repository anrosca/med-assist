package inc.evil.medassist.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inc.evil.medassist.common.entity.AbstractEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_authorities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder @Getter @Setter
public class UserAuthority extends AbstractEntity implements GrantedAuthority {

    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public UserAuthority(final Authority authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + authority.name();
    }
}
