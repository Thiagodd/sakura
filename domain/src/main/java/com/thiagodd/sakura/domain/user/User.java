package com.thiagodd.sakura.domain.user;

import com.thiagodd.sakura.domain.AggregateRoot;
import com.thiagodd.sakura.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class User extends AggregateRoot<UserID> implements Cloneable {

    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String imageUrl;
    private boolean active;
    private String authorities;

    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private User(
        final UserID userID,
        final String firstName,
        final String lastName,
        final String login,
        final String email,
        final String imageUrl,
        final boolean isActive,
        final String authorities,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt
    ) {
        super(userID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.imageUrl = imageUrl;
        this.active = isActive;
        this.authorities = authorities;
        this.createdAt = Objects.requireNonNull(createdAt, "'created_at' should not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "'updated_at' should not be null");
        this.deletedAt = deletedAt;
    }

    public static User newUser(
        final String firstName,
        final String lastName,
        final String login,
        final String email,
        final String imageUrl,
        final boolean isActive,
        final String authorities
    ){
        final var id = UserID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;

        return new User(
            id,
            firstName,
            lastName,
            login,
            email,
            imageUrl,
            isActive,
            authorities,
            now,
            now,
            deletedAt
        );
    }

    public static User with(
        final UserID id,
        final String firstName,
        final String lastName,
        final String login,
        final String email,
        final String imageUrl,
        final boolean isActive,
        final String authorities,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt
    ){
        return new User(
            id,
            firstName,
            lastName,
            login,
            email,
            imageUrl,
            isActive,
            authorities,
            createdAt,
            updatedAt,
            deletedAt
        );
    }



    public static User with(final User user){
        return with(
            user.id,
            user.firstName,
            user.lastName,
            user.login,
            user.email,
            user.imageUrl,
            user.active,
            user.authorities,
            user.createdAt,
            user.updatedAt,
            user.deletedAt
        );
    }

    @Override
    public void validate(ValidationHandler handler){
        new UserValidator(this, handler).validate();
    }

    public User activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();

        return this;
    }

    public User deactivate() {
        if (getDeletedAt() == null) this.deletedAt = Instant.now();
        this.active = false;
        this.updatedAt = Instant.now();

        return this;
    }


    public User update(
        final String firstName,
        final String lastName,
        final String login,
        final String email,
        final String imageUrl,
        final boolean isActive,
        final String authorities
    ){
        if (isActive){
            activate();
        }else {
            deactivate();
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.imageUrl = imageUrl;
        this.authorities = authorities;

        this.updatedAt = Instant.now();
        return this;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public String getAuthorities() {
        return authorities;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    protected User clone() {
        try{
            return (User) super.clone();
        }catch (CloneNotSupportedException exception){
            throw new AssertionError();
        }
    }


}
