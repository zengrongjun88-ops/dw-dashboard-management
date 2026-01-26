package com.dw.dashboard.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@ApiModel(description = "用户创建请求")
public class UserCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

}
