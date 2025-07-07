package com.kh.spring.member.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Setter 
//@Getter 
@NoArgsConstructor 
@AllArgsConstructor
@Data
@Builder
//@ToString 
//@EqualsAndHashCode
public class Member {
	
	private int userNo;
	private String userId;
	private String userPwd;
	private String userName;
	private String profileImg;
	private String email;
    private String birthday;
    private String gender;
    private String phone;
    private String address;
    private Date enrollDate;
    private Date modifyDate;
    private String status;
}
