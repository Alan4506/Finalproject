package dev.teamproject.meeting;

import dev.teamproject.apiResponse.GenericApiResponse;
import dev.teamproject.common.CommonTypes;
import dev.teamproject.exceptionHandler.UserNotFoundException;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles incoming requests related to meetings, providing API endpoints for managing meeting
 * records in the application.
 */
@RestController
@RequestMapping(path = "api/v1/meetings")
public class MeetingController {
  private final MeetingService meetingService;

  @Autowired
  public MeetingController(MeetingService meetingService) {
    this.meetingService = meetingService;
  }

  /**
   * Retrieves meetings for the specified recurrence type.
   *
   * @param recurrence the recurrence type of the meetings to find, (e.g., "daily", "weekly").
   * @return a list of meetings that match the specified recurrence type.
   */
  @GetMapping("/findByRecurrence")
  public List<Meeting> findByRecurrence(@RequestParam("recurrence") String recurrence) {

    //TODO: should check if valid param
    CommonTypes.Recurrence rec = CommonTypes.Recurrence.valueOf(recurrence);

    return meetingService.findByRecurrence(rec);
  }

  /**
   * Retrieves meetings for the specified status.
   *
   * @param status the status of the meetings to find, (e.g., "valid", "invalid").
   * @return a list of meetings that match the specified recurrence type.
   */
  @GetMapping("/findByStatus")
  public List<Meeting> findByStatus(@RequestParam("status") String status) {

    //TODO: should check if valid param
    String upperStatus = status;

    CommonTypes.MeetingStatus meetingStatus = CommonTypes.MeetingStatus.valueOf(upperStatus);
    return meetingService.findByStatus(meetingStatus);
  }

  /**
   * Retrieves meetings for the specified type.
   *
   * @param type the type of the meetings to find, (e.g., "group", "one-on-one").
   * @return a list of meetings that match the specified recurrence type.
   */
  @GetMapping("/findByType")
  public List<Meeting> findByType(@RequestParam("type") String type) {

    //TODO: shoud check if valid param
    CommonTypes.MeetingType meetingType = CommonTypes.MeetingType.valueOf(type);

    return meetingService.findByType(meetingType);
  }

  @GetMapping("/findById")
  public Meeting findByid(@RequestParam("id") int mid) {
    return meetingService.findById(mid);
  }

  @GetMapping("/get_all")
  public List<Meeting> findAll() {
    return meetingService.findAll();
  }

  @GetMapping("/findByOrganizer")
  public List<Meeting> findByOrganizer(@RequestBody User organizer) {
    return meetingService.findByOrganizer(organizer);
  }


  @DeleteMapping()
  public ResponseEntity<Void> deleteMeeting(@RequestParam("mid") int mid) {
    meetingService.deleteMeeting(mid);
    return ResponseEntity.noContent().build();

  }

  @PostMapping("/saveMeeting")
  public ResponseEntity<GenericApiResponse<String>> saveMeeting(
      @RequestBody MeetingDTO meetingDTO) {
    GenericApiResponse<String> response;
    meetingService.save(meetingDTO);
    response = new GenericApiResponse<>("Meeting saved successfully", null, true);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
