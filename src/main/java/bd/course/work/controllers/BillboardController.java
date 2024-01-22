package bd.course.work.controllers;

import bd.course.work.DTO.BillboardDTO;
import bd.course.work.entities.Hero;
import bd.course.work.services.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/billboard")
public class BillboardController {

    private final BillboardService billboardService;

    @Autowired
    public BillboardController(BillboardService billboardService) {
        this.billboardService = billboardService;
    }

    @PostMapping
    public ResponseEntity<Hero> completeQuest(@RequestBody BillboardDTO billboardDTO) {
        return billboardService.completeQuest(billboardDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
