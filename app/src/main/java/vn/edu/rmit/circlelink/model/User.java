package vn.edu.rmit.circlelink.model;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private int userId;
    private int roleId;      // 1: regular user, 2: admin
    private int membershipId;
    private String email;
    private String pwd;
    private String name;
    private String sex;
    private LocalDate birthDate;

    public User(int userId, int roleId, int membershipId, String email, String pwd, String name, String sex, LocalDate birthDate) {
        this.userId = userId;
        this.roleId = roleId;
        this.membershipId = membershipId;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.sex = sex;
        this.birthDate = birthDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleString() {
        if (roleId == 1) {
            return "Regular User";
        } if (roleId == 2) {
            return "Admin";
        }
        return "Role does not exist.";
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && roleId == user.roleId && membershipId == user.membershipId && Objects.equals(email, user.email) && Objects.equals(pwd, user.pwd) && Objects.equals(name, user.name) && Objects.equals(sex, user.sex) && Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId, membershipId, email, pwd, name, sex, birthDate);
    }
}
