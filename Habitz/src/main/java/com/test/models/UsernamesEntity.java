package com.test.models;

import javax.persistence.*;

/**
 * Created by adamm on 3/13/2017.
 */
@Entity
@Table(name = "usernames", schema = "Habitz", catalog = "")
public class UsernamesEntity {
    private String userid;
    private String fullname;
    private String email;
    private String points;

    @Id
    @Column(name = "userid", nullable = false, length = 5)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    @Basic
    @Column(name = "fullname", nullable = true, length = 40)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "points", nullable = true, length = 30)
    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsernamesEntity that = (UsernamesEntity) o;

        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (fullname != null ? !fullname.equals(that.fullname) : that.fullname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (points != null ? !points.equals(that.points) : that.points != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result =userid != null ? userid.hashCode() : 0;
         result =  31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (points != null ? points.hashCode() : 0);

        return result;
    }
}
