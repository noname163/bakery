package com.home.bakery.data.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.home.bakery.data.constans.OtpStatus;
import com.home.bakery.data.constans.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Table(name = "Users")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_sequence")
    private long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password", unique = false)
    private String password;
    @Column(name = "otp", unique = false)
    private String otp;
    @Column(name = "otp_status", unique = false)
    private OtpStatus otpStatus;
    @Column(name = "role", unique = false)
    private UserRole role;
    @Column(name = "status")
    private boolean status;
    @Column(name = "created_date")
    @Default
    private LocalDate createdDate = LocalDate.now();
    @Column(name = "updated_date")
    @Default
    private LocalDate updatedDate = LocalDate.now();
    @OneToOne
    private UserDetail userDetail;
    @OneToMany(mappedBy = "user")
    private List<Bill> bills;
}
