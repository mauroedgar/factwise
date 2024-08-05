package br.com.livecenografia.fact_wise2.rest;

import br.com.livecenografia.fact_wise2.model.MyRolesDTO;
import br.com.livecenografia.fact_wise2.service.MyRolesService;
import br.com.livecenografia.fact_wise2.util.ReferencedException;
import br.com.livecenografia.fact_wise2.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyRolesResource {

    private final MyRolesService myRolesService;

    public MyRolesResource(final MyRolesService myRolesService) {
        this.myRolesService = myRolesService;
    }

    @GetMapping("/myRoless")
    public ResponseEntity<List<MyRolesDTO>> getAllMyRoless() {
        return ResponseEntity.ok(myRolesService.findAll());
    }

    @GetMapping("/myRoless/{id}")
    public ResponseEntity<MyRolesDTO> getMyRoles(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(myRolesService.get(id));
    }

//    @PostMapping
//    @ApiResponse(responseCode = "201")
//    public ResponseEntity<Long> createMyRoles(@RequestBody @Valid final MyRolesDTO myRolesDTO) {
//        final Long createdId = myRolesService.create(myRolesDTO);
//        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
//    }

    @PostMapping("/myRoless")
    public ResponseEntity createRole(@RequestBody MyRolesDTO body){
        myRolesService.create(body);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/myRoless/{id}")
    public ResponseEntity<Long> updateMyRoles(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MyRolesDTO myRolesDTO) {
        myRolesService.update(id, myRolesDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/myRoless/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMyRoles(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = myRolesService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        myRolesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
