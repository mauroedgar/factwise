package br.com.livecenografia.fact_wise2.service;

import br.com.livecenografia.fact_wise2.domain.MyRoles;
import br.com.livecenografia.fact_wise2.domain.MyUsers;
import br.com.livecenografia.fact_wise2.model.MyRolesDTO;
import br.com.livecenografia.fact_wise2.repos.MyRolesRepository;
import br.com.livecenografia.fact_wise2.repos.MyUsersRepository;
import br.com.livecenografia.fact_wise2.util.NotFoundException;
import br.com.livecenografia.fact_wise2.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MyRolesService {

    private final MyRolesRepository myRolesRepository;
    private final MyUsersRepository myUsersRepository;

    public MyRolesService(final MyRolesRepository myRolesRepository,
            final MyUsersRepository myUsersRepository) {
        this.myRolesRepository = myRolesRepository;
        this.myUsersRepository = myUsersRepository;
    }

    public List<MyRolesDTO> findAll() {
        final List<MyRoles> myRoleses = myRolesRepository.findAll(Sort.by("id"));
        return myRoleses.stream()
                .map(myRoles -> mapToDTO(myRoles, new MyRolesDTO()))
                .toList();
    }

    public MyRolesDTO get(final Long id) {
        return myRolesRepository.findById(id)
                .map(myRoles -> mapToDTO(myRoles, new MyRolesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MyRolesDTO myRolesDTO) {
        final MyRoles myRoles = new MyRoles();
        mapToEntity(myRolesDTO, myRoles);
        return myRolesRepository.save(myRoles).getId();
    }

    public void update(final Long id, final MyRolesDTO myRolesDTO) {
        final MyRoles myRoles = myRolesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(myRolesDTO, myRoles);
        myRolesRepository.save(myRoles);
    }

    public void delete(final Long id) {
        myRolesRepository.deleteById(id);
    }

    private MyRolesDTO mapToDTO(final MyRoles myRoles, final MyRolesDTO myRolesDTO) {
        myRolesDTO.setId(myRoles.getId());
        myRolesDTO.setRoleName(myRoles.getRoleName());
        return myRolesDTO;
    }

    private MyRoles mapToEntity(final MyRolesDTO myRolesDTO, final MyRoles myRoles) {
        myRoles.setRoleName(myRolesDTO.getRoleName());
        return myRoles;
    }

    public boolean roleNameExists(final String roleName) {
        return myRolesRepository.existsByRoleNameIgnoreCase(roleName);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final MyRoles myRoles = myRolesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final MyUsers rolesMyUsers = myUsersRepository.findFirstByRoles(myRoles);
        if (rolesMyUsers != null) {
            referencedWarning.setKey("myRoles.myUsers.roles.referenced");
            referencedWarning.addParam(rolesMyUsers.getId());
            return referencedWarning;
        }
        return null;
    }

}
