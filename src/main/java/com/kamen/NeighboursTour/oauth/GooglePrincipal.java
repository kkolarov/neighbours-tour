package com.kamen.NeighboursTour.oauth;

import java.math.BigInteger;
import java.util.Objects;

public class GooglePrincipal {
    private final BigInteger id;

    public GooglePrincipal(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GooglePrincipal that = (GooglePrincipal) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GooglePrincipal{" +
                "id=" + id +
                '}';
    }
}