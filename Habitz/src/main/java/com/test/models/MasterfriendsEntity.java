package com.test.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by adamm on 3/13/2017.
 */
@Entity
@Table(name = "masterfriends", schema = "Habitz", catalog = "")
public class MasterfriendsEntity {
    private String userId;
    private String friendId;

    @Basic
    @Column(name = "userID", nullable = false, length = 5)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "friendID", nullable = false, length = 45)
    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MasterfriendsEntity that = (MasterfriendsEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (friendId != null ? !friendId.equals(that.friendId) : that.friendId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (friendId != null ? friendId.hashCode() : 0);
        return result;
    }
}
