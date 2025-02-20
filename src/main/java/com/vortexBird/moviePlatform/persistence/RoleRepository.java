package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Rol;
import com.vortexBird.moviePlatform.domain.repository.RolRepository;
import com.vortexBird.moviePlatform.persistence.crud.RoleCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Role;
import com.vortexBird.moviePlatform.persistence.mapper.RolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepository implements RolRepository {

    @Autowired
    private RoleCrudRepository rollCrudRepository;

    @Autowired
    private RolMapper rolMapper;

    @Override
    public Optional<List<Rol>> obtenerTodos() {
        return Optional.of(rolMapper.toRoles((List<Role>) rollCrudRepository.findAll()));
    }

    @Override
    public Optional<List<Rol>> obtenerPorCoincidenciaNombre(String matchStr) {
        return rollCrudRepository.searchByNameLike(matchStr).map(rolls -> rolMapper.toRoles(rolls));
    }

    @Override
    public Optional<Rol> obtenerRol(int rolId) {
        return rollCrudRepository.findById(rolId).map(roll -> rolMapper.toRol(roll));
    }

    @Override
    public Optional<Rol> obtenerPorNombre(String nombre) {
        return rollCrudRepository.findByName(nombre).map(roll -> rolMapper.toRol(roll));
    }

    @Override
    public Rol save(Rol rol) {
        return rolMapper.toRol(rollCrudRepository.save(rolMapper.toRole(rol)));
    }

    @Override
    public void delete(int rolId) {
        rollCrudRepository.deleteById(rolId);
    }
}
