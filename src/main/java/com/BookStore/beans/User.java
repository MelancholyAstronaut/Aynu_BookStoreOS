package com.BookStore.beans;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * user
 * @author 
 */
@Data
@Alias("user")
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String gender;

    private String email;

    private String telephone;

    private String introduce;

    private String activecode;

    private Integer state;

    private String role;

    private Date registtime;

    private static final long serialVersionUID = 1L;
}