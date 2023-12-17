package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.*;
import com.flatfusion.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

/**
 * REST Controller for managing {@link CleaningTemplateEntity} instances, including operations related to cleaning templates
 * and associated cleanings. Extends {@link RESTController} for generic CRUD operations.
 *
 * Base URL path: "/api/cleanings/templates"
 */
@RestController
@RequestMapping("/v1/cleaning-templates")
public class CleaningTemplateRESTController extends RESTController<CleaningTemplateEntity>{
    @Autowired
    CleaningTemplateEntityRepository cleaningTemplateRepository;
    @Autowired
    private CleaningEntityRepository cleaningRepository;
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private GroupEntityRepository groupRepository;

    /**
     * Constructor with autowired {@link CleaningTemplateEntityRepository}.
     *
     * @param cleaningTemplateRepository The autowired repository for {@link CleaningTemplateEntity}.
     */
    @Autowired
    public CleaningTemplateRESTController(CleaningTemplateEntityRepository cleaningTemplateRepository) {
        super(cleaningTemplateRepository);
    }

    /**
     * Get cleaning templates by group ID.
     *
     * @param id The ID of the group.
     * @return {@link ResponseEntity} containing a list of cleaning templates with HTTP status.
     *         If found, returns 200 OK; if not found, returns 404 NOT FOUND.
     */
    @GetMapping("/group/{id}")
    public ResponseEntity<List<CleaningTemplateEntity>> getCleaningTemplatesByGroupId(@PathVariable UUID id){
        logger.info("Get Cleaning Templates by group id:  " + id);
        Optional<List<CleaningTemplateEntity>> entities = cleaningTemplateRepository.findByGroupId(id);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

    /**
     * Create a cleaning template with associated cleanings.
     *
     * @param request The request body containing data for the new cleaning template.
     * @return {@link ResponseEntity} containing the created cleaning template with HTTP status.
     *         If created, returns 201 CREATED; if invalid or missing attributes, returns 422 UNPROCESSABLE ENTITY.
     */
    @Transactional
    @PostMapping("/create-with-cleanings")
    public ResponseEntity<CleaningTemplateEntity> createWithCleanings(@RequestBody CleaningTemplateEntity request) {
        UUID createdByUserId;
        UUID groupId;

        try{
            // TODO check against authentication credentials
            createdByUserId = request.getCreatedByUser().getId();
            groupId = request.getGroup().getId();
        } catch (NullPointerException e){
            logger.info("Missing attributes. CleaningTemplate not saved: " + request);
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Reload entity data as app user could have sent outdated group and user data.
        Optional<UserEntity> createdByUserOptional = userRepository.findById(createdByUserId);
        Optional<GroupEntity> groupOptional = groupRepository.findById(groupId);
        List<UserEntity> groupUsers = userRepository.findByGroupId(groupId);

        String name;
        String description;
        Date startDate;
        Date endDate;
        int interval;

        GroupEntity group;
        UserEntity createdByUser;
        CleaningTemplateEntity cleaningTemplate;


        // check if elements are existing in db and get current entities
        if (createdByUserOptional.isPresent() && groupOptional.isPresent() && !groupUsers.isEmpty()){
            // Load data for CleaningTemplate
            createdByUser = createdByUserOptional.get();
            group = groupOptional.get();

            // Initialize data for CleaningTemplate
            name = request.getName();
            description = request.getDescription();
            startDate = request.getStartDate();
            endDate = request.getEndDate();
            interval = request.getInterval();

            cleaningTemplate = new CleaningTemplateEntity(group, name, description, startDate, endDate, interval, createdByUser);

            cleaningTemplateRepository.save(cleaningTemplate);
        } else {
            logger.info("Missing attributes. CleaningTemplate not saved: " + request);
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        //create cleanings and save in dn
        Set<CleaningEntity> newCleanings = createCleaningEntities(groupUsers, group, cleaningTemplate, startDate, endDate, interval);

        cleaningRepository.saveAll(newCleanings);
        logger.info("CleaningTemplate and cleanings created: " + cleaningTemplate);
        System.out.println("CleaningTemplate and cleanings created: " + cleaningTemplate);

        return new ResponseEntity<>(cleaningTemplate, HttpStatus.CREATED);
    }

    /**
     * Helper method to create cleaning entities based on the cleaning template and associated data.
     *
     * @param groupUsers       The list of users in the group.
     * @param group            The group entity.
     * @param cleaningTemplate The cleaning template entity.
     * @param startDate        The start date for creating cleanings.
     * @param endDate          The end date for creating cleanings.
     * @param interval         The interval between cleanings.
     * @return A set of created cleaning entities.
     */
    private Set<CleaningEntity> createCleaningEntities(List<UserEntity> groupUsers, GroupEntity group, CleaningTemplateEntity cleaningTemplate, Date startDate, Date endDate, Integer interval){
        UserEntity cleaningUser;
        Set<CleaningEntity> newCleanings = new HashSet<>();
        LocalDate localEndDate = endDate.toLocalDate();
        LocalDate localCleaningDate = startDate.toLocalDate();

        //make order of the users random
        Collections.shuffle(groupUsers);
        ListIterator<UserEntity> groupUsersIterator = groupUsers.listIterator();

        int i = 1;

        System.out.println("Start of cleaning creation loop.");
        logger.debug("Start of cleaning creation loop.");

        // While cleaning date <= end date
        while (!localCleaningDate.isAfter(localEndDate)){
            if(groupUsersIterator.hasNext()){
                cleaningUser = groupUsersIterator.next();
            } else{
                // begin again with first user from list
                groupUsersIterator = groupUsers.listIterator();
                cleaningUser = groupUsersIterator.next();
            }

            CleaningEntity cleaning = new CleaningEntity(group, cleaningUser, Date.valueOf(localCleaningDate), cleaningTemplate);
            newCleanings.add(cleaning);

            logger.debug("This is the " + i + ". time of creating a cleaning. " + cleaning);
            System.out.println("This is the " + i + ". time of creating a cleaning. " + cleaning);

            // next cleaning date
            try{
                localCleaningDate = localCleaningDate.plusDays(interval);
            } catch (DateTimeException e){
                logger.info("Maximum Date exceeded. Stop creating cleaning. " + e);
                break;
            }

            i++;
            System.out.println("LocalCleaningDate=" + localCleaningDate + " : localEndDate=" + localEndDate);
        }

        return newCleanings;
    }
}
