package com.thiagodd.sakura.domain.user;

import com.thiagodd.sakura.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class UserID extends Identifier {

    private final String value;

    private UserID(final String value){
        Objects.requireNonNull(value, "'id' should not be null");

        this.value = value;
    }

    public static UserID unique(){
        return UserID.from(UUID.randomUUID());
    }

    public static UserID from(final String id){
        return new UserID(id);
    }

    public static UserID from(final UUID id){
        return UserID.from(id.toString());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var userID = (UserID) o;
        return Objects.equals(value, userID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
