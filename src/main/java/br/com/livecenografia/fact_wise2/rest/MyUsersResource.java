package br.com.livecenografia.fact_wise2.rest;

import br.com.livecenografia.fact_wise2.model.MyUsersDTO;
import br.com.livecenografia.fact_wise2.service.MyUsersService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/myUserss", produces = MediaType.APPLICATION_JSON_VALUE)
public class MyUsersResource {

    private final MyUsersService myUsersService;

    public MyUsersResource(final MyUsersService myUsersService) {
        this.myUsersService = myUsersService;
    }

    @GetMapping
    public ResponseEntity<List<MyUsersDTO>> getAllMyUserss() {
        return ResponseEntity.ok(myUsersService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyUsersDTO> getMyUsers(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(myUsersService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMyUsers(@RequestBody @Valid final MyUsersDTO myUsersDTO) {
        final Long createdId = myUsersService.create(myUsersDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMyUsers(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MyUsersDTO myUsersDTO) {
        myUsersService.update(id, myUsersDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMyUsers(@PathVariable(name = "id") final Long id) {
        myUsersService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
