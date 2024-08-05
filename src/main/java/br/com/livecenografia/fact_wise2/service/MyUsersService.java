package br.com.livecenografia.fact_wise2.service;

import br.com.livecenografia.fact_wise2.domain.MyApps;
import br.com.livecenografia.fact_wise2.domain.MyRoles;
import br.com.livecenografia.fact_wise2.domain.MyUsers;
import br.com.livecenografia.fact_wise2.model.MyUsersDTO;
import br.com.livecenografia.fact_wise2.repos.MyAppsRepository;
import br.com.livecenografia.fact_wise2.repos.MyRolesRepository;
import br.com.livecenografia.fact_wise2.repos.MyUsersRepository;
import br.com.livecenografia.fact_wise2.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MyUsersService {

    private final MyUsersRepository myUsersRepository;
    private final MyRolesRepository myRolesRepository;
    private final MyAppsRepository myAppsRepository;

    public MyUsersService(final MyUsersRepository myUsersRepository,
            final MyRolesRepository myRolesRepository, final MyAppsRepository myAppsRepository) {
        this.myUsersRepository = myUsersRepository;
        this.myRolesRepository = myRolesRepository;
        this.myAppsRepository = myAppsRepository;
    }

    public List<MyUsersDTO> findAll() {
        final List<MyUsers> myUserses = myUsersRepository.findAll(Sort.by("id"));
        return myUserses.stream()
                .map(myUsers -> mapToDTO(myUsers, new MyUsersDTO()))
                .toList();
    }

    public MyUsersDTO get(final Long id) {
        return myUsersRepository.findById(id)
                .map(myUsers -> mapToDTO(myUsers, new MyUsersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MyUsersDTO myUsersDTO) {
        final MyUsers myUsers = new MyUsers();
        mapToEntity(myUsersDTO, myUsers);
        return myUsersRepository.save(myUsers).getId();
    }

    public void update(final Long id, final MyUsersDTO myUsersDTO) {
        final MyUsers myUsers = myUsersRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(myUsersDTO, myUsers);
        myUsersRepository.save(myUsers);
    }

    public void delete(final Long id) {
        myUsersRepository.deleteById(id);
    }

    private MyUsersDTO mapToDTO(final MyUsers myUsers, final MyUsersDTO myUsersDTO) {
        myUsersDTO.setId(myUsers.getId());
        myUsersDTO.setEmail(myUsers.getEmail());
        myUsersDTO.setPassword(myUsers.getPassword());
        myUsersDTO.setEnabled(myUsers.getEnabled());
        myUsersDTO.setName(myUsers.getName());
        myUsersDTO.setRoles(myUsers.getRoles() == null ? null : myUsers.getRoles().getId());
        myUsersDTO.setApps(myUsers.getApps() == null ? null : myUsers.getApps().getId());
        return myUsersDTO;
    }

    private MyUsers mapToEntity(final MyUsersDTO myUsersDTO, final MyUsers myUsers) {
        myUsers.setEmail(myUsersDTO.getEmail());
        myUsers.setPassword(myUsersDTO.getPassword());
        myUsers.setEnabled(myUsersDTO.getEnabled());
        myUsers.setName(myUsersDTO.getName());
        final MyRoles roles = myUsersDTO.getRoles() == null ? null : myRolesRepository.findById(myUsersDTO.getRoles())
                .orElseThrow(() -> new NotFoundException("roles not found"));
        myUsers.setRoles(roles);
        final MyApps apps = myUsersDTO.getApps() == null ? null : myAppsRepository.findById(myUsersDTO.getApps())
                .orElseThrow(() -> new NotFoundException("apps not found"));
        myUsers.setApps(apps);
        return myUsers;
    }

    public boolean emailExists(final String email) {
        return myUsersRepository.existsByEmailIgnoreCase(email);
    }

}
