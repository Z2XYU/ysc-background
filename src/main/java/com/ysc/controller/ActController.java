package com.ysc.controller;

import com.ysc.pojo.Act;
import com.ysc.pojo.HanFu;
import com.ysc.pojo.Result;
import com.ysc.service.ActService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/act")
@Slf4j
@CrossOrigin
public class ActController {
    @Autowired
    private ActService actService;
    @GetMapping
    public Result ActList(){
        List<Act> list=actService.showAllActList();
        return Result.success(list);
    }


    @GetMapping("/{actId}")
    public Result getActById(@PathVariable int actId){
        Act act=actService.showActDetail(actId);
        return Result.success(act);
    }

}
