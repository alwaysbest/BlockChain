package cn.edu.nju.software.ui.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Daniel
 * @since 2018/5/13 0:10
 */
@Entity
@Table(name = "chain_user", schema = "tracing", catalog = "")
public class UserEntity {
    private int id;
    private String username;
    private String password;
    private byte[] enrollKey;
    private String enrollCert;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "enroll_key", nullable = true)
    public byte[] getEnrollKey() {
        return enrollKey;
    }

    public void setEnrollKey(byte[] enrollKey) {
        this.enrollKey = enrollKey;
    }

    @Basic
    @Column(name = "enroll_cert", nullable = true, length = 1024)
    public String getEnrollCert() {
        return enrollCert;
    }

    public void setEnrollCert(String enrollCert) {
        this.enrollCert = enrollCert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Arrays.equals(enrollKey, that.enrollKey) &&
                Objects.equals(enrollCert, that.enrollCert);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, username, password, enrollCert);
        result = 31 * result + Arrays.hashCode(enrollKey);
        return result;
    }
}
