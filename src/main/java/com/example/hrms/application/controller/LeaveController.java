package com.example.hrms.application.controller;

import com.example.hrms.application.domain.Job;
import com.example.hrms.application.domain.LeaveRequest;
import com.example.hrms.application.service.LeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
@Api(value = "LeaveRequest API", description = "Used to create and get leave request information")
@Slf4j
public class LeaveController {

    private final LeaveService leaveService;

    @Autowired
    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }


    @ApiOperation(value = "Create a LeaveRequest", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "LeaveRequest created successfully"),
            @ApiResponse(code = 400, message = "Invalid input provided"),
            @ApiResponse(code = 500, message = "Internal server error occurred")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createLeaveRequest(@RequestBody @Valid LeaveRequest leaveRequest) {
        log.info("Creating employee {}", leaveRequest);
        leaveService.createLeaveRequest(leaveRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @ApiOperation(value = "Get a LeaveRequest by LeaveRequest id", response = LeaveRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the LeaveRequest"),
            @ApiResponse(code = 400, message = "Invalid input provided"),
            @ApiResponse(code = 404, message = "LeaveRequest not found"),
            @ApiResponse(code = 500, message = "Internal server error occurred")
    })
    @GetMapping(path = "/{leaveRequestId}")
    public LeaveRequest getLeaveRequest(@PathVariable String leaveRequestId) {
        log.info("Retrieving leaveRequest for id {}", leaveRequestId);
        return this.leaveService.getLeaveRequest(leaveRequestId);
    }


    @ApiOperation(value = "Get all Leave Request", response = LeaveRequest.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all LeaveRequest"),
            @ApiResponse(code = 500, message = "Internal server error occurred")
    })
    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        log.info("retrieving all leave requests");
        return this.leaveService.getAllLeaveRequests();
    }

    @ApiOperation(value = "Update a Leave by Leave id", response = LeaveRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the Leave details"),
            @ApiResponse(code = 400, message = "Invalid input provided"),
            @ApiResponse(code = 404, message = "Leave not found"),
            @ApiResponse(code = 500, message = "Internal server error occurred")
    })
    @PatchMapping(path = "/{leaveRequestId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LeaveRequest updateLeave(@PathVariable String leaveRequestId, @RequestBody Map<String, Object> updates) {
        log.info("Updating job details for id: {}" + leaveRequestId);
        return leaveService.updateLeave(leaveRequestId, updates);

    }

    @ApiOperation(value = "Delete a Leave")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted an Job"),
            @ApiResponse(code = 404, message = "The Job you were trying to delete is not found")
    })
    @DeleteMapping(path = "/{leaveRequestId}")
    public void deleteLeave(@PathVariable String leaveRequestId) {
        log.info("Deleting Employee with id: {}", leaveRequestId);
        leaveService.deleteLeave(leaveRequestId);
    }

}