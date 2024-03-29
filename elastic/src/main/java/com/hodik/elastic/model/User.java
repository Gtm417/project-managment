package com.hodik.elastic.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "users")
public class User {
    @Id
    @UniqueElements
    @Field(type = FieldType.Long)
    private long id;
    @Field(type = FieldType.Keyword)
    @UniqueElements
    @Email(regexp = ".+@.+\\..+|", message = "Provide correct email")
    private String email;

    @Field(type = FieldType.Keyword)
    @NotEmpty(message = "Enter the name")
    @Pattern(regexp = "[A-Z А-Я]\\\\w+", message = "Example : Misha")
    private String firstName;
    @Field(type = FieldType.Keyword)
    @NotEmpty(message = "Enter the last name")
    @Pattern(regexp = "[A-Z А-Я]\\\\w+", message = "Example : Misha")
    private String lastName;
    @Field(type = FieldType.Keyword)

    private Role role;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Keyword)

    private Status status;
    @Field(type = FieldType.Keyword)
    private UserType type;
    @Field(type = FieldType.Text)
    String cv;// probably just text (not sure yet)
    @Field(type = FieldType.Nested)
    private List<Skill> skills; //(nested indexable field)
}
