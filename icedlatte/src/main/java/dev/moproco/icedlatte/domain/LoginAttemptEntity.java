package dev.moproco.icedlatte.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "login_attempt_entity")
public class LoginAttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @Column(name = "attempts")
    private Integer attempts;

    @Column(name = "expiration_datetime")
    private LocalDateTime expirationDatetime;

    @NotNull
    @Column(name = "is_user_locked")
    private Boolean isUserLocked;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getAttempts() {
        return this.attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public LocalDateTime getExpirationDatetime() {
        return this.expirationDatetime;
    }

    public void setExpirationDatetime(LocalDateTime expirationDatetime) {
        this.expirationDatetime = expirationDatetime;
    }

    public Boolean getIsUserLocked() {
        return this.isUserLocked;
    }

    public void setIsUserLocked(Boolean isUserLocked) {
        this.isUserLocked = isUserLocked;
    }

    public LocalDateTime getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

}
