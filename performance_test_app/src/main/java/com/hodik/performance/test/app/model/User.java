package com.hodik.performance.test.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "description")
    private String description;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_status")
    private Status status;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_type")
    private UserType type;
    @Column(name = "picture")
    private byte[] picture;
    @Column(name = "CV")
    private byte[] cv;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<SkillExpertise> skills = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                role == user.role &&
                Objects.equals(description, user.description) &&
                status == user.status &&
                type == user.type &&
                Arrays.equals(picture, user.picture) &&
                Arrays.equals(cv, user.cv);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, email, password, firstName, lastName, role, description, status, type);
        result = 31 * result + Arrays.hashCode(picture);
        result = 31 * result + Arrays.hashCode(cv);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("email", email)
                .append("password", password)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("role", role)
                .append("description", description)
                .append("status", status)
                .append("type", type)
                .append("picture", picture)
                .append("cv", cv)
                .toString();
    }
}
