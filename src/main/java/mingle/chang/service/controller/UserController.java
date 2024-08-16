package mingle.chang.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import mingle.chang.service.dto.CreateUserDto;
import mingle.chang.service.dto.LoginUserDto;
import mingle.chang.service.response.Response;
import mingle.chang.service.service.UserService;
import mingle.chang.service.vo.LoginVo;
import mingle.chang.service.vo.UserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User")
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @Operation(summary = "登陆", description = "用户登陆接口")
    @PostMapping("login")
    @PermitAll
    public Response<LoginVo> login(@RequestBody LoginUserDto dto) {
        LoginVo result = this.userService.login(dto);
        return Response.success(result);
    }

    @Operation(summary = "创建用户", description = "用户创建接口")
    @PostMapping("create")
    public Response<Long> create(@RequestBody CreateUserDto dto) {
        Long result = this.userService.create(dto);
        return Response.success(result);
    }

    @Operation(summary = "用户信息", description = "用户信息接口")
    @PostMapping("info")
    public Response<UserVo> info() {
        UserVo result = this.userService.info();
        return Response.success(result);
    }
}
