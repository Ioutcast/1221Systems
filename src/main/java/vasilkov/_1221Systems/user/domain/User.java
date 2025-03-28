package vasilkov._1221Systems.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import vasilkov._1221Systems.user.domain.enums.Gender;
import vasilkov._1221Systems.user.domain.enums.GoalType;

import java.util.Objects;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @Min(1)
    @Max(120)
    private int age;

    @Min(30)
    @Max(300)
    private double weight;

    @Min(100)
    @Max(250)
    private double height;

    @Enumerated(EnumType.STRING)
    private GoalType goal;

    private double dailyCalorieNorm;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) object;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}