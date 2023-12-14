package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.*;
import com.flatfusion.backend.repositories.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

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


    @Autowired
    public CleaningTemplateRESTController(CleaningTemplateEntityRepository cleaningTemplateRepository) {
        super(cleaningTemplateRepository);
    }


    @GetMapping("/group/{id}")
    public ResponseEntity<List<CleaningTemplateEntity>> getCleaningTemplatesByGroupId(@PathVariable UUID id){
        logger.info("Get Cleaning Templates by group id:  " + id);
        Optional<List<CleaningTemplateEntity>> entities = cleaningTemplateRepository.findByGroupId(id);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }


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
        List<CleaningEntity> newCleanings = createCleaningEntities(groupUsers, group, cleaningTemplate, startDate, endDate, interval);

        cleaningRepository.saveAll(newCleanings);
        logger.info("CleaningTemplate and cleanings created: " + cleaningTemplate);
        System.out.println("CleaningTemplate and cleanings created: " + cleaningTemplate);

        return new ResponseEntity<>(cleaningTemplate, HttpStatus.CREATED);
    }

    private List<CleaningEntity> createCleaningEntities(List<UserEntity> groupUsers, GroupEntity group, CleaningTemplateEntity cleaningTemplate, Date startDate, Date endDate, Integer interval){
        UserEntity cleaningUser;
        List<CleaningEntity> newCleanings = new ArrayList<>();
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

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<CleaningTemplateEntity> delete(@PathVariable UUID id) {
        Optional<CleaningTemplateEntity> existingEntity = cleaningTemplateRepository.findById(id);

        if (existingEntity.isPresent()) {
            // delete all cleanings
            cleaningRepository.deleteAll(existingEntity.get().getCleanings());
            // delete cleaning template
            repository.deleteById(id);
            logger.info("CleaningTemplate and Cleanings deleted: " + existingEntity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
