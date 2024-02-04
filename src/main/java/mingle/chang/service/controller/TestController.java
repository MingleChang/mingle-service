package mingle.chang.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试")
@RestController
@RequestMapping("test")
public class TestController {
    @Operation(summary = "Test")
    @GetMapping("/")
    public String test(){
        return "Hello Word";
    }
}
