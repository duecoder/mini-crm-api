package br.com.gui.minicrm.v1.contacts.infra.adapter.gateway.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(
        name = "contacts",
        schema = "client",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_contacts_email", columnNames = "email")
        },
        indexes = {
                @Index(name = "idx_contacts_name", columnList = "name"),
                @Index(name = "idx_contacts_tags", columnList = "tags")
        }
)
public class ContactsEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 160, unique = true)
    private String email;

    @Column(length = 40)
    private String phone;

    @Column(length = 160)
    private String company;

    /**
     * Postgres text[]  -> Hibernate 6
     * Precisa do columnDefinition e do JdbcTypeCode ARRAY
     */
    @Column(name = "tags", columnDefinition = "text[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private String[] tags;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    // equals/hashCode só pelo ID (boa prática para entidades)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactsEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}