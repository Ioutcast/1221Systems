package vasilkov._1221Systems.user;

public interface UserService<T, R> {
    R createUser(T user);
}
