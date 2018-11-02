package com.calvin.bookstoreuser.mapper;

import com.calvin.bookstorebasis.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * From USER WHERE USERNAME = #{username}")
    User findUserByUsername(@Param("username") String username);

    @Select("SELECT ADDRESS From USER_ADDRESS WHERE USERID = #{userId}")
    List<String> findAddressByUserId(@Param("userId") int userId);

    @Insert("INSERT INTO USER(USERNAME, PASSWORD, GENDER, EMAIL,TELEPHONE) VALUES(#{user.username}," +
            "#{user.password}, #{user.gender}, #{user.email}, #{user.telephone})")
    void addUser(@Param("user") User user);

    @Insert("INSERT INTO USER_ADDRESS(USERID, ADDRESS) VALUES(#{userId}, #{address})")
    boolean addAddress(@Param("userId") int userId, @Param("address") String address);

    @Delete("DELETE FROM USER WHERE USERNAME = #{username}")
    boolean deleteUser(@Param("username") String username);

    @Update("UPDATE USER SET PASSWORD = #{password} WHERE USERNAME = #{username}")
    boolean updatePassword(@Param("username") String username, @Param("password") String password);

    @Update("UPDATE USER_ADDRESS SET ADDRESS = #{address} WHERE ADDRID = #{addrId} AND USERID = #{userId}")
    boolean updateAddress(@Param("address") String address, @Param("addrId") int addrId, @Param("userId") int
            userId);

    @Update("UPDATE USER SET TELEPHONE = #{telephone} WHERE USERNAME = #{username}")
    boolean updateTelephone(@Param("username") String username, @Param("telephone") String telephone);

}
